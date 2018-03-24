package com.example.administrator.mosac_android.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class ThreadPoolUtils {
    private static ThreadPoolExecutor threadPoolExecutor;

    public static ThreadPoolExecutor getInstance() {
        if(threadPoolExecutor == null) {
            synchronized (ThreadPoolExecutor.class) {
                if(threadPoolExecutor == null) {
                    threadPoolExecutor = new ThreadPoolExecutor(5, 10,
                            200, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
                }
            }
        }
        return threadPoolExecutor;
    }
}
