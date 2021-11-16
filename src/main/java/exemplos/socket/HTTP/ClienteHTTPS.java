package exemplos.socket.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.SSLSocketFactory;

public class ClienteHTTPS {
	public static void main(String args[]) throws Exception {
		acessaVerisign(); 
	}
	
	
	public static void acessaVerisign() throws Exception  {
		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");

	    URL url = new URL("https://www.verisign.com/");
	    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

	    String line;
	    while ((line = in.readLine()) != null) {
	      System.out.println(line);
	    }
	    in.close();
	}
	
	public static void acessaIFPA() throws Exception  {
		 // preparar o código com o certificado do IFPA 
		
	}
	
}
