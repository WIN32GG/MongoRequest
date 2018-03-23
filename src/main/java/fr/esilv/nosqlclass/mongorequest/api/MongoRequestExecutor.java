package fr.esilv.nosqlclass.mongorequest.api;

import fr.esilv.nosqlclass.mongorequest.api.status.ConnectionResult;
import fr.esilv.nosqlclass.mongorequest.api.status.ConnectorStatus;

/**
 * Used to connect and Query a MongoDB database
 * @author win32gg
 *
 */
public interface MongoRequestExecutor {
	
	/**
	 * Connect to a mongodb database
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @return
	 */
	public ConnectionResult connectToMongo(String host, int port, String username, char[] password,String database);
	
	/**
	 * Connect to a mongodb database without specifing a login
	 * @param host
	 * @param port
	 * @return
	 */
	public ConnectionResult connectToMongo(String host, int port);
	
	/**
	 * Query the database
	 * @param jsonQuery
	 * @return
	 */
	public MongoResultBase query(String database,String collection,String jsonQuery);
	
	/**
	 * Get the status of this connector
	 * @return
	 */
	public ConnectorStatus getStatus();
	
}
