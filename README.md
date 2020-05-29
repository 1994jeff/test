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

//在应用启动Application类中调用如下方法
Logger.getInstance().detectBlockMethod(1600,25);
