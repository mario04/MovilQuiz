package com.example.movilquiz;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends Activity {
	
	
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mRefreshButton;
	private TextView mQuestionTextView;
	private TextView mQuestionTextView2;
	private TextView mQuestionTextView3;
	private TrueFalse[] mQuestionBank = new TrueFalse[]{
		new TrueFalse(R.string.question1, true),
		new TrueFalse(R.string.question2, false),
		new TrueFalse(R.string.question3, false),
		new TrueFalse(R.string.question4, false),
		new TrueFalse(R.string.question5, false),
		new TrueFalse(R.string.question6, true),
		new TrueFalse(R.string.question7, true),
		new TrueFalse(R.string.question8, true),
		new TrueFalse(R.string.question9, false),
		new TrueFalse(R.string.question10, true),
		new TrueFalse(R.string.question11, true),
		new TrueFalse(R.string.question12, false),
		new TrueFalse(R.string.question13, false),
		new TrueFalse(R.string.question14, true),
		new TrueFalse(R.string.question15, false),
	};
	private int aleatorio=0;
	private int [] mCurrentIndex = new int[10];
	private int aux[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private int index = 0;
	private int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        setContentView(R.layout.activity_quiz);
        for(int i = 0; i < mCurrentIndex.length; i++){
        	aleatorio = (int) Math.floor(Math.random()*(14+1));
        	while(aux[aleatorio]==1)
        		aleatorio = (int) Math.floor(Math.random()*(14+1));
        	aux[aleatorio]=1;
        	mCurrentIndex[i]=aleatorio;
        }
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            score = savedInstanceState.getInt(STATE_SCORE);
            index = savedInstanceState.getInt(STATE_INDEX);
            mCurrentIndex = savedInstanceState.getIntArray(STATE_MCINDEX);
         }
        mQuestionTextView=(TextView)findViewById(R.id.question_text_view);
        mQuestionTextView2=(TextView)findViewById(R.id.textCount2);
        mQuestionTextView2.setText(Integer.toString(score));
        mQuestionTextView3=(TextView)findViewById(R.id.textCount);
        mQuestionTextView3.setText(R.string.Score);
        
        
        final int question = mQuestionBank[mCurrentIndex[index]].getQuestion();
        mQuestionTextView.setText(question);
        mTrueButton=(Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	checkAnswer(true);
                
                index=(index+1);
                if(index>=mCurrentIndex.length){
                	mTrueButton.setVisibility(View.INVISIBLE);
                	mFalseButton.setVisibility(View.INVISIBLE);
                	mRefreshButton.setVisibility(View.VISIBLE);
                	mQuestionTextView.setVisibility(View.INVISIBLE);
                	mQuestionTextView3.setTextColor(Color.GREEN);
                	mQuestionTextView3.setText(R.string.MensajeFinal);
                	mQuestionTextView2.setTextColor(Color.GREEN);
                	mQuestionTextView2.setText(Integer.toString(score));
                }
                else{
                	int question = mQuestionBank[mCurrentIndex[index]].getQuestion();
                	mQuestionTextView.setText(question);
                }
            }
        });
        mFalseButton=(Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                index=(index+1);
                if(index>=mCurrentIndex.length){
                	mTrueButton.setVisibility(View.INVISIBLE);
                	mFalseButton.setVisibility(View.INVISIBLE);
                	mRefreshButton.setVisibility(View.VISIBLE);
                	mQuestionTextView.setVisibility(View.INVISIBLE);
                	mQuestionTextView3.setTextColor(Color.GREEN);
                	mQuestionTextView3.setText(R.string.MensajeFinal);
                	mQuestionTextView2.setTextColor(Color.GREEN);
                	mQuestionTextView2.setText(Integer.toString(score));
                	
                }
                else{
                	int question = mQuestionBank[mCurrentIndex[index]].getQuestion();
                	mQuestionTextView.setText(question);
                }
            }
        });
        mRefreshButton=(Button)findViewById(R.id.reiniciar_button);
        mRefreshButton.setVisibility(View.INVISIBLE);
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<aux.length; i++)
                	aux[i]=0;
                index = 0;
                score = 0;
                for(int i = 0; i < mCurrentIndex.length; i++){
                	aleatorio = (int) Math.floor(Math.random()*(14+1));
                	while(aux[aleatorio]==1)
                		aleatorio = (int) Math.floor(Math.random()*(14+1));
                	aux[aleatorio]=1;
                	mCurrentIndex[i]=aleatorio;
                }
                mTrueButton.setVisibility(View.VISIBLE);
            	mFalseButton.setVisibility(View.VISIBLE);
            	mRefreshButton.setVisibility(View.INVISIBLE);
            	mQuestionTextView.setVisibility(View.VISIBLE);
            	mQuestionTextView2.setTextColor(Color.BLACK);
            	mQuestionTextView3.setTextColor(Color.BLACK);
            	mQuestionTextView2.setText(Integer.toString(score));
            	mQuestionTextView3.setText(R.string.Score);
            	int question = mQuestionBank[mCurrentIndex[index]].getQuestion();
            	mQuestionTextView.setText(question);
            	
            }
        });
         
    }
    
    static final String STATE_SCORE = "playerScore";
    static final String STATE_INDEX = "playerIndex";
    static final String STATE_MCINDEX = "playerMCIndex";
  
    @Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putInt(STATE_SCORE, score);
    	savedInstanceState.putInt(STATE_INDEX, index);
    	savedInstanceState.putIntArray(STATE_MCINDEX, mCurrentIndex);
    	
		
	}


	private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue=mQuestionBank[mCurrentIndex[index]].isTrueQuestion();
        int messageResId=0;
        if(userPressedTrue==answerIsTrue){
            messageResId=R.string.correct_toast;
            score++;
        }
        else{
            messageResId=R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        mQuestionTextView2.setText(Integer.toString(score));
    }


  
}
