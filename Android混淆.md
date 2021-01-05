




#Android Model混淆配置

* 在build.gradle中release中打开minifyEnabled 并且使用consumerProguardFiles引用混淆文件
	
		release {
	            //混淆
	            minifyEnabled true
	            consumerProguardFiles 'proguard-rules.pro'
	    }

* 在proguard-rules.pro文件中只需要添加要混淆的规则就行。


#Android aar混淆配置
* 在build.gradle中defaultConfig中配置混淆文件, consumerProguardFiles不添加混淆会出问题 

		defaultConfig {
	        ...
	        consumerProguardFiles 'proguard-rules.pro'
	    }
	
* 在release中打开minifyEnabled 为true
	
		release {
	            //混淆
	            minifyEnabled true
	            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
	       
	    }
	
* 在proguard-rules.pro文件中添加混淆规则
* 在proguard-rules.pro文件中添加混淆规则

**aar混淆注意：**
 
* 对外的类和方法不能被混淆，否则会引用错误。
* 混淆后的aar要保持不被混淆，避免二次混淆。


**默认的混淆配置：**
	
	# 代码混淆压缩比，在0~7之间
	-optimizationpasses 5
	# 混合时不使用大小写混合，混合后的类名为小写
	-dontusemixedcaseclassnames
	# 指定不去忽略非公共库的类
	-dontskipnonpubliclibraryclasses
	# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
	-dontpreverify
	-verbose
	
	# 屏蔽警告
	-ignorewarnings
	
	#google推荐算法
	-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
	# 避免混淆Annotation、内部类、泛型、匿名类
	-keepattributes *Annotation*,Signature,EnclosingMethod
	-keepattributes InnerClasses
	# 重命名抛出异常时的文件名称
	-renamesourcefileattribute SourceFile
	# 抛出异常时保留代码行号
	-keepattributes SourceFile,LineNumberTable
	# 处理support包
	#-keep class android.support.** {*;}## 保留support下的所有类及其内部类
	-dontnote android.support.**
	-dontwarn android.support.**
	# 保留继承的
	-keep public class * extends android.support.v4.**
	-keep public class * extends android.support.v7.**
	-keep public class * extends android.support.annotation.**
	
	# 保留R下面的资源
	-keep class **.R$* {*;}
	
	#不混淆资源类下static的
	-keepclassmembers class **.R$* {
	public static <fields>;
	}
	
	# 保留四大组件，自定义的Application等这些类不被混淆
	-keep public class * extends android.app.Activity
	-keep public class * extends android.app.Appliction
	-keep public class * extends android.app.Service
	-keep public class * extends android.content.BroadcastReceiver
	-keep public class * extends android.content.ContentProvider
	-keep public class * extends android.preference.Preference
	-keep public class * extends android.app.backup.BackupAgentHelper
	-keep public class * extends android.view.View
	-keep public class com.google.vending.licensing.ILicensingService
	-keep public class com.android.vending.licensing.ILicensingService
	
	#这个主要是在layout 中写的onclick方法android:onclick="onClick"，不进行混淆
	-keepclassmembers class * extends android.app.Activity {
	public void *(android.view.View);
	}
	
	-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
	public <init>(android.content.Context, android.util.AttributeSet);
	public <init>(android.content.Context, android.util.AttributeSet, int);
	}
	
	# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
	-keepclassmembers class * {
	void *(**On*Event);
	void *(**On*Listener);
	}
	# 保留本地native方法不被混淆
	-keepclasseswithmembernames class * {
	native <methods>;
	}
	
	# 保留枚举类不被混淆
	-keepclassmembers enum * {
	public static **[] values();
	public static ** valueOf(java.lang.String);
	}
	
	
	#表示不混淆任何一个View中的setXxx()和getXxx()方法，
	#因为属性动画需要有相应的setter和getter的方法实现，混淆了就无法工作了。
	-keep public class * extends android.view.View{
	*** get*();
	void set*(***);
	public <init>(android.content.Context);
	public <init>(android.content.Context, android.util.AttributeSet);
	public <init>(android.content.Context, android.util.AttributeSet, int);
	public <init>(android.content.Context, android.util.AttributeSet, int, int);
	}
	
	
	# 保留Parcelable序列化类不被混淆
	-keep class * implements android.os.Parcelable {
	public static final android.os.Parcelable$Creator *;
	}
	
	-keepclassmembers class * implements java.io.Serializable {
	static final long serialVersionUID;
	private static final java.io.ObjectStreamField[]   serialPersistentFields;
	private void writeObject(java.io.ObjectOutputStream);
	private void readObject(java.io.ObjectInputStream);
	java.lang.Object writeReplace();
	java.lang.Object readResolve();
	}
	
	
	#保留Keep注解的类名和方法
	-keep,allowobfuscation @interface android.support.annotation.Keep
	-keep @android.support.annotation.Keep class *
	-keepclassmembers class * {
	@android.support.annotation.Keep *;
	}
	
	# 可选
	#assume no side effects:删除android.util.Log输出的日志
	#-assumenosideeffects class android.util.Log {
	#    public static *** v(...);
	#    public static *** d(...);
	#    public static *** i(...);
	#    public static *** w(...);
	#    public static *** e(...);
	#}
	
	#
	#----------------------------- WebView(项目中没有可以忽略) -----------------------------
	#
	#webView需要进行特殊处理
	#-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
	#   public *;
	#}
	#-keepclassmembers class * extends android.webkit.WebViewClient {
	#    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
	#    public boolean *(android.webkit.WebView, java.lang.String);
	#}
	#-keepclassmembers class * extends android.webkit.WebViewClient {
	#    public void *(android.webkit.WebView, jav.lang.String);
	#}
	##在app中与HTML5的JavaScript的交互进行特殊处理
	##我们需要确保这些js要调用的原生方法不能够被混淆，于是我们需要做如下处理：
	#-keepclassmembers class com.ljd.example.JSInterface {
	#    <methods>;
	#}
	
	
	
	
