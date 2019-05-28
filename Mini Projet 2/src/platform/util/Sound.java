package platform.util;

import java.awt.event.*;

import javax.swing.*;
import sun.audio.*;
import java.io.*;

public class Sound {
	//method that plays the sound and returns an AudioDataStream object
	public static AudioDataStream music(String name){
		AudioPlayer AP=AudioPlayer.player;
		AudioStream AS;
		AudioData AD;
		AudioDataStream sound=null;
		try{
			AS=new AudioStream(new FileInputStream(new File("res/"+name)));
			AD=AS.getData();
			sound=new AudioDataStream(AD);
		}catch(IOException error){}//catches an exception if file not found
		AP.start(sound);
		return sound;
	}
	//method that stops the sound (AudioDataStream) in the argument
	public static void stop(AudioDataStream ADS){
		AudioPlayer AP=AudioPlayer.player;
		AP.stop(ADS);
	}
	//method that plays the sound in a loop and returns a ContinuousAudioDataStream object
	public static ContinuousAudioDataStream loop(String name){
		AudioPlayer AP=AudioPlayer.player;
		AudioStream AS;
		AudioData AD;
		ContinuousAudioDataStream sound=null;
		try{
			AS=new AudioStream(new FileInputStream("res/"+name));
			AD=AS.getData();
			sound=new ContinuousAudioDataStream(AD);
		}catch(IOException error){}//catches an exception if file not found
		AP.start(sound);
		return sound;
	}
	//method that stops the sound (AudioDataStream) player in loop, takes a ContinuousAudioDataStream object in argument
	public static void stop(ContinuousAudioDataStream CADS){
		AudioPlayer AP=AudioPlayer.player;
		AP.stop(CADS);
	}
}
