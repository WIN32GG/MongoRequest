package fr.esilv.nosqlclass.mongorequest.api;

/**
 * Mongo Base Response, what the database returns after a query
 * @author win32gg
 *
 */
public class MongoResultBase {

	private boolean isError = false;
	private String response;
	
	public MongoResultBase(boolean error, String rawResponse) {
		this.isError = error;
		this.response = rawResponse;
	}
	
	public boolean isError() {
		return this.isError;
	}
	
	public String getRawResponse() {
		return this.response;
	}
	
}
