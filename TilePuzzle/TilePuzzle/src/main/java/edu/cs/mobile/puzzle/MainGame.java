package edu.cs.mobile.puzzle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by davides on 11/14/13.
 */
public class MainGame extends Activity {

    String difficulty = "none";
    ArrayList<gameTile> list = new ArrayList<gameTile>();
    int moves = 0;

    public class gameTile{
        public ImageButton tileButton;
        //Drawable    tileDrawable;
        public int rank,
                position,
                drawID;

        public gameTile(int buttonID, int drawableID, int r, int p)
        {
            tileButton = (ImageButton)findViewById(buttonID);
            tileButton.setImageResource(drawableID);
            //tileDrawable = getResources().getDrawable(drawableID);
            drawID = drawableID;
            rank = r;
            position = p;
        }

        void setClickListener(){
            tileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView movesTV = (TextView)findViewById(R.id.movesCounterTV);
                    movesTV.setText(Integer.toString(Integer.valueOf(movesTV.getText().toString())+1));
                    //Toast.makeText(MainActivity.this, "row:"+getRow()+"col:"+getCol(), 100).show();

                    // get adjacent tiles

                    // top
                    if((position-4) >= 0){// top cell exists
                        //Toast.makeText(MainActivity.this, "top cell exists", Toast.LENGTH_SHORT).show();
                        if(list.get(position-4).rank == 15){// if the top-adjacent tile is the blank one
                            //Toast.makeText(MainActivity.this, "top cell is the blank one", Toast.LENGTH_SHORT).show();
                            swap(position, position-4);
                        }
                    }
                    // left
                    if(((position-1)/4) == getRow() && (position-1) >= 0){// left cell exists
                        //Toast.makeText(MainActivity.this, "left cell exists", Toast.LENGTH_SHORT).show();
                        if(list.get(position-1).rank == 15){
                            //Toast.makeText(MainActivity.this, "left cell is the blank one", Toast.LENGTH_SHORT).show();
                            swap(position, position-1);
                        }
                    }
                    // right
                    if(((position+1)/4) == getRow() && (position+1) < 16){// right cell exists
                        //Toast.makeText(MainActivity.this, "right cell exists", Toast.LENGTH_SHORT).show();
                        if(list.get(position+1).rank == 15){
                            //Toast.makeText(MainActivity.this, "right cell is the blank one", Toast.LENGTH_SHORT).show();
                            swap(position, position+1);
                        }
                    }
                    // bottom
                    if((position+4) <= 15){// top cell exists
                        //Toast.makeText(MainActivity.this, "bot cell exists", Toast.LENGTH_SHORT).show();
                        if(list.get(position+4).rank == 15){
                            //Toast.makeText(MainActivity.this, "bot cell is the blank one", Toast.LENGTH_SHORT).show();
                            swap(position, position+4);
                        }
                    }
                }
            });
        }

        int getRow(){
            return position/4;
        }

        int getCol(){
            return position%4;
        }

    }

    void swap(int pos1, int pos2)
    {

        Log.d("swap",
                "Swap variables before swap: "
                        + pos1 + " has : "
                        + list.get(pos1).rank + " rank, "
                        + list.get(pos1).drawID + " drawID, "
                        + list.get(pos1).position + " position.");

        Log.d("swap",
                "Swap variables before swap: "
                        +pos2+" has : "
                        +list.get(pos2).rank+" rank, "
                        +list.get(pos2).drawID+" drawID, "
                        +list.get(pos2).position+" position.");

        // copy first Tiles variables into temporary containers
        int tempRank = list.get(pos1).rank;
        int tempDrawID = list.get(pos1).drawID;
        //int tempPos = list.get(pos1).position;

        // put Tile2 data into Tile1
        list.get(pos1).rank = list.get(pos2).rank;
        list.get(pos1).drawID = list.get(pos2).drawID;
        //list.get(pos1).position = list.get(pos2).position;

        // put temp data (Tile1 data) into Tile2
        list.get(pos2).rank = tempRank;
        list.get(pos2).drawID = tempDrawID;
        //list.get(pos2).position = tempPos;

        // set the image resources to the new drawIDs for each button
        list.get(pos1).tileButton.setImageResource(list.get(pos1).drawID);
        list.get(pos1).tileButton.setBackgroundColor(Color.TRANSPARENT);
        list.get(pos1).tileButton.invalidate();

        list.get(pos2).tileButton.setImageResource(list.get(pos2).drawID);
        list.get(pos2).tileButton.setBackgroundColor(0xffcc0000);
        list.get(pos2).tileButton.invalidate();

        Log.d("swap",
                "Swap variables AFTER swap: "
                        +pos1+" has : "
                        +list.get(pos1).rank+" rank, "
                        +list.get(pos1).drawID+" drawID, "
                        +list.get(pos1).position+" position.");

        Log.d("swap",
                "Swap variables AFTER swap: "
                        +pos2+" has : "
                        +list.get(pos2).rank+" rank, "
                        +list.get(pos2).drawID+" drawID, "
                        +list.get(pos2).position+" position.");

    }

    void shuffleTiles(int i)
    {
        for(int j = 0; j < i; j++)
        {
            int choice = (int)(Math.random()*15.0);
            list.get(choice).tileButton.performClick();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maingame);

        difficulty = getIntent().getStringExtra("difficulty");

        //Do something with global variable difficulty
        if(difficulty.equals("easy")){

            // row1
            list.add(new gameTile(R.id.t1, R.drawable.easy1,0,0));
            list.add(new gameTile(R.id.t2, R.drawable.easy2,1,1));
            list.add(new gameTile(R.id.t3, R.drawable.easy3,2,2));
            list.add(new gameTile(R.id.t4, R.drawable.easy4,3,3));

            // row2
            list.add(new gameTile(R.id.t5, R.drawable.easy5,4,4));
            list.add(new gameTile(R.id.t6, R.drawable.easy6,5,5));
            list.add(new gameTile(R.id.t7, R.drawable.easy7,6,6));
            list.add(new gameTile(R.id.t8, R.drawable.easy8,7,7));

            // row3
            list.add(new gameTile(R.id.t9, R.drawable.easy9,8,8));
            list.add(new gameTile(R.id.t10, R.drawable.easy10,9,9));
            list.add(new gameTile(R.id.t11, R.drawable.easy11,10,10));
            list.add(new gameTile(R.id.t12, R.drawable.easy12,11,11));

            // row4
            list.add(new gameTile(R.id.t13, R.drawable.easy13,12,12));
            list.add(new gameTile(R.id.t14, R.drawable.easy14,13,13));
            list.add(new gameTile(R.id.t15, R.drawable.easy15,14,14));
        }
        else if(difficulty.equals("medium")){


            // row1
            list.add(new gameTile(R.id.t1, R.drawable.medium1,0,0));
            list.add(new gameTile(R.id.t2, R.drawable.medium2,1,1));
            list.add(new gameTile(R.id.t3, R.drawable.medium3,2,2));
            list.add(new gameTile(R.id.t4, R.drawable.medium4,3,3));

            // row2
            list.add(new gameTile(R.id.t5, R.drawable.medium5,4,4));
            list.add(new gameTile(R.id.t6, R.drawable.medium6,5,5));
            list.add(new gameTile(R.id.t7, R.drawable.medium7,6,6));
            list.add(new gameTile(R.id.t8, R.drawable.medium8,7,7));

            // row3
            list.add(new gameTile(R.id.t9, R.drawable.medium9,8,8));
            list.add(new gameTile(R.id.t10, R.drawable.medium10,9,9));
            list.add(new gameTile(R.id.t11, R.drawable.medium11,10,10));
            list.add(new gameTile(R.id.t12, R.drawable.medium12,11,11));

            // row4
            list.add(new gameTile(R.id.t13, R.drawable.medium13,12,12));
            list.add(new gameTile(R.id.t14, R.drawable.medium14,13,13));
            list.add(new gameTile(R.id.t15, R.drawable.medium15,14,14));
        }
        else if(difficulty.equals("hard")){


            // row1
            list.add(new gameTile(R.id.t1, R.drawable.hard1,0,0));
            list.add(new gameTile(R.id.t2, R.drawable.hard2,1,1));
            list.add(new gameTile(R.id.t3, R.drawable.hard3,2,2));
            list.add(new gameTile(R.id.t4, R.drawable.hard4,3,3));

            // row2
            list.add(new gameTile(R.id.t5, R.drawable.hard5,4,4));
            list.add(new gameTile(R.id.t6, R.drawable.hard6,5,5));
            list.add(new gameTile(R.id.t7, R.drawable.hard7,6,6));
            list.add(new gameTile(R.id.t8, R.drawable.hard8,7,7));

            // row3
            list.add(new gameTile(R.id.t9, R.drawable.hard9,8,8));
            list.add(new gameTile(R.id.t10, R.drawable.hard10,9,9));
            list.add(new gameTile(R.id.t11, R.drawable.hard11,10,10));
            list.add(new gameTile(R.id.t12, R.drawable.hard12,11,11));

            // row4
            list.add(new gameTile(R.id.t13, R.drawable.hard13,12,12));
            list.add(new gameTile(R.id.t14, R.drawable.hard14,13,13));
            list.add(new gameTile(R.id.t15, R.drawable.hard15,14,14));
        }
        else{
            Toast.makeText(this, "Diificulty not found", Toast.LENGTH_SHORT).show();
        }


        list.add(new gameTile(R.id.t16, R.drawable.tileempty,15,15));

        for(int i = 0; i < 16; i++)
            list.get(i).setClickListener();

        shuffleTiles(1000);
        moves = 0;
        TextView movesTV = (TextView)findViewById(R.id.movesCounterTV);
        movesTV.setText(Integer.toString(moves));

        Button checkButton = (Button)findViewById(R.id.checkPuzzle);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean gameWon = true;
                for(int i = 0; i < 16; i++)
                {
                    if(list.get(i).rank != i)
                        gameWon = false;
                }
                if(gameWon)
                    Toast.makeText(MainGame.this, "You Won!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainGame.this, "Keep trying!", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




}