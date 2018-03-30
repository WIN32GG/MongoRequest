package fr.esilv.nosqlclass.mongorequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.lang.ArrayUtils;

import fr.esilv.nosqlclass.mongorequest.api.MongoRequestExecutor;
import fr.esilv.nosqlclass.mongorequest.api.WindowActionReceptor;
import fr.esilv.nosqlclass.mongorequest.api.WindowActions;
import fr.esilv.nosqlclass.mongorequest.api.connector.MongoConnector;
import fr.esilv.nosqlclass.mongorequest.api.status.ConnectionResult;
import fr.esilv.nosqlclass.mongorequest.api.status.ConnectorStatus;
import fr.esilv.nosqlclass.mongorequest.config.ConfigManager;
import fr.esilv.nosqlclass.mongorequest.graphic.ConnectionWindow;
import fr.esilv.nosqlclass.mongorequest.graphic.MainWindow;
import fr.esilv.nosqlclass.mongorequest.graphic.RequestEditionScreen;
import fr.esilv.nosqlclass.mongorequest.objects.Config;
import fr.esilv.nosqlclass.mongorequest.objects.Request;

public class MongoRequestSession implements WindowActionReceptor {

	public static final int
		CLOSE_WINDOW = 0,
		SHUTDOWN 	 = 1;
	
	
	private static MongoRequestSession instance;
	public static MongoRequestSession getInstance() {
		if(instance == null)
			try {
				instance = new MongoRequestSession();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		
		
		return instance;
	}
	
	
	public final MongoRequestExecutor executor;
	private List<RequestEditionScreen> editors = new LinkedList<RequestEditionScreen>();
	
	private final ConfigManager cfg;
	private ConnectionWindow connw;
	private Config theConfig;
	
	private MainWindow theMainWindow;
	
	private MongoRequestSession() throws Exception {
		cfg = new ConfigManager();
		executor = new MongoConnector();
	}
	
	private void loadConfig() throws IOException {
		this.theConfig = cfg.loadConfig();
		System.out.println("Config loaded");
	}
	
	public void start() throws IOException {
		this.loadConfig();
		this.openConnectionScreen();
	}
	
	public void exit() {
		System.exit(0);
	}
	
	private void openConnectionScreen() {
		this.connw = new ConnectionWindow(this);
		this.connw.setAddress(this.theConfig.connection.host+":"+this.theConfig.connection.port);
		connw.freeze(true);
		this.testConectionAndOpenMain();
	}
	
	private void testConectionAndOpenMain() {
		if(!this.ensureConnection()) {
			JOptionPane.showMessageDialog(null, "Cannot connect to the MongoDb Server", "Connection problem", JOptionPane.ERROR_MESSAGE);
			if(this.connw != null)
				this.connw.freeze(false);
		} else {
			if(this.connw != null) {
				
				this.connw.setVisible(false); //dispose?
				this.connw = null;
			}
			this.showMainScreen();
		}
	}
	
	private void ensureAndAlert() {
		if(!this.ensureConnection()) {
			JOptionPane.showMessageDialog(null, "Cannot connect to the Local MongoDb Server", "Connection problem", JOptionPane.ERROR_MESSAGE);
		} 
	}
	
	private boolean ensureConnection() { 
		System.out.println("Connection State is "+this.executor.getStatus());
		if(this.executor.getStatus().equals(ConnectorStatus.DISCONNECTED)) 
			return this.connect().equals(ConnectionResult.SUCCESS);			
	
		return true;
	}
	
	private ConnectionResult connect() {
		System.out.println("Connecting to "+this.theConfig.connection.host+":"+this.theConfig.connection.port);
		return this.executor.connectToMongo(this.theConfig.connection.host, this.theConfig.connection.port);
	}
	
	private void showMainScreen() {
		this.theMainWindow = new MainWindow(this);
		this.refreshMainList();
	}
	
	private Request craftDefaultRequest() {
		Request r = new Request();
		
		r.description = "Request description";
		r.title = "Requets title";
		r.parameters = new HashMap<String, String>();
		r.request = "{}";
		r.db = "database";
		r.collection = "collection";
		r.maxResults = 10;
		
		return r;
		
	}

	private void openEditorWindow(Request req) {
		if(req == null) {
			req = craftDefaultRequest();
			this.theConfig.requests.add(req);
		}
		
		RequestEditionScreen res = new RequestEditionScreen(this);
		res.loadRequest(req);
		
		this.editors.add(res);
		
	}
	
	private void exec(RequestEditionScreen screen, Request r) {
		this.ensureConnection();
		r.lastResult =  this.executor.query(r.db, r.collection, this.compute(r), 10);
		screen.loadResponse(r);
	}
	
	private String compute(Request r) {
		String re = r.request;
		for(String k:r.parameters.keySet()) {
			re = re.replace(k, r.parameters.get(k));
		}
		
		return re;
	}
	
	private void refreshMainList() {
		if(this.theMainWindow != null)
			this.theMainWindow.setRequests(this.theConfig.requests);
	}
	
	private void close(JFrame window) {
		window.dispose();
		this.editors.remove(window);
	}
	
	
	
	@Override
	public void onAction(JFrame window, WindowActions identifier, Object... args) {
		switch(identifier) {
			case ADD_REQUEST:
				this.openEditorWindow(null);
				this.refreshMainList();
				break;
				
			case CLOSE_REQUEST_WINDOW:
				this.close(window);
				break;
				
			case DELETE_REQUEST:
				this.theConfig.requests.remove(args[0]);
				this.refreshMainList();
				window.dispose();
				break;
				
			case EXEC_REQUEST:
				this.exec((RequestEditionScreen)window, (Request)args[0]);
				break;
				
			case OPEN_REQUEST:
				this.openEditorWindow((Request) args[0]);
				break;
				
			case SAVE_REQUEST:
				((RequestEditionScreen)window).getRequest();
				try {
					this.cfg.saveConfig(this.theConfig);
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.refreshMainList();
				break;
			
			case COMPUTE_REQUEST:
				System.out.println(this.compute((Request) args[0]));
				break;
		
			case CONNECTION:
				this.theConfig.connection.host = (String) args[0];
				this.theConfig.connection.port = (int) args[1];
				this.testConectionAndOpenMain();
				break;
					
		}
		
	}

	@Override
	public void onSpecialAction(JFrame frame, int code) {
		System.out.println("Got code "+code);
		
		if(code == CLOSE_WINDOW) 
			this.close(frame);
		
		if(code == SHUTDOWN) {
			try {
				this.cfg.saveConfig(this.theConfig);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
		
	}
	
}
