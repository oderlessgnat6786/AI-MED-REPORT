package med;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

// Audio recording functionality for the application
public class AudioRecorder {
    public void  startRecording(File f,BufferedReader sc) {
        
            try {
                AudioFormat af = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44110, 16,2,4,44110,false);
                DataLine.Info info = new DataLine.Info(TargetDataLine.class,af); 
                if (!AudioSystem.isLineSupported(info)) System.err.println("unsupported");
                TargetDataLine targetDataLine = (TargetDataLine)AudioSystem.getLine(info);
                targetDataLine.open();
                targetDataLine.start();
                System.out.println("Audio recording started...");
                Thread t = new Thread(()->{
                    AudioInputStream stream = new AudioInputStream(targetDataLine);
                    try {
                        AudioSystem.write(stream, AudioFileFormat.Type.WAVE,f);
                    } catch (IOException e) { System.out.println(e);}
                    System.out.println("Audio recording stopped.");
                });
                t.start();
                System.out.println("Press enter to stop recording audio.");
                String s = sc.readLine(); //sc refers to an BufferedReader object
                
                targetDataLine.stop();
                targetDataLine.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
           
        
    }
}