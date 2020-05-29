# Test
Logger,方便的打印日志，并能检测主线程耗时方法的工具

Step 1. Add the JitPack repository to your build file

allprojects {

		repositories {
    
			...
			maven { url 'https://jitpack.io' }
      
		}
    
}


Step 2. Add the dependency

dependencies {

		implementation 'com.github.User:Repo:Tag'
    
}

Step 3.Use Logger


//在应用启动Application类中调用

Logger.getInstance().detectBlockMethod(1600,25);


当有主线程有耗时调用，则会展示如下Log输出

2020-05-29 08:41:55.432 17522-17606/com.djeff.test E/LogMonitor:  Method stack call time use more than 1600ms 
    java.lang.Thread.sleep(Native Method)
    java.lang.Thread.sleep(Thread.java:386)
    java.lang.Thread.sleep(Thread.java:327)
    com.djeff.test.MainActivity.doSleep(MainActivity.java:38)
    com.djeff.test.MainActivity.onResume(MainActivity.java:26)
    android.app.Instrumentation.callActivityOnResume(Instrumentation.java:1426)
    android.app.Activity.performResume(Activity.java:7663)
    android.app.ActivityThread.performResumeActivity(ActivityThread.java:4427)
    android.app.ActivityThread.handleResumeActivity(ActivityThread.java:4485)
    android.app.servertransaction.ResumeActivityItem.execute(ResumeActivityItem.java:51)
    android.app.servertransaction.TransactionExecutor.executeLifecycleState(TransactionExecutor.java:154)
    android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:76)
    android.app.ActivityThread$H.handleMessage(ActivityThread.java:2209)
    android.os.Handler.dispatchMessage(Handler.java:112)
    android.os.Looper.loop(Looper.java:216)
    android.app.ActivityThread.main(ActivityThread.java:7586)
    java.lang.reflect.Method.invoke(Native Method)
    com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:524)
    com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1034)
    
可以很方便的定位主线程耗时问题，或者利用这个拿来做App优化，包括启动时间优化，跳转的耗时优化等
