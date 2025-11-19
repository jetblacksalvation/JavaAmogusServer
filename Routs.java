import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map;

public class Routs 
{
    public Routs()
    {
        listFilesForFolder(new File("./wwwroot"),"/");
    }
    public void listFilesForFolder(final File folder, String routeName) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                //for debug lol
                //i wrote this high it, this code sucks vvv 
                //System.out.println(fileEntry.getName()+", "+((fileEntry.getName().indexOf('.')>0)?routeName+fileEntry.getName().substring(0,fileEntry.getName().indexOf('.')):routeName+fileEntry.getName())+'/' );
                listFilesForFolder(fileEntry,routeName+fileEntry.getName()+'/');
            } 
            else if(fileEntry.isFile()) {
                
                try
                {
                    _routingMap.put(routeName, new FileReader(fileEntry));
                    System.out.println("PUT "+routeName +" : "+fileEntry);
                }
                catch(Exception E)
                {
                    System.out.println("Somethign got fucked :( fuckery is -> "+E);
                    //verbose error handling,withoutt this the compiler complains... C++ would never smh
                }
                System.out.println(fileEntry.getName()+", "+routeName+fileEntry.getName() );
            }
        }
    }
    public void writeRouteContent(String route, OutputStream stream)
    {
        if(_routingMap.get(route) == null)
        {
            System.out.println("invlaid route bud..."+route);
        }
        BufferedReader fileReader = new BufferedReader(_routingMap.get(route));
        try
        {
            String line;
            while ((line = fileReader.readLine()) != null) {
                // Convert the line (characters) to bytes using a specific charset
                byte[] lineBytes = line.getBytes(StandardCharsets.UTF_8);
                stream.write(lineBytes);

                // Add a newline character if desired, as readLine() consumes it
                //stream.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
            }


        }
        catch (Exception E)
        {

        }
        
    }
    public HashMap<String,FileReader> _routingMap=new HashMap<>();

}
