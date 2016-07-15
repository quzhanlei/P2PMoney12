package cn.itcast.wh.p2pmoney12.common;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/12/16.
 * <p/>
 * getView的实现全部交给BaseHolder类去做
 */
public abstract class BaseHolder<T> {

    private View rootView;
    private T mData;

    public T getData() {
        return mData;
    }

    public BaseHolder() {

        this.rootView = initView();
        this.rootView.setTag(this);
        //绑定!!
        ButterKnife.bind(BaseHolder.this, rootView);
    }

    public View getRootView() {
        return rootView;
    }

    public void setData(T t) {
        this.mData = t;
        //设置完数据后,进行给控件赋值。
        refreshView();
    }

    public abstract View initView();

    protected abstract void refreshView();
}
