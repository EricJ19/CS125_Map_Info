package com.example.cs125finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {
    /**
     * Buttons to the answers.
     */
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;
    /**
     * The question TextView.
     */
    private TextView question;
    /**
     * The playerScore TextView.
     */
    private TextView playerScore;
    /**
     * This is an internal counter (not visible to player)
     * reflects the question number the user is currently on.
     */
    private int questionNumb;
    /**
     * Current name and score of player for the player history UI.
     */
    public static String currentNameScore;
    /**
     * Previous current name and score of player for the player history UI.
     */
    public static String previousCurrentNameScore;
    /**
     * Number of times the player has played the quiz. Starts at -1 but updates to 0 on first create, which continues to update.
     */
    public static int numberTimesPlayed = -1;
    /**
     * Player stores player info: name, score.
     */
    public static Player newPlayer;
    /**
     * Correct - true if answer is button clicked, false if answer is not button clicked.
     */
    private boolean correct;
    /**
     * The score counter that updates as questions are answered is visible to player.
     */
    private int activeScore;
    private JSONObject QandA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        question = findViewById(R.id.question);
        playerScore = findViewById(R.id.playerScore);
        questionNumb = 0;
        activeScore = 0;
        Bundle bundle = getIntent().getExtras();
        newPlayer = new Player(bundle.getString("newPlayerName"),
         0, bundle.getInt("chosenTopic"));
        String display = newPlayer.getName() + " " + 0;
        playerScore.setText(display);
        numberTimesPlayed++;
        //QandA = webResponse();
        updateQandA();
    }
    /** gets the questions from the url and returns it as a Json Object. */
    /**private JSONObject webResponse() {
        final RequestQueue queue = Volley.newRequestQueue(this);
        String generalUrl = "";
        switch (newPlayer.getTopic()) {
            case 1:
                generalUrl = "https://opentdb.com/api.php?amount=10&category=9&type=multiple";
                break;
            case 2:
                generalUrl = "https://opentdb.com/api.php?amount=10&category=18&type=multiple";
                break;
            case 3:
                generalUrl = "https://opentdb.com/api.php?amount=10&category=11&type=multiple";
                break;
            case 4:
                generalUrl = "https://opentdb.com/api.php?amount=10&category=20&type=multiple";
                break;
            case 5:
                generalUrl = "https://opentdb.com/api.php?amount=10&category=23&type=multiple";
                break;
            default:
                break;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, generalUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        question.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                question.setText("Web Request Failed");
            }
        });
        queue.add(stringRequest);
    }*/
    /**
     * Generates the random questions.
     */
    public void generateQandA() {
        final RequestQueue queue = Volley.newRequestQueue(this);
        String generalUrl = "";
        switch (newPlayer.getTopic()) {
            case 1:
                generalUrl = "https://opentdb.com/api.php?amount=10&category=9&type=multiple";
                break;
            case 2:
                generalUrl = "https://opentdb.com/api.php?amount=10&category=18&type=multiple";
                break;
            case 3:
                generalUrl = "https://opentdb.com/api.php?amount=10&category=11&type=multiple";
                break;
            case 4:
                generalUrl = "https://opentdb.com/api.php?amount=10&category=20&type=multiple";
                break;
            case 5:
                generalUrl = "https://opentdb.com/api.php?amount=10&category=23&type=multiple";
                break;
            default:
                break;
        }
        String url ="http://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        question.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                question.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);
    }
    /**
     * Update questions and answers when an answer is clicked.
     * If the number of questions answered is 10 (after user clicked 10 answers), then the app will go to players page.
     * currentScore will be set to the score of this session.
     * previousCurrentScore is the score of last session and is "0" if there was no session previously.
     */
    public void updateQandA() {
        //the correctAnswer is based on the question and answer. Code doesn't reflect that and instead has int 2 for testing purposes.
        final int correctAnswer = 2;
        if (questionNumb == 10) {
            if (numberTimesPlayed == 0) {
                previousCurrentNameScore = "Player 0";
            } else {
                //important that previousCurrentScore is modified before currentScore.
                //This is so the previousCurrentScore is updated with the last CurrentScore before CurrentScore
                // is modified to reflect the score now.
                previousCurrentNameScore = currentNameScore;
            }
            newPlayer.setPoints(activeScore);
            currentNameScore = newPlayer.getName() + " " + newPlayer.getPoints();
            Intent intent = new Intent(this, ScoreHistory.class);
            startActivity(intent);
        }
        questionNumb++;
        generateQandA();
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Change the label's text
                if (correctAnswer == 1) {
                    correct = true;
                    updatePlayerScore();
                } else {
                    correct = false;
                    updatePlayerScore();
                }
                updateQandA();
            }
        });
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Change the label's text
                if (correctAnswer == 2) {
                    correct = true;
                    updatePlayerScore();
                } else {
                    correct = false;
                    updatePlayerScore();
                }
                updateQandA();
            }
        });
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Change the label's text
                if (correctAnswer == 3) {
                    correct = true;
                    updatePlayerScore();
                } else {
                    correct = false;
                    updatePlayerScore();
                }
                updateQandA();
            }
        });
        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Change the label's text
                if (correctAnswer == 4) {
                    correct = true;
                    updatePlayerScore();
                } else {
                    correct = false;
                    updatePlayerScore();
                }
                updateQandA();
            }
        });
    }
    /**
     * Updates the score part of UI after the answer is clicked by user.
     */
    public void updatePlayerScore() {
        if (correct) {
            activeScore++;
            String display = newPlayer.getName() + " " + String.valueOf(activeScore);
            playerScore.setText(display);
        } else {
            activeScore--;
            String display = newPlayer.getName() + " " + String.valueOf(activeScore);
            playerScore.setText(display);
        }
    }

}
