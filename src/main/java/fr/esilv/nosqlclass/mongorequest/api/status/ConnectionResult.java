package fr.esilv.nosqlclass.mongorequest.api.status;

/**
 * The possible outcomes of a connection to mongodb
 * @author win32gg
 *
 */
public enum ConnectionResult {

	SUCCESS,
	TIMED_OUT,
	AUTHENTICATION_ERROR,
	INTERNAL_ERROR;
	
}
