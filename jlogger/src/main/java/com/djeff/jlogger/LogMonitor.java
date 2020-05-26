package com.djeff.jlogger;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;

public final class LogMonitor {
    private static final String TAG = "LogMonitor";
    private static LogMonitor sInstance = new LogMonitor();
    private Handler mIoHandler;
    //方法耗时的卡口,1500毫秒
    private static long TIME_BLOCK = 1500L;
    //调用链方法数
    private static int method_call_length = 20;

    private LogMonitor() {
        HandlerThread logThread = new HandlerThread("log");
        logThread.start();
        mIoHandler = new Handler(logThread.getLooper());
    }

    private static Runnable mLogRunnable = new Runnable() {
        @Override
        public void run() {
            //打印出执行的耗时方法的栈消息
            StringBuilder sb = new StringBuilder();
            sb.append("\n\n Method stack call time use more than ").append(TIME_BLOCK).append("ms \n");
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();

            //当调用栈过长时，截取最后调用的method_call_length个方法
            if(stackTrace.length > method_call_length){
                for(int i=0;i<method_call_length;i++){
                    sb.append(stackTrace[i].toString());
                    sb.append("\n");
                }
                sb.append("......");
            }else {
                for (StackTraceElement s : stackTrace) {
                    sb.append(s.toString());
                    sb.append("\n");
                }
            }

            Log.e(TAG, sb.append("\n\n").toString());
        }
    };

    public static LogMonitor getInstance() {
        return sInstance;
    }


    /**
     * 开始计时
     */
    public void startMonitor() {
        mIoHandler.postDelayed(mLogRunnable, TIME_BLOCK);
    }

    /**
     * 停止计时
     */
    public void removeMonitor() {
        mIoHandler.removeCallbacks(mLogRunnable);
    }

    /**
     * 设置方法调用超时阈值
     * @param time 超时时间
     */
    public void setMethodBlockTime(long time) {
        TIME_BLOCK = time;
    }

    /**
     * 设置打印的方法调用链长度
     * @param methodCallLength 默认为20
     */
    public void setMethodCallLength(int methodCallLength){
        method_call_length = methodCallLength;
    }

    public int getMethodCallLength() {
        return method_call_length;
    }

    public void start() {

        Looper.getMainLooper().setMessageLogging(new Printer() {
            //分发和处理消息开始前的log
            private static final String START = ">>>>> Dispatching";
            //分发和处理消息结束后的log
            private static final String END = "<<<<< Finished";

            @Override
            public void println(String x) {
                if (x.startsWith(START)) {
                    //开始计时
                    LogMonitor.getInstance().startMonitor();
                }
                if (x.startsWith(END)) {
                    //结束计时，并计算出方法执行时间
                    LogMonitor.getInstance().removeMonitor();
                }
            }
        });

    }
}
