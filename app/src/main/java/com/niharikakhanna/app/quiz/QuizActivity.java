package com.niharikakhanna.app.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mOptionOne;
    private Button mCheatButton;
    private Button mOptionTwo;
    private Button mOptionThree;
    private Button mOptionFour;
    private Button mNextButton;
    private Button mAllButton;
    TextView mScoreText;
    ImageView imageView;
    public int i = 0;
    public int score = 0;
    public int answered = 0;
    private boolean cheated = false;
    public String loginName;

    private Questions[] mQuestionBank = QuestionDatabase.getArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        loginName = intent.getExtras().getString("fullname");
        score = intent.getExtras().getInt("score");
        i = intent.getExtras().getInt("id");


        mOptionOne = (Button) findViewById(R.id.option1);
        mOptionTwo = (Button) findViewById(R.id.option2);
        mOptionThree = (Button) findViewById(R.id.option3);
        mOptionFour = (Button) findViewById(R.id.option4);
        mNextButton = (Button) findViewById(R.id.next_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mScoreText = (TextView) findViewById(R.id.score);
        mAllButton = (Button) findViewById(R.id.view_all);

        mScoreText.setText(""+score);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion();
                if(!mQuestionBank[i-1].isSkipped()) {
                    mQuestionBank[i-1].setSkipped(true);
                    answered++;
                }
            }
        });

        mOptionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String buttonText = b.getText().toString();
                checkAnswer(buttonText);
                updateQuestion();
            }
        });

        mOptionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String buttonText = b.getText().toString();
                checkAnswer(buttonText);
                updateQuestion();
            }
        });

        mOptionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String buttonText = b.getText().toString();
                checkAnswer(buttonText);
                updateQuestion();
            }
        });

        mOptionFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String buttonText = b.getText().toString();
                checkAnswer(buttonText);
                updateQuestion();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer =  mQuestionBank[i].getAnswer();
                cheated = true;
                Toast.makeText(QuizActivity.this, answer, Toast.LENGTH_SHORT)
                        .show();
                mQuestionBank[i].setCheated(true);
            }
        });

        mAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this,GameActivity.class);
                intent.putExtra("score",score);
                intent.putExtra("fullname",loginName);
                startActivity(intent);
                finish();
            }
        });
        i--;
        updateQuestion();

    }


    private void checkAnswer(String userAnswer) {
        answered++;
       String answer = mQuestionBank[i].getAnswer();

        int messageResId;
        if(mQuestionBank[i].isSkipped())
            messageResId = R.string.skipped;
        else if (userAnswer.equals(answer)&&!mQuestionBank[i].isCheated()) {
            score++;
            mScoreText.setText(""+score);
            messageResId = R.string.correct_toast;
        }
        else if(userAnswer.equals(answer)&&mQuestionBank[i].isCheated()) {
            messageResId = R.string.correct_cheat;
        }
        else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT)
                .show();

        if(answered == mQuestionBank.length) {
            Intent intent = new Intent(QuizActivity.this,ScoreDisplay.class);
            intent.putExtra("fullname",loginName);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();

        }
    }


    public void updateQuestion() {
        i++;
        if (i == mQuestionBank.length) {
            i = 0;
        }
            imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageResource(mQuestionBank[i].getImageResId());

            String[] options = mQuestionBank[i].getOptions();
            mOptionOne.setText(options[0]);
            mOptionTwo.setText(options[1]);
            mOptionThree.setText(options[2]);
            mOptionFour.setText(options[3]);

    }

}

