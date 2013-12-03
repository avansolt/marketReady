package edu.cs.mobile.puzzle;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String difficulty = "none";
                switch (view.getId())
                {
                    case R.id.easy_button:
                        difficulty = "easy";
                        break;
                    case R.id.hard_button:
                        difficulty = "hard";
                        break;
                    case R.id.medium_button:
                        difficulty = "medium";
                }

                Intent myIntent = new Intent(getApplicationContext(), MainGame.class);

                myIntent.putExtra("difficulty",difficulty);

                startActivity(myIntent);
            }
        };

        ((Button)findViewById(R.id.easy_button)).setOnClickListener(buttonListener);
        ((Button)findViewById(R.id.medium_button)).setOnClickListener(buttonListener);
        ((Button)findViewById(R.id.hard_button)).setOnClickListener(buttonListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
