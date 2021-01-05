# Android性能优化

### 一. Android卡顿优化

* 不要在主线程进行网络访问/大文件的IO操作
* 绘制UI时，尽量减少绘制UI层次；减少不必要的view嵌套，可以用Hierarchy Viewer工具来检测
* 当我们的布局是用的FrameLayout的时候，我们可以把它改成merge,可以避免自己的帧布局和系统的ContentFrameLayout帧布局重叠造成重复计算(measure和layout)
* 提高显示速度,使用ViewStub：当加载的时候才会占用。不加载的时候就是隐藏的，仅仅占用位置。
* 删除控件中无用的属性;
* 布局复用.比如listView 布局复用
* 尽量避免过度绘制（overdraw）,比如：背景经常容易造成过度绘制。由于我们布局设置了背景，同时用到的MaterialDesign的主题会默认给一个背景。这时应该把主题添加的背景去掉；还有移除 XML 中非必须的背景
* 自定义View优化。使用 canvas.clipRect()来帮助系统识别那些可见的区域，只有在这个区域内才会被绘制。也是避免过度绘制．
* 启动优化,启动速度的监控，发现影响启动速度的问题所在，优化启动逻辑，提高应用的启动速度。比如闪屏页面，合理优化布局，加载逻辑优化，数据准备，这里后面我会单独写一篇文章讲如何优化程序的启动速度及Splash页面设计，这里还会讲到热启动和冷启动．
* 合理的刷新机制，尽量减少刷新次数，尽量避免后台有高的 CPU 线程运行，缩小刷新区域。
* 使用注解代替枚举
* SparseArray替换HashMap

### 二. App包优化

