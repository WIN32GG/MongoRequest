package fr.esilv.nosqlclass.mongorequest.graphic;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import org.apache.commons.lang.ArrayUtils;

import fr.esilv.nosqlclass.mongorequest.MongoRequestSession;
import fr.esilv.nosqlclass.mongorequest.api.WindowActionReceptor;
import fr.esilv.nosqlclass.mongorequest.api.WindowActions;
import fr.esilv.nosqlclass.mongorequest.objects.Request;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class RequestEditionScreen extends JFrame {
	
	public static final int 
		STATUS_IDLE = 0,
		STATUS_FAILED = 1,
		STATUS_SUCCESS = 2;
	
	private JEditorPane request;
	private JEditorPane dtrpnDescription;
	private JTextField title;
	private JComboBox argsCombo;
	private JTextField txtValue;
	private JEditorPane result;
	private JTextField collection;
	private JTextField db;
	private JLabel lblStatus;
	
	private WindowActionReceptor receptor;
	private Request req;
	private JProgressBar progressBar;
	private JLabel lblDisplayingResults;

	private Map<String, String> args = new HashMap<String, String>();
	
	
	@Deprecated
	public RequestEditionScreen() {
		init();
		this.setVisible(true);
	}
	
	public RequestEditionScreen(WindowActionReceptor receptor) {
		init();
		this.setVisible(true);
		
		this.receptor = receptor;
	}
	
	public void loadRequest(Request rq) {
		this.req = rq;
		
		this.request.setText(rq.request);argsCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("e");
				System.out.println(argsCombo.getSelectedItem());
			}
		});
		this.title.setText(rq.title);
		this.dtrpnDescription.setText(rq.description);
		this.db.setText(rq.db);
		this.collection.setText(rq.collection);
		this.args = rq.parameters;
		
		this.txtValue.setText("");
		
		if(rq.lastResult != null) {
			this.result.setText(rq.lastResult.response);
			this.setStatus(rq.lastResult.isError? STATUS_FAILED:STATUS_SUCCESS);
		}
		
	}
	
	private void setStatus(int s) {
		String label = "???";
		Color c = Color.RED;
		
		if(s == STATUS_IDLE) {
			label = "Idle";
			c = Color.BLUE;
		} else if(s == STATUS_FAILED) {
			label = "Failed";
			c = Color.RED;
		} else if(s == STATUS_SUCCESS) {
			label = "OK";
			c = Color.GREEN;
		}
		
		this.lblStatus.setText(label);
		this.lblStatus.setForeground(c);
	}
	
	public Request getRequest() {
		
		this.req.request = this.request.getText();
		this.req.title = this.title.getText();
		this.req.description = this.dtrpnDescription.getText();
		this.req.db = this.db.getText();
		this.req.collection = this.collection.getText();
		this.req.parameters = this.args;
		
		return this.req;
	}
	
	public void freeze(boolean set) {
		this.progressBar.setVisible(set);
		

		this.request.setEditable(!set);
		this.title.setEditable(!set);
		this.dtrpnDescription.setEditable(!set);
		this.db.setEditable(!set);
		this.collection.setEditable(!set);
		
	}
	
	private void refreshArgs() {		
		this.argsCombo.setModel(new DefaultComboBoxModel<String>(this.getAliases(this.request.getText())));
	}
	
	private String[] getAliases(String req) {
		Pattern p = Pattern.compile("<(.*?)>");
		Matcher m = p.matcher(req);
		
		String[] array = new String[0];
		
		while(m.find()) {
			String grp = m.group();
			if(ArrayUtils.contains(array, grp))
				continue;
			
			array = (String[]) ArrayUtils.add(array, grp);
		}
		
		return array;
	}

	public void loadResponse(Request r) {
		if(r.lastResult == null) {
			this.setStatus(STATUS_IDLE);
			return;
		}
		this.result.setText(r.lastResult.response);
		this.setStatus(r.lastResult.isError?STATUS_FAILED:STATUS_SUCCESS);
	}
	
	
	private void init() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				//CLOSE WINDOW
				receptor.onSpecialAction(RequestEditionScreen.this, MongoRequestSession.CLOSE_WINDOW);
			}
		});

		
		this.setResizable(false);
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
		execBtn.addActionListener((e) -> {
			//EXEC
			new Thread(){
				public void run() {
					try  {
						freeze(true);
						receptor.onAction(RequestEditionScreen.this, WindowActions.EXEC_REQUEST, getRequest());
					
					}catch(Exception ex) {
						ex.printStackTrace();
					}finally {
						freeze(false);
					}
				}
			}.start();
			
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
		
		argsCombo = new JComboBox<String>();
		argsCombo.addItemListener((e) -> {
			if(e.getStateChange() == ItemEvent.DESELECTED) {
				this.args.put((String) e.getItem(), this.txtValue.getText());
			} else if(e.getStateChange() == ItemEvent.SELECTED) {
				this.txtValue.setText(this.args.get(e.getItem()));
			}
		});
		
		argsCombo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				refreshArgs();
			}
		});
		argsCombo.setBounds(202, 7, 151, 24);
		panel.add(argsCombo);
		argsCombo.setForeground(Color.ORANGE);
		argsCombo.setBackground(Color.BLACK);
		
		txtValue = new JTextField();
		txtValue.setBounds(365, 10, 226, 19);
		panel.add(txtValue);
		txtValue.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		txtValue.setForeground(Color.ORANGE);
		txtValue.setText("value");
		txtValue.setBackground(Color.BLACK);
		txtValue.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(179, 264, 486, 144);
		getContentPane().add(scrollPane);
		
		result = new JEditorPane();
		result.setEditable(false);
		result.setForeground(Color.ORANGE);
		scrollPane.setViewportView(result);
		result.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		result.setBackground(Color.BLACK);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener((e) -> {
			//save
			this.receptor.onAction(this, WindowActions.SAVE_REQUEST, this.getRequest());
		});
		btnSave.setBackground(Color.BLACK);
		btnSave.setForeground(Color.GREEN);
		btnSave.setBounds(25, 306, 114, 25);
		getContentPane().add(btnSave);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener((e) -> {
			this.receptor.onAction(this, WindowActions.DELETE_REQUEST, this.getRequest());
		});
		btnDelete.setBackground(Color.BLACK);
		btnDelete.setForeground(Color.RED);
		btnDelete.setBounds(25, 343, 114, 25);
		getContentPane().add(btnDelete);
		
		lblStatus = new JLabel("Idle");
		lblStatus.setForeground(Color.BLUE);
		lblStatus.setBounds(579, 234, 86, 15);
		getContentPane().add(lblStatus);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener((e) -> {
			//close
			this.receptor.onAction(this, WindowActions.CLOSE_REQUEST_WINDOW);
		});
		btnClose.setForeground(Color.BLUE);
		btnClose.setBackground(Color.BLACK);
		btnClose.setBounds(25, 383, 114, 25);
		getContentPane().add(btnClose);
		
		JButton btnNewButton = new JButton("Compute");
		btnNewButton.addActionListener((e) -> {
			//compute
			this.receptor.onAction(this, WindowActions.COMPUTE_REQUEST, this.getRequest());	
		});
		btnNewButton.setForeground(Color.GREEN);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setBounds(25, 247, 114, 25);
		getContentPane().add(btnNewButton);
		
		db = new JTextField();
		db.setText("db");
		db.setForeground(Color.ORANGE);
		db.setBackground(Color.BLACK);
		db.setBounds(180, 210, 124, 19);
		getContentPane().add(db);
		db.setColumns(10);
		
		collection = new JTextField();
		collection.setText("collection");
		collection.setForeground(Color.ORANGE);
		collection.setBackground(Color.BLACK);
		collection.setBounds(326, 210, 124, 19);
		getContentPane().add(collection);
		collection.setColumns(10);
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		progressBar.setEnabled(false);
		progressBar.setIndeterminate(true);
		progressBar.setBounds(179, 238, 148, 14);
		getContentPane().add(progressBar);
		
		lblDisplayingResults = new JLabel("Displaying 10 results");
		lblDisplayingResults.setForeground(Color.ORANGE);
		lblDisplayingResults.setBounds(189, 420, 226, 15);
		getContentPane().add(lblDisplayingResults);
		setResizable(false);
		setSize(677,476);
	}

}
