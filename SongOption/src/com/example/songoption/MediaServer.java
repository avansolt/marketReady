package com.example.songoption;
import java.io.IOException;

import android.R.string;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MediaServer extends Service {

	    int choice, radio;
	    int counter=0;
	    private int currentTrack = 0;
	    private String[] tracks = {
	    		
	    		"https://dl.dropbox.com/s/6e6pn43916nl10i/Bob_Marley-Jammin.mp3?dl=1",
	    		"http://freedownloads.last.fm/download/305596593/Prel%25C3%25BAdio.mp3",
	            "http://freedownloads.last.fm/download/142005075/Piano%2BSonata%2B22%2B-%2Bmovement%2B2%2B%2528Beethoven%2529.mp3"

	    };
	    static MediaPlayer mediaPlayer;
        Runnable stopPlayerTask1 = new Runnable(){
			        @Override
			        public void run() {
			        	mediaPlayer.reset();
			        	
			        	    
			        	 if(counter < 1){
			        	    	counter++;   
			        	        //Log.e("MediaService Example", "Service Started media.. ");
			                    currentTrack = (currentTrack + 1) % tracks.length;
			                    Uri nextTrack = Uri.parse(tracks[currentTrack]);
			                    try {
			                    	mediaPlayer.setDataSource(MediaServer.this,nextTrack);
			                    	mediaPlayer.prepareAsync();
			                    	mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			                        	@Override
			                        	public void onPrepared(MediaPlayer mediaPlayer) {
			                        		startmedia(); 	
			                        	}
			                        });

			                    } catch (Exception e) {// TODO Auto-generated catch block
			                        e.printStackTrace();} 
			        	  } 
			        	  
			                }};
			               
			       
			        	        
	  public void onCreate() {	 
		 super.onCreate();
		 Log.e("MediaService Example", "Service Started media.. ");	 
	  }
	   
	  @Override		
	  public void onDestroy(){
		 Log.e("MediaService Example", "Service Stopped.. ");		
		super.onDestroy();}

	   public int onStartCommand(Intent intent, int flags, int startId)	   {	
		   Log.e("Media Service","in onStartCommand");
		   
		   Bundle extras = intent.getExtras();
		    choice= extras.getInt("choice");
		    radio = extras.getInt("set");
		   //String song = extras.getString("data");
		   
		   mediaPlayer = new MediaPlayer(); 
		   Uri file = Uri.parse(tracks[this.currentTrack]);  
		   mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		
		   try {
				mediaPlayer.setDataSource(this, file);
			    } catch (IllegalArgumentException e) {// TODO Auto-generated catch block
				e.printStackTrace();} catch (SecurityException e) {// TODO Auto-generated catch block
				e.printStackTrace();} catch (IllegalStateException e) {// TODO Auto-generated catch block
				e.printStackTrace();} catch (IOException e) {// TODO Auto-generated catch block
				e.printStackTrace();}
		   
		   mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			 @Override
		     public void onPrepared(MediaPlayer mediaPlayer) {
			      startmedia(); 	
			    }
			});
			try {
				mediaPlayer.prepareAsync();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                if(counter < 1){
                    counter++;
                    currentTrack = (currentTrack + 1) % tracks.length;
                    Uri nextTrack = Uri.parse(tracks[currentTrack]);
                    try {
                        mp.setDataSource(MediaServer.this,nextTrack);
                        mp.prepareAsync();
                        mp.setOnPreparedListener(new OnPreparedListener() {
                        	@Override
                        	public void onPrepared(MediaPlayer mediaPlayer) {
                        		startmedia(); }
                        });
                    } catch (Exception e) {// TODO Auto-generated catch block
                        e.printStackTrace();} 
                }}
            });
		
	 return Service.START_NOT_STICKY;
	}
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public void startmedia()
	{
		if(choice == 1)//start to a min
		{ mediaPlayer.seekTo(0);
		  mediaPlayer.start();
		  Handler handler1 = new Handler();
	      handler1.postDelayed(stopPlayerTask1, 200000);
		}
		if(choice == 2)//pause choice
		{ mediaPlayer.pause();}
		
		
		if(choice == 3)//end of app
		{ mediaPlayer.seekTo(0);
		  mediaPlayer.pause();
		  mediaPlayer.release();
		}
		if(choice == 4)//start after 30 sec 
		{ mediaPlayer.seekTo(30000);
		  mediaPlayer.start();
		  Handler handler1 = new Handler();
	      handler1.postDelayed(stopPlayerTask1, 90000);
		}
		if(choice == 5)//start after 1 min 
		{ mediaPlayer.seekTo(60000);
		  mediaPlayer.start();
		  Handler handler1 = new Handler();
	      handler1.postDelayed(stopPlayerTask1, 120000);
		}
		if(choice == 6)//start after 1 min and 1/2
		{ mediaPlayer.seekTo(90000);
		  mediaPlayer.start();
		  Handler handler1 = new Handler();
	      handler1.postDelayed(stopPlayerTask1, 150000);
		}
		if(choice == 7)//song is less than a min
		{ mediaPlayer.seekTo(0);
		  mediaPlayer.start();
		}
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            if(counter < 1){
                counter++;
                currentTrack = (currentTrack + 1) % tracks.length;
                Uri nextTrack = Uri.parse(tracks[currentTrack]);
                try {
                    mp.setDataSource(MediaServer.this,nextTrack);
                    mp.prepareAsync();
                    mp.setOnPreparedListener(new OnPreparedListener() {
                    	@Override
                    	public void onPrepared(MediaPlayer mediaPlayer) {
                    		startmedia(); }
                    });
                } catch (Exception e) {// TODO Auto-generated catch block
                    e.printStackTrace();} 
            }}
        });
	}
	private void checkSize()
	{/*
		//option for song less than 1 min, option 7 
        if(songCursor.getLong(2) < 60000 )
        {  startmedia(7);}
        
        else if(songCursor.getLong(2) < 90000 )//option 1
        {  startmedia(1);  }
        else{startmedia(1);  }*/
	}
	
	

}
