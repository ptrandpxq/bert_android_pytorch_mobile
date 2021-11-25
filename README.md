# bert_android

This repository is for the development of make the BERT model inference on android devices.

## Prerequisites

* PyTorch 1.9.0 and torchvision 0.10.0 (Optional)
* Python 3.8 or above (Optional)
* Android Pytorch library pytorch_android_lite:1.9.0, pytorch_android_torchvision:1.9.0
* Android Studio 4.0.1 or later

## Quick Start

To Test Run the BERT Android App, follow the steps below:

### 1. Prepare the Model

You can train your own BERT model (the BERT-base model) or download a BERT base model file to the `/app/src/main/assets` folder using the link [here](Coming soon).

Recommend to download the model from huggingface.


### 2. Use Android Studio

Open the BERT android project using Android Studio. Note the app's `build.gradle` file has the following lines:

```
implementation 'org.pytorch:pytorch_android_lite:1.9.0'
implementation 'org.pytorch:pytorch_android_torchvision:1.9.0'
```

and in the MainActivity.java, the code below is used to load the model:

```
mModule = LiteModuleLoader.load(MainActivity.assetFilePath(getApplicationContext(), "BERT.ptl"));
```

### 3. Run the app
Select an Android emulator or device and build and run the app. The demo screenshot is as follows:

<img swidth="440" height="300" src="./imgs/screenshot.jpg"/>

Input some text (e.g., I like reading.) and touch the button Start to get the result (binary classification task).

Mode 2 (for advanced users)

The code can make inference on the SST-2 dev set (binary classification task). Please check the commnet part of the code and fit your requirement. There is no feedback until the inference is done, you can check the run console to make sure everything is fine.

## Tutorial

Read the tutorial [here](https://pytorch.org/mobile/android/) to get the basics of pytorch android.

For more information on using Mobile Interpreter in Android, see the tutorial [here](https://pytorch.org/tutorials/recipes/mobile_interpreter.html).
