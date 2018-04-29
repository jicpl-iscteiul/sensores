package sensores.app;

import org.eclipse.paho.client.mqttv3.MqttClient;             
import org.eclipse.paho.client.mqttv3.MqttException;          
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;     
                                                              

public class App 
{
    public static void main( String[] args )
    {
    	  String topic        = "iscte_sid_2016_S1";                                                              
    	  String broker       = "tcp://iot.eclipse.org";                 
    	  String clientId     = "js-utility-QJnOD";                          
    	  MemoryPersistence persistence = new MemoryPersistence();           
    	                                                                     
    	  try {    
    		  MongoConnection mongoConnection = new MongoConnection();
    		  
    	      MqttClient client = new MqttClient(broker, clientId, persistence);
    	                                                                     
    	                                                                     
    	      MqttConnectOptions connOpts = new MqttConnectOptions();        
    	      connOpts.setCleanSession(true);  
    	                                                                     
    	      client.connect();                                                                                
    	      client.subscribe(topic);                                       
    	       boolean connected = client.isConnected();                     
    	      System.out.println("Connected: " + connected);                   
    	                                                                     
    	      client.setCallback(new SimpleMqttCallBack(mongoConnection));                  
    	                                                                     
    	  } catch (MqttException me) {                                       
    	      System.out.println("reason "+ me.getReasonCode());             
    	      System.out.println("msg "+ me.getMessage());                   
    	      System.out.println("loc "+ me.getLocalizedMessage());          
    	      System.out.println("cause "+ me.getCause());                   
    	      System.out.println("excep "+ me);                              
    	      me.printStackTrace();                                          
    	  }                                                                  
    }
}
