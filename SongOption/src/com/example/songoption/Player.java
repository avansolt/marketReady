package com.example.songoption;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("CutPasteId")
public class Player extends Activity {

	long[] audioId;
	String [] title;
    String [] artist;
    long[] duration;
    int counter, currentposition;
    double percent;
    static Handler handler1;
    boolean isPlaying = false;
    private int currentTrack = 0; 
    
    static MediaPlayer mediaPlayer;
  
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		setContentView(R.layout.start_screen);
		
		ImageButton playpause = (ImageButton) findViewById(R.id.imageButton1);
		ImageButton quit = (ImageButton) findViewById(R.id.imageButton2);
		
		//Get the bundle
	    Bundle bundle = getIntent().getExtras();
	    int opt = bundle.getInt("option");
	    int seekto= bundle.getInt("start");
	    counter =opt;
	    String locationSong = bundle.getString("game");
	    Cursor songCursor;
	    
	    if(locationSong.equals("playlist")){
			    //Extract the data…
			    String idList = bundle.getString("idList"); 		  		    
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
            handler1 = new Handler();
                
            
            playpause.setOnClickListener(new View.OnClickListener() {
      	      @Override
      	      public void onClick(View v) {
      	    	ImageButton btn = (ImageButton) findViewById(R.id.imageButton1);
      	    	
      	    	
      	    	if (isPlaying) {
      	    		mediaPlayer.pause();
      	    		btn.setImageResource(R.drawable.playbutton);      	    		
      	    		handler1.removeCallbacks(stopPlayerTask1);
                  }else{
                	  mediaPlayer.start();
                	  handler1.postDelayed(stopPlayerTask1,60000);
                	  btn.setImageResource(R.drawable.pausebutton);
               
                  }
                  isPlaying = !isPlaying;
          }}); 
         }
        
         quit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mediaPlayer.stop();
				mediaPlayer.release();
				handler1.removeCallbacks(stopPlayerTask1);
				finish();
				
			}
		});
        
        String s =  String.valueOf(seekto);
        Log.e("seekBar",s);
     
        percent=  seekto * .01;
        
        s =  String.valueOf(percent);
        Log.e("seekBar",s);
        
        
        firstSong();
	   
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
		                		startmedia(); 	
		                	}
		                });
		            }
		        } catch (Exception e) {e.printStackTrace();}
	
	}
	public void startmedia( )
	{
		TextView track = (TextView)findViewById(R.id.textView12);
		TextView artistname = (TextView)findViewById(R.id.textView11);
		
		track.setText(title[currentTrack]);
		artistname.setText(artist[currentTrack]);
		
		int start = (int) ((int)(percent * duration[currentTrack]));
		   
		int diff = ((int)duration[currentTrack]) - start;
		
		if(diff  > 60000)
		{Log.e("in","if");    
		    mediaPlayer.seekTo(start);
	        mediaPlayer.start(); 
		     //Handler handler2 = new Handler();
	        handler1.postDelayed(stopPlayerTask1,60000);
		 }
		 else{
		    Log.e("in","else");
		    mediaPlayer.seekTo(start);
		    mediaPlayer.start();
		    mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                	nextSong();
                
            }});			 
		  }//else		 		 
	}//startmedia()
	
	Runnable stopPlayerTask1 = new Runnable()
    {
        @Override
        public void run() {	
        	nextSong();
        	
     }};
     
     

	public void nextSong() {
		mediaPlayer.reset();
		if(counter > 0){
    	    counter--;   
    	    //Log.e("MediaService Example", "Service Started media.. ");
            currentTrack = (currentTrack + 1) % title.length; 
            try {
            if (audioId[currentTrack] > 0) {
                Uri contentUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, Long.valueOf(audioId[currentTrack]));
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(Player.this, contentUri);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                	@Override
                	public void onPrepared(MediaPlayer mediaPlayer)
                	{ startmedia(); 	}
                 });
             }} catch (Exception e) {e.printStackTrace();}       
    	 }	
		else{
			mediaPlayer.stop();
			mediaPlayer.release();
			handler1.removeCallbacks(stopPlayerTask1);
			finish();		
		}
	}  
}
