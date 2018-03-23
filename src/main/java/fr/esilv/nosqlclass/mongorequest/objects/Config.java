package fr.esilv.nosqlclass.mongorequest.objects;

import java.io.Serializable;
import java.util.List;

public class Config implements Serializable {

	public ConnectionInfo connection;
	public List<Request> requests;
	
}
