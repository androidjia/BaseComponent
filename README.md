# BaseComponent
v1.0    添加room的使用
        增删改查和rxjava2在room中的使用

        常用的异常收集日志(demo中未使用)：
        友盟
        优点：Android和iOS可用，统计功能突出，能捕获应用层Crash；
        缺点：bug收集不及时。
        腾讯Bugly
        优点：Android和iOS可用，bug收集及时，能捕获应用Java Crash、C/C++ Native Crash和ANR并提供相应的bug修复建议，对bug进行了合并统计，目前是免费的；
        缺点：bugly收集的信息较多。
        Bugtags
        优点：一套完善的bug管理系统，Android和iOS可用。
        缺点：免费版功能有限。
        DIY（自己搭建bug收集平台,例如：https://github.com/msdx/android-crash  可以发送邮件和服务地址）
        优点：可以根据自己的业务需求实现，不依赖第三方平台；
        缺点：需要编写错误收集代码，需要搭建错误日志收集服务器

v1.0.1  通知的使用
v1.0.2  服务的使用 (未完成)
        微信支付分享工具类
        拍照和下载工具类
        添加通用基类库baseview

        状态栏工具类添加
        沉浸式状态栏添加
        添加config.gradle版本统一配置
        自定义buidConfigs 和添加多个编译环境





版本适配描述：
        android 6:权限适配
        android 7:fileprovider 文件路径适配
        android 8:
            权限分组适配
            通知渠道
            SharedPreferences闪退 SharedPreferences read = getSharedPreferences(RELEASE_POOL_DATA, MODE_WORLD_READABLE); //MODE_WORLD_READABLE ：8.0以后不能使用这个获取，会闪退，修改成MODE_PRIVATE
            使用动态广播代替静态广播
            全屏透明页面不允许设置方向

        android 9:
            挖孔屏适配
            p 限制了明文网络传输
            在资源文件新建xml目录，新建文件
            <?xml version="1.0" encoding="utf-8"?>
            <network-security-config>
                <base-config cleartextTrafficPermitted="true" />
            </network-security-config>

            清单文件配置：
            <application
                android:networkSecurityConfig="@xml/network_security_config">
                <!--9.0加的，哦哦-->
                <uses-library
                    android:name="org.apache.http.legacy"
                    android:required="false" />
            </application>

        android 10、11:
            但是在android10的时候，Google还是为开发者考虑，留了一手。
            在targetSdkVersion = 29应用中，设置android:requestLegacyExternalStorage=“true”， 就可以不启动分区存储，让以前的文件读取正常使用。
            但是targetSdkVersion = 30中不行了，强制开启分区存储。当然，作为人性化的android，还是为开发者留了一小手，
            如果是覆盖安装呢，可以增加android:preserveLegacyExternalStorage=“true”，暂时关闭分区存储，好让开发者完成数据迁移的工作

            一次性权限:
            <!-- Grants the READ_PHONE_STATE permission only on devices that run Android 10 (API level 29) and lower. -->
                <uses-permission android:name="READ_PHONE_STATE" android:maxSdkVersion="29" />
                <uses-permission android:name="READ_PHONE_NUMBERS" />

            分区存储对于App访问存储方式、App数据存放以及App间数据共享，都产生很大影响。
            而Environment.getExternalStorageDirectory() 在 API Level 29 开始已被弃用，
            开发者应迁移至 Context#getExternalFilesDir(String), MediaStore, 或Intent#ACTION_OPEN_DOCUMENT。