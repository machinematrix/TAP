package main;
import java.util.Date;

import database.User;

public class Token
{
	private Date date;
	//private UUID id = UUID.randomUUID();
	private User owner;
	
	public Token(User user)
	{
		owner = user;
		date = new Date();
	}
	
	public boolean hasExpired()
	{
		if(new Date().getTime() - date.getTime() > 600000) return true; //600000 == 10 minutos
		else return false;
	}
	
	public User getUser()
	{
		return owner;
	}
}