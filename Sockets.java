import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



class Sockets
{
    public static void main(String[] args) 
    {
        
        Routs routs = new Routs();

        try
        {
            InetAddress localHost = InetAddress.getLocalHost();
            
            ServerSocket serverSocket = new ServerSocket(80,50,localHost);
            System.out.println("http://"+serverSocket.getInetAddress().getHostAddress()+" is the server localhost!");
            while(true)
            {
                Socket clientSock = serverSocket.accept();

                int requestSize = clientSock.getReceiveBufferSize();
                clientSock.setReceiveBufferSize(requestSize);
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSock.getInputStream(), "UTF-8"));
                
                ArrayList<String> headers = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) 
                {
                    if(line.contains("/favicon.ico"))
                    {
                        System.out.println("sent favicon.ico");
                        routs.writeRouteContent("/favicon.ico", clientSock.getOutputStream());
                        // clientSock.close(); //web browser wants the connection not to be closed prob
                        continue;
                    }
                    headers.add(line);
                }
                
                System.out.println(headers);
                routs.writeRouteContent("/home.html", clientSock.getOutputStream());
                System.out.println("Wrote Content to socket");
                clientSock.close();


            }

        }
        catch(Exception e)
        {
            System.err.println(e+" IS EXCEPTIOn");
        }
    

    }
}
