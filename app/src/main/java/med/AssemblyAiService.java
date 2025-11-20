package med;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;

import javax.xml.datatype.Duration;

public class AssemblyAiService {

    private String transcribeText(String url, String lang) throws Exception{
        HTTP_Request http = new HTTP_Request(java.time.Duration.ofMinutes(2));

        URI uri = new URI("https://api.assemblyai.com/v2/transcript");

        HashMap<String,String> data = new HashMap<>();
        data.put("audio_url", url);
        data.put("language_code",lang);

        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization", key.assemblyai_apikey.getApikey());

        String[] returnList = {"id"};

        HashMap<String,String> returner = http.POST(uri, data, headers, returnList);

        String id = returner.get("id");
        return id;
    }

    private String getText(String id) throws Exception{
        HTTP_Request http = new HTTP_Request(java.time.Duration.ofMinutes(2));

        URI uri = new URI("https://api.assemblyai.com/v2/transcript/"+id);

        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization", key.assemblyai_apikey.getApikey());

        String[] returnList = {"text"};

        HashMap<String,String> returner = http.GET(uri,headers,returnList,"completed","error");
        String txt = returner.get("text");
        return txt;
    }

    private String uploadAudio(File f) throws Exception{
        HTTP_Request http = new HTTP_Request(java.time.Duration.ofMinutes(2));

        URI uri = new URI("https://api.assemblyai.com/v2/upload");

        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization", key.assemblyai_apikey.getApikey());
        headers.put("Content-Type", "application/octet-stream");

        Path p = Path.of(f.getAbsolutePath());

        String[] returnList = {"upload_url"};

        HashMap<String,String> returner = http.POST(uri, p,headers,returnList);

        String url = returner.get("upload_url");
        return url;
    }
    public String run(File f,String lang) throws Exception{
        String url = uploadAudio(f);
        String id = transcribeText(url, lang);
        String txt = getText(id);
        return txt;
    }

}
