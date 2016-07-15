package cn.itcast.wh.p2pmoney12.util;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import cn.itcast.wh.p2pmoney12.common.MyApplication;

/**
 * Created by Administrator on 2015/12/11.
 * UIUtils cn.itcast.wh.p2pmoney12.util.UIUtils
 */
public class UIUtils {

    //获取int类型的颜色。
    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    //获取布局view。
    public static View getXmlView(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    //将int 值传递进来可以获取数组。
    public static String[] getStringArr(int arrId) {
        return getContext().getResources().getStringArray(arrId);
    }

    /**
     * 1dp---1px;
     * 1dp---0.75px;
     * 1dp---0.5px;
     * ....
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    ;

    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    public static Context getContext() {
        return MyApplication.context;
    }

    public static Handler getHandler() {
        return MyApplication.handler;
    }

    /**
     * 保证runnable对象的run方法是运行在主线程当中
     * 在主线程中就直接run方法,在子线程中handler。post方法。
     *
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        if (isInMainThread()) {
            runnable.run();
        } else {
            getHandler().post(runnable);
        }
    }

    private static boolean isInMainThread() {
        //当前线程的id tid threadid
        int tid = android.os.Process.myTid();
        if (tid == MyApplication.mainThreadId) {
            return true;
        }
        return false;
    }


    public static void Toast(String text, boolean isLong) {
        Toast.makeText(getContext(), text, isLong == true ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }
}
