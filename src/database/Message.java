package database;

public class Message
{
	private String from;
	private String to;
	private String msg;
	
	public Message(String userFrom, String userTo, String msg)
	{
		from = userFrom;
		to = userTo;
		this.msg = msg;
	}
	
	public String getRemitent()
	{
		return from;
	}
	
	public String getReceiver()
	{
		return to;
	}
	
	public String getContent()
	{
		return msg;
	}
}