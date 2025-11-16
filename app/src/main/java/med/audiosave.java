package med;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class audiosave {
    private ArrayList<File> ar = new ArrayList<>();
    public void save(File e) throws FileNotFoundException,IOException{ //automatically saves a record of the audios while its being recorded
        File f = new File(e.getParentFile().getAbsolutePath(),generateName(true));
        ar.add(f);
    }
    public void commit(File tmp) throws Exception{ //merges all temp recordings to single recording when the recording finally stops by the users action
        File path = new File(tmp.getParentFile().getAbsolutePath(),generateName(false));
        FileWriter f = new FileWriter(path);
        for (int i = 0; i< ar.size(); i++){
            File data;
        }
    }
    private String generateName(boolean temp){ //to create file and return the path
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String date = LocalDateTime.now().format(formatter);
        String name = (!temp?"LOG_"+date+".wav":"_"+date+".wav"); //temp file? if not temp : if temp
        return name;
    }
}
