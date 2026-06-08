import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
                String fixedPathStr = fixPath(fileEntry.getPath());
                try
                {
                    _routingMap.put(fixedPathStr, new FileReader(fileEntry));
                    System.out.println("PUT "+fixedPathStr+" : "+fileEntry);
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
        public String fixPath(String route)
    {
        String buffRoute = "";
        if(route.startsWith(".\\"))
        {
            buffRoute = route.substring(1,route.length());
        }
        else
        {
            buffRoute = route;
        }
        
        buffRoute = buffRoute.replace("\\", "/").replace("/wwwroot","");
        return buffRoute;

    }
    public void setErrorPage(String route)
    {
        Set<Map.Entry<String, FileReader>> entries = _routingMap.entrySet();
        for(Map.Entry<String,FileReader> entry : entries)
        {
            if(entry.getKey() == route)
            {
                _errorPage = entry;
            }
        }

    }
    public void writeErrorContent(OutputStream stream)
    {
        _errorPage.getValue();
    }
    public void writeRouteContent(String route, OutputStream stream)
    {
        if(_routingMap.get(route) == null)
        {
            System.out.println("invlaid route bud : "+route);
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
            System.out.println("SOME FUCKERYSFDFKDSFKSL : "+E);

        }
        
    }
    public HashMap<String,FileReader> _routingMap= new HashMap<>();
    public Map.Entry<String, FileReader> _errorPage;

}
