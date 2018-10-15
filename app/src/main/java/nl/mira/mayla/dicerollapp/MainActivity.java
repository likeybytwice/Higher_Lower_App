package nl.mira.mayla.dicerollapp;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //Local variables
    private List<String> mDiceThrows;
    private ListView throwHistory;

    private ArrayAdapter mAdapter;

    private TextView mScoreLabel;
    private TextView mHighScoreLabel;

    private FloatingActionButton higher;
    private FloatingActionButton lower;

    private ImageView mImageView;
    private int[] mImageNames;

    private int currentScore = 0;
    private int highScore = 0;
    private int index = 0;
    private int lastThrow = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize variables
        mScoreLabel = findViewById(R.id.textView);
        mHighScoreLabel = findViewById(R.id.textView2);

        throwHistory = findViewById(R.id.ListView);
        throwHistory.setTranscriptMode(1);

        mImageView = findViewById(R.id.imageView);
        mImageNames = new int[]{R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4,
                R.drawable.d5, R.drawable.d6};

        higher = findViewById(R.id.btn_higher);
        lower = findViewById(R.id.btn_lower);

        mDiceThrows = new ArrayList<>();
        mDiceThrows.add("Let's get started!");

        updateUI();

        //Tell the adapter that the data set has been modified: the screen will be refreshed
        mAdapter.notifyDataSetChanged();

        //Arrow down button click
        lower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
                lowerGuess();
                updateUI();
            }
        });

        //Arrow up button click
        higher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
                higherGuess();
                updateUI();
            }
        });

    }

        private void rollDice(){
            int random = (int)(Math.random() * 6);
            index = random;
            mDiceThrows.add("Throw is " + (random + 1));
            throwHistory.setBackgroundColor(Color.red(1));
        }

        private void lowerGuess() {

            //If throwIndex is smaller or equal to the previous throw, the user wins and gets a point
            if (index <= lastThrow) {
                currentScore++;
                //Show snackbar --> getCurrentFocus = return the view in this Window
                Snackbar.make(getCurrentFocus(),"You were right! You got a point!",Snackbar.LENGTH_LONG).show();

            } else { //If not, then the user loses and has to try again
                //Show snackbar --> getCurrentFocus = return the view in this Window
                Snackbar.make(getCurrentFocus(),"You were wrong. You lost. . . Try again",Snackbar.LENGTH_LONG).show();

                //If the user's current score is higher than the high score, set it as the high score
                if(currentScore > highScore) {
                    highScore = currentScore;
                }
                currentScore = 0;
            }
            lastThrow = index;

        }

        private void higherGuess() {

            //If throwIndex is bigger or equal to the previous throw, the user wins and gets a point
            if (index >= lastThrow) {
                currentScore++;
                //Show snackbar --> getCurrentFocus = return the view in this Window
                Snackbar.make(getCurrentFocus(),"You were right! You you got a point!",Snackbar.LENGTH_LONG).show();

            } else {
                //Show snackbar --> getCurrentFocus = return the view in this Window
                Snackbar.make(getCurrentFocus(),"You were wrong. You lost. . . Try again",Snackbar.LENGTH_LONG).show();

                //If the user's current score is higher than the high score, set it as the high score
                if(currentScore > highScore) {
                    highScore = currentScore;
                }
                currentScore = 0;
            }
            lastThrow = index;
        }


        //Update screen
        private void updateUI() {
                mScoreLabel.setText("Score: " + currentScore);
                mHighScoreLabel.setText("Highscore: " + highScore);

                if(mAdapter == null) {
                    mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDiceThrows);
                    throwHistory.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
                mImageView.setImageResource(mImageNames[index]);
            }

    }

