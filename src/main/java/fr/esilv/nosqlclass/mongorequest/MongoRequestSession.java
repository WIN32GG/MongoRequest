package fr.esilv.nosqlclass.mongorequest;

import fr.esilv.nosqlclass.mongorequest.api.MongoRequestExecutor;
import fr.esilv.nosqlclass.mongorequest.api.connector.MongoConnector;
import fr.esilv.nosqlclass.mongorequest.config.ConfigManager;
import fr.esilv.nosqlclass.mongorequest.objects.Request;

public class MongoRequestSession {

	public final ConfigManager cfg;
	public final MongoRequestExecutor executor;
	
	public MongoRequestSession() {
		cfg = new ConfigManager();
		executor = new MongoConnector();
	}
	
	public void start() {
		
	}
	
	public void exit() {
		
	}
	
	public void openRequestEdition(Request req) {
		
	}
	
	private void showMainScreen() {
		
	}
	
}
