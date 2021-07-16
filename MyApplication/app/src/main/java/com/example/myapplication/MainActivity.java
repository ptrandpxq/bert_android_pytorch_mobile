package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.pytorch.IValue;
import org.pytorch.LiteModuleLoader;
import org.pytorch.Module;
import org.pytorch.Tensor;
//import org.pytorch.torchvision.TensorImageUtils;
//import org.pytorch.MemoryFormat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private final int MODEL_INPUT_LENGTH = 128;
    private final int EXTRA_ID_NUM = 3;
    private final String CLS = "[CLS]";
    private final String SEP = "[SEP]";
    private final String PAD = "[PAD]";
    private final String UNK = "[UNK]";
    private final String START_LOGITS = "start_logits";
    private final String END_LOGITS = "end_logits";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the model
        try {
            mModule = LiteModuleLoader.load(MainActivity.assetFilePath(getApplicationContext(), "BERT.ptl"));
        } catch (IOException e) {
            Log.e("BERT Inference", "Error reading assets", e);
            finish();
        }

    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) throws IOException {
        // Do something in response to button
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName2);

        String message = editText.getText().toString();


        // logic
//        BufferedReader br = new BufferedReader(new InputStreamReader(this.getAssets().open("vocab.txt")));
        InputStreamReader ir = new InputStreamReader(this.getAssets().open("vocab.txt")); // need to use inpustreamreader and getassets method
