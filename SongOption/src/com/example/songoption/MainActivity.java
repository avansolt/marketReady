package com.example.songoption;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	
    
    long[] duration;
    String game;
	String idList = null;
  
	@Override
    public void onCreate(final Bundle savedInstanceState) {

        // Create the superclass portion of the object.
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
      
        
        
        Button enter = (Button)findViewById(R.id.button2);     
        enter.setOnClickListener(new View.OnClickListener() {
   	 
		      @Override
		      public void onClick(View v) {
		    	  PlaySongsFromAPlaylist();
		            }
		        });
        
     }
//---------------PLAYLIST- SPINNER IN HERE (DO-WHILE)-----------------------------------------------------------------------------------------------	
    public void PlaySongsFromAPlaylist(){ 
    	
    	Boolean found = false;
    	EditText path = (EditText) findViewById(R.id.EditText01);
        String name = path.getText().toString();
    	
    	Uri playlistUri = Uri.parse("content://com.google.android.music.MusicContent/playlists");
        Cursor playlistCursor = getContentResolver().query(playlistUri, new String[] {"_id", "playlist_name" }, null, null, null);
        
        if (playlistCursor.getCount() > 0){
            playlistCursor.moveToFirst();
            do {
               String nameList = playlistCursor.getString(1);
               Log.e("query", nameList);
               Log.e("query", name);
               idList= String.valueOf(playlistCursor.getLong(0));

                if(name.equals(nameList))
                { found =true;  break;}
             }while (playlistCursor.moveToNext());  
            
          playlistCursor.close();
         }
 //------------END PLAYLIST OPTION---------------------------------------------------------------------------------------------------
//------------------------------//PUT THE TWO OPTIONS HERE!!!!!!-----------------------------------------------------------------------------
      if(found == true){      
      
       Button option1 = (Button) findViewById(R.id.button1); 
       option1.setOnClickListener(new View.OnClickListener() {
    	      @Override
    	      public void onClick(View v) {
    	    	  RadioButton songskip = (RadioButton) findViewById(R.id.radioButton1);
    	    	  RadioButton wholesong = (RadioButton) findViewById(R.id.radioButton2);
    	    	  RadioButton allsong = (RadioButton) findViewById(R.id.radioButton3);
    	    	  RadioButton playlist = (RadioButton) findViewById(R.id.radioButton4);
    	    	  
    	    	  if(allsong.isChecked()){
    	    		  game="allsong" ; 
    	    	  }
    	    	  else if(playlist.isChecked()){
    	    		 game ="playlist";
    	    	  }
    	    	  
    	    	  
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
      }
      else{ 
    	  Toast.makeText(getApplicationContext(),
          "No Playlist Found", Toast.LENGTH_SHORT).show();
    	  path.setText(null);
    	  
      }
      
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


