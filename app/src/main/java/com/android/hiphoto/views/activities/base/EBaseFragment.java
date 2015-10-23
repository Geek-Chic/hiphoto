/*
 * 文件名: EBaseFragment
 * 描    述: Fragment基类
 * 创建人: jp
 * 创建时间: 25/8/15
 */
package com.android.hiphoto.views.activities.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.hiphoto.AvengersApplication;
import com.android.hiphoto.views.utils.log.L;

import butterknife.ButterKnife;

/**
 * Fragment基类<BR>
 *
 * @author jp
 * @version [V20150825, 25/8/15]
 */
public abstract class EBaseFragment extends Fragment implements Handler.Callback {

    protected Context context;

    private BackHandledInterface mBackHandledInterface;

    private boolean isActivityCreated = false; // 页面控件是否已初始化
    private boolean isFirstVisible = false; // 是否第一次可见

    protected EBaseActivity mContext;
    protected AvengersApplication mApplication;
    protected Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutId() > 0) {
            View rootView = inflater.inflate(getLayoutId(), container, false);
            return rootView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);
        initPageView(view);
        initPageViewListener();
    }

    /**
     * 获得子Activity的布局ID
     *
     * @return 子Activity的布局ID
     */
    public abstract int getLayoutId();

    private void initPageViewListener() {

    }

    private void initPageView(View view) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (!(getActivity() instanceof BackHandledInterface)) {
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        } else {
            this.mBackHandledInterface = (BackHandledInterface) getActivity();
        }
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        init();
    }

    protected void init() {
        mContext = (EBaseActivity) getActivity();
        mApplication = AvengersApplication.getInstance();
        mHandler = new Handler(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isActivityCreated = true;
        if (getUserVisibleHint()) {
            if (!isFirstVisible) {
                isFirstVisible = true;
                onPageFirstVisible();
            }
        }
        process(savedInstanceState);
    }

    /**
     * 当页面首次可见时调用。调用时页面控件已经完成初始化
     * 用于ViewPager下的页面懒加载，在一个生命周期内只会调用一次
     */
    public void onPageFirstVisible() {
        L.v("onPageFirstVisible");
    }

    /**
     * 逻辑处理
     */
    protected void process(Bundle savedInstanceState) {
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onStart() {
        super.onStart();
        mBackHandledInterface.setSelectedFragment(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * back事件，如果返回true，则fragment拦截消费back事件
     */
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (getActivity() != null) {
            performHandleMessage(msg);
        }
        return true;
    }

    protected void performHandleMessage(Message msg) {

    }

    /**
     * 启动Activity
     */
    protected void launchActivity(Class<? extends Activity> cls) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        startActivity(intent);
    }

}
