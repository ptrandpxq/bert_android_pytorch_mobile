package com.example.myapplication;


import org.jetbrains.annotations.NotNull;

public final class QAException extends Exception {
    @NotNull
    private String message;

    @NotNull
    public String getMessage() {
        return this.message;
    }

    public void setMessage(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.message = var1;
    }

    public QAException(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        super();
        this.message = message;
    }
}
// MainActivity.java
package org.pytorch.demo.questionanswering;

        import android.content.Context;
        import android.content.res.AssetManager;
        import android.os.Bundle;
        import android.text.style.StyleSpan;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import androidx.appcompat.app.AppCompatActivity;
        import java.io.BufferedReader;
        import java.io.Closeable;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.Reader;
        import java.nio.LongBuffer;
        import java.util.ArrayList;
        import java.util.Collection;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;
        import kotlin.Metadata;
        import kotlin.Unit;
        import kotlin.io.CloseableKt;
        import kotlin.jvm.internal.Intrinsics;
        import kotlin.text.Regex;
        import kotlin.text.StringsKt;
        import org.jetbrains.annotations.NotNull;
        import org.jetbrains.annotations.Nullable;
        import org.pytorch.IValue;
        import org.pytorch.LiteModuleLoader;
        import org.pytorch.Module;
        import org.pytorch.Tensor;

@Metadata(
        mv = {1, 4, 3},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0016\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u0005H\u0002J\u0010\u0010\u001d\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u001a\u0010 \u001a\u0004\u0018\u00010\u00052\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0005J\u0012\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010'H\u0014J\b\u0010(\u001a\u00020%H\u0016J\u0018\u0010)\u001a\u00020*2\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u0005H\u0002J\u0018\u0010+\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140,2\u0006\u0010-\u001a\u00020\u0005H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0014\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."},
        d2 = {"Lorg/pytorch/demo/questionanswering/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Ljava/lang/Runnable;", "()V", "CLS", "", "END_LOGITS", "EXTRA_ID_NUM", "", "MODEL_INPUT_LENGTH", "PAD", "SEP", "START_LOGITS", "mButton", "Landroid/widget/Button;", "mEditTextQuestion", "Landroid/widget/EditText;", "mEditTextText", "mIdTokenMap", "Ljava/util/HashMap;", "", "mModule", "Lorg/pytorch/Module;", "mTextViewAnswer", "Landroid/widget/TextView;", "mTokenIdMap", "answer", "question", "text", "argmax", "array", "", "assetFilePath", "context", "Landroid/content/Context;", "assetName", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "run", "tokenizer", "", "wordPieceTokenizer", "", "questionOrText", "app_debug"}
)
public final class MainActivity extends AppCompatActivity implements Runnable {
    private Module mModule;
    private EditText mEditTextQuestion;
    private EditText mEditTextText;
    private TextView mTextViewAnswer;
    private Button mButton;
    private HashMap mTokenIdMap;
    private HashMap mIdTokenMap;
    private final int MODEL_INPUT_LENGTH = 360;
    private final int EXTRA_ID_NUM = 3;
    private final String CLS = "[CLS]";
    private final String SEP = "[SEP]";
    private final String PAD = "[PAD]";
    private final String START_LOGITS = "start_logits";
    private final String END_LOGITS = "end_logits";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(1300009);
        this.mButton = (Button)this.findViewById(1000153);
        this.mEditTextText = (EditText)this.findViewById(1000177);
        this.mEditTextQuestion = (EditText)this.findViewById(1000077);
        this.mTextViewAnswer = (TextView)this.findViewById(1000184);
        EditText var10000 = this.mEditTextText;
        if (var10000 != null) {
            var10000.setText((CharSequence)"There is a growing need to execute ML models on edge devices to reduce latency, preserve privacy and enable new interactive use cases. In the past, engineers used to train models separately. They would then go through a multi-step, error prone and often complex process to transform the models for execution on a mobile device. The mobile runtime was often significantly different from the operations available during training leading to inconsistent developer and eventually user experience. PyTorch Mobile removes these friction surfaces by allowing a seamless process to go from training to deployment by staying entirely within the PyTorch ecosystem. It provides an end-to-end workflow that simplifies the research to production environment for mobile devices. In addition, it paves the way for privacy-preserving features via Federated Learning techniques. PyTorch Mobile is in beta stage right now and in wide scale production use. It will soon be available as a stable release once the APIs are locked down. Key features of PyTorch Mobile: Available for iOS, Android and Linux; Provides APIs that cover common preprocessing and integration tasks needed for incorporating ML in mobile applications; Support for tracing and scripting via TorchScript IR; Support for XNNPACK floating point kernel libraries for Arm CPUs; Integration of QNNPACK for 8-bit quantized kernels. Includes support for per-channel quantization, dynamic quantization and more; Build level optimization and selective compilation depending on the operators needed for user applications, i.e., the final binary size of the app is determined by the actual operators the app needs; Support for hardware backends like GPU, DSP, NPU will be available soon.");
        }

