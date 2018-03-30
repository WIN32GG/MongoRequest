package fr.esilv.nosqlclass.mongorequest.graphic;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import fr.esilv.nosqlclass.mongorequest.MongoRequestSession;
import fr.esilv.nosqlclass.mongorequest.api.WindowActionReceptor;
import fr.esilv.nosqlclass.mongorequest.api.WindowActions;

public class ConnectionWindow extends JFrame {
	private JTextField textField;

	private WindowActionReceptor receptor;
	private JButton btnGo;
	private JProgressBar progressBar;
	
	@Deprecated
	public ConnectionWindow() {
		init();
	}
	
	public ConnectionWindow(WindowActionReceptor r) {
		init();
		this.receptor = r;
	}
	
	public void freeze(boolean b) {
		this.progressBar.setVisible(b);
		this.btnGo.setEnabled(!b);
		this.textField.setEditable(!b);
	}
	
	public void setAddress(String g) {
		this.textField.setText(g);
	}
	
	private void init() {
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ConnectionWindow.this.receptor.onSpecialAction(ConnectionWindow.this, MongoRequestSession.SHUTDOWN);
			}
		});
		setResizable(false);
		setSize(551,171);
		getContentPane().setBackground(Color.BLACK);
		setBackground(Color.BLACK);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Database Connection");
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setBounds(172, 12, 237, 15);
		getContentPane().add(lblNewLabel);
		
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setBounds(12, 39, 516, 14);
		getContentPane().add(progressBar);
		
		textField = new JTextField();
		textField.setForeground(Color.ORANGE);
		textField.setBackground(Color.BLACK);
		textField.setBounds(193, 65, 124, 19);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setForeground(Color.ORANGE);
		lblAddress.setBounds(93, 65, 82, 15);
		getContentPane().add(lblAddress);
		
		btnGo = new JButton("Go");
		btnGo.addActionListener((e) -> {
			String[] a  = this.textField.getText().split(":");
			
			if(a.length != 2)
				return;
			
			String host = a[0];
			int port = Integer.parseInt(a[1]);
			
			this.freeze(true);
			new Thread() {
				public void run() {
					receptor.onAction(ConnectionWindow.this, WindowActions.CONNECTION, host, port);

				}
			}.start();
		});
		btnGo.setForeground(Color.GREEN);
		btnGo.setBackground(Color.BLACK);
		btnGo.setBounds(359, 62, 114, 25);
		getContentPane().add(btnGo);
	}
}
