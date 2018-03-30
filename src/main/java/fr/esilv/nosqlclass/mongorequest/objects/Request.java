package fr.esilv.nosqlclass.mongorequest.objects;

import java.io.Serializable;
import java.util.Map;

import fr.esilv.nosqlclass.mongorequest.api.MongoResultBase;

public class Request implements Serializable {

	public String request;
	public String description;
	public String title;
	public int maxResults;
	public MongoResultBase lastResult;
	public Map<String, String> parameters;
	
	public String db;
	public String collection;
	
	@Override
	public String toString() {
		return this.title;
	}
	
}
