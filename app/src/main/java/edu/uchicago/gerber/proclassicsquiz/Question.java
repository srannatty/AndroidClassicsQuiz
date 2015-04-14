package edu.uchicago.gerber.proclassicsquiz;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jennifer1 on 4/14/15.
 */
public class Question implements Serializable{

    //this overrides the serializable id
    private static final long serialVersionUID = 6546546516546843135L;

    private String mLatinGreek;
    private String mEnglish;
    private String mLanguage;
    private Set<String> mWrongAnswers = new HashSet<String>();

    public Question(String LatinGreek, String english, String language) {

        this.mLatinGreek = LatinGreek;
        this.mEnglish = english;
        this.mLanguage = language;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public String getLatinGreek() {
        return mLatinGreek;
    }

    public String getEnglish() {
        return mEnglish;
    }

    public Set<String> getWrongAnswers() {
        return mWrongAnswers;
    }

    public boolean addWrongAnswer(String wrongAnswer){
        return mWrongAnswers.add(wrongAnswer);
    }

    public String getQuestionText(){
        return "Which is " + mEnglish + "?";
    }

}
