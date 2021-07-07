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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.jvm.internal.Intrinsics;


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
    public void sendMessage(View view) throws IOException {
        // Do something in response to button
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName2);

        String message = editText.getText().toString();


        // logic
        InputStreamReader ir = new InputStreamReader(this.getAssets().open("vocab.txt")); // need to use inpustreamreader and getassets method
//        FileReader fr = new FileReader("../vocab.txt");
        BufferedReader br = new BufferedReader(ir);

        System.out.println(br.readLine());
        String line;
        this.mTokenIdMap = new HashMap();
        this.mIdTokenMap = new HashMap();


        long count = 0L;
        System.out.println("no");
        while(true) {

            line = br.readLine();
            if (line != null) {
                System.out.println(line);
                this.mTokenIdMap.put(line,count);
                this.mIdTokenMap.put(count, line);
            } else {
                break;
            }


        }




        ////



        TextView textView = findViewById(R.id.text_output);
        String temp_message = tokenizer(message); // let input pass the tokenizer
        textView.setText(temp_message);
        // until here

        //            BufferedReader br = new BufferedReader(new InputStreamReader(this.getAssets().open("vocab.txt")));



    }

    private final String tokenizer(String text) {
            List<Long> tokenIdsText = this.wordPieceTokenizer(text);
            System.out.println("startqweqweqwe");
            for (int i = 0; i < tokenIdsText.size(); ++i) {
                System.out.println(tokenIdsText.get(i));
            }
            int inputLength = tokenIdsText.size() + this.EXTRA_ID_NUM;
            long[] ids = new long[Math.min(this.MODEL_INPUT_LENGTH, inputLength)];


//            ids[0] = this.mTokenIdMap.get(this.CLS);
//            System.out.println(ids[0]);
//            System.out.println("qweqweqwe");
////
////
//            for(int i =0; i <tokenIdsText.size(); ++i) {
//                ids[i + 1] = this.mTokenIdMap.get(tokenIdsText.get(i)).longValue(); // get word's id
//            }
//            ids[tokenIdsText.size() + 1] = this.mTokenIdMap.get(this.SEP);

//            System.out.println(ids);
//            for (long j : ids) {
//                System.out.println(j);
//                System.out.println("qweqweqwe");
//            }

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
        List<Long> tokenIds = (List)(new ArrayList());
        Pattern p = Pattern.compile("\\w+|\\S");
        Matcher m = p.matcher((CharSequence)questionOrText); //seprate the tokens

        while (m.find()) {
            String token = m.group().toLowerCase();
            System.out.println(this.mTokenIdMap.containsKey(token)+"werwerwer");

            // if mTokenIdMap doest have the token
            if (!this.mTokenIdMap.containsKey(token)) {
                tokenIds.add(this.mTokenIdMap.get(token));
            } else {
                System.out.println("jinlaile");
                for (int i = 0; i < token.length(); ++i) {


                    if (!this.mTokenIdMap.containsKey(token.substring(0, token.length() - i - 1))) {

                        tokenIds.add(this.mTokenIdMap.get(token.substring(0, token.length() - i - 1)));
                        String subToken = token.substring(token.length() - i - 1);
                        int j = 0;

                        while (j < subToken.length()) {
                            if (!this.mTokenIdMap.containsKey("##" + subToken.substring(0, subToken.length() - j))) {
                                tokenIds.add(this.mTokenIdMap.get("##" + subToken.substring(0, subToken.length() - j)));
                                subToken = subToken.substring(subToken.length() - j);
                                j = subToken.length() - j;
                            } else if (j == subToken.length() - 1) {
                                tokenIds.add(this.mTokenIdMap.get("##$subToken"));
                                break;
                            } else {
                                j++;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return tokenIds;
    }

}