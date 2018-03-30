package fr.esilv.nosqlclass.mongorequest.api;

import javax.swing.JFrame;

public interface WindowActionReceptor {

	public void onAction(JFrame window, WindowActions identifier, Object... args);
	public void onSpecialAction(JFrame frame, int code);
	
}
