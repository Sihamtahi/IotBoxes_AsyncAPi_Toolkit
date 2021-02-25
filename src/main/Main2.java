package main;

import java.text.MessageFormat;


import iotbox._id_.monitor_.SubscribeStatus;

public class Main2 {

	public static void main(String[] args) throws Exception 
	{
		// TODO Auto-generated method stub
		
/*		
try {
			
			SubscribeStatus.subscribe((message, params) -> {
				// Inform about the message received
				System.err.println(MessageFormat.format(
						"Subscription to ''{0}'' with ID ''{1}'':\n{2} Presses at {3}",
						SubscribeStatus.TOPIC_ID,
						// Notice that both the params and the message fields can be
						// queried via getters that know about the domain being modeled 
						params.getId(),
						message.getId(), 
						message.getPresses()));
			});

	}
catch ( Exception e){
	e.getMessage();
}
finally

{
	SubscribeStatus.unsubscribe();
}
*/
		
		
		SubscribeStatus.subscribe ((message, params) -> {
			 System.out. println ( MessageFormat . format (" Message received from IoTBox ''{0}''!", params.getId ()));
			 System.out. println ( MessageFormat . format (" Info about production line ''{0}'':", message.getId ()));
			 
			 message.getPresses().stream().forEach (
					 
			 press -> System.out.println (
			 MessageFormat . format ("At {0} , press ''{1}'' was pressing at {2} Pa",
			 press . getTimestamp () ,
			 press . getId () ,
			 press . getValue ()
			 )
			 )
 			 );
			 });
}
}	
	

