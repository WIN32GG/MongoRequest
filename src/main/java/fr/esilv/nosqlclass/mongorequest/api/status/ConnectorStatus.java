package fr.esilv.nosqlclass.mongorequest.api.status;

/**
 * Current status of connector
 * @author win32gg
 *
 */
public enum ConnectorStatus {

	CONNECTED_AND_READY,
	QUERYING,
	CONNECTING,
	DISCONNECTED;
	
}
