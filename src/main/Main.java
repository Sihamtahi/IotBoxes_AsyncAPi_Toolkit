package main;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import iotbox._id_.monitor_.PublishStatus;
import iotbox._id_.monitor_.PublishStatus.PublishStatusParams;
import schemas.LineInfo;
import schemas.PressInfo;
import iotbox._id_.monitor_.SubscribeStatus;

public class Main {
	public static void main(String[] args) throws Exception {
		// TODO: Put your code he1e
	   // create a ligne Info 
		
		LineInfo line1 = LineInfo.newBuilder()
				.withId("line A")
				.addToPresses(
						PressInfo.newBuilder()
						.withId("Press A1")
						.withTimestamp(Instant.now().toString())
						.withValue(10.0)
						.build()
						)
				.addToPresses(
						PressInfo.newBuilder()
						.withId("Press A2")
						.withTimestamp(Instant.now().toString())
						.withValue(15.0)
						.build()
						)
				.build();
		
		PublishStatusParams para = PublishStatusParams.create()
				.withId("box1");
		
		PublishStatus.publish ( line1, para);
				
				
		try {
			
			/***SubscribeStatus.subscribe((message, params) -> {
				// Inform about the message received
				System.err.println(MessageFormat.format(
						"Subscription to ''{0}'' with ID ''{1}'':\n{2} Presses at {3}",
						SubscribeStatus.TOPIC_ID,
						// Notice that both the params and the message fields can be
						// queried via getters that know about the domain being modeled 
						params.getId(),
						message.getId(), 
						message.getPresses()));
			});*/
			
			
			PressInfo pr;
			PressInfo pr2;
			// Prepare to publish several messages
			// PressInfo p1 = PressInfo("6","4",5.4);
			 
						for (int i = 0; i < 10; i++) {
							
							 pr= PressInfo.newBuilder()
									.withId(String.valueOf(i ))
									.withTimestamp(LocalDateTime.now().toString())
									.withValue(5.5)
									.build();
							 pr2= PressInfo.newBuilder()
										.withId(String.valueOf(i * 2 ))
										.withTimestamp(LocalDateTime.now().toString())
										.withValue(5.5)
										.build();
							
							// Create the payload via the payloadBuiler offered by the publish  operation
							
							LineInfo payload = PublishStatus.payloadBuilder()
									// Notice that the properties of the payload can be set via
									// setter that know about the domain (e.g., name and type of
									// the property
									
									.withId(String.valueOf(i))
									.addToPresses(pr)
									.addToPresses(pr2)
									.build();
							 
							 
							// Set the value of the parameters. Notice that a setter is also provided
							PublishStatusParams params = PublishStatusParams.create()
									.withId(UUID.randomUUID().toString());
									 
							
							// Inform about the message to be sent
							System.out.println(MessageFormat.format(
									"Publishing at ''{0}'':\n{1}",
									PublishStatus.expand(params), payload.toJson(true)));
							
							// Publish the LightMeasured message
							PublishStatus.publish(payload, params);
						}
			
			
			
			
			
			
			
			
			
			
			
		}
		finally
		
		{
			/**SubscribeStatus.unsubscribe();*/
			System.out.println("got it ");
			
		}
	}
}
