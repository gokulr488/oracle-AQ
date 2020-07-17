package objectQDQ;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Hashtable;
import java.util.Properties;

import OracleDBQueueTest.AppConfigs;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.aq.AQAgent;
import oracle.jdbc.aq.AQEnqueueOptions;
import oracle.jdbc.aq.AQFactory;
import oracle.jdbc.aq.AQMessage;
import oracle.jdbc.aq.AQMessageProperties;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class ObjectEnqueueHandler {

	private OracleConnection conn;
	private Hashtable map;

	public ObjectEnqueueHandler() {
		try {
			getConnection();
			System.out.println("Enqueue Handler Connected to DB");
		} catch (SQLException e) {
			System.out.println("Enqueue Handler Could not connect to DB");
			e.printStackTrace();
		}
	}

	public void enqueuData(EventObj data) throws SQLException {
		System.out.println("----------- Enqueue start ------------");

		try {
			map = (java.util.Hashtable) conn.getTypeMap();
			map.put("SVC_ESB.T_ICAP_EVENT", Class.forName("objectQDQ.EventObj"));
		} catch (Exception ex) {
			System.out.println("Error registering type: " + ex);
		}

		// First create the message properties:
		AQMessageProperties msgprop = AQFactory.createAQMessageProperties();
		msgprop.setCorrelation("mycorrelation");
		msgprop.setExceptionQueue("MY_EXCEPTION_QUEUE");

		// Specify an agent as the sender:
		AQAgent ag = AQFactory.createAQAgent();
		ag.setName("MY_SENDER_AGENT_NAME");
		ag.setAddress("MY_SENDER_AGENT_ADDRESS");
		msgprop.setSender(ag);
		AQAgent[] recipients = new AQAgent[2];

		// Add recipients
		recipients[0] = AQFactory.createAQAgent();
		recipients[0].setName("BLUE");
		recipients[1] = AQFactory.createAQAgent();
		recipients[1].setName("RED");

		// handle multi consumer case:
		if (recipients != null)
			msgprop.setRecipientList(recipients);

		System.out.println(msgprop.toString());

		// Create the actual AQMessage instance:
		AQMessage mesg = AQFactory.createAQMessage(msgprop);
		// and add a payload:

		// AQObjectPayload payload=mesg.get

//		 dummy = new Dummy();
//		 dummy.setEVENT_SEQ(eventSequence);
//		 dummy.setEVENT_STRING(new OracleAQBLOBUtil().createClob(""+xmlString,connection));
//		 dummy.setEVENT_TIMESTAMP(new Date(System.currentTimeMillis()));
//		 dummy.setEVENT_TYPE("SIMSWA");
//		 dummy.setICAP_ID(eventSequence);
		StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("T_ICAP_EVENT", conn);

		Object[] itemAtributes = new Object[] { data.getEventSeq(), data.getEventTimeStamp(), data.getEventType(),
				data.getIcapId(), data.getEventString() };

		STRUCT itemObject2 = new STRUCT(itemDescriptor, conn, itemAtributes);

		// Object[] attributes = { data };

		// Struct struct = conn.createStruct("SVC_ESB.event_obj", attributes);// new
		// STRUCT(structDescriptor, connection,
		// payloadMap);
		// aqMessage.setPayload(struct);

		// AQObjectPayload payload = null;
		mesg.setPayload(itemObject2);
		;

		// We want to retrieve the message id after enqueue:
		AQEnqueueOptions opt = new AQEnqueueOptions();
		opt.setRetrieveMessageId(true);

		// execute the actual enqueue operation:
		conn.enqueue("SixDQueue", opt, mesg);

		byte[] mesgId = mesg.getMessageId();

		if (mesgId != null) {
			String mesgIdStr = byteBufferToHexString(mesgId, 20);
			System.out.println("Message ID from enqueue call: " + mesgIdStr);
		}
		System.out.println("----------- Enqueue done ------------");
	}

	public String byteBufferToHexString(byte[] buffer, int maxNbOfBytes) {
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

	public void initialiseTables() throws SQLException {
//				CREATE type event_obj as object (
//				EVENT_SEQ                                          NUMBER(10)
//				 EVENT_TIMESTAMP                                    DATE
//				 EVENT_TYPE                                         VARCHAR2(6)
//				 ICAP_ID                                            NUMBER(10)
//				 EVENT_STRING                                       CLOB);   
//
//
//				/* Creating a object type queue table and queue: */
//				EXECUTE DBMS_AQADM.CREATE_QUEUE_TABLE (
//				queue_table        => 'SVC_ESB.event_queue_table',
//				queue_payload_type => 'SVC_ESB.event_obj');
//
//				EXECUTE DBMS_AQADM.CREATE_QUEUE (
//				queue_name         => 'event_object_queue',
//				queue_table        => 'SVC_ESB.event_queue_table');
//
//				EXECUTE DBMS_AQADM.START_QUEUE (
//				queue_name         => 'event_object_queue');
		tryUpdateDatabase(conn, "DROP TYPE SVC_ESB.T_ICAP_EVENT;");
		tryUpdateDatabase(conn, "BEGIN " + "  DBMS_AQADM.STOP_QUEUE('SVC_ESB.SIXDQUEUE'); " + "END; ");
		tryUpdateDatabase(conn,
				"BEGIN " + "  DBMS_AQADM.DROP_QUEUE_TABLE( "
						+ "    QUEUE_TABLE         => 'SVC_ESB.SIXDQUEUETABLE', "
						+ "    FORCE               => TRUE); " + "END; ");

		doUpdateDatabase(conn,
				"CREATE type SVC_ESB.T_ICAP_EVENT as object (\r\n"
						+ "EVENT_SEQ                                          NUMBER(10),\r\n"
						+ " EVENT_TIMESTAMP                                    DATE,\r\n"
						+ " EVENT_TYPE                                         VARCHAR2(6),\r\n"
						+ " ICAP_ID                                            NUMBER(10),\r\n"
						+ " EVENT_STRING                                       CLOB);  ");

		doUpdateDatabase(conn,
				"BEGIN\r\n" + "       DBMS_AQADM.CREATE_QUEUE_TABLE(\r\n"
						+ "            QUEUE_TABLE         =>  'SVC_ESB.SIXDQUEUETABLE',\r\n"
						+ "            multiple_consumers  => TRUE,    \r\n"
						+ "            QUEUE_PAYLOAD_TYPE  =>  'SVC_ESB.T_ICAP_EVENT',\r\n"
						+ "            COMPATIBLE          =>  '10.0' \r\n" + "\r\n" + "            ); \r\n" + " END;");
		doUpdateDatabase(conn, " BEGIN DBMS_AQADM.CREATE_QUEUE (\r\n" + "queue_name         => 'SixDQueue',\r\n"
				+ "queue_table        => 'SVC_ESB.SIXDQUEUETABLE');END;");
		doUpdateDatabase(conn, "BEGIN DBMS_AQADM.START_QUEUE (\r\n" + "queue_name         => 'SixDQueue');END;");

	}

	public EventObj populateData() throws Exception {
		EventObj data = new EventObj();
		data.setEventSeq(1);

		String xmlPayLoad = "<?xml version=\"1.0\"?>\r\n" + "<ROWSET>\r\n" + "	<ROW>\r\n"
				+ "		<XML_VERSION>0.010</XML_VERSION>\r\n" + "		<T_SUB_EVN>\r\n"
				+ "			<EVENT_TYPE>SIMSWP</EVENT_TYPE>\r\n" + "			<EVENT_SEQ>1</EVENT_SEQ>\r\n"
				+ "			<REQUEST_SEQ>2</REQUEST_SEQ>\r\n"
				+ "			<EVENT_DATE>2020-06-28 12:02:00</EVENT_DATE>\r\n" + "			<ICAP_ID>50</ICAP_ID>\r\n"
				+ "			<CUSTOMER_ID></CUSTOMER_ID>\r\n" + "			<ICAP_EVENT_SEQ></ICAP_EVENT_SEQ>\r\n"
				+ "			<EVN_ATR_LIST>\r\n" + "				<T_EVN_ATR>\r\n"
				+ "					<ORIGIN_ID>6D</ORIGIN_ID>\r\n" + "					<OFFER_ID></OFFER_ID>\r\n"
				+ "					<OFFER_GROUP></OFFER_GROUP>\r\n"
				+ "					<EVENT_MSISDN>255745757350</EVENT_MSISDN>\r\n"
				+ "                    <OLD_ICC_ID>8925504100000024</OLD_ICC_ID>\r\n"
				+ "                    <NEW_ICC_ID>8925504200401457806</NEW_ICC_ID>\r\n"
				+ "				</T_EVN_ATR>\r\n" + "			</EVN_ATR_LIST>\r\n" + "			<SUB_ATR_LIST>\r\n"
				+ "				<T_SUB_ATR>\r\n" + "					<MSIN></MSIN>\r\n"
				+ "					<ICC_ID></ICC_ID>\r\n" + "					<T11_MSISDN></T11_MSISDN>\r\n"
				+ "					<ACTIVATION_DATE></ACTIVATION_DATE>\r\n"
				+ "					<CONNECTION_DATE></CONNECTION_DATE>\r\n"
				+ "					<OFFER_START_DATE></OFFER_START_DATE>\r\n"
				+ "					<GSM_OFFER_ID_ORG></GSM_OFFER_ID_ORG>\r\n"
				+ "					<GSM_OFFER_ID></GSM_OFFER_ID>\r\n"
				+ "					<SP_REF_KEY></SP_REF_KEY>\r\n"
				+ "					<PAYMENT_METHOD></PAYMENT_METHOD>\r\n" + "					<CLASS></CLASS>\r\n"
				+ "					<TWC_STATUS></TWC_STATUS>\r\n"
				+ "					<HLR_NUMBERING_TYPE>S</HLR_NUMBERING_TYPE>\r\n"
				+ "					<ADMIN_LOCK></ADMIN_LOCK>\r\n" + "					<STOLEN_LOCK></STOLEN_LOCK>\r\n"
				+ "					<CLL_STATUS></CLL_STATUS>\r\n"
				+ "					<SUBSCRIBER_STATUS></SUBSCRIBER_STATUS>\r\n"
				+ "					<RICA_STATUS></RICA_STATUS>\r\n" + "					<RICA_DATE></RICA_DATE>\r\n"
				+ "					<RICA_LOCK></RICA_LOCK>\r\n"
				+ "					<ORIGINAL_ICC_ID></ORIGINAL_ICC_ID>\r\n" + "					<SUB_CTG_LIST>\r\n"
				+ "						<T_SUB_CTG>\r\n"
				+ "							<CATEGORY_TYPE></CATEGORY_TYPE>\r\n"
				+ "							<CATEGORY></CATEGORY>\r\n"
				+ "							<DATE_FROM></DATE_FROM>\r\n"
				+ "						</T_SUB_CTG>						\r\n"
				+ "					</SUB_CTG_LIST>\r\n" + "					<COMMISSION_SEQ></COMMISSION_SEQ>\r\n"
				+ "					<COMMISSION_CODE></COMMISSION_CODE>\r\n"
				+ "					<COMMISSION_POINT></COMMISSION_POINT>\r\n" + "				</T_SUB_ATR>\r\n"
				+ "			</SUB_ATR_LIST>\r\n" + "			<SUB_SRV_LIST>\r\n" + "				<T_SUB_SRV>\r\n"
				+ "					<O_ID></O_ID>\r\n" + "					<ST_ID></ST_ID>\r\n"
				+ "					<SO_ID></SO_ID>\r\n" + "					<DATE_FROM></DATE_FROM>\r\n"
				+ "					<SUB_SRV_ID_LIST>\r\n" + "						<T_SRV_ID>\r\n"
				+ "							<ID_TYPE></ID_TYPE>\r\n" + "							<ID></ID>\r\n"
				+ "							<START_DATE></START_DATE>\r\n"
				+ "						</T_SRV_ID>						\r\n"
				+ "					</SUB_SRV_ID_LIST>\r\n" + "					<SUB_SRV_NE_LIST>\r\n"
				+ "						<T_SRV_NE>\r\n" + "							<NE_TYPE></NE_TYPE>\r\n"
				+ "							<NE_ID></NE_ID>\r\n" + "						</T_SRV_NE>\r\n"
				+ "					</SUB_SRV_NE_LIST>\r\n" + "				</T_SUB_SRV>\r\n"
				+ "				<T_SUB_SRV>\r\n" + "					<O_ID></O_ID>\r\n"
				+ "					<ST_ID></ST_ID>\r\n" + "					<SO_ID></SO_ID>\r\n"
				+ "					<DATE_FROM></DATE_FROM>\r\n" + "					<SUB_SRV_ID_LIST>\r\n"
				+ "						<T_SRV_ID>\r\n" + "							<ID_TYPE></ID_TYPE>\r\n"
				+ "							<ID></ID>\r\n" + "							<START_DATE></START_DATE>\r\n"
				+ "						</T_SRV_ID>\r\n" + "					</SUB_SRV_ID_LIST>\r\n"
				+ "				</T_SUB_SRV>				\r\n" + "			</SUB_SRV_LIST>\r\n"
				+ "			<SUB_DTL_LIST>\r\n" + "				<T_SUB_DTL>\r\n"
				+ "					<TITLE_ID></TITLE_ID>\r\n" + "					<SURNAME></SURNAME>\r\n"
				+ "					<FIRST_NAME></FIRST_NAME>\r\n" + "					<INITIALS></INITIALS>\r\n"
				+ "					<BIRTH_DATE></BIRTH_DATE>\r\n" + "					<LISTED_YN></LISTED_YN>\r\n"
				+ "					<GENDER></GENDER>\r\n" + "					<SUB_ADR_LIST>\r\n"
				+ "						<T_SUB_ADR>\r\n"
				+ "							<STREET_POSTAL_IND></STREET_POSTAL_IND>\r\n"
				+ "							<LINE_1></LINE_1>\r\n"
				+ "							<POSTAL_CODE></POSTAL_CODE>\r\n"
				+ "							<COUNTRY_ID></COUNTRY_ID>\r\n"
				+ "							<DATE_FROM></DATE_FROM>\r\n"
				+ "						</T_SUB_ADR>						\r\n"
				+ "					</SUB_ADR_LIST>\r\n" + "					<SUB_ID_LIST>\r\n"
				+ "						<T_SUB_ID>\r\n" + "							<COUNTRY_ID></COUNTRY_ID>\r\n"
				+ "							<ID_TYPE></ID_TYPE>\r\n" + "							<ID></ID>\r\n"
				+ "							<DATE_FROM></DATE_FROM>\r\n" + "						</T_SUB_ID>\r\n"
				+ "					</SUB_ID_LIST>\r\n" + "					<SUB_ROL_LIST>\r\n"
				+ "						<T_SUB_ROL>\r\n" + "							<ROLE_TYPE></ROLE_TYPE>\r\n"
				+ "							<DATE_FROM></DATE_FROM>\r\n"
				+ "						</T_SUB_ROL>						\r\n"
				+ "					</SUB_ROL_LIST>\r\n" + "				</T_SUB_DTL>\r\n"
				+ "			</SUB_DTL_LIST>\r\n" + "		</T_SUB_EVN>\r\n" + "	</ROW>\r\n" + "</ROWSET>";
		// XMLType xmlType = new XMLType(conn, xmlPayLoad);

		Clob eventString = OracleAQBLOBUtil.createClob(xmlPayLoad, (oracle.jdbc.driver.OracleConnection) conn);
		data.setEventString(eventString);
		data.setEventTimeStamp(new Date(System.currentTimeMillis()));
		data.setEventType("SIMSWP");
		data.setIcapId(50);
		return data;
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

}
