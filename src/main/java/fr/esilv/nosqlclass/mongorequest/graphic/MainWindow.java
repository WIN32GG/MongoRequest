package fr.esilv.nosqlclass.mongorequest.graphic;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {
	public MainWindow() {
		init();
		
		this.setVisible(true);
	}
	
	private void init() {
		getContentPane().setBackground(Color.BLACK);
		setSize(239, 480);
		getContentPane().setLayout(null);
		
		JList list = new JList();
		list.setForeground(Color.ORANGE);
		list.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		list.setBackground(Color.BLACK);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener((event) -> {
			
		});
		list.setBounds(12, 64, 202, 271);
		getContentPane().add(list);
		
		JLabel lblNewLabel = new JLabel("Requests list");
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 23, 202, 15);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Add New");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ADD
			}
		});
		btnNewButton.setForeground(Color.GREEN);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setBounds(58, 347, 114, 25);
		getContentPane().add(btnNewButton);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//OPEN
			}
		});
		btnOpen.setForeground(Color.ORANGE);
		btnOpen.setBackground(Color.BLACK);
		btnOpen.setBounds(58, 391, 114, 25);
		getContentPane().add(btnOpen);
	}
}
