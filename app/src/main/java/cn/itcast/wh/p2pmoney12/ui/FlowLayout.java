package cn.itcast.wh.p2pmoney12.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.layout_height;

/**
 * Created by Administrator on 2015/12/19.
 */
public class FlowLayout extends ViewGroup {


    //每一行所包含的所有子view
    private List<List<View>> allViews = new ArrayList<>();

    private List<Integer> allHeights = new ArrayList<>();


    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 求取布局的宽高
     *
     * @param widthMeasureSpec  ----宽度的测量规格
     * @param heightMeasureSpec ---高度的测量规格
     */


    // android:layout_width="match_parent"
    //android:layout_height="wrap_content"
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取测量模式和测量值

        //父布局的宽度模式 精确模式 整个屏幕的
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //父布局的宽度值
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //父布局的高度模式 最大模式 子view相加
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //父布局的高度值
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        // 初始的宽高为0.
        int width = 0;
        int height = 0;
        //行宽和行高
        int lineWidth = 0;
        int lineHeight = 0;

        //求取at_most模式下的布局宽、高值  当控件的宽高是至多模式的时候,就需要计算每一个控件的宽和高,来计算布局的宽和高。
        int cCount = getChildCount();



        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            //调用系统的方法。先测量,才会有宽高值!!
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //基于测量模式!
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            //
            MarginLayoutParams mp = ((MarginLayoutParams) child.getLayoutParams());



            // 如果我当前宽度+控件的宽+左右margin   大于 widthSize,就 换行
            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin > widthSize) {
                //换行:
                // 宽度--对比获取 宽度绝对为 lineWidth。
                width = Math.max(width, lineWidth);
                // 高度为行的高度
                height += lineHeight;


                //重置一下 换行后的宽为子view的宽。换行后的高为子view的高。
                lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
            } else {
                //不换行
                // :行高--对比获得
                // 获取行的宽度  在循环里面!
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                //获取行的高度
                lineHeight = Math.max(lineHeight, childHeight + mp.topMargin + mp.bottomMargin);
            }





            //最后一次循环 ,如果没有循环,需要计算布局的宽和高,,
            // 如果换行的话 ,也得加,因为宽跟高都是上一次的值!!!         还要再参与计算一次 0  1          2 =3-1
            if (i == cCount - 1) {
                //每次循环的末尾 都算下布局 的 宽和高。  宽为那行最宽。
                width = Math.max(width, lineWidth);
                //高度为 叠加。
                height += lineHeight;
            }
        }



        Log.e("zoubo", "width:" + width + "----heigth:" + height);
//设定布局的宽高
        // 如果widthMode == MeasureSpec.EXACTLY 就采用系统的widthSize,如果不是的话就是width。
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }





    //摆放控件。
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int width = getWidth();
        int cCount = getChildCount();

        //初始值 行宽和行高都为0.
        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<>();

        //单个for循环。。开始将每个控件放置!
        for (int i = 0; i < cCount; i++) {
            // 首先获取第一个view
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //从MarginLayoutParams 获取左右上下的margin参数。
            MarginLayoutParams mp = ((MarginLayoutParams) child.getLayoutParams());

            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin > width) {
                //换行  allViews中放置的是一个一个集合,每个集合都是每行的子view集合。
                //allHeights 放置的是每个行高。基本都一样。
                allViews.add(lineViews);
                allHeights.add(lineHeight);
                //重置一下变量
                lineViews = new ArrayList<>();
                lineWidth = 0;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
            }

            //不换行 默认将子控件放入集合lineViews集合中。
            lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + mp.topMargin + mp.bottomMargin);
            lineViews.add(child);

            if (i == cCount - 1) {
                allViews.add(lineViews);
                allHeights.add(lineHeight);
            }
        }
        //通过计算每一行的每一个子view的left,top,right,bottom,摆放每一行的每一个子view的位置
        int left = 0;
        int top = 0;
        // 如果有4行的话。

//        嵌套for循环
        for (int i = 0; i < allViews.size(); i++) {
            //当前行的高。
            int curLineHeight = allHeights.get(i);
            //当前行的所有子view
            List<View> views = allViews.get(i);

            //对于每行子控件! 左不一样 ,
            for (View view : views) {
                int viewWidth = view.getMeasuredWidth();
                int viewHeight = view.getMeasuredHeight();
                MarginLayoutParams mp = ((MarginLayoutParams) view.getLayoutParams());
//                left current     左上右下
                int lc = left + mp.leftMargin;
                int tc = top + mp.topMargin;
                //
                int rc = lc + viewWidth;
                int bc = tc + viewHeight;
                //
                view.layout(lc, tc, rc, bc);
                left += viewWidth + mp.rightMargin + mp.leftMargin;
            }

            left = 0;
            // top变大了!
            top += curLineHeight;
        }
    }
// 返回总的布局的LayoutParams。
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //重新写这个方法,来生成布局的参数。              子view的LayoutParams跟父LayoutParams一致。
//不用系统默认viewgrpup的LayoutParams,改成带有margin的
        MarginLayoutParams mp = new MarginLayoutParams(getContext(), attrs);
        return mp;
    }
}
