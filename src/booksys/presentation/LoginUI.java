package booksys.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class LoginUI extends JFrame {
	

	public static void main(String[] args) {
		new LoginUI();
	}
	
	public LoginUI() {
		// TODO Auto-generated constructor stub
		
		JButton loginButton, passwordButton;
		TextArea textBox,textInput;
		TextField usernametext, passwordtext;
		

		textBox=new TextArea("",14,50);
		textBox.setBackground(Color.white);
		textInput=new TextArea("",4,50);
		textInput.setBackground(Color.white);
		passwordtext=new TextField();
		passwordtext.setBackground(Color.lightGray);
		usernametext=new TextField();
		usernametext.setBackground(Color.lightGray);

		Panel p3=new Panel(); 
		p3.setLayout(new GridLayout(1,4));
		p3.add(new Label("ID: "));
		p3.add(usernametext);
		
		
		Panel p5=new Panel(); 
		p5.setLayout(new GridLayout(1,4));
		p5.add(new Label("Password: "));
		p5.add(passwordtext);

		Panel p9=new Panel(); 
		p9.setLayout(new BorderLayout());
		p9.add(p3,BorderLayout.NORTH);
		p9.add(p5,BorderLayout.SOUTH);
		
		passwordButton=new JButton("Find Password");
		passwordButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
			    String inputUsername = usernametext.getText();
			    System.out.println(inputUsername);
			    String sql1 = "SELECT phoneNumber FROM User WHERE name = '" + inputUsername + "'";
			    try {
			    	conn = DriverManager.getConnection("jdbc:sqlite:./bs.db");
			    	 Statement stmt = conn.createStatement();
			         ResultSet rset = stmt.executeQuery(sql1);
					JOptionPane.showMessageDialog(null, "Your password is: " + rset.getString(1));
			         stmt.close();
			         conn.close();
			    }catch (Exception e1) {
			    	JOptionPane.showMessageDialog(null, "Enter userID first!");
				}
			}
		});
		
		loginButton=new JButton("login");
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
			    String inputUsername = usernametext.getText();
			    System.out.println(inputUsername);
			    String sql1 = "SELECT phoneNumber FROM User WHERE name = '" + inputUsername + "'";
			    try {
			    	conn = DriverManager.getConnection("jdbc:sqlite:./bs.db");
			    	 Statement stmt = conn.createStatement();
			         ResultSet rset = stmt.executeQuery(sql1);
			         if(passwordtext.getText().equals(rset.getString(1))) {
			        	 new BookingSystemApp(usernametext.getText());
			        	 setVisible(false);
			         }else {
						JOptionPane.showMessageDialog(null, "Password or Username not correct! \n Please check again.");
			         }
			         stmt.close();
			         conn.close();
			    }catch (Exception e1) {
			    	System.out.println("Error!!");
				}
			}
		});
		
		Panel p4=new Panel(); 
		p4.add(loginButton);
		p4.add(passwordButton);

		Panel p10=new Panel(); 
		p10.setLayout(new BorderLayout());
		p10.add(p9,BorderLayout.NORTH);
		p10.add(p4,BorderLayout.SOUTH);

		setLayout(new BorderLayout());
		add(p10,BorderLayout.CENTER);
		setSize(350,120);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	
	}
	
}
