package com.example.nickliau.tic_tac_toe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.GridLayout.LayoutParams;
import android.view.Gravity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "tictactoe";
    GridLayout mgridLayout;

    // true : player 0, false : player 1
    boolean switchplayer;

    boolean finalmove;

    public class playeronposition {
        // This position is selected by played or not
        boolean isSelect;
        //player 0: false, player 1: true
        boolean player;
        //red: false, yellow: true
        boolean color ;

        View view;
    }

    playeronposition player[] = new playeronposition[]{
        new playeronposition(), new playeronposition(), new playeronposition(),
        new playeronposition(), new playeronposition(), new playeronposition(),
        new playeronposition(), new playeronposition(), new playeronposition()
    };

    private int[][] sWinningPositions =
            {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mgridLayout = (GridLayout)findViewById(R.id.gridLayout);

        init_tic_tac_toe(null);
    }

    public void init_tic_tac_toe(View view)  {
        Log.i (TAG, "initial tic tac toe view");
        for (int i = 0; i < 9 ; i++) {
            player[i].isSelect = false;
            player[i].player = false;
            player[i].color = false;
            if (player[i].view != null)
                player[i].view.setBackgroundColor(Color.TRANSPARENT);
            player[i].view = null;
        }

        for (int j = 0; j< mgridLayout.getChildCount(); j++) {
            ((ImageView) mgridLayout.getChildAt(j)).setImageResource(0);
        }

        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        switchplayer = true;
        finalmove = false;
    }

    public void dropIn(View view) {
        ImageView position = (ImageView) view;
        int tappedCounter = Integer.parseInt(position.getTag().toString());
        boolean gameover = true;

        if (finalmove)
            return;

        if (player[tappedCounter].isSelect) {
            return;
        }

        player[tappedCounter].isSelect = true;
        player[tappedCounter].player = switchplayer;
        player[tappedCounter].color = switchplayer;
        player[tappedCounter].view = view;

        //Show image view on user select position
        if (switchplayer) {
            position.setImageResource(R.drawable.yellow);
        } else {
            position.setImageResource(R.drawable.red);
        }
        position.setTranslationY(-1000f);
        position.animate().translationYBy(1000f).rotation(360).setDuration(100);

        // Is this move win this game?
        for (int[] winningPosition : sWinningPositions) {
            if (player[winningPosition[0]].isSelect && player[winningPosition[1]].isSelect
                    && player[winningPosition[2]].isSelect) {
                if (player[winningPosition[0]].player == switchplayer &&
                        player[winningPosition[1]].player == switchplayer &&
                        player[winningPosition[2]].player == switchplayer) {
                    player[winningPosition[0]].view.setBackgroundColor(Color.GREEN);
                    player[winningPosition[1]].view.setBackgroundColor(Color.GREEN);
                    player[winningPosition[2]].view.setBackgroundColor(Color.GREEN);

                    String Winner = "Winner is " + (switchplayer ? "Player 0" : "Player 1");
                    //We hava a winner
                    Log.i (TAG, Winner);

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(Winner);
                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                    finalmove = true;
                    return;
                }
            }
        }

        // If this is the final move
        for (playeronposition a: player) {
            if (!a.isSelect) {
                gameover = false;
                break;
            }
        }

        if (gameover) {
            Log.i (TAG, "No winner. This is the final move");
            TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
            winnerMessage.setText("No Winner, This is the final move");
            LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
            layout.setVisibility(View.VISIBLE);
            finalmove = true;
        }

        // Next player
        switchplayer = !switchplayer;
    }


}
