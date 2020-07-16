package objectQDQ;

import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class EventObj implements SQLData {
	private String sql_type;
	private long eventSeq;
	private Date eventTimeStamp;
	private String eventType;
	private long icapId;
	private Clob eventString;

	@Override
	public String getSQLTypeName() throws SQLException {

		return sql_type;
	}

	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		sql_type = typeName;
		eventSeq = stream.readLong();
		eventTimeStamp = stream.readDate();
		eventType = stream.readString();
		icapId = stream.readLong();
		eventString = stream.readClob();

	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		stream.writeLong(eventSeq);
		stream.writeDate(eventTimeStamp);
		stream.writeString(eventType);
		stream.writeLong(icapId);
		stream.writeClob(eventString);

	}

	public long getEventSeq() {
		return eventSeq;
	}

	public void setEventSeq(long eventSeq) {
		this.eventSeq = eventSeq;
	}

	public Date getEventTimeStamp() {
		return eventTimeStamp;
	}

	public void setEventTimeStamp(Date eventTimeStamp) {
		this.eventTimeStamp = eventTimeStamp;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public long getIcapId() {
		return icapId;
	}

	public void setIcapId(long icapId) {
		this.icapId = icapId;
	}

	public Clob getEventString() {
		return eventString;
	}

	public void setEventString(Clob eventString) {
		this.eventString = eventString;
	}

	public String getSql_type() {
		return sql_type;
	}

	public void setSql_type(String sql_type) {
		this.sql_type = sql_type;
	}

}
