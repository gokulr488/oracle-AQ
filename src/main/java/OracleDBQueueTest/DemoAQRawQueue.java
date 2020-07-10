package OracleDBQueueTest;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.aq.AQAgent;
import oracle.jdbc.aq.AQDequeueOptions;
import oracle.jdbc.aq.AQEnqueueOptions;
import oracle.jdbc.aq.AQFactory;
import oracle.jdbc.aq.AQMessage;
import oracle.jdbc.aq.AQMessageProperties;

public class DemoAQRawQueue {

	public DemoAQRawQueue() throws InterruptedException {
		try {
			
			System.out.println("Starting with deque mode= " + AppConfigs.dequeMode);
			this.run();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	static String USERNAME;
	static String PASSWORD;
	static String URL;

	void run() throws SQLException, InterruptedException {
		USERNAME = AppConfigs.username;
		PASSWORD = AppConfigs.password;
		URL = AppConfigs.url;
		OracleConnection connection = connect();
		connection.setAutoCommit(false);
		//setup(connection);
		if (!AppConfigs.dequeMode && AppConfigs.cleanUp) {
			cleanup(connection);
			System.out.println("cleaned");
			setup(connection);
		}

		// run the demo for single consumer queue:
		demoSingleConsumerQueue(connection);
		// run the demo for multi consumer queue:
		// demoMultipleConsumerQueue(connection);
		connection.close();
	}

	/**
	 * Single consumer queue demo: This method enqueues a dummy message into the
	 * SINGLE_QUEUE queue and dequeues it. Then it registers for AQ notification
	 * on this same queue and enqueues a message again. The AQ listener will be
	 * notified that a new message has arrived in the queue and it will dequeue it.
	 * 
	 * @throws InterruptedException
	 */
	void demoSingleConsumerQueue(OracleConnection connection) throws SQLException, InterruptedException {
		System.out.println("\n ============= Start single consumer queue demo ============= \n");

		String queueType = "RAW";
	    String queueName = USERNAME+".TEST_QUEUE";
		
		if (AppConfigs.dequeMode) {
			do{
				dequeue(connection, queueName, queueType, null);
				Thread.sleep(1000);
			}while(AppConfigs.runmode.equals("loop"));

		} else {
			do{
				enqueueDummyMessage(connection, queueName, null);
				Thread.sleep(2000);
				
			}while(AppConfigs.runmode.equals("loop"));
		}

	}

	/**
	 * This method enqueues a dummy message in the queue specified by its name.
	 */
	public void enqueueDummyMessage(OracleConnection conn, String queueName, AQAgent[] recipients) throws SQLException {
		System.out.println("----------- Enqueue start ------------");
		// First create the message properties:
		AQMessageProperties msgprop = AQFactory.createAQMessageProperties();
		msgprop.setCorrelation("mycorrelation");
		msgprop.setExceptionQueue("MY_EXCEPTION_QUEUE");

		// Specify an agent as the sender:
		AQAgent ag = AQFactory.createAQAgent();
		ag.setName("MY_SENDER_AGENT_NAME");
		ag.setAddress("MY_SENDER_AGENT_ADDRESS");
		msgprop.setSender(ag);

		// handle multi consumer case:
		if (recipients != null)
			msgprop.setRecipientList(recipients);

		System.out.println(msgprop.toString());

		// Create the actual AQMessage instance:
		AQMessage mesg = AQFactory.createAQMessage(msgprop);
		// and add a payload:
		byte[] rawPayload = new byte[500];
		for (int i = 0; i < rawPayload.length; i++)
			rawPayload[i] = 'b';
		mesg.setPayload(new oracle.sql.RAW(rawPayload));

		// We want to retrieve the message id after enqueue:
		AQEnqueueOptions opt = new AQEnqueueOptions();
		opt.setRetrieveMessageId(true);

		// execute the actual enqueue operation:
		conn.enqueue(queueName, opt, mesg);

		byte[] mesgId = mesg.getMessageId();

		if (mesgId != null) {
			String mesgIdStr = byteBufferToHexString(mesgId, 20);
			System.out.println("Message ID from enqueue call: " + mesgIdStr);
		}
		System.out.println("----------- Enqueue done ------------");
	}

	/**
	 * This methods dequeues the next available message from the queue specified by
	 * "queueName".
	 */
	public void dequeue(OracleConnection conn, String queueName, String queueType, String consumerName)
			throws SQLException {
		System.out.println("----------- Dequeue start ------------");
		AQDequeueOptions deqopt = new AQDequeueOptions();
		deqopt.setRetrieveMessageId(true);
		if (consumerName != null)
			deqopt.setConsumerName(consumerName);

		// dequeue operation:
		AQMessage msg = conn.dequeue(queueName, deqopt, queueType);

		// print out the message that has been dequeued:
		byte[] payload = msg.getPayload();
		byte[] msgId = msg.getMessageId();
		if (msgId != null) {
			String mesgIdStr = byteBufferToHexString(msgId, 20);
			System.out.println("ID of message dequeued = " + mesgIdStr);
		}
		AQMessageProperties msgProp = msg.getMessageProperties();
		System.out.println(msgProp.toString());
		String payloadStr = new String(payload, 0, 10);
		System.out.println("payload.length=" + payload.length + ", value=" + payloadStr);
		System.out.println("----------- Dequeue done ------------");
	}

	private void setup(Connection conn) throws SQLException {
	    
	    doUpdateDatabase(conn,
	               "BEGIN "+
	               "DBMS_AQADM.CREATE_QUEUE_TABLE( "+
	               "   QUEUE_TABLE        =>  '"+USERNAME+".TEST_QUEUE_TABLE',  "+
	               "   QUEUE_PAYLOAD_TYPE =>  'RAW', "+
	               "   COMPATIBLE         =>  '10.0'); "+
	               "END; ");
	    doUpdateDatabase(conn,
	               "BEGIN "+
	               "DBMS_AQADM.CREATE_QUEUE( "+
	               "    QUEUE_NAME     =>   '"+USERNAME+".TEST_QUEUE', "+
	               "    QUEUE_TABLE    =>   '"+USERNAME+".TEST_QUEUE_TABLE'); "+
	               "END;  ");
	    doUpdateDatabase(conn,
	               "BEGIN "+
	               "  DBMS_AQADM.START_QUEUE('"+USERNAME+".TEST_QUEUE'); "+
	               "END; ");
	  }

	void cleanup(Connection conn) {
		
		{
		    tryUpdateDatabase(conn,
		                "BEGIN "+
		                "  DBMS_AQADM.STOP_QUEUE('"+USERNAME+".TEST_QUEUE'); "+
		                "END; "); 
		    tryUpdateDatabase(conn,
		                "BEGIN "+
		                "  DBMS_AQADM.DROP_QUEUE_TABLE( "+
		                "    QUEUE_TABLE         => '"+USERNAME+".TEST_QUEUE_TABLE', "+
		                "    FORCE               => TRUE); "+
		                "END; "); 
		  }
	}

	/**
	 * Creates a connection the database.
	 */
	OracleConnection connect() throws SQLException {
		OracleDriver dr = new OracleDriver();
		Properties prop = new Properties();
		prop.setProperty("user", DemoAQRawQueue.USERNAME);
		prop.setProperty("password", DemoAQRawQueue.PASSWORD);
		System.out.println("Connecting with username= " + DemoAQRawQueue.USERNAME);
		System.out.println("password= " + DemoAQRawQueue.PASSWORD);
		System.out.println("URL= " + DemoAQRawQueue.URL);
		return (OracleConnection) dr.connect(DemoAQRawQueue.URL, prop);
	}

	/**
	 * Utility method: executes a DML query but doesn't throw any exception if an
	 * error occurs.
	 */
	private void tryUpdateDatabase(Connection conn, String sql) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException sqlex) {
			System.out.println("Exception (" + sqlex.getMessage() + ") while trying to execute \"" + sql + "\"");
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException exx) {
					exx.printStackTrace();
				}
			}
		}
	}

	/**
	 * Utility method: executes a DML query and throws an exception if an error
	 * occurs.
	 */
	private void doUpdateDatabase(Connection conn, String sql) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException exx) {
				}
			}
		}
	}

	static final String byteBufferToHexString(byte[] buffer, int maxNbOfBytes) {
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
