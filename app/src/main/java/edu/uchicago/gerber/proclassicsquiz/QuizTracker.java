package edu.uchicago.gerber.proclassicsquiz;

/**
 * Created by jennifer1 on 4/14/15.
 */
public class QuizTracker {

    //Added extra variable in to check what quiz mode we are in
    private int mLanguageMode;

    /*
    private static final int LATIN = 0;
    private static final int GREEK = 1;
    private static final int MIXED = 2;
*/
    //1. strictly encapsulate members by making them all private
    private int mCorrectAnswerNum = 0;
    private int mIncorrectAnswerNum = 0;
    private String mName;
    private int mQuestionNum;
    //2. provide a static member of the same type of the enclosing class
    private  static QuizTracker sQuizTracker;

    //3. override its default constructor (remember that by default, Java provides a no-arg contractor)
    // and make it private. No one outside this class can instantiate it now.
    private QuizTracker(){}

    //4. provide a static getInstance() method. In this case, we don't instantiate until getInstance() is called for the first time (also know as lazy initialization)
    //All subsequent calls to getInstance() will return a reference to the already-instantiated sQuizTracker object. With this technique we can guarantee that
    //there is only ever one instance of this object, no matter how many times we call getInstance(). Because it is static, sQuizTracker it will persist in memory space throughout
    // the entire lifecycle of the app and accessible to any first-order component (e.g. Activity).

    //5. Activity members are often flushed -- for example in the event of a bounce.  Notice that QuizTracker is nowhere a member of any Activity.
    public static QuizTracker getInstance(){
        if (sQuizTracker == null){
            sQuizTracker = new QuizTracker();
            return sQuizTracker;
        }
        else {
            return sQuizTracker;
        }
    }

    public void setIncorrectAnswerNum(int incorrectAnswerNum) {
        mIncorrectAnswerNum = incorrectAnswerNum;
    }

    public void setCorrectAnswerNum(int correctAnswerNum) {
        mCorrectAnswerNum = correctAnswerNum;
    }

    public void reset(){
        setName("");
        setQuestionNum(1);
        setIncorrectAnswerNum(0);
        setCorrectAnswerNum(0);
    }

    public void again(){
        setQuestionNum(1);
        setIncorrectAnswerNum(0);
        setCorrectAnswerNum(0);
    }

    public int getQuestionNum() {
        return mQuestionNum;
    }

    public void setQuestionNum(int questionNum) {
        mQuestionNum = questionNum;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    //language get and set function
    public int getLanguageMode() {
        return mLanguageMode;
    }
    public void setLanguageMode(int type) {
        mLanguageMode = type;
    }

    public void answeredWrong(){
        mIncorrectAnswerNum++;
    }

    public void answeredRight(){
        mCorrectAnswerNum++;
    }

    public void incrementQuestionNumber(){
        mQuestionNum++;
    }

    public int getCorrectAnswerNum(){
        return mCorrectAnswerNum;
    }

    public int getIncorrectAnswerNum(){
        return mIncorrectAnswerNum;
    }

    public int getTotalAnswers(){
        return mCorrectAnswerNum + mIncorrectAnswerNum;
    }

}
