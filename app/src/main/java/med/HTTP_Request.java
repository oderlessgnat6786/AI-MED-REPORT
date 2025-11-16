package med;
// This class will handle HTTP requests for the application

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.JsonObject;



public class HTTP_Request {
    HttpClient client = HttpClient.newHttpClient();
    Gson gson = new Gson();

    public String upload(Path path,String api) throws Exception{
        
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
    }
}
