package edu.uchicago.gerber.proclassicsquiz;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by jennifer1 on 4/14/15.
 */
public class QuestionActivity extends ActionBarActivity {


    public static final String QUESTION = "edu.uchicago.cs.quiz.gerber.QUESTION";

    private static final String DELIMITER = "\\|";
    private static final int NUM_ANSWERS = 5;
    private static final int LG = 0; //change 0->1
    private static final int ENG = 1; // change 1->0
    private static final int LANG = 2;

    private Random mRandom;

    private Question mQuestion;

    private String[] mWords;

    private boolean mItemSelected = false;

    //make these members
    private TextView mQuestionNumberTextView;
    private RadioGroup mQuestionRadioGroup;
    private TextView mQuestionTextView;
    private Button mSubmitButton;
    private Button mQuitButton;

    //type checks
    private static final int LATIN = 0;
    private static final int GREEK = 1;
    private static final int MIXED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        //generate a question
        mWords = getResources().getStringArray(R.array.word_classics);

        //get refs to inflated members
        mQuestionNumberTextView = (TextView) findViewById(R.id.questionNumber);
        mQuestionTextView = (TextView) findViewById(R.id.questionText);
        mSubmitButton = (Button) findViewById(R.id.submitButton);

        //init the random
        mRandom = new Random();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submit();
            }
        });


        //set quit button action
        mQuitButton = (Button) findViewById(R.id.quitButton);
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayResult();
            }
        });

        mQuestionRadioGroup = (RadioGroup) findViewById(R.id.radioAnswers);
        //disallow submitting until an answer is selected
        mQuestionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mSubmitButton.setEnabled(true);
                mItemSelected = true;
            }
        });


        // fireQuestion();
        fireQuestion(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //pass the question into the bundle when I have a config change
        outState.putSerializable(QuestionActivity.QUESTION, mQuestion);
    }

    private void fireQuestion(){
        mQuestion = getQuestion();
        populateUserInterface();
    }
    //overloaded to take savedInstanceState
    private void fireQuestion(Bundle savedInstanceState){

        if (savedInstanceState == null ){
            mQuestion = getQuestion();
        } else {
            mQuestion = (Question) savedInstanceState.getSerializable(QuestionActivity.QUESTION);
        }

        populateUserInterface();

    }



    private void submit() {

        Button checkedButton = (Button) findViewById(mQuestionRadioGroup.getCheckedRadioButtonId());
        String guess = checkedButton.getText().toString();
        //see if they guessed right
        if (mQuestion.getLatinGreek().equals(guess)) {
            QuizTracker.getInstance().answeredRight();
        } else {
            QuizTracker.getInstance().answeredWrong();
        }
        if (QuizTracker.getInstance().getTotalAnswers() < Integer.MAX_VALUE) {
            //increment the question number
            QuizTracker.getInstance().incrementQuestionNumber();

            fireQuestion();
        } else {

            displayResult();
        }

    }

    private void populateUserInterface() {
        //take care of button first
        mSubmitButton.setEnabled(false);
        mItemSelected = false;

        //populate the QuestionNumber textview
        String questionNumberText = getResources().getString(R.string.questionNumberText);
        int number = QuizTracker.getInstance().getQuestionNum();
        mQuestionNumberTextView.setText(String.format(questionNumberText, number));

        //set question text
        mQuestionTextView.setText(mQuestion.getQuestionText());

        //will generate a number 0-4 inclusive
        int randomPosition = mRandom.nextInt(NUM_ANSWERS);
        int counter = 0;
        mQuestionRadioGroup.removeAllViews();
        //for each of the 5 wrong answers
        for (String wrongAnswer : mQuestion.getWrongAnswers()) {
            if (counter == randomPosition) {
                //insert the cor answer
                addRadioButton(mQuestionRadioGroup, mQuestion.getLatinGreek());
            } else {
                addRadioButton(mQuestionRadioGroup, wrongAnswer);
            }
            counter++;
        }
    }

    private void addRadioButton(RadioGroup questionGroup, String text) {
        RadioButton button = new RadioButton(this);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setButtonDrawable(android.R.drawable.btn_radio);
        questionGroup.addView(button);

    }


    private Question getQuestion() {

        //Check what type of quiz we are in
        int type = QuizTracker.getInstance().getLanguageMode();
        String mLangType;

        if (type==LATIN) {
            mLangType = "LAT";
        }
        else if (type==GREEK) {
            mLangType = "GRK";
        }
        else {
            mLangType = "MIX";
        }

        //generate corr answer, initialize for Latin and Greek mode
        String[] strAnswers = getWord();
        //get question until you grab the word in the same language.
        if (!mLangType.equals("MIX")) {
            while (!strAnswers[LANG].equals(mLangType)) {
                strAnswers = getWord();
            }
        }
        mQuestion = new Question(strAnswers[LG], strAnswers[ENG], strAnswers[LANG]);


        //generates 5 wrong answers
        while (mQuestion.getWrongAnswers().size() < NUM_ANSWERS) {
            String[] strLatinGreek = getWord();

            //if the one we picked is equal to the answer OR
            //if is not from the same language as the answer OR
            //if we already picked this one
            while (strLatinGreek[ENG].equals(strAnswers[ENG]) ||
                    !strLatinGreek[LANG].equals(strAnswers[LANG]) ||
                    mQuestion.getWrongAnswers().contains(strLatinGreek[ENG])) {
                //then we need pick another one
                strLatinGreek = getWord();
            }

            mQuestion.addWrongAnswer(strLatinGreek[LG]);
        }
        return mQuestion;
    }

//    public Question getTheQuestion(){
//        return mQuestion;
//    }

    private String[] getWord() {
        int index = mRandom.nextInt(mWords.length);
        return mWords[index].split(DELIMITER);
    }


    private void displayResult(){

        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuQuit:
                displayResult();
                return true;

            case R.id.menuSubmit:
                if(mItemSelected){

                    submit();
                }
                else{
                    Toast toast = Toast.makeText(this, getResources().getText(R.string.pleaseSelectAnswer), Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
