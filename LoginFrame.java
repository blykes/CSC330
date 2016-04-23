import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/*******************************************************************************
 * Copyright (c) 2011 Google, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Google, Inc. - initial API and implementation
 *******************************************************************************/

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;

/**
 * Cyclic focus traversal policy based on array of components.
 * <p>
 * This class may be freely distributed as part of any application or plugin.
 * 
 * @author scheglov_ke
 */
class FocusTraversalOnArray extends FocusTraversalPolicy {
	private final Component m_Components[];
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	public FocusTraversalOnArray(Component components[]) {
		m_Components = components;
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Utilities
	//
	////////////////////////////////////////////////////////////////////////////
	private int indexCycle(int index, int delta) {
		int size = m_Components.length;
		int next = (index + delta + size) % size;
		return next;
	}
	private Component cycle(Component currentComponent, int delta) {
		int index = -1;
		loop : for (int i = 0; i < m_Components.length; i++) {
			Component component = m_Components[i];
			for (Component c = currentComponent; c != null; c = c.getParent()) {
				if (component == c) {
					index = i;
					break loop;
				}
			}
		}
		// try to find enabled component in "delta" direction
		int initialIndex = index;
		while (true) {
			int newIndex = indexCycle(index, delta);
			if (newIndex == initialIndex) {
				break;
			}
			index = newIndex;
			//
			Component component = m_Components[newIndex];
			if (component.isEnabled() && component.isVisible() && component.isFocusable()) {
				return component;
			}
		}
		// not found
		return currentComponent;
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// FocusTraversalPolicy
	//
	////////////////////////////////////////////////////////////////////////////
	public Component getComponentAfter(Container container, Component component) {
		return cycle(component, 1);
	}
	public Component getComponentBefore(Container container, Component component) {
		return cycle(component, -1);
	}
	public Component getFirstComponent(Container container) {
		return m_Components[0];
	}
	public Component getLastComponent(Container container) {
		return m_Components[m_Components.length - 1];
	}
	public Component getDefaultComponent(Container container) {
		return getFirstComponent(container);
	}
}

class User
{
	private String username;
	private Set<String> following;
	
	public User(String username) throws IOException
	{
		this.username = username;
		following = new HashSet<String>();
		
		populateFollowers();
	}
	
	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public Set<String> getfollowing() 
	{
		return following;
	}
	
	public void follow(String userToFollow) throws IOException
	{
		if(!following.contains(userToFollow))
		{
			File f = new File("following.txt");
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			if(!f.exists())
				f.createNewFile();
			
			bw.write(username + ":" + userToFollow);
			bw.newLine();
			bw.close();
			
			following.add(userToFollow);
		}
	}
	
	public void populateFollowers() throws IOException
	{
		following.clear();
		
		File f = new File("following.txt");
		
		if(f.exists())
		{
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line;
		
			while((line = br.readLine()) != null)
			{
				if(line.contains(":"))
				{
					String currentUsername = line.substring(0, line.indexOf(':'));
					
					if(currentUsername.equals(username))
						following.add(line.substring(line.indexOf(':') + 1));
				}
			}
			
			br.close();
		}
	}
}

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JButton btnLogIn;
	private JPasswordField passwordField;
	public static LoginFrame loginFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginFrame = new LoginFrame();
					loginFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void MessageBox(String text)
	{
		JOptionPane.showMessageDialog(null,  text);
	}
	
	private int MessageBoxQuestion(String text, String title)
	{
		return JOptionPane.showConfirmDialog(null, text, title, JOptionPane.YES_NO_OPTION);
	}
	
	private void registerUser(String username, String password) throws IOException
	{
		File f = new File("users.txt");
		FileWriter fw = new FileWriter(f, true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		if(!f.exists())
			f.createNewFile();

		bw.write(username + ":" + password);
		bw.newLine();
		bw.close();
	}
	
	private String getPassword(String username) throws IOException
	{
		File f = new File("users.txt");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line;
		String password = null;
		
		while((line = br.readLine()) != null && password == null)
		{
			if(line.startsWith(username))
				password = line.substring(line.indexOf(':') + 1, line.length());
		}
		
		br.close();
		
		return password;
	}
	
	private boolean findUser(String username) throws IOException
	{
		File f = new File("users.txt");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line;
		boolean found = false;
		
		while((line = br.readLine()) != null && !found)
		{
			if(line.startsWith(username))
			{
				found = true;
			}
		}
		
		br.close();
		
		return found;
	}
	
	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("LetItGo.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-30.0f);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setTitle("Login");
		setIconImage(new ImageIcon(getClass().getResource("twitter.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 425, 174);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(null);
		contentPane.setToolTipText("");
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
					passwordField.grabFocus();
			}
		});
		textField.setBounds(146, 39, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		passwordField = new JPasswordField();
		passwordField.setBounds(146, 70, 86, 20);
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0)
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
						btnLogIn.doClick();
			}
		});
		contentPane.add(passwordField);
		
		lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(71, 39, 76, 17);
		contentPane.add(lblUsername);
		
		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(71, 70, 76, 17);
		contentPane.add(lblPassword);
		
		btnLogIn = new JButton("Log In");
		btnLogIn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					btnLogIn.doClick();
				}
			}
		});
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText().length() <= 0 || passwordField.getPassword().length <= 0)
				{
					MessageBox("Invalid username or password");
					return;
				}
				
				File file = new File("users.txt");

				try {
					if(!file.exists() || !findUser(textField.getText()))
					{
						if(MessageBoxQuestion("Register now?", "You are not registered") == JOptionPane.YES_OPTION)
						{
							try {
								registerUser(textField.getText(), String.valueOf(passwordField.getPassword()));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					else
					{
						if(String.valueOf(passwordField.getPassword()).equals(getPassword(textField.getText())))
						{
							
							setVisible(false);
							ClientFrame frame = new ClientFrame(new User(textField.getText()));
							frame.setVisible(true);
							textField.setText("");
							passwordField.setText("");
							textField.grabFocus();
						}
						
						else
						{
							MessageBox("Wrong password");
							passwordField.setText("");
							passwordField.grabFocus();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnLogIn.setBounds(266, 39, 89, 51);
		contentPane.add(btnLogIn);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textField, passwordField, btnLogIn}));
	}
}
