package com.djeff.jlogger;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

public final class LogMonitor {
    private static final String TAG = "LogMonitor";
    private static LogMonitor sInstance = new LogMonitor();
    private Handler mIoHandler;
    //方法耗时的卡口,1500毫秒
    private static long TIME_BLOCK = 1500L;

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
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString());
                sb.append("\n");
            }
            Log.e(TAG, sb.toString());
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
}
