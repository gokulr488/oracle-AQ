package OracleDBQueueTest;

import java.io.IOException;

public class Start {

	public static void main(String[] args) throws InterruptedException {
		try {
			AppConfigs.getAllValues();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DemoAQRawQueue aqRawQueue = new DemoAQRawQueue();
	}
}