        var10000 = this.mEditTextQuestion;
        if (var10000 != null) {
            var10000.setText((CharSequence)"What are the key features of pytorch mobile?");
        }

        Button var10 = this.mButton;
        if (var10 != null) {
            var10.setOnClickListener((OnClickListener)(new OnClickListener() {
                public final void onClick(View it) {
                    Button var10000 = MainActivity.this.mButton;
                    if (var10000 != null) {
                        var10000.setEnabled(false);
                    }

                    Thread thread = new Thread((Runnable)MainActivity.this);
                    thread.start();
                }
            }));
        }

        try {
            BufferedReader br = new BufferedReader((Reader)(new InputStreamReader(this.getAssets().open("vocab.txt"))));
            String line = null;
            this.mTokenIdMap = new HashMap();
            this.mIdTokenMap = new HashMap();
            long count = 0L;

            while(true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }

                HashMap var11 = this.mTokenIdMap;
                Intrinsics.checkNotNull(var11);
                ((Map)var11).put(line, count);
                var11 = this.mIdTokenMap;
                Intrinsics.checkNotNull(var11);
                ((Map)var11).put(count, line);
                ++count;
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }

    }

    private final long[] tokenizer(String question, String text) throws QAException {
        List tokenIdsQuestion = this.wordPieceTokenizer(question);
        if (tokenIdsQuestion.size() >= this.MODEL_INPUT_LENGTH) {
            throw (Throwable)(new QAException("Question too long"));
        } else {
            List tokenIdsText = this.wordPieceTokenizer(text);
            int inputLength = tokenIdsQuestion.size() + tokenIdsText.size() + this.EXTRA_ID_NUM;
            long[] ids = new long[Math.min(this.MODEL_INPUT_LENGTH, inputLength)];
            HashMap var10002 = this.mTokenIdMap;
            Intrinsics.checkNotNull(var10002);
            Object var10 = var10002.get(this.CLS);
            Intrinsics.checkNotNull(var10);
            Intrinsics.checkNotNullExpressionValue(var10, "mTokenIdMap!![CLS]!!");
            ids[0] = ((Number)var10).longValue();
            int maxTextLength = 0;

            int i;
            int var10001;
            for(i = ((Collection)tokenIdsQuestion).size(); maxTextLength < i; ++maxTextLength) {
                var10001 = maxTextLength + 1;
                var10 = tokenIdsQuestion.get(maxTextLength);
                Intrinsics.checkNotNull(var10);
                ids[var10001] = ((Number)var10).longValue();
            }

            var10001 = tokenIdsQuestion.size() + 1;
            var10002 = this.mTokenIdMap;
            Intrinsics.checkNotNull(var10002);
            var10 = var10002.get(this.SEP);
            Intrinsics.checkNotNull(var10);
            Intrinsics.checkNotNullExpressionValue(var10, "mTokenIdMap!![SEP]!!");
            ids[var10001] = ((Number)var10).longValue();
            maxTextLength = Math.min(tokenIdsText.size(), this.MODEL_INPUT_LENGTH - tokenIdsQuestion.size() - this.EXTRA_ID_NUM);
            i = 0;

            for(int var9 = maxTextLength; i < var9; ++i) {
                var10001 = tokenIdsQuestion.size() + i + 2;
                var10 = tokenIdsText.get(i);
                Intrinsics.checkNotNull(var10);
                ids[var10001] = ((Number)var10).longValue();
            }

            var10001 = tokenIdsQuestion.size() + maxTextLength + 2;
            var10002 = this.mTokenIdMap;
            Intrinsics.checkNotNull(var10002);
            var10 = var10002.get(this.SEP);
            Intrinsics.checkNotNull(var10);
            Intrinsics.checkNotNullExpressionValue(var10, "mTokenIdMap!![SEP]!!");
            ids[var10001] = ((Number)var10).longValue();
            return ids;
        }
    }

    private final List wordPieceTokenizer(String questionOrText) {
        List tokenIds = (List)(new ArrayList());
        Pattern p = Pattern.compile("\\w+|\\S");
        Matcher m = p.matcher((CharSequence)questionOrText);

        while(true) {
            label69:
            while(m.find()) {
                String var10000 = m.group();
                Intrinsics.checkNotNullExpressionValue(var10000, "m.group()");
                String var6 = var10000;
                boolean var7 = false;
                if (var6 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }

                var10000 = var6.toLowerCase();
                Intrinsics.checkNotNullExpressionValue(var10000, "(this as java.lang.String).toLowerCase()");
                String token = var10000;
                HashMap var20 = this.mTokenIdMap;
                Intrinsics.checkNotNull(var20);
                HashMap var21;
                if (var20.containsKey(token)) {
                    var21 = this.mTokenIdMap;
                    Intrinsics.checkNotNull(var21);
                    tokenIds.add(var21.get(token));
                } else {
                    int i = 0;

                    for(int var15 = token.length(); i < var15; ++i) {
                        var20 = this.mTokenIdMap;
                        Intrinsics.checkNotNull(var20);
                        byte var9 = 0;
                        int var10 = token.length() - i - 1;
                        boolean var11 = false;
                        if (token == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }

                        String var10001 = token.substring(var9, var10);
                        Intrinsics.checkNotNullExpressionValue(var10001, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        if (var20.containsKey(var10001)) {
                            var21 = this.mTokenIdMap;
                            Intrinsics.checkNotNull(var21);
                            var9 = 0;
                            var10 = token.length() - i - 1;
                            var11 = false;
                            if (token == null) {
                                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                            }

                            String var10002 = token.substring(var9, var10);
                            Intrinsics.checkNotNullExpressionValue(var10002, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                            tokenIds.add(var21.get(var10002));
                            var10 = token.length() - i - 1;
                            var11 = false;
                            if (token == null) {
                                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                            }

                            var10000 = token.substring(var10);
                            Intrinsics.checkNotNullExpressionValue(var10000, "(this as java.lang.String).substring(startIndex)");
                            String subToken = var10000;
                            int j = 0;

                            while(true) {
                                if (j >= subToken.length()) {
                                    continue label69;
                                }

                                var20 = this.mTokenIdMap;
                                Intrinsics.checkNotNull(var20);
                                StringBuilder var22 = (new StringBuilder()).append("##");
                                byte var17 = 0;
                                int var12 = subToken.length() - j;
                                boolean var13 = false;
                                if (subToken == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                                }

                                var10002 = subToken.substring(var17, var12);
                                Intrinsics.checkNotNullExpressionValue(var10002, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                                if (var20.containsKey(var22.append(var10002).toString())) {
                                    var21 = this.mTokenIdMap;
                                    Intrinsics.checkNotNull(var21);
                                    StringBuilder var23 = (new StringBuilder()).append("##");
                                    var17 = 0;
                                    var12 = subToken.length() - j;
                                    var13 = false;
                                    if (subToken == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                                    }

                                    String var10003 = subToken.substring(var17, var12);
                                    Intrinsics.checkNotNullExpressionValue(var10003, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                                    tokenIds.add(var21.get(var23.append(var10003).toString()));
                                    int var18 = subToken.length() - j;
                                    boolean var19 = false;
                                    if (subToken == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                                    }

                                    var10000 = subToken.substring(var18);
                                    Intrinsics.checkNotNullExpressionValue(var10000, "(this as java.lang.String).substring(startIndex)");
                                    subToken = var10000;
                                    j = subToken.length() - j;
                                } else {
                                    if (j == subToken.length() - 1) {
                                        var21 = this.mTokenIdMap;
                                        Intrinsics.checkNotNull(var21);
                                        tokenIds.add(var21.get("##" + subToken));
                                        continue label69;
                                    }

                                    ++j;
                                }
                            }
                        }
                    }
                }
            }

            return tokenIds;
        }
    }

    public void run() {
        EditText var10001 = this.mEditTextQuestion;
        Intrinsics.checkNotNull(var10001);
        String var2 = var10001.getText().toString();
        EditText var10002 = this.mEditTextText;
        Intrinsics.checkNotNull(var10002);
        final String result = this.answer(var2, var10002.getText().toString());
        this.runOnUiThread((Runnable)(new Runnable() {
            public final void run() {
                Button var10000 = MainActivity.this.mButton;
                Intrinsics.checkNotNull(var10000);
                var10000.setEnabled(true);
            }
        }));
        if (result != null) {
            this.runOnUiThread((Runnable)(new Runnable() {
                public final void run() {
                    Object var10000 = MainActivity.this.getSystemService("input_method");
                    if (var10000 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
                    } else {
                        InputMethodManager imm = (InputMethodManager)var10000;
                        View view = MainActivity.this.getCurrentFocus();
                        if (view == null) {
                            view = new View((Context)MainActivity.this);
                        }

                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        EditText var10 = MainActivity.this.mEditTextText;
                        Intrinsics.checkNotNull(var10);
                        String var4 = var10.getText().toString();
                        boolean var5 = false;
                        if (var4 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        } else {
                            String var11 = var4.toLowerCase();
                            Intrinsics.checkNotNullExpressionValue(var11, "(this as java.lang.String).toLowerCase()");
                            int startIdx = StringsKt.indexOf$default((CharSequence)var11, result, 0, false, 6, (Object)null);
                            TextView var12;
                            if (startIdx == -1) {
                                var12 = MainActivity.this.mTextViewAnswer;
                                Intrinsics.checkNotNull(var12);
                                var12.setText((CharSequence)"Beat me!");
                            } else {
                                var12 = MainActivity.this.mTextViewAnswer;
                                Intrinsics.checkNotNull(var12);
                                var12.setText((CharSequence)result);
                                var10 = MainActivity.this.mEditTextText;
                                Intrinsics.checkNotNull(var10);
                                EditText var10001 = MainActivity.this.mEditTextText;
                                Intrinsics.checkNotNull(var10001);
                                var10.setText((CharSequence)var10001.getText().toString());
                                var10 = MainActivity.this.mEditTextText;
                                Intrinsics.checkNotNull(var10);
                                var10.setSelection(startIdx, startIdx + result.length());
                                StyleSpan boldSpan = new StyleSpan(1);
                                var10 = MainActivity.this.mEditTextText;
                                Intrinsics.checkNotNull(var10);
                                int startSel = var10.getSelectionStart();
                                var10 = MainActivity.this.mEditTextText;
                                Intrinsics.checkNotNull(var10);
                                int endSel = var10.getSelectionEnd();
                                int flag = 18;
                                var10 = MainActivity.this.mEditTextText;
                                Intrinsics.checkNotNull(var10);
                                var10.getText().setSpan(boldSpan, startSel, endSel, flag);
                            }
                        }
                    }
                }
            }));
        }
    }

    @Nullable
    public final String assetFilePath(@NotNull Context context, @Nullable String assetName) {
        Intrinsics.checkNotNullParameter(context, "context");
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0L) {
            return file.getAbsolutePath();
        } else {
            AssetManager var10000 = context.getAssets();
            Intrinsics.checkNotNull(assetName);
            Closeable var4 = (Closeable)var10000.open(assetName);
            boolean var5 = false;
            boolean var6 = false;
            Throwable var34 = (Throwable)null;

            String var21;
            try {
                InputStream is = (InputStream)var4;
                int var8 = false;
                Closeable var9 = (Closeable)(new FileOutputStream(file));
                boolean var10 = false;
                boolean var11 = false;
                Throwable var35 = (Throwable)null;

                try {
                    FileOutputStream os = (FileOutputStream)var9;
                    int var13 = false;
                    byte[] buffer = new byte[4096];
                    boolean var15 = false;

                    while(true) {
                        int var16 = is.read(buffer);
                        boolean var17 = false;
                        boolean var18 = false;
                        int var20 = false;
                        if (var16 == -1) {
                            os.flush();
                            Unit var36 = Unit.INSTANCE;
                            break;
                        }

                        os.write(buffer, 0, var16);
                    }
                } catch (Throwable var30) {
                    var35 = var30;
                    throw var30;
                } finally {
                    CloseableKt.closeFinally(var9, var35);
                }

                var21 = file.getAbsolutePath();
            } catch (Throwable var32) {
                var34 = var32;
                throw var32;
            } finally {
                CloseableKt.closeFinally(var4, var34);
            }

            return var21;
        }
    }

    private final String answer(String question, String text) {
        if (this.mModule == null) {
            this.mModule = LiteModuleLoader.load(this.assetFilePath((Context)this, "qa360_quantized.ptl"));
        }

        try {
            long[] tokenIds = this.tokenizer(question, text);
            LongBuffer inTensorBuffer = Tensor.allocateLongBuffer(this.MODEL_INPUT_LENGTH);
            long[] var8 = tokenIds;
            int var9 = tokenIds.length;

            for(int var7 = 0; var7 < var9; ++var7) {
                long n = var8[var7];
                inTensorBuffer.put(n);
            }

            int var19 = 0;

            for(int var6 = this.MODEL_INPUT_LENGTH - tokenIds.length; var19 < var6; ++var19) {
                HashMap var10000 = this.mTokenIdMap;
                Intrinsics.checkNotNull(var10000);
                Long var32 = (Long)var10000.get(this.PAD);
                if (var32 != null) {
                    Long var22 = var32;
                    boolean var24 = false;
                    boolean var26 = false;
                    int var11 = false;
                    Intrinsics.checkNotNullExpressionValue(var22, "it");
                    inTensorBuffer.put(var22);
                }
            }

            Tensor inTensor = Tensor.fromBlob(inTensorBuffer, new long[]{1L, (long)this.MODEL_INPUT_LENGTH});
            Module var34 = this.mModule;
            Intrinsics.checkNotNull(var34);
            Map outTensors = var34.forward(new IValue[]{IValue.from(inTensor)}).toDictStringKey();
            Object var35 = outTensors.get(this.START_LOGITS);
            Intrinsics.checkNotNull(var35);
            Tensor startTensor = ((IValue)var35).toTensor();
            var35 = outTensors.get(this.END_LOGITS);
            Intrinsics.checkNotNull(var35);
            Tensor endTensor = ((IValue)var35).toTensor();
            Intrinsics.checkNotNullExpressionValue(startTensor, "startTensor");
            float[] starts = startTensor.getDataAsFloatArray();
            Intrinsics.checkNotNullExpressionValue(endTensor, "endTensor");
            float[] ends = endTensor.getDataAsFloatArray();
            List answerTokens = (List)(new ArrayList());
            Intrinsics.checkNotNullExpressionValue(starts, "starts");
            int start = this.argmax(starts);
            Intrinsics.checkNotNullExpressionValue(ends, "ends");
            int end = this.argmax(ends);
            int i = start;

            for(int var15 = end + 1; i < var15; ++i) {
                HashMap var10001 = this.mIdTokenMap;
                Intrinsics.checkNotNull(var10001);
                answerTokens.add(var10001.get(tokenIds[i]));
            }

            String var36 = String.join((CharSequence)" ", (Iterable)answerTokens);
            Intrinsics.checkNotNullExpressionValue(var36, "java.lang.String.join(\" \", answerTokens)");
            CharSequence var29 = (CharSequence)var36;
            String var30 = " ##";
            boolean var16 = false;
            Regex var31 = new Regex(var30);
            String var33 = "";
            boolean var17 = false;
            var29 = (CharSequence)var31.replace(var29, var33);
            var30 = "\\s+(?=\\p{Punct})";
            var16 = false;
            var31 = new Regex(var30);
            var33 = "";
            var17 = false;
            return var31.replace(var29, var33);
        } catch (final QAException var18) {
            this.runOnUiThread((Runnable)(new Runnable() {
                public final void run() {
                    TextView var10000 = MainActivity.this.mTextViewAnswer;
                    Intrinsics.checkNotNull(var10000);
                    var10000.setText((CharSequence)var18.getMessage());
                }
            }));
            return null;
        }
    }

    private final int argmax(float[] array) {
        int maxIdx = 0;
        double maxVal = -1.7976931348623157E308D;
        int j = 0;

        for(int var6 = array.length; j < var6; ++j) {
            if ((double)array[j] > maxVal) {
                maxVal = (double)array[j];
                maxIdx = j;
            }
        }

        return maxIdx;
    }

    // $FF: synthetic method
    public static final void access$setMButton$p(MainActivity $this, Button var1) {
        $this.mButton = var1;
    }

    // $FF: synthetic method
    public static final void access$setMEditTextText$p(MainActivity $this, EditText var1) {
        $this.mEditTextText = var1;
    }

    // $FF: synthetic method
    public static final void access$setMTextViewAnswer$p(MainActivity $this, TextView var1) {
        $this.mTextViewAnswer = var1;
    }
}
