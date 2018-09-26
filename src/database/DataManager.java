package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DataManager
{
	private static DBManager manager = DBManager.getInstance();
	
	public static void createUserTable()
	{
		if(!manager.tableExists("users"))
		{
			Connection con = manager.connect();
			try {
				Statement s = con.createStatement();
				s.executeQuery("CREATE TABLE users (user VARCHAR(15) PRIMARY KEY, pass VARCHAR(15) NOT NULL)");
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createMessageTable()
	{
		if(!manager.tableExists("messages") && manager.tableExists("users"))
		{
			Connection con = manager.connect();
			try {
				Statement s = con.createStatement();
				s.executeQuery("CREATE TABLE messages (id INTEGER IDENTITY, userFrom VARCHAR(15), userTo VARCHAR(15), message VARCHAR(100) NOT NULL, FOREIGN KEY (userFrom) REFERENCES users(user), FOREIGN KEY (userTo) REFERENCES users(user))");
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addUser(User user)
	{
		String query = "INSERT INTO users (user, pass) VALUES ('" + user.getUserName() + "', '" + user.getPassword() + "')";
		Connection c = manager.connect();
		
		try
		{
			Statement s = c.createStatement();
			s.executeUpdate(query);
			c.commit();
		}
		catch(SQLException e)
		{
			try {
				c.rollback();
				e.printStackTrace();
			}
			catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/*public static void deleteUser(String user)
	{
		String query = "DELETE FROM users WHERE user = " + user;
		Connection con = manager.connect();
		
		try {
			Statement st = con.createStatement();
			st.executeUpdate(query);
			con.commit();
		}
		catch(SQLException e)
		{
			try {
				con.rollback();
				e.printStackTrace();
			}
			catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
	}*/
	
	public static User getUser(String name)
	{
		Connection con = manager.connect();
		User result = null;
		
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM users WHERE user = '" + name + "'");
			if(rs.next()) {
				result = new User(rs.getString("user"), rs.getString("pass"));
			}
			con.commit();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void addMessage(Message msg)
	{
		String query = "INSERT INTO messages(userFrom, userTo, message) VALUES ('" + msg.getRemitent() + "', '" + msg.getReceiver() +"', '" + msg.getContent() + "')";
		Connection con = manager.connect();
		
		try {
			Statement st = con.createStatement();
			st.executeUpdate(query);
			con.commit();
		}
		catch(SQLException e)
		{
			try {
				con.rollback();
				e.printStackTrace();
			}
			catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public static ArrayList<Message> getMessages(String userTo)
	{
		String query = "SELECT * FROM messages WHERE userTo = '" + userTo + "'";
		Connection con = manager.connect();
		ArrayList<Message> messages = new ArrayList<Message>();
		
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				Message msg = new Message(rs.getString("userFrom"), rs.getString("userTo"), rs.getString("message"));
				messages.add(msg);
			}
		}
		catch(SQLException e) { e.printStackTrace(); }
		
		return messages;
	}
}