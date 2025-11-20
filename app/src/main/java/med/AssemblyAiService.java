package med;

import java.io.File;
import java.net.URI;
import java.util.HashMap;

import javax.xml.datatype.Duration;

public class AssemblyAiService {

    public String transcribe(String url, String lang) throws Exception{
        HTTP_Request http = new HTTP_Request(java.time.Duration.ofMinutes(2));

        URI uri = new URI(url);

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
}
