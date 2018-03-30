package fr.esilv.nosqlclass.mongorequest.objects;

import java.io.Serializable;

public class ConnectionInfo implements Serializable {

	public String connectionDescription = "defaultConnection";
	public String host = "127.0.0.1", username = null, password = null;
	public int port = 27017;
	
}
