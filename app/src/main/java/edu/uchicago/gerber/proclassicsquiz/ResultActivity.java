package edu.uchicago.gerber.proclassicsquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jennifer1 on 4/14/15.
 */
public class ResultActivity extends ActionBarActivity {

    private Button mResetButton;
    private Button mAgainButton;
    private TextView mHeaderTextView;
    private TextView mCorrectTextView;
    private  TextView mIncorrectTextView;
    private TextView mScoreTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate layout
        setContentView(R.layout.activity_result);

        //get refs to inflated members
        mResetButton = (Button) findViewById(R.id.resetButton);
        mAgainButton = (Button) findViewById(R.id.anotherQuizButton);
        mHeaderTextView = (TextView) findViewById(R.id.resultHeader);
        mCorrectTextView = (TextView) findViewById(R.id.correctText);
        mIncorrectTextView = (TextView) findViewById(R.id.incorrectText);
        mScoreTextView = (TextView) findViewById(R.id.scoreText);

        //set name
        String name = QuizTracker.getInstance().getName();
        String headerTemplate = getResources().getString(R.string.nameResultHeader);
        mHeaderTextView.setText(String.format(headerTemplate, name));

        //set number correct
        int numberCorrect = QuizTracker.getInstance().getCorrectAnswerNum();
        String correctTemplate = getResources().getString(R.string.correct);
        mCorrectTextView.setText(String.format(correctTemplate, numberCorrect));

        //set number incorrect
        int numberWrong = QuizTracker.getInstance().getIncorrectAnswerNum();
        String incorrectTemplate = getResources().getString(R.string.incorrect);
        mIncorrectTextView.setText(String.format(incorrectTemplate, numberWrong));

        //calculate percent score
        int total = QuizTracker.getInstance().getTotalAnswers();
        String scoreTemplate = getResources().getString(R.string.score);
        int scoreValue = (int) Math.floor(100*((double) numberCorrect)/((double) total));
        mScoreTextView.setText(String.format(scoreTemplate, scoreValue));


        //define behavior of buttons
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reset();
            }
        });


        mAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                again();
            }
        });
    }




    private void reset() {
        QuizTracker.getInstance().reset();
        Intent intent = new Intent(this,QuizActivity.class );
        startActivity(intent);
        finish();
    }

    private void again(){
        QuizTracker.getInstance().again();
        Intent intent = new Intent(this,QuestionActivity.class );
        startActivity(intent);
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAnotherQuiz:

                again();
                return true;
            case R.id.menuReset:

                reset();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
