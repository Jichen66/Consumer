## mlkit-example


## Table of Contents

 * [Introduction](#introduction)
 * [Preparation](#preparation)
 * [Installation](#installation)
 * [Experience Different Functions](#experience-different-functions)
 * [Supported Environments](#supported-environments)
 * [License](#license)


## Introduction
    The sample code mainly shows the use of Huawei Machine Learning SDK.

	Including face recognition, text recognition(bankcard recognition, ID card recognition, general card recognition), image classification, landmark recognition, object detection and tracking, translation, language detection, product visual search, image segmentation.

	It includes both camera capture video for real-time detection and still image recognition.

    Ability called by the sample:
    1. Face Recognition
		a. MLAnalyzerFactory.getInstance().GetFaceAnalyzer (MLFaceAnalyzerSetting): Create a face recognizer. This is the most core class of face recognition.
		b. MLFaceAnalyzer.setTransactor(): Set the face recognition result processor for subsequent processing of the recognition result.
		c. MLFaceAnalyzerSetting.Factory().SetFeatureType (MLFaceAnalyzerSetting.TYPE_FEATURES): Turn on facial expression and feature detection, including smile, eyes open, beard and age.
		d. MLFaceAnalyzerSetting.Factory().AllowTracing (): Whether to start face tracking mode
		e. LensEngine: camera source that generates continuous image data for detection.
	2. Text Recognition
		a. MLAnalyzerFactory.getInstance().getLocalTextAnalyzer()：Create a device text recognizer.
		b. MLAnalyzerFactory.getInstance().getRemoteTextAnalyzer()：Create a cloud text recognizer.
		c. MLAnalyzerFactory.getInstance().getRemoteDocumentAnalyzer()：Create a cloud document recognizer.
		d. MLTextAnalyzer.asyncAnalyseFrame(frame): Parse text information in pictures.
		e. MLDocumentAnalyzer.asyncAnalyseFrame(frame): Parse document information in pictures.
		f. MLText.getBlocks(): Get text blocks. Generally, a text block represents one line. There is also a case where a text block corresponds to multiple lines.
		g. MLText.Block.getContents(): Get list of text lines(MLText.TextLine).
		h. MLText.TextLine.getContents(): Get the text content of each line(MLText.Word, The device text analyzer returns contains spaced, the cloud text analyzer does not).
		i. MLText.Word.getStringValue(): Gets the word of each line.
		j. MLDocument.getBlocks(): Get document blocks. Generally, a document block represents multiple paragraphs(MLDocument.Block).
        k. MLDocument.getSections(): Get list of document paragraphs(MLDocument.Section).
        l. MLDocument.getLineList(): Get list of document lines(MLDocument.Line).
        m. MLDocument.getWordList(): Get list of document words(MLDocument.Word).
	3. Image Classification
		a. MLAnalyzerFactory.getInstance().getLocalImageClassificationAnalyzer(setting)：Create a device image classifier.
		b. MLAnalyzerFactory.getInstance().getRemoteImageClassificationAnalyzer()：Create a cloud image classifier.
		c. MLImageClassificationAnalyzer.asyncAnalyseFrame(frame): Classify images and generate a MLImageClassification collection, which indicates the category to which the image belongs.
		d. MLImageClassification.getName()：Get the name of the image category, such as pen, phone, computer, etc.
    4. Object Detection And Tracking
		a. MLAnalyzerFactory.getInstance().getLocalObjectAnalyzer(setting)：Creating an object analyzer.
		b. MLObjectAnalyzerSetting.Factory.setAnalyzerType(MLObjectAnalyzerSetting.TYPE_VIDEO): Set the recognition mode.
		c. MLOject.getTypePossibility: Get the category name of the object.
		d. MLOject.getTypeIdentity()：Get the ID number of the object.
		e. LensEngine：camera source that generates continuous image data for detection.
	5. Landmark Detection
	    a. MLAnalyzerFactory.getInstance().getRemoteLandmarkAnalyzer(settings)：Create a landmark analyzer.
		b. MLRemoteLandmarkAnalyzerSetting.Factory.setLargestNumOfReturns()：Set the maximum number of detection results.
		c. MLRemoteLandmarkAnalyzerSetting.Factory.setPatternType()：Set detection mode.
		d. MLRemoteLandmarkAnalyzer.asyncAnalyseFrame(frame): Parse out all landmark information contained in the picture.
	6. Translation
	    a. MLTranslatorFactory.getInstance().getRemoteTranslator(settings)：Create a translator.
		b. MLRemoteTranslateSetting.Factory.setSourceLangId()：Set source language ID.
		c. MLRemoteTranslateSetting.Factory.setTargetLangId()：Set target language ID.
		d. MLRemoteTranslator.asyncTranslate(sourceText): Parse out text from source language to target language, sourceText indicates the language to be detected.
	7. Language Detection
        a. MLLangDetectorFactory.getInstance().getRemoteLangDetector(settings)：Create a language detector.
		b. MLRemoteLangDetectorSetting.Factory.setTrustedThreshold()：Set the minimum confidence threshold for language detection.
		c. MLRemoteLangDetector.firstBestDetect(sourceText):
		d. MLRemoteLangDetector.probabilityDetect(sourceText): Returns the language code with the highest confidence, sourceText represents the language to be detected.
	8. Product Visual Search
		a. MLAnalyzerFactory.getInstance().getRemoteProductVisionSearchAnalyzer(settings)：Create a product visual search analyzer.
    	b. MLRemoteProductVisionSearchAnalyzerSetting.Factory.setLargestNumOfReturns()：Set the maximum number of detection results.
    	c. MLRemoteProductVisionSearchAnalyzer.asyncAnalyseFrame(frame): Parse out all product information contained in the picture.
	9. Image Segmentation
        a. MLAnalyzerFactory.getInstance().getImageSegmentationAnalyzer(settings)：Create a image segment analyzer.
        b. MLImageSegmentationSetting.Factory.setExact()：Set detection mode, true is fine detection mode, false is speed priority detection mode.
        c. MLImageSegmentationAnalyzer.asyncAnalyseFrame(frame): Parse out all target contained in the picture.
        d. LensEngine：camera source that generates continuous image data for detection.
    10. ID card recognition
        a. new MLCnIcrCapture.Callback() { }: Create the recognition result callback function and reload the onSuccess, onCanceled, onFailure, and onDenied functions.
        b. public void onSuccess(MLCnIcrCaptureResult idCardResult){ }: Get notification of recognition results, where you can process the results.
        c. new MLCnIcrCaptureConfig.Factory().setFront(isFront).setRemote(isRemote).create(): Set the recognition parameters for calling the capture API of the recognizer.
        d. MLCnIcrCaptureFactory.getInstance().getIcrCapture(this.config): Create a new detector, pass in the detector configuration.
        e. icrCapture.capture(idCallBack ,this): Call the detection interface to get the ID card information.
        f. MLCardAnalyzerFactory.getInstance().getIcrAnalyzer(): Create a ID card recognition analyzer.
        g. MLIcrAnalyzerSetting.Factory().setSideType(MLIcrAnalyzerSetting.FRONT): Set the front or back side of an ID card.
        h. MLRemoteIcrAnalyzer.asyncAnalyseFrame(frame): Parse out all target on cloud contained in the picture.
        i. MLIcrAnalyzer.asyncAnalyseFrame(frame): Parse out all target on device contained in the picture.
    11. Bankcard recognition
        a. new MLBcrCapture.Callback() { }: Create the recognition result callback function and reload the onSuccess, onCanceled, onFailure, and onDenied functions.
        b. public void onSuccess(MLBcrCaptureResult cardResult){ }: Get notification of recognition results, where you can process the results.
        c. new MLBcrCaptureConfig.Factory().setFront(isFront).setRemote(isRemote).create(): Set the recognition parameters for calling the capture API of the recognizer.
        d. MLBcrCaptureFactory.getInstance().getBcrCapture(this.config): Create a new detector, pass in the detector configuration.
        e. MLBcrCapture.capture(callback ,this): Call the detection interface to get the bank card information.
        f. MLCardAnalyzerFactory.getInstance().getBcrAnalyzer(): Create a bank card recognition analyzer.
        g. MLBcrAnalyzerSetting.Factory().setLangType("cn"): Set the language code of bank card.
        h. MLBcrAnalyzer.asyncAnalyseFrame(frame): Parse out all target contained in the picture.
    12. General card recognition
        a. new MLGcrCapture.Callback() { }: Create the recognition result callback function and reload the onResult, onCanceled, onFailure, and onDenied functions.
        b. public int onResult(MLGcrCaptureResult result, Object object) { }:  Get notification of recognition results, where you can process the results.
        c. MLGcrCaptureConfig.Factory().create(): Set the recognition parameters for calling the capture API of the recognizer.
        d. MLGcrCaptureUIConfig.Factory().setScanBoxCornerColor(Color.BLUE): Set the scan UI color of general card recognition.
        e. MLGcrCaptureUIConfig.Factory().setTipText("Taking picture, align edges"): Set the scan tips of general card recognition.
        f. MLGcrCaptureUIConfig.Factory().setOrientation(MLGcrCaptureUIConfig.ORIENTATION_AUTO): Set the scan screen rotation of general card recognition.
        g. MLGcrCaptureFactory.getInstance().getUcrCapture(cardConfig, uiConfig): Create a general card recognition analyzer.
        h. MLGcrCapture.capturePhoto(this, object, callback): Call the detection interface to get the general card information.

## Preparation
### 1. Register as a developer.
	Register a [HUAWEI account](https://developer.huawei.com/consumer/en/).
### 2. Create an app and apply for a agconnect-services.json.
	Create an app and set Package type to APK (Android app). Apply for an agconnect-services.json file in the developer alliance(https://developer.huawei.com/consumer/en/doc/development/HMS-Guides/ml-preparations4).
### 3. Build
	To build this demo, please first import the demo in the Android Studio (3.x+). Then download the file "agconnect-services.json" of the app on AGC, and add the file to the app root directory(\app) of the demo.

## Installation
    Download the sample code and open in android Studio

## Experience Different Functions
    You can change the main activity in the manifest to experience the different features provided by MLKit

## Supported Environments
	android 4.4 or a later version is recommended.

##  License
    ML Kit example is licensed under the [Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).