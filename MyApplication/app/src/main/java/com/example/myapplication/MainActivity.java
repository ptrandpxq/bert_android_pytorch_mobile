package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.pytorch.IValue;
//import org.pytorch.LiteModuleLoader;
import org.pytorch.Module;
import org.pytorch.Tensor;
//import org.pytorch.torchvision.TensorImageUtils;
import org.pytorch.MemoryFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    private Module mModule;
    private EditText mEditTextQuestion;
    private EditText mEditTextText;
    private TextView mTextViewAnswer;
//    private Button mButton;

    private HashMap<String, Long> mTokenIdMap;
    private HashMap<Long, String> mIdTokenMap;

    private final int MODEL_INPUT_LENGTH = 360;
    private final int EXTRA_ID_NUM = 3;
    private final String CLS = "[CLS]";
    private final String SEP = "[SEP]";
    private final String PAD = "[PAD]";
    private final String START_LOGITS = "start_logits";
    private final String END_LOGITS = "end_logits";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName2);

        String message = editText.getText().toString();

        TextView textView = findViewById(R.id.text_output);
        String temp_message = tokenizer(message,message);
        textView.setText(temp_message);
    }

    private final String tokenizer(String question, String text) {
        List<Long> tokenIdsQuestion = this.wordPieceTokenizer(question);
//        if (tokenIdsQuestion.size() >= this.MODEL_INPUT_LENGTH) {
//            throw (Throwable)(new org.pytorch.demo.questionanswering.QAException("Question too long"));
//            System.out.println("no");
//        } else {
            List<Long> tokenIdsText = this.wordPieceTokenizer(text);
            int inputLength = tokenIdsQuestion.size() + tokenIdsText.size() + this.EXTRA_ID_NUM;
            long[] ids = new long[Math.min(this.MODEL_INPUT_LENGTH, inputLength)];
//
            ids[0] =this.mTokenIdMap.get(CLS);
//
//
//            for(int i =0; i <tokenIdsQuestion.size(); ++i) {
//                ids[i + 1] = tokenIdsQuestion.get(i).longValue();
//            }
//            ids[tokenIdsQuestion.size() + 1] = this.mTokenIdMap.get(SEP);
//            int maxTextLength = Math.min(tokenIdsText.size(), this.MODEL_INPUT_LENGTH - tokenIdsQuestion.size() - this.EXTRA_ID_NUM);
//
//            for(int i = 0; i < maxTextLength; ++i) {
//                ids[tokenIdsQuestion.size() + i + 2] = tokenIdsText.get(i).longValue();
//            }
//
//            ids[tokenIdsQuestion.size() + maxTextLength + 2] = this.mTokenIdMap.get(SEP);

            return "Pass";
//        }
    }

    private final List<Long> wordPieceTokenizer(String questionOrText) {
        // for each token, if it's in the vocab.txt (a key in mTokenIdMap), return its Id
        // else do: a. find the largest sub-token (at least the first letter) that exists in vocab;
        // b. add "##" to the rest (even if the rest is a valid token) and get the largest sub-token "##..." that exists in vocab;
        // and c. repeat b.
        List tokenIds = (List)(new ArrayList());
        Pattern p = Pattern.compile("\\w+|\\S");
        Matcher m = p.matcher((CharSequence)questionOrText);

//        while (m.find()) {
//            String token = m.group().toLowerCase();
//
//            if (this.mTokenIdMap.containsKey(token) == null) {
//                tokenIds.add(mTokenIdMap.get(token));
//            } else {

//        }
        return tokenIds;
    }

}