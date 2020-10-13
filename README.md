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

        通知的使用