package main;
import java.util.Hashtable;
import java.util.UUID;
import database.User;

public class TokenManager
{
	private Hashtable<UUID, Token> tokens = new Hashtable<UUID, Token>();
	private static TokenManager instance = null;
	
	private TokenManager() {}
	
	public static TokenManager getInstance()
	{
		if(instance == null) {
			instance = new TokenManager();
		}
		return instance;
	}
	
	public Token getToken(UUID id)
	{
		return tokens.get(id);
	}
	
	public UUID createToken(User user)
	{
		UUID id = UUID.randomUUID();
		tokens.put(id, new Token(user));
		return id;
	}
	
	public void deleteToken(UUID id)
	{
		tokens.remove(id);
	}
}