1. 资源优化

   1. 只用一套图片，使用高分辨率的图片。

   2. UI设计在ps安装TinyPNG([https://tinypng.com](https://links.jianshu.com/go?to=https%3A%2F%2Ftinypng.com))插件，对图片进行压缩。

      （已测试，图片大小差不多缩小了3倍左右）

   3. png换成jpg

      经验发现，一些背景，启动页，宣传页的PNG图片比较大，这些图片图形比较复杂，如果转用有损JPG，能只有不到一半（当然是有损，不过通过设置压缩参数可以这种损失比较小到忽略）。因为都是大图，所以这种方式能有效减小apk的大小。

   4. jpg换成webp

      如果png大图转成jpg还是很大，或者想压的更小，而尽量不降低画质，那么可以考虑一下webp。（

      android4.0+ 才支持webp,4.0以下的将无法显示图片，如果想要做兼容处理，要引入webp相关的so文件增大apk大小）。

      图片使用WebP([https://developers.google.com/speed/webp/](https://links.jianshu.com/go?to=https%3A%2F%2Fdevelopers.google.com%2Fspeed%2Fwebp%2F))的格式（Facebook、腾讯、淘宝在用。）缺点：加载相比于PNG要慢很多。 但是配置比较高。工具：[http://isparta.github.io/](https://links.jianshu.com/go?to=http%3A%2F%2Fisparta.github.io%2F)

   5. svg图片：一些图片的描述，牺牲CPU的计算能力的，节省空间。使用的原则：简单的图标。

   6. 使用tintcolor(android - Change drawable color programmatically)实现按钮反选效果。

2. 代码优化

   1. 实现功能模块的逻辑简化

   2. Lint工具检查无用文件将无用的资源列在“UnusedResources: Unused resources”，删除。

      > Analyze—>Run Inspection by Name…—>输入Unuserd resources

   3. 移除无用的依赖库和重复库。

   4. 打包优化

      > 在release中添加一些命令可以优化apk
      >
      > zipAlignEnabled true 资源对齐
      >
      > shrinkResourecs true 去除无用资源，as 3.0.1 之后必须先开启混淆才能使用
   >
      > resConfigs 选择需要的语言，去掉其它不需要的语言
      >
      > 
   
      ```java
      android{
        defaultConfig{
          resConfigs 'zh-rCN'     //本次打包，只把中文简体打进包内，其他语言忽略
        }
        buildTypes{
          release {
                  zipAlignEnabled true  //资源对齐，减小zip体积，增加运行效率
                  minifyEnabled true   //是否混淆
                  shrinkResources true //去除无效资源文件 as 3.0.1 之后必须先开启混淆才能使用
                  proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
              }
        }
      }
      
      
      ```

   

3. lib资源优化

   1. 动态下载的资源。
   2. 一些模块的插件化动态添加。
   3. so文件的剪裁和压缩。

   ​		mips、armeabi、armeabi-v7a和x86到底是什么

   ​    	**mips：**MIPS是世界上很流行的一种RISC处理器。MIPS的意思是“无内部互锁流水级的微处理器”(Microprocessor without interlockedpiped stages)，其机制是尽量利用软件办法避免流水线中的数据相关问题。

   ​    	**armeabi：**默认选项，将创建以基于ARM* v5TE 的设备为目标的库。 具有这种目标的浮点运算使用软件浮点运算。 使用此 ABI （二进制接口）创建的二进制代码将可以在所有 ARM* 设备上运行。所以armeabi通用性很强。但是速度慢

   ​		**armeabi-v7a：**创建支持基于ARM* v7 的设备的库，并将使用硬件 FPU 指令。armeabi-v7a是针对有浮点运算或高级扩展功能的arm v7 cpu。

   ​		**x86:**支持基于硬件的浮点运算的IA-32 指令集。x86是可以兼容armeabi平台运行的，无论是armeabi-v7a还是armeabi，同时带来的也是性能上的损耗，另外需要指出的是，打包出的x86的so，总会比armeabi平台的体积更小。

   ​		如果项目只包含了 armeabi，那么在所有Android设备都可以运行；
    	   如果项目只包含了 armeabi-v7a，除armeabi架构的设备外都可以运行；

   ​		如果项目只包含了 x86，那么armeabi架构和armeabi-v7a的Android设备是无法运行的； 如果同时包含了 armeabi， armeabi-v7a和x86，

   ​		所有设备都可以运行，程序在运行的时候去加载不同平台对应的so，这是较为完美的一种解决方案，同时也会导致包变大。所以一般我们只需要适配armeabi就可以了。

   

4. assets资源优化

   1. 音频文件最好使用有损压缩的格式，比如采用opus、mp3等格式，但是最好不要使用无损压缩的音乐格式
   2. 对ttf字体文件压缩，可以采用FontCreator工具只提取出你需要的文字。比如在做日期显示时，其实只需要数字字体，但是使用原有的字体库可能需要10MB大小，如果只是把你需要的字体提取出来生成的字体文件只有10KB

5. 代码混淆

​        

##启动白屏和启动时间优化

### 一 白屏优化：

1. 方法一：
	
	针对出现白屏,黑屏的现象可以直接干掉。
	> 定义一个style:
	
	     <style name="AppTheme.Launcher">
	        <!--关闭启动窗口-->
	        <item name="android:windowDisablePreview">true</item>
        </style>
  
    > 在启动页面引用style:
  
        <activity android:name=".MainActivity"
            android:theme="@style/AppTheme.Launcher">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
  
    > 在Activity中恢复正常主题:
  
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);//恢复正常主题
        setContentView(R.layout.activity_main);
    }
  
    这样启动app没有白屏,但是会出现点击桌面图标而半天没有反应的现象.


2. 将启动页设置成透明主题
  
    > 设置style并在启动页引用：
    
        <style name="AppStartTheme" parent="Theme.AppCompat.Light.NoActionBar">
	        <item name="android:windowIsTranslucent">true</item>
	        <item name="android:windowFullscreen">true</item>
	        <item name="android:windowBackground">@android:color/transparent</item>
        </style>
	



###二 启动时间优化

使用异步初始化去分担初始化工作

​	1. 现在application初始化内容有：阿里云推送初始化，im初始化……等等。将部分逻辑放到Service或者异步线程中处理，可以缩短很多时间。开启线程，将部分逻辑和耗时的初始化操作放到这里处理，可以减少application初始化时间

​	2.使用透明主题，将application或者MainActivity的activity Theme设置成为透明的，速度加快10ms左右。

	 <style name="AppThemes" parent="Theme.AppCompat.Light.DarkActionBar">
	    <item name="android:windowFullscreen">true</item>
	    <item name="android:windowIsTranslucent">true</item>
	</style>
​	3.MainActivity的onCreate方法优化：

​		尽量减少onCreate方法中的初始化操作。