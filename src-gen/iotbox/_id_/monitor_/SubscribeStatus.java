package iotbox._id_.monitor_;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import schemas.LineInfo;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


/**
 */
public class SubscribeStatus {
	
	public static final String TOPIC_ID = "iotbox/{id}/monitor:";
	public static final String TOPIC_PATTERN = "iotbox/+/monitor:";
	
	private static final int QOS = 2;
	
	private static MqttClient client;
		
	static {
		String broker = "tcp://localhost:1883";
		String clientId = MqttClient.generateClientId();
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			client = new MqttClient(broker, clientId, persistence);
		} catch (MqttException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Subscribes to the given channel with the given
	 * {@link ISubscribeStatusCallback}. Only a single subscription and
	 * callback method is allowed per channel. If multiple subscriptions are
	 * solicited, only the latest registered callback will be honored.
	 * 
	 * @param callback
	 * @throws MqttException
	 */
	public static final void subscribe(ISubscribeStatusCallback callback) throws MqttException {
	    if (!client.isConnected()) {
		    MqttConnectOptions connOpts = new MqttConnectOptions();
		    connOpts.setCleanSession(true);
		    client.connect(connOpts);
	    }
	    client.setCallback(new MqttCallback() {
			@Override public void deliveryComplete(IMqttDeliveryToken token) {}
			@Override public void connectionLost(Throwable cause) {}
			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
			callback.messageArrived(LineInfo.fromJson(new String(message.getPayload())), new SubscribeStatusParams(topic));
			}
		});
	    client.subscribe(TOPIC_PATTERN, QOS);
	}
	
	/**
	 * Unsubscribes from the given channel and closes the connection if a
	 * subscription was previously registered
	 * 
	 * @throws MqttException
	 */
	public static final void unsubscribe() throws MqttException {
		if (client.isConnected()) {
		    client.unsubscribe(TOPIC_PATTERN);
			client.disconnect();
		}
	}
	
	/**
	 * Interface that must be implemented for subscribing to the
	 * {@link SubscribeStatus} channel
	 */
	public interface ISubscribeStatusCallback {
		public void messageArrived(LineInfo payload, SubscribeStatusParams params);
	}
	
	/**
	 * Utility class that can be used to retrieve the parameters used to 
	 * configure the {@link SubscribeStatus} channel
	 */
	public static class SubscribeStatusParams {
		
		private Map<String, Object> parameters = new HashMap<>();
		
		private SubscribeStatusParams(String topic) {
			String regex = TOPIC_ID;
			regex = regex.replaceAll("\\{id\\}", "(?<id>.+)");
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(topic);
			if (matcher.matches()) {
				this.parameters.put("id", matcher.group("id"));
			}
		}
		
		private SubscribeStatusParams(Map<String, Object> parameters) {
			this.parameters.putAll(parameters);
		}
		
		/**
		 * Getter for the <code>id</code> parameter
		 *
		 * @return id
		 */ 
		public String getId() {
			return (String) this.parameters.get("id");
		}
	}
}
