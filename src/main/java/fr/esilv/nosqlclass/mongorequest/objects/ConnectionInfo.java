package fr.esilv.nosqlclass.mongorequest.objects;

import java.io.Serializable;

public class ConnectionInfo implements Serializable {

	public String connectionDescription;
	public String host, username, password;
	public int port;
	
}
