# HMS Search Kit Demo

## Table of Contents

 * [Introduction](#introduction)
 * [Installation](#installation)
 * [Supported Environments](#supported-environments)
 * [Configuration ](#configuration)
 * [Sample Code](#sample-code)
 * [License](#license)
 
 
## Introduction
HMS Search Kit sample code encapsulates APIs of the HUAWEI Search Kit. It provides a sample programs for your reference or usage.

## Installation
Before using HMS Search Kit sample code, check whether the BD_SearchKitCodeLab environment has been installed.
Decompress the HMS Search Kit sample code package.
    
Refresh the project and ensure that is successfully.
    
## Supported Environments
searchkit 5.0.4.305 or a later version is recommended.
The Android SDK is available only on a mobile device running Android API-Level 24 (Android 7.0 Marshmallow) or higher.
Android studio.
	
## Configuration	
1. Register and sign in to HUAWEI Developers.
2. Create a project and then create an app in the project, enter the project package name.
3. Go to Project Settings > Manage APIs, find the Search Kit API, and enable it.
4. Go to Project Settings > General information, click Set next to Data storage location under Project information, and select a data storage location in the displayed dialog box.
5. Download the agconnect-services.json file and place it to the app's root directory of the project.
6. Add the Maven repository address maven {url 'https://developer.huawei.com/repo/'} and plug-in class path 'com.huawei.agconnect:agcp:1.3.1.300' to the project-level build.gradle file.
7. Add apply plugin: 'com.huawei.agconnect' to the last line of the app-level build.gradle file.
8. Configure the dependency com.huawei.hms:searchkit:5.0.4.305 in the app-level buildle.gradle file.
9. Synchronize the project.
	
## Sample Code

1. Web search.
Code location: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)
    
2. Image search.
Code location: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)
    
3. Video search.
Code location: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)
    
4. News search.
Code location: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)
    
5. Custom search.
Code location: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)
	
6. Suggest.
Code location: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)
	
7. Spell check.
Code location: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)


## Result
**web search**

<img src="https://github.com/HMS-Core/hms-search-demo/blob/main/image/web-search.gif" width=280>

**image search**

<img src="https://github.com/HMS-Core/hms-search-demo/blob/main/image/image-search.gif" width=280>

**video search**

<img src="https://github.com/HMS-Core/hms-search-demo/blob/main/image/video-search.gif" width=280>

**news search**

<img src="https://github.com/HMS-Core/hms-search-demo/blob/main/image/news-search.gif" width=280>

##  License
HMS Search Kit sample is licensed under the [Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
