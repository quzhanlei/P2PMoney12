package cn.itcast.wh.p2pmoney12.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import cn.itcast.wh.p2pmoney12.ui.LoadingPage;
import cn.itcast.wh.p2pmoney12.util.UIUtils;

/**
 * Created by Administrator on 2015/12/14.
 */
public abstract class BaseFragment extends Fragment {

    private LoadingPage loadingPage;


    // 就是view。
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


            // 看的时候,可以把这个看成一个具体的实现类。
            loadingPage = new LoadingPage(container.getContext()) {

                // 子类提供 参数,路径, 布局id, 处理成功的操作。
                @Override
                protected RequestParams params() {
                    return getParams();
                }

                @Override
                protected String url() {
                    return getUrl();
                }
                //由具体的fragment告诉我。
                @Override
                public int LayoutId() {
                    return getLayoutId();
                }

                @Override
                protected void OnSuccess(ResultState resultState, View successView) {

                    // 在加载成功的时候,才进行加载。
                    ButterKnife.bind(BaseFragment.this, successView);
                    initTitle();
                    // 将服务器返回的数据放入枚举中。
                    initData(resultState.getContent());
                }


            };
            return loadingPage;
        }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
// 在onViewCreated之后调用!!!  布局加载完成加载数据。
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                show();
            }
        }, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    protected abstract RequestParams getParams();

    protected abstract String getUrl();

    protected abstract void initData(String content);

    protected abstract void initTitle();

    public abstract int getLayoutId();


    // 调用loadingpage的show方法。
    public void show() {
        loadingPage.show();
    }
}
