package exemplos.socket.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SocketClientHTTP
{
    public static void main( String[] args )
    {
    	String server = "";
        String path = "";

        if( args.length < 2 )
        {
            System.out.println( "USE ASSIM: SocketClientHTTP <server> <path>" );
        	server = "www.ifpa.edu.br";
            path = "/";
        }else {
        	server = args[ 0 ];
            path = args[ 1 ];
        }
        System.out.println("CONTEUDO DO SERVER = "+server + "   PATH="+path
        		+ "\n=======================================================\n");        	
    
        try
        {
            // Connecta ao servidor HTTP (porta padrao=80)
            Socket socket = new Socket( server, 80 );

            // Cria streams de input e output para ler e gravar o conteudo do servidor  
            PrintStream out = new PrintStream( socket.getOutputStream() );
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream() ) );

            // Seguindo o protocolo HTTP, envia o comando GET <path> HTTP/1.0 seguido de uma linha vazia 
            out.println( "GET " + path + " HTTP/1.0" );
            out.println();

            // Ler os dados do servidor até encontrar o final do documento/conteudo 
            String line = in.readLine();
            while( line != null )
            {
                System.out.println( line );
                line = in.readLine();
            }

            // Close/fecha os streams e a porta socket
            in.close();
            out.close();
            socket.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}