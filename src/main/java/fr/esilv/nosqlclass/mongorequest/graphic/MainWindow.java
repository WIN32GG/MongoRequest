package fr.esilv.nosqlclass.mongorequest.graphic;

import java.awt.Color;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import fr.esilv.nosqlclass.mongorequest.MongoRequestSession;
import fr.esilv.nosqlclass.mongorequest.api.WindowActionReceptor;
import fr.esilv.nosqlclass.mongorequest.api.WindowActions;
import fr.esilv.nosqlclass.mongorequest.objects.Request;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {
	
	private WindowActionReceptor receptor;
	private JList<Request> list;
	
	public MainWindow(WindowActionReceptor rec) {
		this.receptor = rec;
		
		init();
		this.setVisible(true);
	}
	
	@Deprecated // for Window builder testing
	public MainWindow() {
		
		this.init();
	}
	
	public void setRequests(List<Request> reqs) {
		DefaultListModel<Request> r = new DefaultListModel<Request>();
		for(Request re:reqs) {
			r.addElement(re);
		}
		list.setModel(r);
	}
	
	private void init() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				receptor.onSpecialAction(MainWindow.this, MongoRequestSession.SHUTDOWN);
			}
		});
		this.setResizable(false);
		getContentPane().setBackground(Color.BLACK);
		setSize(239, 480);
		getContentPane().setLayout(null);
		
		list = new JList<Request>();
		list.setForeground(Color.ORANGE);
		list.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		list.setBackground(Color.BLACK);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		list.setBounds(12, 64, 202, 271);
		getContentPane().add(list);
		
		JLabel lblNewLabel = new JLabel("Requests list");
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 23, 202, 15);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Add New");
		btnNewButton.addActionListener((e) -> {
			//ADD
			receptor.onAction(this, WindowActions.ADD_REQUEST, new Object[0]);		
		});
		btnNewButton.setForeground(Color.GREEN);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setBounds(58, 347, 114, 25);
		getContentPane().add(btnNewButton);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener((e) -> {
				//OPEN
				receptor.onAction(this, WindowActions.OPEN_REQUEST, list.getSelectedValue());
		});
		btnOpen.setForeground(Color.ORANGE);
		btnOpen.setBackground(Color.BLACK);
		btnOpen.setBounds(58, 391, 114, 25);
		getContentPane().add(btnOpen);
	}
}
