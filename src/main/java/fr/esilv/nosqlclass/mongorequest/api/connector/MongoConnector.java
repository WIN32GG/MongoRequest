package fr.esilv.nosqlclass.mongorequest.api.connector;

import java.util.Arrays;

import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

import fr.esilv.nosqlclass.mongorequest.api.MongoRequestExecutor;
import fr.esilv.nosqlclass.mongorequest.api.MongoResultBase;
import fr.esilv.nosqlclass.mongorequest.api.status.ConnectionResult;
import fr.esilv.nosqlclass.mongorequest.api.status.ConnectorStatus;


public class MongoConnector implements MongoRequestExecutor {
	

	private MongoClient mongoClient;
	private ConnectorStatus status = ConnectorStatus.DISCONNECTED;
	
	@Override
	public ConnectionResult connectToMongo(String host, int port, String username, char[] password,String database) {
		status = ConnectorStatus.CONNECTING;
		if(username == null && password == null && database == null)
		{
			try 
			{
				Builder b = MongoClientOptions.builder().serverSelectionTimeout(1000);
				mongoClient = new MongoClient( new ServerAddress(host, port), b.build() );
				mongoClient.getAddress();
			}
			catch(Exception e)
			{
				System.out.println(e.getClass().getSimpleName());
				//e.printStackTrace();
				
				status = ConnectorStatus.DISCONNECTED;
				return ConnectionResult.INTERNAL_ERROR;
			}
			System.out.println("connection ok");
			status = ConnectorStatus.CONNECTED_AND_READY;
			return ConnectionResult.SUCCESS;
		}
		else 
		{
			try 
			{
				MongoCredential credential = MongoCredential.createCredential(username, database, password);
				MongoClientOptions options = MongoClientOptions.builder().sslEnabled(true).connectTimeout(1).build();
				
				MongoClient mongoClient = new MongoClient(new ServerAddress(host, port),
				                                           Arrays.asList(credential),
				                                           options);
				status = ConnectorStatus.CONNECTED_AND_READY;
			}
			catch(Exception e)
			{
				System.out.println(e.getClass().getSimpleName());
				//e.printStackTrace();
				status = ConnectorStatus.DISCONNECTED;
				return ConnectionResult.INTERNAL_ERROR;
			}
			return ConnectionResult.SUCCESS;
			
		}
		
	}

	@Override
	public ConnectionResult connectToMongo(String host, int port) {
		return connectToMongo(host,port,null,null,null);
	}

	@Override
	public MongoResultBase query(String database,String collection,String jsonQuery, int maxResults) {
		
		if(status != ConnectorStatus.CONNECTED_AND_READY) {
			
			return MongoResultBase.error("Not ready");
		}
		
		status = ConnectorStatus.QUERYING;
		
		MongoResultBase mongoResultBase;
		try
		{
			DB db = mongoClient.getDB(database);
	        DBCollection coll = db.getCollection(collection);
	        
	        DBObject query = (DBObject) JSON.parse(jsonQuery);
	        DBCursor cursor = coll.find(query);
	        mongoResultBase = new MongoResultBase(false,toJson(cursor, maxResults));
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			mongoResultBase = new MongoResultBase(true,e.getClass().getSimpleName()+" "+e.getMessage());
		}finally {
			this.status = ConnectorStatus.CONNECTED_AND_READY;
		}
		
        
		return mongoResultBase;
	}

	private Gson gson = new Gson();
	private String toJson(DBCursor c, int max) {
		String g = "";
		int i = 0;
		
		while(c.hasNext() && i < max) {
			g+= gson.toJson(c.next().toMap())+"\n";
			i++;
		}
		
		return g;
	}
	
	
	@Override
	public ConnectorStatus getStatus() {
		return status;		
	}

}
