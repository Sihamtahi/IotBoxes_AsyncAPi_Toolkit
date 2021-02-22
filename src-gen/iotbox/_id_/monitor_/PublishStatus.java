package iotbox._id_.monitor_;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import schemas.LineInfo;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 */
public class PublishStatus {
	
	public static final String TOPIC_ID = "iotbox/{id}/monitor:";
	
	private static final int QOS = 2;
	
	/**
	 * Creates a new builder that can be used to build the payload necessary to
	 * publish in the PublishStatus channel
	 * 
	 * @return the builder
	 */
	public static final LineInfo.LineInfoBuilder payloadBuilder() {
		return LineInfo.newBuilder();
	}
	
	/**
	 * Publishes the given {@link LineInfo} in the channel with 
	 * no parameters
	 * 
	 * @param payload
	 * @throws MqttException
	 */
	public static final void publish(LineInfo payload) throws MqttException {
		publish(payload, PublishStatusParams.create());
	}
	
	/**
	 * Publishes the given {@link LineInfo} in the channel 
	 * configured with the given {@link PublishStatusParams}
	 * 
	 * @param payload
	 * @throws MqttException
	 */
	public static final void publish(LineInfo payload, PublishStatusParams params) throws MqttException {
		String topic = expand(params);
		String broker = "tcp://localhost:1883";
		String clientId = MqttClient.generateClientId();
		MemoryPersistence persistence = new MemoryPersistence();
		
		try (MqttClient client = new MqttClient(broker, clientId, persistence);) {
		    
		    MqttConnectOptions connOpts = new MqttConnectOptions();
		    connOpts.setCleanSession(true);
		    
		    MqttMessage message = new MqttMessage(payload.toJson().getBytes());
		    message.setQos(QOS);
		    
		    client.connect(connOpts);
		    client.publish(topic, message);
		    client.disconnect();
		}
	}
	
	/**
	 * Expands the parameters of the {@link #TOPIC_ID} with the values given 
	 * in {@link PublishStatusParams}
	 * 
	 * @param params
	 * @return
	 */
	public static String expand(PublishStatusParams params) {
		return params.apply(TOPIC_ID);
	}
	
	/**
	 * Utility class to set the parameters that can be used to configure the
	 * {@link PublishStatus} channel
	 */
	public static class PublishStatusParams {

		private Map<String, Object> parameters = new HashMap<>();
		
		/**
		 * Method to create a new instance of PublishStatusParams
		 * 
		 * @return a new PublishStatusParams
		 */
		public static PublishStatusParams create() {
			return new PublishStatusParams();
		}

		private String apply(String topic) {
			for (Entry<String, Object> entry : parameters.entrySet()) {
				topic = topic.replaceAll("\\{" + entry.getKey() + "\\}", entry.getValue().toString());
			}
			return topic;
		}
		
		/**
		 * Set the <code>id</code> parameter
		 */ 
		public PublishStatusParams withId(String id) {
			this.parameters.put("id", id);
			return this;
		}
	} 
}
