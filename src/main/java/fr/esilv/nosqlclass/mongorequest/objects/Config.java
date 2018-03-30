package fr.esilv.nosqlclass.mongorequest.objects;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Config implements Serializable {

	public ConnectionInfo connection = new ConnectionInfo();
	public List<Request> requests = new LinkedList<Request>();
	
}
