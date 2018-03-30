package fr.esilv.nosqlclass.mongorequest.api;

/**
 * Mongo Base Response, what the database returns after a query
 * @author win32gg
 *
 */
public class MongoResultBase {

	public boolean isError = false;
	public String response = "";
	
	public MongoResultBase(boolean error, String rawResponse) {
		this.isError = error;
		this.response = rawResponse;
	}
	
	public MongoResultBase() {
		
	}

	public static MongoResultBase error(String string) {
		MongoResultBase res = new MongoResultBase();
		res.isError = true;
		res.response = string;
		return res;
	}
	
	
	
}
