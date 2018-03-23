package fr.esilv.nosqlclass.mongorequest.api.connector;

import fr.esilv.nosqlclass.mongorequest.api.MongoRequestExecutor;
import fr.esilv.nosqlclass.mongorequest.api.MongoResultBase;
import fr.esilv.nosqlclass.mongorequest.api.status.ConnectionResult;
import fr.esilv.nosqlclass.mongorequest.api.status.ConnectorStatus;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;

import java.util.Arrays;

import javax.swing.text.Document;


public class MongoConnector implements MongoRequestExecutor {
	

	private MongoClient mongoClient;
	
	@Override
	public ConnectionResult connectToMongo(String host, int port, String username, char[] password,String database) {
		// TODO Auto-generated method stub
		if(username == null && password == null && database == null)
		{
			try 
			{
				mongoClient = new MongoClient( host , port );
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ConnectionResult.INTERNAL_ERROR;
			};
			System.out.println("ok");
			return ConnectionResult.SUCCESS;
		}
		else 
		{
			try 
			{
				MongoCredential credential = MongoCredential.createCredential(username, database, password);
				MongoClientOptions options = MongoClientOptions.builder().sslEnabled(true).build();

				
				MongoClient mongoClient = new MongoClient(new ServerAddress(host, port),
				                                           Arrays.asList(credential),
				                                           options);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ConnectionResult.INTERNAL_ERROR;
			};
			System.out.println("ok");
			return ConnectionResult.SUCCESS;
			
		}
		
	}

	@Override
	public ConnectionResult connectToMongo(String host, int port) {
		// TODO Auto-generated method stub
		return connectToMongo(host,port,null,null,null);
		
		
	}

	@Override
	public MongoResultBase query(String database,String collection,String jsonQuery) {
		
		MongoResultBase mongoResultBase;
		try
		{
			DB db = mongoClient.getDB(database);
	        DBCollection coll = db.getCollection(collection);
	        
	        DBObject query = (DBObject) JSON.parse(jsonQuery);
	        DBCursor cursor = coll.find(query);
	        mongoResultBase = new MongoResultBase(false,cursor.toString());
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			mongoResultBase = new MongoResultBase(true,"");
		}
		// TODO Auto-generated method stub
        
		return mongoResultBase;
	}

	@Override
	public ConnectorStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

}
