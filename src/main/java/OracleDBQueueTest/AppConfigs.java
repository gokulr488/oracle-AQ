package OracleDBQueueTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AppConfigs {

	public AppConfigs() throws IOException {
		getAllValues();
	}

	static boolean dequeMode;
	static String username;
	static String password;
	static String url;
	static String runmode;
	static boolean cleanUp;

	static FileInputStream fileInputStream;

	public static void getAllValues() throws IOException {

		try {
			Properties prop = new Properties();
			String propFileName = "app.config";

			fileInputStream = new FileInputStream(new File(propFileName));

			if (fileInputStream != null) {
				prop.load(fileInputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			String dequemode = prop.getProperty("dequemode");
			String clean = prop.getProperty("cleanup");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			url = prop.getProperty("URL");
			runmode = prop.getProperty("runmode");

			if (clean.equals("T") || clean.equals("t")) {
				cleanUp = true;
			} else {
				cleanUp = false;
			}

			if (dequemode.equals("T") || dequemode.equals("t")) {
				dequeMode = true;
			} else {
				dequeMode = false;
			}

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			fileInputStream.close();
		}

	}

}
