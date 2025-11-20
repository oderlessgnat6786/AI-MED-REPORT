package med;
// This class will handle HTTP requests for the application

import java.io.File;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;



public class HTTP_Request {
    HttpClient client;;
    Gson gson;;
    HTTP_Request(Duration time){
            this.client = HttpClient.newBuilder()
                .connectTimeout(time)
                .build();
            this.gson = new Gson();
        }
    /*public String upload(Path path,String api) throws Exception{
        
        transcriptAssemblyAI transcript = new transcriptAssemblyAI();

        String Key = key.assemblyai_apikey.getApikey();

        
        String json = gson.toJson(transcript);

        HttpRequest post = HttpRequest.newBuilder()
        .uri(new URI(api))
        .header("Authorization",Key)
        .header("Content-Type", "application/octet-stream")
        .POST(BodyPublishers.ofFile(path))
        .build();
        HttpResponse<String> response = client.send(post, BodyHandlers.ofString());
        System.out.println(response.body());
        JsonObject jsonObj = gson.fromJson(response.body(), JsonObject.class);
        
        System.out.println(response.body()+"\nURL will probably be returned back to the method call");
        
        return jsonObj.get("upload_url").getAsString();

    }

    public String call(String audio, String lang, String api) throws Exception { //assembly_ai

        transcriptAssemblyAI transcript = new transcriptAssemblyAI();
        transcript.setAudio_url(audio);
        transcript.setLanguage_code(lang);
        String Key = key.assemblyai_apikey.getApikey();

        
        String json = gson.toJson(transcript);

        HttpRequest post = HttpRequest.newBuilder()
        .uri(new URI(api))
        .header("Authorization",Key)
        .POST(BodyPublishers.ofString(json))
        .build();

        

        HttpResponse<String> response = client.send(post, BodyHandlers.ofString());
        System.out.println(response.body()+"\nID will probably be returned back to the method call");
        transcript = gson.fromJson(response.body(), transcriptAssemblyAI.class);
        return transcript.getId();
    }

    public String get(String id, String api) throws Exception { //assembly_ai
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(api+"/"+id))
            .header("Authorization",key.assemblyai_apikey.getApikey())
            .GET()
            .build();
        
        transcriptAssemblyAI transcript;
        while (true) {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            transcript = gson.fromJson(response.body(),transcriptAssemblyAI.class);
            System.out.println(transcript.getStatus());
            if ("completed".equals(transcript.getStatus()) || "error".equals(transcript.getStatus())){
                System.out.println(response.body());
                break;
            }
            Thread.sleep(1000);
        }
        
        System.out.println("Transcription completed");
        return transcript.getText();
    }*/

    private String createJson(Transcript t,HashMap<String,String> data){
        String json;
        for (HashMap.Entry<String,String> i: data.entrySet()){
            if (data.containsKey("audio_url")) t.setAudio_url(i.getValue());
            if (data.containsKey("language_code")) t.setAudio_url(i.getValue()); //add more entries later
            if (data.containsKey("upload_url")) t.setAudio_url(i.getValue());
        }
        json = gson.toJson(t);

        return json;
    }

    

    public HashMap POST(URI url, HashMap<String,String> data,HashMap<String,String> headers,String[] returnList) throws Exception{
        
        HttpRequest.Builder builder = HttpRequest.newBuilder()
            .uri(url)
            .POST(BodyPublishers.ofString(createJson(new Transcript(),data)));
        
        for (HashMap.Entry<String,String> i: headers.entrySet()){
            builder.header(i.getKey(), i.getValue());
        }
        
        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request,BodyHandlers.ofString());

        if (response.statusCode() < 200 ||response.statusCode() >= 300) 
            throw new RuntimeException("POST failed\nSTATUS CODE="+response.statusCode()+"\n"+response.body());

        Transcript obj = gson.fromJson(response.body(),Transcript.class);
        HashMap<String,String> returner = new HashMap<>();
        for (String s: returnList){
            switch (s) {
                case "id":
                    returner.put(s, obj.getId());
                    break;
                case "text":
                    returner.put(s, obj.getText());
                    break;
                case "status":
                    returner.put(s, obj.getStatus());
                    break;
                default:
                    returner.put(s, null);
                    break;
            }
        }
        return returner;
    }

}
