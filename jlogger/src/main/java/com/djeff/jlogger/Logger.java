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
     */
    public void detectBlockMethod(long time){
        LogMonitor.getInstance().setMethodBlockTime(time);
        BlockDetectByPrinter.start();
    }
}
