package main;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.Arrays;
import java.util.List;

import database.DataManager;
import database.User;

public class RegisterHandler implements HttpHandler //?user=X&pass=Y
{
	public void handle(HttpExchange exchange) throws IOException
	{
		String response = null, userName = null, pass = null;
		
		List<String> params = Arrays.asList(exchange.getRequestURI().getQuery().split("&"));
		userName = params.get(0).split("=")[1];
		pass = params.get(1).split("=")[1];
		
		if(DataManager.getUser(userName) == null) {
			DataManager.addUser(new User(userName, pass));
			response = "Usuario creado correctamente";
		}
		else response = "El nombre de usuario ya existe";
		
		exchange.sendResponseHeaders(200, response.getBytes().length); //GET result body
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}