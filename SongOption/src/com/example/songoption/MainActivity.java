package com.example.songoption;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	
    
    long[] duration;
    String playname [] = new String[100];
    String playid [] = new String[100];
    String game;
    int numberPlaylist=0;
	String idList = null;
  
	@Override
    public void onCreate(final Bundle savedInstanceState) {

        // Create the superclass portion of the object.
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
      /*
        Uri playlistUri = Uri.parse("content://com.google.android.music.MusicContent/playlists");
        Cursor playlistCursor = getContentResolver().query(playlistUri, new String[] {"_id", "playlist_name" }, null, null, null);
         
        List<String> SpinnerArray =  new ArrayList<String>();
        String playname [] = new String[100];
        String playid [] = new String[100];
         int i=0;
         
        if (playlistCursor.getCount() > 0){
            playlistCursor.moveToFirst();
            do {
               String nameList = playlistCursor.getString(1);
               SpinnerArray.add(nameList);
               playname[i]=nameList;
               
               String playId= String.valueOf(playlistCursor.getLong(0));
               playid[i]=playId;
               i++;                
             }while (playlistCursor.moveToNext());  
            
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner Items = (Spinner) findViewById(R.id.Spinner2);
            Items.setAdapter(adapter);
  
            playlistCursor.close();
            
            String selected = Items.getSelectedItem().toString();
            for(int k=0; k<i; k++)
            {
            	if(selected.equals(playname))
            	{
            		idList= playid[k];
            		
            	}
            }
            
         }
       */
        PlaySongsFromAPlaylist();
     }
//---------------PLAYLIST- SPINNER IN HERE (DO-WHILE)-----------------------------------------------------------------------------------------------	
    public void PlaySongsFromAPlaylist(){ 
    	
    	Boolean found = false;
    	Uri playlistUri = Uri.parse("content://com.google.android.music.MusicContent/playlists");
        Cursor playlistCursor = getContentResolver().query(playlistUri, new String[] {"_id", "playlist_name" }, null, null, null);
         
        List<String> SpinnerArray =  new ArrayList<String>();
         
         
        if (playlistCursor.getCount() > 0){
            playlistCursor.moveToFirst();
            do {
               String nameList = playlistCursor.getString(1);
               SpinnerArray.add(nameList);
               playname[numberPlaylist]=nameList;
               
               String playId= String.valueOf(playlistCursor.getLong(0));
               playid[numberPlaylist]=playId;
               numberPlaylist++;                
             }while (playlistCursor.moveToNext());  
            
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner Items = (Spinner) findViewById(R.id.Spinner2);
            Items.setAdapter(adapter);
  
            playlistCursor.close();
            
            
         }
 //------------END PLAYLIST OPTION---------------------------------------------------------------------------------------------------
//------------------------------//PUT THE TWO OPTIONS HERE!!!!!!-----------------------------------------------------------------------------
         
      
       Button option1 = (Button) findViewById(R.id.button1); 
       option1.setOnClickListener(new View.OnClickListener() {
    	      @Override
    	      public void onClick(View v) {
    	    	  Spinner Items = (Spinner) findViewById(R.id.Spinner2);
    	    	  RadioButton songskip = (RadioButton) findViewById(R.id.radioButton1);
    	    	  RadioButton wholesong = (RadioButton) findViewById(R.id.radioButton2);
    	    	  RadioButton allsong = (RadioButton) findViewById(R.id.radioButton3);
    	    	  RadioButton playlist = (RadioButton) findViewById(R.id.radioButton4);
    	    	  
    	    	  if(allsong.isChecked()){
    	    		  game="allsong" ; 
    	    	  }
    	    	  else if(playlist.isChecked()){
    	    		 game ="playlist";
    	    		 String selected = Items.getSelectedItem().toString();
    	             
    	             for(int k=0; k<numberPlaylist; k++)
    	             {
    	             	if(selected.equals(playname[k]))
    	             	{
    	             		idList= playid[k];
    	             		  Log.e("found",playname[k]);
    	             		
    	             	}
    	             }
    	    	  }
    	    	  Log.e("game",game);
    	    	  
    	    	  if(songskip.isChecked()){
    	    		  secondAct();  
    	    	  }
    	    	  else if(wholesong.isChecked()){
    	    		  thirdAct();
    	    	  }
    	    	  else{
    	    		  Toast.makeText(getApplicationContext(),
    	    		  "Choose a Game Option", Toast.LENGTH_SHORT).show();
    	    	  }   
                        	  
        }}); 
      
      
      
   }//end of playSong

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void secondAct(){
		Intent k = new Intent(MainActivity.this, Player.class);
		SeekBar seekBar = (SeekBar)findViewById(R.id.volume_bar);
		Spinner country = (Spinner) findViewById(R.id.spinner1); 
		String str = country.getSelectedItem().toString();
		int option = Integer.valueOf(str);
		int value = seekBar.getProgress();
	
		  Bundle bundle = new Bundle();
		  bundle.putString("idList",idList);
		  bundle.putString("game",game);
		  bundle.putInt("option",option);
		  bundle.putInt("start", value);
		  //Add the bundle to the intent
		  k.putExtras(bundle);
		  
		  startActivity(k);
	}
	public void thirdAct()
	{
	Intent j = new Intent(MainActivity.this, Player2.class);
			Spinner country = (Spinner) findViewById(R.id.spinner1); 
			String str = country.getSelectedItem().toString();
			int option = Integer.valueOf(str);	
			
			  Bundle bundle = new Bundle();
			  bundle.putInt("option",option);
			  bundle.putString("game",game);
			  bundle.putString("idList",idList);
			 
			  //Add the bundle to the intent
			  j.putExtras(bundle);
			  
			  startActivity(j);
		
	}
}


