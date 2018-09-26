package main;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

import database.DBManager;
import database.DataManager;

public class Main
{
	private static HttpServer server;
	
	public static void main(String[] args)
	{
			DataManager.createUserTable();
			DataManager.createMessageTable();
			DBManager manager = DBManager.getInstance();
			
			try { server = HttpServer.create(new InetSocketAddress(8080), 0); }
			catch(IOException ioe) {
				System.out.println("Error al crear servidor");
				return;
			}
			server.createContext("/login", new LoginHandler());
			server.createContext("/mensajes", new MessagesHandler());
			server.createContext("/registrar", new RegisterHandler());
			server.createContext("/mandar_mensaje", new SendMessageHandler());
			server.start();
			
			String str;
			do {
				str = Dentre.texto(">");
			} while(!str.equals("quit"));
			
			server.stop(0);
			try { manager.shutdown(); }
			catch(Exception e) {
				System.out.println("Error al apagar la base de datos");
			}
	}
}