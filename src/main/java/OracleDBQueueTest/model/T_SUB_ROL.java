package OracleDBQueueTest.model;

public class T_SUB_ROL {
	private String ROLE_TYPE;

	private String DATE_FROM;

	public String getROLE_TYPE() {
		return ROLE_TYPE;
	}

	public void setROLE_TYPE(String ROLE_TYPE) {
		this.ROLE_TYPE = ROLE_TYPE;
	}

	public String getDATE_FROM() {
		return DATE_FROM;
	}

	public void setDATE_FROM(String DATE_FROM) {
		this.DATE_FROM = DATE_FROM;
	}

	@Override
	public String toString() {
		return "ClassPojo [ROLE_TYPE = " + ROLE_TYPE + ", DATE_FROM = " + DATE_FROM + "]";
	}
}
