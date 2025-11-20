package med;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.http.HttpRequest;
import java.nio.file.Path;
//The main class to run the application and display the output logs and results
public class Main {

    /*private static String getDir(String folders,String name) throws IOException{
        
        File dir = new File(System.getProperty("user.dir")).getAbsoluteFile();
        while (dir != null && !dir.getName().equals("app")) {
            dir = dir.getParentFile();
            System.out.println(dir);
        }//walk upward till we reach the 'app' folder if it even exists
        if (dir == null){
            dir = new File(System.getProperty("user.dir","/app")); //if 'app' is never found
        }
        //creating the path
        File target = new File(dir,folders);
        target.mkdirs(); //makes required folders
        if (!name.isEmpty()) return target.getAbsolutePath();
        else {
            target = new File (dir, folders+"/"+name);
            target.createNewFile();
            return target.getAbsolutePath();
        }

    } // to be used at a later time */ 

    

    public static void main(String[] args) throws Exception {
        /*System.out.println("Working dir: " + System.getProperty("user.dir"));
        System.out.println("Java home: " + System.getProperty("java.home"));
        System.out.println("Java version: " + System.getProperty("java.version"));*/

        System.out.println("Welcome to the Medical Report Application!");

        System.out.print("Please enter to start recording audio: ");
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
        String s = rd.readLine();
        AudioRecorder recorder = new AudioRecorder();
        File f = new File("audios/aud.wav");
        f.getParentFile().mkdirs();
        f.createNewFile();
        recorder.startRecording(f,rd);

        

    }
}
