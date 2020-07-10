package OracleDBQueueTest.model;

public class T_SUB_ID {
	private String COUNTRY_ID;

	private String DATE_FROM;

	private String ID;

	private String ID_TYPE;

	public String getCOUNTRY_ID() {
		return COUNTRY_ID;
	}

	public void setCOUNTRY_ID(String COUNTRY_ID) {
		this.COUNTRY_ID = COUNTRY_ID;
	}

	public String getDATE_FROM() {
		return DATE_FROM;
	}

	public void setDATE_FROM(String DATE_FROM) {
		this.DATE_FROM = DATE_FROM;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getID_TYPE() {
		return ID_TYPE;
	}

	public void setID_TYPE(String ID_TYPE) {
		this.ID_TYPE = ID_TYPE;
	}

	@Override
	public String toString() {
		return "ClassPojo [COUNTRY_ID = " + COUNTRY_ID + ", DATE_FROM = " + DATE_FROM + ", ID = " + ID + ", ID_TYPE = "
				+ ID_TYPE + "]";
	}
}
