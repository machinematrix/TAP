package main;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import database.DataManager;
import database.User;
import database.Message;

public class MessagesHandler implements HttpHandler //?token=XXX
{
	public void handle(HttpExchange exchange) throws IOException
	{
		TokenManager tokenManager = TokenManager.getInstance();
		Token token = null;
		UUID uuid = null;
		String response = new String(), tokenUUID = null;
		User user = null;
		
		List<String> params = Arrays.asList(exchange.getRequestURI().getQuery().split("&"));
		tokenUUID = params.get(0).split("=")[1];
		uuid = UUID.fromString(tokenUUID);
		token = tokenManager.getToken(uuid);

		if(token != null && !token.hasExpired())
		{
			user = DataManager.getUser(token.getUser().getUserName());
			if(user != null)
			{
				ArrayList<Message> messages = DataManager.getMessages(token.getUser().getUserName());
				
				if(!messages.isEmpty())
				{
					for(Message msg: messages) {
						response = response.concat("From: " + msg.getRemitent() + "\n" + msg.getContent() + "\n\n");
					}
				}
				else response = "No tiene mensajes";
			}
		}
		else {
			tokenManager.deleteToken(uuid);
			response = "Token no valido";
		}
		
		exchange.sendResponseHeaders(200, response.getBytes().length); //GET result body
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}