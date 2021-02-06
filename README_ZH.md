# 华为搜索服务示例代码

中文 | [English](https://github.com/HMS-Core/hms-search-demo/blob/main/README.md)

## 目录

 * [简介](#简介)
 * [安装](#安装)
 * [环境要求](#环境要求)
 * [配置](#配置)
 * [示例代码](#示例代码)
 * [授权许可](#授权许可)


## 简介
本实例代码封装华为搜索服务(HUAWEI Search Kit)安卓接口，提供样例代码供您参考。

## 安装
下载并解压缩示例代码包。

刷新项目，确保项目同步成功。

## 环境要求
建议使用Search Kit 5.0.4.305及以上版本。
Android SDK运行要求Android API为24(Android 7.0)及以上版本。
Android studio.

## 配置
1. 在华为开发者联盟注册成为开发者并登陆。
2. 创建一个项目，在项目下添加应用，并设置应用包名。
3. 在"项目设置 -> API管理"中，找到Search Kit，打开API开关。
4. 在"项目设置 -> 常规"中，点击"项目"中"数据存储位置"后的"设置",在弹出的对话框中选择一个数据存储位置。
5. 下载"agconnect-services.json"文件并将其拷贝到应用级根目录下。
6. 在项目级build.gradle文件中配置Maven仓地址: maven {url 'https://developer.huawei.com/repo/'} 和插件地址: 'com.huawei.agconnect:agcp:1.3.1.300'。
7. 在应用级build.gradle文件到最后一行添加: 'com.huawei.agconnect'。
8. 在应用级build.gradle文件中添加依赖 com.huawei.hms:searchkit:5.0.4.305。
9. 同步项目。

## 示例代码

1. 网页搜索示例代码: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)

2. 图片搜索示例代码: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)

3. 视频搜索示例代码: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)

4. 新闻搜索示例代码: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)

5. Custom search示例代码: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)

6. 文本补齐示例代码: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)

7. 拼写检查示例代码: [searchindex/SearchActivity.java](https://github.com/HMS-Core/hms-search-demo/blob/main/SearchKit_android_SampleCode/app/src/main/java/com/huawei/searchindex/activity/SearchActivity.java)


## 运行结果
**网页搜索**

<img src="https://github.com/HMS-Core/hms-search-demo/blob/main/image/web-search.gif" width=280>

**图片搜索**

<img src="https://github.com/HMS-Core/hms-search-demo/blob/main/image/image-search.gif" width=280>

**视频搜索**

<img src="https://github.com/HMS-Core/hms-search-demo/blob/main/image/video-search.gif" width=280>

**新闻搜索**

<img src="https://github.com/HMS-Core/hms-search-demo/blob/main/image/news-search.gif" width=280>

##  授权许可
华为搜索服务示例代码经过[Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0)授权许可.
