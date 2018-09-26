package database;

public class User
{
	public String user;
	public String pass;
	
	public User(String user, String pass)
	{
		this.user = user;
		this.pass = pass;
	}
	
	public String getUserName()
	{
		return user;
	}
	
	public String getPassword()
	{
		return pass;
	}
}
