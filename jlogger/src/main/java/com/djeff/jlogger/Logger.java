package com.djeff.jlogger;

public class Logger {

    public static Logger getInstance(){
        return LoggerCreater.logger;
    }

    private Logger(){
    }

    private static class LoggerCreater{
        public static Logger logger = new Logger();;
    }

    /**
     * 检测调用超时time的方法
     * 并且会打印出当时调用的方法栈
     * @param time 默认1500ms
     * @param methodCallLength 显示的方法调用数
     */
    public void detectBlockMethod(long time,int methodCallLength){
        //方法超时的阈值必须大于300ms才允许
        if(time > 300){
            LogMonitor.getInstance().setMethodBlockTime(time);
        }
        //默认的方法调用链展示数，默认20， 最低20
        if(methodCallLength > LogMonitor.getInstance().getMethodCallLength()){
            LogMonitor.getInstance().setMethodCallLength(methodCallLength);
        }

        //开启检测
        BlockDetectByPrinter.start();
    }

}
