package cn.itcast.wh.p2pmoney12.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by Administrator on 2015/12/11.
 */
public class MyApplication extends Application {

    //运行在主线程中。。
    // 静态方法。 任何地方需要就可以引用。
    public static Context context = null;

    public static Handler handler = null;

    public static Thread mainThread = null;

    public static int mainThreadId = 0;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        handler = new Handler();
        //Application 的线程是主线程。。。。
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();


//        CrashHandler.getInstance().init(this);
    }
}
