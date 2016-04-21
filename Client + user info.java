public class Client
{
	private User currentUser = null;
	
	//todo: currentUser.followers.at(0).getFeeds();
	
	/**
	 * chooses an account to log in as
	 * @param userName the username of account
	 * @param password the password of account
	 */
	public void logIn(String userName, String password)
	{
		if(currentUser != null) //return if already logged in and havent logged out yet
			return;
		
		File file = new File("users.txt"); //open users.txt file this is where the list of users and passwords are stored
		/* Format of users.txt:
		username1:password1 <-- no spaces
		username2:password2 <-- new line for every user
		*/
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = null; //temp var for reading file line by line
		boolean found = false; //user found?
		
		while((line = br.readLine()) != null) //while we have lines left in the text file
		{
			if(line.startsWith(userName)) //if the current line is the requested userName login
			{
				found = true; //we found the user
				String realPassword = line.substring(line.indexOf(':') + 1, line.length()); //store real password
				
				if(password.equals(realPassword)) //if the password passed into this function matches the real password
				{
					currentUser = new User(userName); //set currentUser the client logged into as the user
					break;
				}
				
				else
				{
					//invalid password
					break;
				}
			}
		}
		
		if(found == false)
		{
			//user not registered, should probably prompt user if want to register or not
		}
		
		br.close();
	}
	
	/**
	 * logs out of current account
	 */
	public void logOut()
	{
		currentUser = null; //allow garbage collection
	}
	
	/**
	 * registers user
	 * @param userName desired user name
	 * @param password desired password
	 */
	public void registerUser(string userName, string password)
	{
		File file = new File("users.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		string line = null;
		bool found = false;
		
		while((line = br.readLine()) != null && found == false) //read all lines or until we found that user alerady exists
		{
			if(line.startWith(userName)) //user exists
			{
				found = true; //exit loop
			}
		}
		
		if(found)
		{
			//already registered
		}
		
		else //register user
		{
			FileWriter fw = new FileWriter(file, true); //the true is to tell filewriter that we will append to end of file instead of overwriting
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(userName + ":" + password); //add username:password line to users.txt file
			bw.newLine(); //add newline to file
		}
		
		bw.close();
	}
	
	public void showFeeds()
	{
		if(currentUser != null) //logged in
		{
			//show feeds for specific current user
		}
		
		else
		{
			//do nothing cause not logged in
		}
	}
}

public class User
{
	private String userName;
	private List<User> followers;
	
	public User(String userName)
	{
		//populate followers list
		File file = new File("followers.txt");
		/* format of followers.txt
		username:followerUserName
		username:followerUserName2
		
		*/
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public List<User> getFollowers()
	{
		return followers;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
}