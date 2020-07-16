package objectQDQ;

import java.sql.SQLException;
import java.util.Properties;

import OracleDBQueueTest.AppConfigs;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.aq.AQDequeueOptions;
import oracle.jdbc.aq.AQMessage;
import oracle.jdbc.aq.AQMessageProperties;
import oracle.sql.STRUCT;
import oracle.xdb.XMLType;

public class ObjectDequeuHandler {

	private OracleConnection conn;

	public ObjectDequeuHandler() {
		try {
			getConnection();
			System.out.println("Enqueue Handler Connected to DB");
		} catch (SQLException e) {
			System.out.println("Enqueue Handler Could not connect to DB");
			e.printStackTrace();
		}
	}

	public void dequeueData(String consumerName, String queueName, String queueType) throws SQLException {
		System.out.println("----------- Dequeue start ------------");
		AQDequeueOptions deqopt = new AQDequeueOptions();
		deqopt.setRetrieveMessageId(true);

		if (consumerName != null) {
			deqopt.setConsumerName(consumerName);
		}
		// dequeue operation:
		AQMessage msg = conn.dequeue(queueName, deqopt, queueType);
		System.out.println("dequed and received");

		// print out the message that has been dequeued:
		STRUCT objectPayLoad = msg.getSTRUCTPayload();
		XMLType xmlPayload = null;

		AQMessageProperties msgProp = msg.getMessageProperties();
		System.out.println(msgProp.toString());

		System.out.println("payload.length=" + xmlPayload.getLength() + ", value=" + xmlPayload.getStringVal());
		System.out.println("----------- Dequeue done ------------");
	}

	public void closeConnection() {
		try {
			conn.close();
			System.out.println("Enque Handler closed connection");
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void getConnection() throws SQLException {
		OracleDriver dr = new OracleDriver();
		Properties prop = new Properties();
		prop.setProperty("user", AppConfigs.username);
		prop.setProperty("password", AppConfigs.password);
		System.out.println("Connecting with username= " + AppConfigs.username);
		System.out.println("password= " + AppConfigs.password);
		System.out.println("URL= " + AppConfigs.url);
		conn = (OracleConnection) dr.connect(AppConfigs.url, prop);
		conn.setAutoCommit(false);
	}

}
