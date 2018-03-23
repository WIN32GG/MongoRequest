package fr.esilv.nosqlclass.mongorequest.graphic;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import fr.esilv.nosqlclass.mongorequest.objects.Request;

public class RequestEditionScreen extends JFrame {
	private JEditorPane request;
	private JEditorPane dtrpnDescription;
	private JTextField title;
	private JComboBox comboBox;
	private JTextField txtValue;
	private JEditorPane result;
	private JLabel lblStatus;
	
	
	public RequestEditionScreen() {
		init();
	}
	
	public void loadRequest(Request rq) {
		
	}
	
	
	
	private void init() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				//CLOSE WINDOW
			}
		});
		init();
		this.setVisible(true);
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);
		
		title = new JTextField();
		title.setText("Title");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(Color.ORANGE);
		title.setBackground(Color.BLACK);
		title.setBounds(12, 12, 653, 19);
		getContentPane().add(title);
		title.setColumns(10);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 43, 201, 106);
		getContentPane().add(scrollPane_2);
		
		dtrpnDescription = new JEditorPane();
		scrollPane_2.setViewportView(dtrpnDescription);
		dtrpnDescription.setText("description");
		dtrpnDescription.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		dtrpnDescription.setForeground(Color.ORANGE);
		dtrpnDescription.setBackground(Color.BLACK);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(225, 43, 430, 106);
		getContentPane().add(scrollPane_1);
		
		request = new JEditorPane();
		scrollPane_1.setViewportView(request);
		request.setText("Request");
		request.setForeground(Color.ORANGE);
		
		request.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		request.setBackground(Color.BLACK);
		
		JButton execBtn = new JButton("Execute");
		execBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//EXEC
			}
		});
		execBtn.setForeground(Color.GREEN);
		execBtn.setBackground(Color.BLACK);
		execBtn.setBounds(25, 224, 114, 25);
		getContentPane().add(execBtn);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(255, 200, 0)));
		panel.setBounds(37, 161, 605, 37);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblRequestParameters = new JLabel("Request parameters:");
		lblRequestParameters.setBounds(12, 7, 179, 20);
		panel.add(lblRequestParameters);
		lblRequestParameters.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRequestParameters.setForeground(Color.ORANGE);
		
		comboBox = new JComboBox();
		comboBox.setBounds(202, 7, 151, 24);
		panel.add(comboBox);
		comboBox.setForeground(Color.ORANGE);
		comboBox.setBackground(Color.BLACK);
		
		txtValue = new JTextField();
		txtValue.setBounds(365, 10, 226, 19);
		panel.add(txtValue);
		txtValue.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		txtValue.setForeground(Color.ORANGE);
		txtValue.setText("value");
		txtValue.setBackground(Color.BLACK);
		txtValue.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(169, 224, 486, 144);
		getContentPane().add(scrollPane);
		
		result = new JEditorPane();
		scrollPane.setViewportView(result);
		result.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		result.setBackground(Color.BLACK);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//SAVE
			}
		});
		btnSave.setBackground(Color.BLACK);
		btnSave.setForeground(Color.DARK_GRAY);
		btnSave.setBounds(25, 306, 114, 25);
		getContentPane().add(btnSave);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DEL
			}
		});
		btnDelete.setBackground(Color.BLACK);
		btnDelete.setForeground(Color.RED);
		btnDelete.setBounds(25, 343, 114, 25);
		getContentPane().add(btnDelete);
		
		lblStatus = new JLabel("Idle");
		lblStatus.setForeground(Color.BLUE);
		lblStatus.setBounds(556, 388, 86, 15);
		getContentPane().add(lblStatus);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//CLOSE
			}
		});
		btnClose.setForeground(Color.BLUE);
		btnClose.setBackground(Color.BLACK);
		btnClose.setBounds(25, 383, 114, 25);
		getContentPane().add(btnClose);
		
		JButton btnNewButton = new JButton("Compute");
		btnNewButton.setForeground(Color.GREEN);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setBounds(25, 247, 114, 25);
		getContentPane().add(btnNewButton);
		setResizable(false);
		setSize(677,461);
	}
}
