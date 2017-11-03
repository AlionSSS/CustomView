#自定义控件

这是一个Android项目

----------

**名称：自定义控件**

**开发环境：Android Studio**

----------

##屏幕截图

![image](https://github.com/AlionSSS/CustomView/blob/master/Screenshot_2016-11-07-20-51-02_com.example.origin.png)
![image](https://github.com/AlionSSS/CustomView/blob/master/Screenshot_2016-11-07-20-51-10_com.example.origin.png)
![image](https://github.com/AlionSSS/CustomView/blob/master/Screenshot_2016-11-07-20-51-18_com.example.origin.png)
![image](https://github.com/AlionSSS/CustomView/blob/master/Screenshot_2016-11-07-20-51-25_com.example.origin.png)
![image](https://github.com/AlionSSS/CustomView/blob/master/Screenshot_2016-11-07-20-51-40_com.example.origin.png)

##build.gradle
	apply plugin: 'com.android.application'
	
	android {
	    compileSdkVersion 23
	    buildToolsVersion '24.0.1'
	
	    defaultConfig {
	        applicationId "com.example.originalview"
	        minSdkVersion 15
	        targetSdkVersion 23
	        versionCode 1
	        versionName "1.0"
	    }
	    buildTypes {
	        release {
	            minifyEnabled false
	            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
	        }
	    }
	}
	
	dependencies {
	    compile fileTree(include: ['*.jar'], dir: 'libs')
	    testCompile 'junit:junit:4.12'
	    compile 'com.android.support:appcompat-v7:23.0.1'
	}
