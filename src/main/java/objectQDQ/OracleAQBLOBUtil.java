package objectQDQ;

import java.sql.Clob;

import oracle.jdbc.OracleConnection;
import oracle.sql.CLOB;

public class OracleAQBLOBUtil {

	public static Clob createClob(String stringData, OracleConnection connection) throws Exception {
		java.sql.Clob clob = connection.createClob();
		clob.setString(1, stringData);

		return clob;
	}

}