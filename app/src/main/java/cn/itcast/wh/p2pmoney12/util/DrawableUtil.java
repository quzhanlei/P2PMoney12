package cn.itcast.wh.p2pmoney12.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by Administrator on 2015/12/19.
 */
public class DrawableUtil {

    /**
     * 定义一个shape资源
     *
     * @param rgb
     * @param corneradius
     * @return
     */
    public static GradientDrawable getDrawable(int rgb, int corneradius) {
        // 圆角矩形,随机颜色
        GradientDrawable gradientDrawable = new GradientDrawable();
        //颜色
        gradientDrawable.setColor(rgb);
        //矩形
        gradientDrawable.setGradientType(GradientDrawable.RECTANGLE);
        // 圆角半径
        gradientDrawable.setCornerRadius(corneradius);
        //边框的颜色大小
        gradientDrawable.setStroke(UIUtils.dp2px(1), rgb);


        return gradientDrawable;
    }

    // selector
    public static StateListDrawable getSelector(Drawable normalDrawable,Drawable pressDrawable) {

        StateListDrawable stateListDrawable = new StateListDrawable();
        //给当前的颜色选择器添加选中图片指向状态，未选中图片指向状态
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        //设置默认状态
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }

}
