import javax.print.attribute.standard.Media;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

class Tweet
{
	private String username;
	private String tweet;
	private LocalDateTime dateTime;
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getTweet()
	{
		return tweet;
	}
	
	public void setTweet(String tweet)
	{
		this.tweet = tweet;
	}
	
	public LocalDateTime getDateTime()
	{
		return dateTime;
	}
	
	public void setDateTime(LocalDateTime dateTime)
	{
		this.dateTime = dateTime;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "@" + username + " " + dateTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")) + "\n" + tweet;
	}
}

public class ClientFrame extends JFrame {

	private JPanel contentPane;
	private TextArea textArea;
	private List<Tweet> tweetsList;
	private Stack<Tweet> tweets;
	private TextArea textArea_1;
	private Timer timer;
	private int tweetsLength;
	private JLabel lblNewLabel;
	private JLabel lblPost;
	private JTabbedPane tabbedPane;
	private JPanel panel_1;
	private JList<String> list;
	private User user;
	private JList<String> list_1;
	private JLabel lblFollowing;
	
	private void postTweet() throws IOException
	{
		File file = new File("tweets.txt");
		FileWriter fw = new FileWriter(file, true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		if(!file.exists())
			file.createNewFile();
		
		bw.write("[Tweet By:" + user.getUsername() + "][" + "DateTime:" + LocalDateTime.now().toString() + "]");
		bw.newLine();
		bw.write(textArea.getText());
		bw.newLine();
		
		bw.close();
	}
	
	private void populateTweets() throws IOException
	{
		if(tweetsList == null)
			tweetsList = new ArrayList<Tweet>();
		
		else if(!tweetsList.isEmpty())
			tweetsList.clear();
		
		if(tweets == null)
			tweets = new Stack<Tweet>();

		else if(!tweets.isEmpty())
			tweets.clear();
		
		File file = new File("tweets.txt");
		
		if(!file.exists())
			return;
		
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		List<String> tweetList = Files.readAllLines(Paths.get("tweets.txt"));
		Tweet tweet = new Tweet();
		String tweetMsg = null;
		
		for(Iterator<String> it = tweetList.iterator(); it.hasNext();)
		{
			String line = it.next();
			
			if(line.startsWith("[Tweet By:"))
			{
				if(tweet.getUsername() != null)
				{
					tweet.setTweet(tweetMsg);
					tweetsList.add(tweet);
					tweet = new Tweet();
				}
					
				tweet.setUsername(line.substring("[Tweet By:".length(), line.indexOf(']')));
				tweet.setDateTime(LocalDateTime.parse(line.substring(line.indexOf("DateTime:") + "DateTime:".length(), line.length() - 1)));
				tweetMsg = "";
			}

			else
			{
				tweetMsg += line += '\n';

				if(!it.hasNext())
				{
					tweet.setTweet(tweetMsg);
					tweetsList.add(tweet);
				}
			}
		}
		
		br.close();
	}
	
	private void updateFeeds()
	{
		textArea_1.setText("");

		for(Tweet t : tweetsList)
		{
			if(t.getUsername().equals(user.getUsername()) || user.getfollowing().contains(t.getUsername()))
				tweets.push(t);
		}
		
		while(!tweets.empty())
		{
			Tweet currentTweet = tweets.pop();
			
			if(currentTweet.getUsername().equals(user.getUsername()) || user.getfollowing().contains(currentTweet.getUsername()))
				textArea_1.append(currentTweet.toString() + (new String(new char[90]).replace('\0', '-')) + "\n");
		}
		
		textArea_1.setCaretPosition(0);
	}
	
	private void updateFollowList() throws IOException
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		Set<String> following = user.getfollowing();
		File file = new File("users.txt");
		
		if(file.exists())
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			
			while((line = br.readLine()) != null)
			{
				if(!line.contains(":"))
					continue;
				
				String currentUserName = line.substring(0, line.indexOf(':'));
				
				if(!following.contains(currentUserName) && !user.getUsername().equals(currentUserName))
					model.addElement(currentUserName);
			}
		
			br.close();
			list.setModel(model);
		}
	}
	
	private void updateFollowingList() throws IOException
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		Set<String> following = user.getfollowing();

		for(String s : following)
			model.addElement(s);
		
		list_1.setModel(model);
	}
	
	/**
	 * Create the frame.
	 */
	public ClientFrame(User user) {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				LoginFrame.loginFrame.setVisible(true);
			}
		});
		this.user = user;
		setTitle("Twitter - Logged in as " + this.user.getUsername());
		setIconImage(new ImageIcon(getClass().getResource("twitter.png")).getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 483, 570);
		contentPane = new JPanel();
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, getWidth(), getHeight());
		panel.setBorder(null);
		panel.setLayout(null);
		
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(null);
		setContentPane(panel);
		contentPane.setLayout(null);
		
		textArea = new TextArea();
		textArea.setBounds(90, 29, 361, 160);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textArea.getText().length() > 0)
					try {
						LoginFrame.MessageBox("Tweeted");
						postTweet();
						populateTweets();
						updateFeeds();
						textArea.setText("");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else
					LoginFrame.MessageBox("Please type something");
			}
		});
		btnNewButton.setIcon(new ImageIcon(getClass().getResource("tweet.jpg")));
		btnNewButton.setBounds(131, 208, 239, 84);
		contentPane.add(btnNewButton);
		
		textArea_1 = new TextArea();
		textArea_1.setEditable(false);
		textArea_1.setBounds(90, 317, 361, 160);
		contentPane.add(textArea_1);
		
		lblNewLabel = new JLabel("Feeds");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(10, 390, 75, 23);
		contentPane.add(lblNewLabel);
		
		lblPost = new JLabel("Post:");
		lblPost.setForeground(Color.BLACK);
		lblPost.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblPost.setBounds(10, 73, 75, 23);
		contentPane.add(lblPost);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if(tabbedPane.getSelectedIndex() == 1)
					try {
						updateFollowList();
						updateFollowingList();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		
		tabbedPane.setBounds(0, 0, 482, 546);
		tabbedPane.addTab("Tweets", contentPane);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.CYAN);
		panel_1.setBounds(0, 0, 477, 518);
		tabbedPane.addTab("Following", panel_1);
		panel_1.setLayout(null);
		
		list = new JList<String>();
		list.setBounds(117, 31, 214, 94);
		panel_1.add(list);
		
		JButton btnNewButton_2 = new JButton("Follow");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String value = list.getSelectedValue();
					
					if(value != null)
					{
						user.follow(value);
						LoginFrame.MessageBox("Followed");
					}
					
					else
						LoginFrame.MessageBox("Please select a user to follow");
					
					updateFollowList();
					updateFollowingList();
					updateFeeds();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(355, 61, 89, 23);
		panel_1.add(btnNewButton_2);
		
		JLabel lblUnfollowedUsers = new JLabel("Available Users");
		lblUnfollowedUsers.setBounds(117, 11, 97, 14);
		panel_1.add(lblUnfollowedUsers);
		
		list_1 = new JList<String>();
		list_1.setBounds(117, 192, 214, 94);
		panel_1.add(list_1);
		
		lblFollowing = new JLabel("Following");
		lblFollowing.setBounds(117, 167, 97, 14);
		panel_1.add(lblFollowing);
		
		panel.add(tabbedPane);

		try {
			populateTweets();
			updateFeeds();
			
			if(Files.exists(Paths.get("tweets.txt")))
				tweetsLength = Files.readAllBytes(Paths.get("tweets.txt")).length;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if(Files.exists(Paths.get("tweets.txt")))
					{
						byte[] fileBytes = Files.readAllBytes(Paths.get("tweets.txt"));
						if(fileBytes.length != tweetsLength)
						{
							tweetsLength = fileBytes.length;
							populateTweets();
							updateFeeds();
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		
		timer = new Timer(1000, listener);
		timer.start();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();

		if(timer.isRunning())
			timer.stop();
	}
}
