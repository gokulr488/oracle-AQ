package OracleDBQueueTest;

import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.aq.AQDequeueOptions;
import oracle.jdbc.aq.AQMessage;
import oracle.jdbc.aq.AQMessageProperties;
import oracle.xdb.XMLType;

public class Consumer {

	public static void main(String[] args) throws SQLException {
		System.out.println("Starting...");
		Consumer consumer = new Consumer();
		OracleConnection connection = consumer.connect();
		connection.setAutoCommit(false);
		String queueName="SVC_ESB.SIXDQUEUE";
		String queueType="XMLTYPE";
		String consumerName="BLUE";
		consumer.dequeue(connection, queueName, queueType, consumerName);

	}

	public void dequeue(OracleConnection conn, String queueName, String queueType, String consumerName)
			throws SQLException {
		System.out.println("----------- Dequeue start ------------");
		AQDequeueOptions deqopt = new AQDequeueOptions();
		deqopt.setRetrieveMessageId(true);
		
		if (consumerName != null) {
			deqopt.setConsumerName(consumerName);
			
			//deqopt.setCorrelation("Test_Correlation");
			
		}
		// dequeue operation:
		AQMessage msg = conn.dequeue(queueName, deqopt, queueType);
		System.out.println("dequed and received");

		// print out the message that has been dequeued:
		XMLType xmlPayload = msg.getXMLTypePayload();
		byte[] payload = msg.getPayload();
		byte[] msgId = msg.getMessageId();
		if (msgId != null) {
			String mesgIdStr = byteBufferToHexString(msgId, 20);
			System.out.println("ID of message dequeued = " + mesgIdStr);
		}
		AQMessageProperties msgProp = msg.getMessageProperties();
		System.out.println(msgProp.toString());
		String payloadStr = new String(payload, 0, 10);
		System.out.println("payload.length=" + payload.length + ", value=" + xmlPayload.getStringVal());
		System.out.println("----------- Dequeue done ------------");
	}

	OracleConnection connect() throws SQLException {
		String username="SVC_ESB";
		String password="VoAQ12wsza#12317jem";
		String url="jdbc:oracle:thin:@localhost:1521:crmtst";
		OracleDriver dr = new OracleDriver();
		Properties prop = new Properties();
		prop.setProperty("user", username);
		prop.setProperty("password", password);
		System.out.println("Connecting with username= " + username);
		System.out.println("password= " + password);
		System.out.println("URL= " + url);
		return (OracleConnection) dr.connect(url, prop);
	}

	String byteBufferToHexString(byte[] buffer, int maxNbOfBytes) {
		if (buffer == null)
			return null;
		int offset = 0;
		boolean isFirst = true;
		StringBuffer sb = new StringBuffer();
		while (offset < buffer.length && offset < maxNbOfBytes) {
			if (!isFirst)
				sb.append(' ');
			else
				isFirst = false;
			String hexrep = Integer.toHexString((int) buffer[offset] & 0xFF);
			if (hexrep.length() == 1)
				hexrep = "0" + hexrep;
			sb.append(hexrep);
			offset++;
		}
		String ret = sb.toString();
		return ret;
	}

}