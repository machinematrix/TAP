package main;
import java.io.*;

public class Dentre
{
	public static  String texto(String mensaje)
	{
		try
		{
			System.out.print(mensaje);
			String entrada = new BufferedReader(
			new InputStreamReader( System.in)).readLine();
			
			return entrada;
		}
		catch(IOException e) 
		{
			System.out.print(e); 
	       	System.exit(1);
			return e.toString(); 
	     }
	}

	/* Metodo que permite entrar UN caracter y, como el anterior, se le pasa como
	argumento el texto para solicitar dicho caracter */

	/*public static char caracter(String mensaje)
	{
		String aux = texto( mensaje );
		return aux.charAt(0);
	}

	public static int entero(String mensaje) 
	{ 
		Integer dato = new Integer(texto(mensaje)); 
		return dato.intValue();  
	}
	
	public static long largo(String mensaje)
	{
		Long dato = new Long(texto(mensaje));
		return dato.longValue();
	}
		 
	public static float flotante(String mensaje)
	{ 
		Float dato = new Float(texto(mensaje)); 
		return dato.floatValue();  
	} 

	public static double doble(String mensaje)
	{
		Double dato = new Double(texto(mensaje));
		return dato.doubleValue();  
	}*/
}