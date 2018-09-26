package main;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import database.DataManager;
import database.User;
import database.Message;

public class SendMessageHandler implements HttpHandler //?token=XXX&receiver=YYY&message=ZZZ
{
	public void handle(HttpExchange exchange) throws IOException
	{
		TokenManager tokenManager = TokenManager.getInstance();
		Token token = null;
		UUID uuid = null;
		String response = new String(), tokenUUID = null;
		User remitent = null, receiver = null;
		
		List<String> params = Arrays.asList(exchange.getRequestURI().getQuery().split("&"));
		tokenUUID = params.get(0).split("=")[1];
		uuid = UUID.fromString(tokenUUID);
		token = tokenManager.getToken(uuid);

		if(token != null && !token.hasExpired())
		{
			remitent = DataManager.getUser(token.getUser().getUserName());
			receiver = DataManager.getUser(params.get(1).split("=")[1]);
			if(remitent != null && receiver != null) {
				DataManager.addMessage(new Message(remitent.getUserName(), receiver.getUserName(), params.get(2).split("=")[1]));
				response = "Mensaje enviado";
			}
			else response = "Usuario no valido";
		}
		else
		{
			tokenManager.deleteToken(uuid);
			response = "Token no valido";
		}
		
		exchange.sendResponseHeaders(200, response.getBytes().length); //GET result body
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}