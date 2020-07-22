package objectQDQ;

import java.io.IOException;
import java.sql.SQLException;

import OracleDBQueueTest.AppConfigs;

public class ObjectQDQ {

	public static void main(String[] args) throws Exception {
	
		
		try {
			AppConfigs.getAllValues();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ObjectEnqueueHandler enquer = new ObjectEnqueueHandler();
		enquer.initialiseTables();
		for(int i=0;i<25;i++) {
			enquer.enqueuData(enquer.populateData());
		}
		
		enquer.closeConnection();
		
		
		ObjectDequeuHandler dequeuer=new ObjectDequeuHandler();
		String queueType="Object";
		String queueName="event_object_queue";
		String consumerName="RED";
		dequeuer.dequeueData(consumerName, queueName, queueType);
		dequeuer.closeConnection();

	}

}
