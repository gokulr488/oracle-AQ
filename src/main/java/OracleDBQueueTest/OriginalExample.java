package OracleDBQueueTest;

import java.sql.*;
import java.util.Properties;
import oracle.jdbc.*;
import oracle.jdbc.aq.*;
import oracle.xdb.XMLType;

public class OriginalExample {
	static final String USERNAME = "SVC_ESB";
	static final String PASSWORD = "VoAQ12wsza#12317jem";
	static final String URL = "jdbc:oracle:thin:@localhost:1521:crmtst";

	public static final void main(String[] argv) {
		OriginalExample demo = new OriginalExample();
		try {
			demo.run();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	void run() throws SQLException {
		OracleConnection connection = connect();
		connection.setAutoCommit(false);
		//cleanup(connection);
		//setup(connection);
		// run the demo for single consumer queue:
		//demoSingleConsumerQueue(connection);
		// run the demo for multi consumer queue:
		demoMultipleConsumerQueue(connection);
		connection.close();
	}

	/**
	 * Single consumer queue demo: This method enqueues a dummy message into the
	 * RAW_SINGLE_QUEUE queue and dequeues it. Then it registers for AQ notification
	 * on this same queue and enqueues a message again. The AQ listener will be
	 * notified that a new message has arrived in the queue and it will dequeue it.
	 */
	void demoSingleConsumerQueue(OracleConnection connection) throws SQLException {
		System.out.println("\n ============= Start single consumer queue demo ============= \n");

		String queueType = "RAW";
		String queueName = USERNAME + ".RAW_SINGLE_QUEUE";

		enqueueDummyMessage(connection, queueName, null);
		dequeue(connection, queueName, queueType, null);

		AQNotificationRegistration reg = registerForAQEvents(connection, queueName);
		//DemoAQRawQueueListener single_li = new DemoAQRawQueueListener(queueName, queueType);
		//reg.addListener(single_li);

		enqueueDummyMessage(connection, queueName, null);
		connection.commit();

		//while (single_li.getEventsCount() < 1) {
		//	try {
		//		Thread.currentThread().sleep(1000);
		//	} catch (InterruptedException e) {
		//	}
		//}
		//single_li.closeConnection();
		connection.unregisterAQNotification(reg);
	}

	/**
	 * Multi consumer queue demo: This method first registers for AQ notification
	 * upon the agent "BLUE". It then enqueues a message for "RED" and "BLUE"
	 */
	void demoMultipleConsumerQueue(OracleConnection connection) throws SQLException {
		System.out.println("\n ============= Start multi consumer queue demo ============= \n");
		String queueType = "XMLTYPE";
		String queueName = USERNAME + ".RAW_MULTIPLE_QUEUE";

		//AQNotificationRegistration reg = registerForAQEvents(connection, queueName + ":BLUE");
		//DemoAQRawQueueListener multi_li = new DemoAQRawQueueListener(queueName, queueType);
		//reg.addListener(multi_li);
		AQAgent[] recipients = new AQAgent[2];
		recipients[0] = AQFactory.createAQAgent();
		recipients[0].setName("BLUE");
		recipients[1] = AQFactory.createAQAgent();
		recipients[1].setName("RED");

		enqueueDummyMessage(connection, queueName, recipients);
		connection.commit();

//		while (multi_li.getEventsCount() < 1) {
//			try {
//				Thread.currentThread().sleep(1000);
//			} catch (InterruptedException e) {
//			}
//		}

		dequeue(connection, queueName, queueType, "RED");

		//multi_li.closeConnection();
		//connection.unregisterAQNotification(reg);
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
		String xmlPayLoad="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\r\n" + 
				"<ROWSET>\r\n" + 
				"  <ROW>\r\n" + 
				"    <XML_VERSION>0.010</XML_VERSION>\r\n" + 
				"    <T_SUB_EVN>\r\n" + 
				"      <EVENT_TYPE>PSWSIM</EVENT_TYPE>\r\n" + 
				"      <BUS_EVENT_TYPE/>\r\n" + 
				"      <EVENT_SEQ/>\r\n" + 
				"      <REQUEST_SEQ/>\r\n" + 
				"      <EVENT_DATE>20200501 21:59:32</EVENT_DATE>\r\n" + 
				"      <ICAP_ID/>\r\n" + 
				"      <CUSTOMER_ID/>\r\n" + 
				"      <ICAP_EVENT_SEQ/>\r\n" + 
				"      <REF_NO>CRMSSW_20</REF_NO>\r\n" + 
				"      <EVN_ATR_LIST>\r\n" + 
				"        <T_EVN_ATR>\r\n" + 
				"          <ORIGIN_ID>6D</ORIGIN_ID>\r\n" + 
				"          <EVENT_MSISDN>255745757351</EVENT_MSISDN>\r\n" + 
				"          <OLD_ICC_ID>8925504100000024392</OLD_ICC_ID>\r\n" + 
				"          <NEW_ICC_ID>8925504200401457803</NEW_ICC_ID>\r\n" + 
				"        </T_EVN_ATR>\r\n" + 
				"      </EVN_ATR_LIST>\r\n" + 
				"    </T_SUB_EVN>\r\n" + 
				"  </ROW>\r\n" + 
				"</ROWSET>\r\n" + 
				"";
		
		
		byte[] rawPayload = new byte[500];
		for (int i = 0; i < rawPayload.length; i++)
			rawPayload[i] = 'b';
		//mesg.setPayload(new oracle.sql.RAW(rawPayload));
		mesg.setPayload(new XMLType(conn, xmlPayLoad));

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
		queueName="SVC_ESB.SIXDQUEUE";
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
		String payloadStr = new String(payload);
		System.out.println("payload.length=" + payload.length + ", value=" + payloadStr);
		System.out.println("----------- Dequeue done ------------");
	}

	public AQNotificationRegistration registerForAQEvents(OracleConnection conn, String queueName) throws SQLException {
		Properties globalOptions = new Properties();
		String[] queueNameArr = new String[1];
		queueNameArr[0] = queueName;
		Properties[] opt = new Properties[1];
		opt[0] = new Properties();
		opt[0].setProperty(OracleConnection.NTF_AQ_PAYLOAD, "true");
		AQNotificationRegistration[] regArr = conn.registerAQNotification(queueNameArr, opt, globalOptions);
		AQNotificationRegistration reg = regArr[0];
		return reg;
	}

	/**
	 * 
	 */
	private void setup(Connection conn) throws SQLException {
//
//		doUpdateDatabase(conn,
//				"BEGIN " + "DBMS_AQADM.CREATE_QUEUE_TABLE( " + "   QUEUE_TABLE        =>  '" + USERNAME
//						+ ".RAW_SINGLE_QUEUE_TABLE',  " + "   QUEUE_PAYLOAD_TYPE =>  'RAW', "
//						+ "   COMPATIBLE         =>  '10.0'); " + "END; ");
//		doUpdateDatabase(conn,
//				"BEGIN " + "DBMS_AQADM.CREATE_QUEUE( " + "    QUEUE_NAME     =>   '" + USERNAME + ".RAW_SINGLE_QUEUE', "
//						+ "    QUEUE_TABLE    =>   '" + USERNAME + ".RAW_SINGLE_QUEUE_TABLE'); " + "END;  ");
//		doUpdateDatabase(conn, "BEGIN " + "  DBMS_AQADM.START_QUEUE('" + USERNAME + ".RAW_SINGLE_QUEUE'); " + "END; ");

		// create a multi consumer RAW queue:
		doUpdateDatabase(conn,
				"BEGIN " + "DBMS_AQADM.CREATE_QUEUE_TABLE( " + "   QUEUE_TABLE        =>  '" + USERNAME
						+ ".RAW_MULTIPLE_QUEUE_TABLE',  " + "   QUEUE_PAYLOAD_TYPE =>  'SYS.XMLTYPE', "
						+ "   MULTIPLE_CONSUMERS =>  TRUE, " + "   COMPATIBLE         =>  '10.0'); " + "END; ");
		doUpdateDatabase(conn,
				"BEGIN " + "DBMS_AQADM.CREATE_QUEUE( " + "    QUEUE_NAME     =>   '" + USERNAME
						+ ".RAW_MULTIPLE_QUEUE', " + "    QUEUE_TABLE    =>   '" + USERNAME
						+ ".RAW_MULTIPLE_QUEUE_TABLE'); " + "END;  ");
		doUpdateDatabase(conn,
				"BEGIN " + "  DBMS_AQADM.START_QUEUE('" + USERNAME + ".RAW_MULTIPLE_QUEUE'); " + "END; ");
	}

	void cleanup(Connection conn) {
//		tryUpdateDatabase(conn, "BEGIN " + "  DBMS_AQADM.STOP_QUEUE('" + USERNAME + ".RAW_SINGLE_QUEUE'); " + "END; ");
//		tryUpdateDatabase(conn, "BEGIN " + "  DBMS_AQADM.DROP_QUEUE_TABLE( " + "    QUEUE_TABLE         => '" + USERNAME
//				+ ".RAW_SINGLE_QUEUE_TABLE', " + "    FORCE               => TRUE); " + "END; ");

		tryUpdateDatabase(conn,
				"BEGIN " + "  DBMS_AQADM.STOP_QUEUE('" + USERNAME + ".RAW_MULTIPLE_QUEUE'); " + "END; ");
		tryUpdateDatabase(conn, "BEGIN " + "  DBMS_AQADM.DROP_QUEUE_TABLE( " + "    QUEUE_TABLE         => '" + USERNAME
				+ ".RAW_MULTIPLE_QUEUE_TABLE', " + "    FORCE               => TRUE); " + "END; ");
	}

	/**
	 * Creates a connection the database.
	 */
	OracleConnection connect() throws SQLException {
		OracleDriver dr = new OracleDriver();
		Properties prop = new Properties();
		prop.setProperty("user", OriginalExample.USERNAME);
		prop.setProperty("password", OriginalExample.PASSWORD);
		return (OracleConnection) dr.connect(OriginalExample.URL, prop);
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

//class DemoAQRawQueueListener implements AQNotificationListener {
//	OracleConnection conn;
//	String queueName;
//	String typeName;
//	int eventsCount = 0;
//
//	public DemoAQRawQueueListener(String _queueName, String _typeName) throws SQLException {
//		queueName = _queueName;
//		typeName = _typeName;
//		conn = (OracleConnection) DriverManager.getConnection(OriginalExample.URL, OriginalExample.USERNAME,
//				OriginalExample.PASSWORD);
//	}
//
//	public void onAQNotification(AQNotificationEvent e) {
//		System.out.println("\n----------- DemoAQRawQueueListener: got an event ----------- ");
//		System.out.println(e.toString());
//		System.out.println("------------------------------------------------------");
//
//		System.out.println("----------- DemoAQRawQueueListener: Dequeue start -----------");
//		try {
//			AQDequeueOptions deqopt = new AQDequeueOptions();
//			deqopt.setRetrieveMessageId(true);
//			if (e.getConsumerName() != null)
//				deqopt.setConsumerName(e.getConsumerName());
//			if ((e.getMessageProperties()).getDeliveryMode() == AQMessageProperties.MESSAGE_BUFFERED) {
//				deqopt.setDeliveryMode(AQDequeueOptions.DEQUEUE_BUFFERED);
//				deqopt.setVisibility(AQDequeueOptions.DEQUEUE_IMMEDIATE);
//			}
//			AQMessage msg = conn.dequeue(queueName, deqopt, typeName);
//			byte[] msgId = msg.getMessageId();
//			if (msgId != null) {
//				String mesgIdStr = OriginalExample.byteBufferToHexString(msgId, 20);
//				System.out.println("ID of message dequeued = " + mesgIdStr);
//			}
//			System.out.println(msg.getMessageProperties().toString());
//			byte[] payload = msg.getPayload();
//			if (typeName.equals("RAW")) {
//				String payloadStr = new String(payload, 0, 10);
//				System.out.println("payload.length=" + payload.length + ", value=" + payloadStr);
//			}
//			System.out.println("----------- DemoAQRawQueueListener: Dequeue done -----------");
//		} catch (SQLException sqlex) {
//			System.out.println(sqlex.getMessage());
//		}
//		eventsCount++;
//	}
//
//	public int getEventsCount() {
//		return eventsCount;
//	}
//
//	public void closeConnection() throws SQLException {
//		conn.close();
//	}
//}