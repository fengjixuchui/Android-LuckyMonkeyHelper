# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Develop\Android\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#permissionsdispatcher
#在自身的aar中，无需添加



# 代码压缩级别
-optimizationpasses 5

# 使用大小写混合
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
# 混淆时预校验
-dontpreverify
# 记录日志
-verbose
-dontshrink

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-assumenosideeffects class android.util.Log {
     public static boolean isLoggable(java.lang.String, int);
     public static int v(...);
     public static int i(...);
     public static int w(...);
     public static int d(...);
     public static int e(...);
}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.app.Fragment

-keep class * implements android.os.Serializable {*;}
-keep class * implements java.io.Serializable {*;}

-keep public class * implements android.os.Parcelable {*;}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers enum * {                   # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#view
-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context);
}

-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepattributes *Annotation* -keepattributes SourceFile,LineNumberTable

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-keep public class * extends java.lang.Exception

# Keep the support library
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-keep class * extends android.support.design.widget.CoordinatorLayout$Behavior {
    *;
}

-keep interface android.support.** { *; }

-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }

-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

-keep class android.support.v8.** { *; }
-keep interface android.support.v8.** { *; }

-keep class android.support.v8.renderscript.** { *; }
-keep interface android.support.v8.renderscript.** { *; }

# Allow obfuscation of android.support.v7.internal.view.menu.**
# to avoid problem on Samsung 4.2.2 devices with appcompat v21
# see https://code.google.com/p/android/issues/detail?id=78377
-keep class !android.support.v7.internal.view.menu.**,android.support.** {*;}

-keep class * extends android.support.v7.internal.view.menu.MenuBuilder
-keep class * implements android.support.v7.internal.view.menu.MenuBuilder
-keep class android.support.v7.internal.view.menu.MenuBuilder

# R
-keep public class com.jeanboy.app.luckymonkeyhelper.R$*{
		public static final int *;
}

#-----------------------------------------------------------------------------------------

#model
-keep class com.newtoncode.app.luckymonkeyhelper.model.** { *; }




#-----------------------------------------------------------------------------------------

#EventBus
#-keepattributes *Annotation*
#-keepclassmembers class ** {
#    @org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#
## Only required if you use AsyncExecutor
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}
#
#
-dontwarn com.crashlytics.android.**
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.** {*;}

-keep class com.fabric.sdk.android.** {*;}
-keep class io.fabric.** {*;}
-keep class com.flurry.** {*;}

# Crashlytics 2.+
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-keepattributes SourceFile, LineNumberTable, *Annotation*

# If you are using custom exceptions, add this line so that custom exception types are skipped during obfuscation:
-keep public class * extends java.lang.Exception

# For Fabric to properly de-obfuscate your crash reports, you need to remove this line from your ProGuard config:
# -printmapping mapping.txt

#-dontwarn org.simpleframework.xml.stream.**
-dontnote org.apache.http.conn.*
-dontnote org.apache.http.params.HttpParams
-dontnote org.apache.http.conn.scheme.*
-dontnote android.net.http.*


#picasso
-dontwarn com.squareup.okhttp.**

#FastJson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }

#greendao
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

-dontwarn rx.**
-keep class rx.** { *; }
-dontwarn net.sqlcipher.database.SQLiteOpenHelper
-keep class net.sqlcipher.database.SQLiteOpenHelper
-dontwarn net.sqlcipher.database.SQLiteDatabase
-keep class net.sqlcipher.database.SQLiteDatabase
-dontwarn net.sqlcipher.database.SQLiteStatement
-keep class net.sqlcipher.database.SQLiteStatement
-dontwarn org.greenrobot.greendao.database.DatabaseOpenHelper$EncryptedHelper
-keep class org.greenrobot.greendao.database.DatabaseOpenHelper$EncryptedHelper
-dontwarn net.sqlcipher.database.SQLiteDatabase$CursorFactory

# volley
-keep class com.android.volley.** {*;}
-keep class com.android.volley.toolbox.** {*;}
-keep class com.android.volley.Response$* { *; }
-keep class com.android.volley.Request$* { *; }
-keep class com.android.volley.RequestQueue$* { *; }
-keep class com.android.volley.toolbox.HurlStack$* { *; }
-keep class com.android.volley.toolbox.ImageLoader$* { *; }

#Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#阿里百川反馈
-keep class com.alibaba.sdk.android.feedback.impl.FeedbackServiceImpl {*;}
-keep class com.alibaba.sdk.android.feedback.impl.FeedbackAPI {*;}
-keep class com.alibaba.sdk.android.feedback.util.IWxCallback {*;}
-keep class com.alibaba.sdk.android.feedback.util.IUnreadCountCallback{*;}
-keep class com.alibaba.sdk.android.feedback.FeedbackService{*;}
-keep public class com.alibaba.mtl.log.model.LogField {public *;}
-keep class com.taobao.securityjni.**{*;}
-keep class com.taobao.wireless.security.**{*;}
-keep class com.ut.secbody.**{*;}
-keep class com.taobao.dp.**{*;}
-keep class com.alibaba.wireless.security.**{*;}
-keep class com.ta.utdid2.device.**{*;}

#友盟在线参数
-keepclassmembers class * {
        public <init>(org.json.JSONObject);
}
-keep class com.umeng.onlineconfig.OnlineConfigAgent {
        public <fields>;
        public <methods>;
}
-keep class com.umeng.onlineconfig.OnlineConfigLog {
        public <fields>;
        public <methods>;
}
-keep interface com.umeng.onlineconfig.UmengOnlineConfigureListener {
        public <methods>;
}

#talkingdata
-dontwarn com.tendcloud.tenddata.**
-keep class com.tendcloud.** {*;}
-keep public class com.tendcloud.tenddata.** { public protected *;}
-keepclassmembers class com.tendcloud.tenddata.**{
public void *(***);
}
-keep class com.talkingdata.sdk.TalkingDataSDK {public *;}
-keep class com.apptalkingdata.** {*;}


