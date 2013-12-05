package com.example.songoption;

import java.io.IOException;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("CutPasteId")
public class Player2 extends Activity {

    long[] audioId;
    String [] title;
    String [] artist;
    long[] duration;
    long startTime = 0;
    int counter;
    double percent;
    TextView timerTextView;
    private int currentTrack = 0; 
    static MediaPlayer mediaPlayer;
    static MediaPlayer mediaPlayer2;
    static Handler handler1;
    boolean isPlaying = false;
    
    Runnable timerRunnable = new Runnable (){
            public void run(){
            
            	Log.e("handler","in");
            	playSound(Player2.this, getAlarmUri());  
            }
    };
  
        @Override
     public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
   
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
            
            setContentView(R.layout.start_screen);
             handler1 = new Handler();
            ImageButton playpause = (ImageButton) findViewById(R.id.imageButton1);
    		ImageButton quit = (ImageButton) findViewById(R.id.imageButton2);
    		
            quit.setOnClickListener(new View.OnClickListener() { 			
    			@Override
    			public void onClick(View v) {
    				mediaPlayer.stop();
    				mediaPlayer.release();
    				handler1.removeCallbacks(timerRunnable);
    				finish();
    				
    			}
    		});
            playpause.setOnClickListener(new View.OnClickListener() {
        	      @Override
        	      public void onClick(View v) {
        	    	ImageButton btn = (ImageButton) findViewById(R.id.imageButton1);
        	    	if (isPlaying) {
        	    		mediaPlayer.pause();
        	    		handler1.removeCallbacks(timerRunnable);
        	    		btn.setImageResource(R.drawable.playbutton);
                    }else{
                  	  mediaPlayer.start();
                  	  handler1.postDelayed(timerRunnable, 60000); 
                  	  btn.setImageResource(R.drawable.pausebutton);
                 
                    }
                    isPlaying = !isPlaying;
            }}); 
  
      
            //Get the bundle
            Bundle bundle = getIntent().getExtras();
            //Extract the data�
            String idList = bundle.getString("idList"); 
            int seekto= bundle.getInt("start");
            int opt = bundle.getInt("option");
     	    counter =opt;
     	   String locationSong = bundle.getString("game");
   	    Cursor songCursor;
   	    
   	    if(locationSong.equals("playlist")){
   			    //Extract the data�		  		    
   			    String[] proj1 = {MediaStore.Audio.Playlists.Members.TITLE, MediaStore.Audio.Playlists.Members.ARTIST, 
   			            MediaStore.Audio.Playlists.Members.DURATION, "SourceId",MediaStore.Audio.Playlists.Members._ID};
   		
   		        Uri songUri = Uri.parse("content://com.google.android.music.MusicContent/playlists/" + idList + "/members");
   		         songCursor = getContentResolver().query(songUri, proj1, null, null, null);
   	    }
   	    else
   	    {
   	    	String[] proj1 = {MediaStore.Audio.Playlists.Members.TITLE, MediaStore.Audio.Playlists.Members.ARTIST, 
   		            MediaStore.Audio.Playlists.Members.DURATION, "SourceId",MediaStore.Audio.Playlists.Members._ID};
   	
   	        Uri songUri = Uri.parse("content://com.google.android.music.MusicContent/audio");
   	         songCursor = getContentResolver().query(songUri, proj1, null, null, null);
   	    }      
            
            int i =0;
        
            //Save all song information 
            if (songCursor.getCount() > 0) {
            songCursor.moveToFirst();
            
            audioId = new long [songCursor.getCount()];
            title = new String [songCursor.getCount()];
            artist = new String [songCursor.getCount()];
            duration = new long[songCursor.getCount()];
             
            do {
               audioId[i]=  songCursor.getLong(songCursor.getColumnIndex("SourceId"));
               String j =  String.valueOf(audioId[i]);
               Log.e("audioId",j);
               title[i]= songCursor.getString(0);
               
               artist[i]= songCursor.getString(1);
               duration[i] = songCursor.getLong(2);
               i++;
            } while (songCursor.moveToNext());
            
            songCursor.close();
            
            mediaPlayer = new MediaPlayer();
            
            mediaPlayer2 = new MediaPlayer();
            
         }
        
        String s =  String.valueOf(seekto);
        Log.e("seekBar",s);
     
        percent=  seekto * .01;
        
        s =  String.valueOf(percent);
        Log.e("seekBar",s);
        
          
        firstSong();
           
        }
     private void playSound(Context context, Uri alert) {
       
        try {
        	 String url= "https://dl.dropboxusercontent.com/u/85245874/DrinkNigga.mp3?dl=1";
              mediaPlayer2.reset();
              /*  
                mediaPlayer2.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
               
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0);
            
            
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            	audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0);
       
                mediaPlayer2.setAudioStreamType(AudioManager.STREAM_ALARM);*/
                
                mediaPlayer2.setDataSource(url);
                mediaPlayer2.prepareAsync();
                mediaPlayer2.setOnPreparedListener(new OnPreparedListener() {
                	@Override
                	public void onPrepared(MediaPlayer mediaPlayer)
                	{ Log.e("handler","2kk");startmedia(); 	}
                 });
            
            //}
        } catch (IOException e) {System.out.println("OOPS");}
    }
     
     public void startmedia(){
         if(counter > 0){
         	 counter--;
             CountDownTimer cntr_aCounter = new CountDownTimer(4000, 1000) {
            	 public void onTick(long millisUntilFinished) {                     
            		 mediaPlayer2.start();}
            	 public void onFinish() {
            		 mediaPlayer2.stop();}
             };cntr_aCounter.start();
               
         	handler1.postDelayed(timerRunnable, 60000); 
         }
         else{
         	mediaPlayer.stop(); 
         	mediaPlayer.release();
            finish();
         }
     } 
     public void firstSong()
        {
           try {
                 if (audioId[currentTrack] > 0) {
                     Uri contentUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, Long.valueOf(audioId[currentTrack]));
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(this, contentUri);
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer m) {
                        	TextView track = (TextView)findViewById(R.id.textView12);
                    		TextView artistname = (TextView)findViewById(R.id.textView11);
                    		
                    		track.setText(title[currentTrack]);
                    		artistname.setText(artist[currentTrack]);
                        	mediaPlayer.start();
                        	
                        	handler1.postDelayed(timerRunnable, 60000); 
                        	mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                	nextSong();
                                
                            }});     	
                           }
                        });
                    }
                }catch (Exception e) {e.printStackTrace();}
        
        }
        

    //Get an alarm sound. Try for an alarm. If none set, try notification, 
    //Otherwise, ringtone.
     private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }

     public void buzzer()
        {
                try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {}
        }
     public void nextSong() {
            mediaPlayer.reset();
              
                //Log.e("MediaService Example", "Service Started media.. ");
            currentTrack = (currentTrack + 1) % title.length; 
            try {
            if (audioId[currentTrack] > 0) {
                Uri contentUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, Long.valueOf(audioId[currentTrack]));
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(Player2.this, contentUri);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer)
                        { 
                        	TextView track = (TextView)findViewById(R.id.textView12);
                    		TextView artistname = (TextView)findViewById(R.id.textView11);
                    		
                    		track.setText(title[currentTrack]);
                    		artistname.setText(artist[currentTrack]);
                                mediaPlayer.start();
                                mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                    	nextSong();
                                    
                                }});
                        }
                 });
             }} catch (Exception e) {e.printStackTrace();}       
                     
        }  
}
