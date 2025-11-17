import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class Sockets
{
    public static void main(String[] args) 
    {
        


        try
        {
                InetAddress localHost = InetAddress.getLocalHost();

                ServerSocket serverSocket = new ServerSocket(80,50,localHost);
                System.out.println("http://"+serverSocket.getInetAddress()+" is the server localhost!");
                Socket clientSock = serverSocket.accept();
                int requestSize = clientSock.getReceiveBufferSize();
                clientSock.setReceiveBufferSize(requestSize);
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSock.getInputStream(), "UTF-8"));
                
                ArrayList<String> headers = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) 
                {
                    headers.add(line);
                }
                
                System.out.println(headers);
            

            // new InputStreamReader( clientSock.getReceiveBuffer());
            

        }
        catch(Exception e)
        {
            System.err.println(e+" IS EXCEPTIOn");
        }
    

    }
}
