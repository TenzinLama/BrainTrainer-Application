package com.example.tensin.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

   //textviews
    TextView resultTextView;
    TextView pointsTextView;
    TextView sumTextView;
    TextView timerTextView;

    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int numQuestions = 0;

    //buttons
    Button startButton;

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainButton;

    //layouts
    RelativeLayout startLayout;
    RelativeLayout gameLayout;

    public void generateQuestion(){

        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));
        locationOfCorrectAnswer = rand.nextInt(4);
        int incorrectAnswer;
        //clear the array;
        answers.clear();
        for(int i = 0; i < 4; i ++){
            if(i == locationOfCorrectAnswer){
                answers.add(a + b);
            } else {
                incorrectAnswer = rand.nextInt(41);
                while(incorrectAnswer == (a + b)){
                    incorrectAnswer = rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));


    }


    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
    }

    public void chooseAnswer(View view){
        if(timerTextView.getText().toString() != "0s") {
            if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
                score++;
                resultTextView.setText("CORRECT!");
            } else {
                resultTextView.setText("WRONG!");
            }
            numQuestions++;
            pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numQuestions));
            generateQuestion();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);
        //textViews
        sumTextView = (TextView)findViewById(R.id.sumTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView)findViewById(R.id.pointsTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);


        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);

        //layouts

        startLayout = (RelativeLayout)findViewById(R.id.startLayout);
        gameLayout = (RelativeLayout)findViewById(R.id.gameLayout);

        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        playAgain(findViewById(R.id.playAgainButton));



    }

    public void playAgain(View view){
        score = 0;
        numQuestions = 0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");

        playAgainButton.setVisibility(View.INVISIBLE);

        generateQuestion();


        new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your score is " + Integer.toString(score) + "/" + Integer.toString(numQuestions));
            }
        }.start();
    }

}
