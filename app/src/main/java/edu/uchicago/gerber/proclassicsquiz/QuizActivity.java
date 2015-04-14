package edu.uchicago.gerber.proclassicsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

/**
 * Created by jennifer1 on 4/14/15.
 */
public class QuizActivity extends ActionBarActivity{


    private Button mExitButton;

    private Button mStartButtonLatin;
    private Button mStartButtonGreek;
    private Button mStartButtonMixed;

    private String mName;
    private EditText mNameEditText;

    private static final int LATIN = 0;
    private static final int GREEK = 1;
    private static final int MIXED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //define behaviors
        mNameEditText = (EditText) findViewById(R.id.editName);

        //exit button
        mExitButton = (Button) findViewById(R.id.exitButton);
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });

        //start button
        mStartButtonLatin = (Button) findViewById(R.id.startButtonLatin);
        mStartButtonLatin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMe(LATIN);
            }
        });

        mStartButtonGreek = (Button) findViewById(R.id.startButtonGreek);
        mStartButtonGreek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMe(GREEK);
            }
        });

        mStartButtonMixed = (Button) findViewById(R.id.startButtonMixed);
        mStartButtonMixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMe(MIXED);
            }
        });


    }

    private void startMe(int type) {
        mName = mNameEditText.getText().toString();
        QuizTracker.getInstance().setName(mName);
        QuizTracker.getInstance().setLanguageMode(type);
        askQuestion(1);
    }

    private void askQuestion(int number) {
        QuizTracker.getInstance().setQuestionNum(number);
        Intent intent = new Intent(QuizActivity.this, QuestionActivity.class);
        startActivity(intent);
    }



    //these are for the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuExit:
                getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                return true;
            case R.id.menuStartLatin:
                startMe(LATIN);
                return true;
            case R.id.menuStartGreek:
                startMe(GREEK);
                return true;
            case R.id.menuStartMixed:
                startMe(MIXED);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
