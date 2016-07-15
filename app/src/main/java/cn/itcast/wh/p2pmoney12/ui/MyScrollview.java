package cn.itcast.wh.p2pmoney12.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2015/12/13.
 */
public class MyScrollview extends ScrollView {

    //要操作的布局
    private View innerView;
    private float y;
    // 矩形。这里只是个形式，只是用于判断是否需要动画
    private Rect normal = new Rect();
    private boolean animationFinish = true;

    public MyScrollview(Context context) {
        super(context, null);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //因为只有一个孩子,所有获取第一个孩子
    @Override
    protected void onFinishInflate() {
        int childCount = getChildCount();
        if (childCount > 0) {
            innerView = getChildAt(0);
        }
    }

    //处理 onTouchEvent事件。
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (innerView == null) {
            return super.onTouchEvent(ev);
        } else {

            commonTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 自定义touch事件处理
     *
     * @param ev
     */
    private void commonTouchEvent(MotionEvent ev) {
        if (animationFinish) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // 返回的是相对于当前view的左上角的坐标。
                    y = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float preY = y == 0 ? ev.getY() : y;
                    float nowY = ev.getY();
                    //手指往上拉。
                    int detailY = (int) (preY - nowY);
                    y = nowY;
                    //操作view进行拖动detailY的一半
                    if (isNeedMove()) {
                        //布局改变位置之前，记录一下正常状态的位置
                        //改变孩子的布局参数。
                        if (normal.isEmpty()) {
//讲内部view的坐标保存在rect中。
                            normal.set(innerView.getLeft(), innerView.getTop(), innerView.getRight(), innerView.getBottom());
                        }
                        //动画。 在MotionEvent.ACTION_MOVE事件中,通过子View.layout()不断更改子布局相对于scrollview的高度。
                        innerView.layout(innerView.getLeft(), innerView.getTop() - detailY / 2, innerView.getRight(), innerView.getBottom() - detailY / 2);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    y = 0;
                    //布局回滚到原来的位置
                    if (isNeedAnimation()) {
                        animation();
                    }
                    break;
            }
        }
    }

    private void animation() {

        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, normal.top - innerView.getTop());
        ta.setDuration(200);
        ta.setAnimationListener(new Animation.AnimationListener() {
            //
            @Override
            public void onAnimationStart(Animation animation) {
                animationFinish = false;
            }
            //
            @Override
            public void onAnimationEnd(Animation animation) {
                innerView.clearAnimation();
                innerView.layout(normal.left, normal.top, normal.right, normal.bottom);
                normal.setEmpty();
                animationFinish = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //给view设置动画。在动画结束后通过子View.layout()重新布局。
        innerView.startAnimation(ta);
    }

    /**
     * 判断是否需要回滚, rect中有数据的需要回滚。
     *
     * @return
     */
    private boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    /**
     * 判断是否需要移动,这里主要是scrollview的特点
     *
     * @return
     */
    private boolean isNeedMove() {
        //getHeight(),scroll的的高度, 屏幕显示的高度
        //getMeasuredHeight() 是测量的子view的高度
        int offset = innerView.getMeasuredHeight() - getHeight();
        //往上移动,getScrollY()为正值。  scrollY移动的是view的内容,
        int scrollY = getScrollY();
        Log.e("zoubo", "getMeasuredHeight:" + innerView.getMeasuredHeight() + "----getHeight:" + getHeight());
        Log.e("zoubo", "offset:" + offset + "----scrollY:" + scrollY);


        //如果子view的布局比较小,因为没有scroll的布局里面的内容少,没法滚动。scrollY == 0,需要移动
        //如果子view的布局比较大, scrollY == 0 顶部|| scrollY == offset底部
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }
}