//        FileReader fr = new FileReader("../vocab.txt");
        BufferedReader br = new BufferedReader(ir);

        String line;
        this.mTokenIdMap = new HashMap<String, Long>(); // create the HashMap that maps word and id
        this.mIdTokenMap = new HashMap<Long, String>(); // create the HashMap that maps id and word


        long count = 0L;
        while(true) {

            line = br.readLine();
            if (line != null) {
//                System.out.println(line+count);
                this.mTokenIdMap.put(line, count); // HashMap that maps word and id
                this.mIdTokenMap.put(count, line); // HashMap that maps id and word
                count++; // count++ and give each word a id
            } else {
                break;
            }

        }


        TextView textView = findViewById(R.id.text_output);
        long[] ids = tokenizer("like two the the"); // let input pass the tokenizer
        for (long i : ids) {
            System.out.print(i+",");
        }
        System.out.println("?<?><");
        String temp_message = String.valueOf(ids[1]);
        textView.setText(temp_message);

        int a = this.Inference("I hate the movie .");
        System.out.println("Prediction: " + a);
        // until here



    }

    private final long[] tokenizer(String text) throws IOException {
            List<Long> tokenIdsText = this.wordPieceTokenizer(text);
//            for (int i = 0; i < tokenIdsText.size(); ++i) {
//                System.out.println(tokenIdsText.get(i));
//            }
            int inputLength = tokenIdsText.size() + this.EXTRA_ID_NUM;
            long[] ids = new long[Math.min(this.MODEL_INPUT_LENGTH, inputLength)];


            ids[0] = this.mTokenIdMap.get(this.CLS);
//            System.out.println(ids[0]);
//
//            System.out.println(tokenIdsText.size()); // The size will be equal to the input length e.g., like the = 2
            for(int i = 0; i < tokenIdsText.size(); ++i) {
                ids[i + 1] = tokenIdsText.get(i).longValue(); // put word ids into ids List
            }
            ids[tokenIdsText.size() + 1] = this.mTokenIdMap.get(this.SEP);
//
//            System.out.println(ids);
//            for (long j : ids) {
//                System.out.println("iiii"+j);
//            }


//            int maxTextLength = Math.min(tokenIdsText.size(), this.MODEL_INPUT_LENGTH - tokenIdsQuestion.size() - this.EXTRA_ID_NUM);
//
//            for(int i = 0; i < maxTextLength; ++i) {
//                ids[tokenIdsQuestion.size() + i + 2] = tokenIdsText.get(i).longValue();
//            }
//
//            ids[tokenIdsQuestion.size() + maxTextLength + 2] = this.mTokenIdMap.get(SEP);

            return ids;
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

            // if the token in the mTokenIdMap then add its id to tokenIds
            if (this.mTokenIdMap.containsKey(token)) {
                tokenIds.add(this.mTokenIdMap.get(token));
            } else {

                for (int i = 0; i < token.length(); ++i) {

                    System.out.println("jinlaile"+token.substring(0, token.length() - i - 1));
                    if (this.mTokenIdMap.containsKey(token.substring(0, token.length() - i - 1))) {

                        tokenIds.add(this.mTokenIdMap.get(token.substring(0, token.length() - i - 1)));
                        String subToken = token.substring(token.length() - i - 1); // latter part of the word
                        int j = 0;
                        System.out.println(this.mTokenIdMap.get(token.substring(0, token.length() - i - 1)));
                        System.out.println(subToken);

                        while (j < subToken.length()) {
                            if (this.mTokenIdMap.containsKey("##" + subToken.substring(0, subToken.length() - j))) {
                                System.out.println(this.mTokenIdMap.get("##" + subToken.substring(0, subToken.length() - j)));
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


    public static String assetFilePath(Context context, String assetName) throws IOException {
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(assetName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }

    private int Inference(String input) throws IOException {
//        float[] ft= new float[1024];
//        long[] shape = {1,1024};
//        Tensor test_tensor = Tensor.fromBlob(ft, shape);
//        System.out.println(test_tensor+"iopiopiop");

//        IValue outTensors = mModule.forward(IValue.from(test_tensor));
//        System.out.println(outTensors.toTensor());

        int result;

        long[] tokenIds = this.tokenizer(input);
        for (long i : tokenIds) {
            System.out.println("Token id "+i);
        }

        LongBuffer inTensorBuffer = Tensor.allocateLongBuffer(this.MODEL_INPUT_LENGTH);
        // put token ids to inTensorBuffer
        for (long n : tokenIds) {
            inTensorBuffer.put(n);
        }
        // Fill paddings
        for (int i = 0; i < MODEL_INPUT_LENGTH - tokenIds.length; ++i) {
            inTensorBuffer.put(this.mTokenIdMap.get(this.PAD));
        }

        Tensor inTensor = Tensor.fromBlob(inTensorBuffer, new long[]{1L, this.MODEL_INPUT_LENGTH}); // fromBlob (input_buffer, input_shape)



        LongBuffer inTensorBuffer_2 = Tensor.allocateLongBuffer(this.MODEL_INPUT_LENGTH);
        // put token ids to inTensorBuffer
        for (int i = 0; i < MODEL_INPUT_LENGTH; ++i) {
            inTensorBuffer_2.put(0L);
        }
        Tensor inTensor2 = Tensor.fromBlob(inTensorBuffer_2, new long[]{1L, this.MODEL_INPUT_LENGTH});


        LongBuffer inTensorBuffer_3 = Tensor.allocateLongBuffer(this.MODEL_INPUT_LENGTH);
        // put token ids to inTensorBuffer
        for (int i = 0; i < MODEL_INPUT_LENGTH; ++i) {
            inTensorBuffer_3.put(1L);
        }
        Tensor inTensor3 = Tensor.fromBlob(inTensorBuffer_3, new long[]{1L, this.MODEL_INPUT_LENGTH});


        System.out.println(inTensor);
        long[] test = inTensor.getDataAsLongArray();
        for (long i : test) {
            System.out.println(i);
        }
//        final long startTime = SystemClock.elapsedRealtime();
        IValue outIValue = mModule.forward(IValue.from(inTensor), IValue.from(inTensor2), IValue.from(inTensor3));
//        IValue outTensors = mModule.forward(IValue.from(inTensor)); // the output of BERT is a tuple
//        final long inferenceTime = SystemClock.elapsedRealtime() - startTime;
//        Log.d("BERTINFERNCE",  "inference time (ms): " + inferenceTime);

        IValue[] outTuple = outIValue.toTuple();
//        Map<String, IValue> outTensors = mModule.forward(IValue.from(inTensor)).toDictStringKey();
//        System.out.println(outTuple.length);
        Tensor outTensor = outTuple[0].toTensor();
        float[] outTensorFloatArray = outTensor.getDataAsFloatArray();
//        System.out.println(outTuple[0].toTensor());

//        for (float f : outTensorFloatArray) {
//            System.out.print(f);
//        }

        // Add a simple prediction label
        if (outTensorFloatArray[0] > outTensorFloatArray[1]) {
            result = 0;
        } else {
            result = 1;
        }

        return result;
    }

}