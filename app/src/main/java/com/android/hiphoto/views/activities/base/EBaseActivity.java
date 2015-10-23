/*
 * 文件名: EBaseActivity
 * 版    权：  Copyright Daman Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: evil
 * 创建时间: 22/10/15
 *
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.android.hiphoto.views.activities.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.hiphoto.AvengersApplication;
import com.android.hiphoto.R;
import com.android.hiphoto.injector.components.ActivityComponent;
import com.android.hiphoto.views.utils.PreferencesUtil;
import com.android.hiphoto.views.utils.log.L;

import butterknife.ButterKnife;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author evil
 * @version [Daman Client V20150420, 22/10/15]
 */
public abstract class EBaseActivity extends AppCompatActivity implements ActionMenuView.OnMenuItemClickListener, BackHandledInterface, Handler.Callback {
    protected Toolbar toolbar;
    protected ViewGroup rootView;
    protected ActionMenuView actionMenuView;
    private LinearLayout mContentContainer;
    private EBaseFragment mBackHandedFragment;

    private ActivityComponent mComponent;
    protected boolean isDestroy;
    protected FragmentActivity mContext;
    protected AvengersApplication mApplication;
    protected Handler mHandler;

    // 统一的加载对话框
    protected ProgressDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        rootView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.base_activity_layout, null);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        actionMenuView = (ActionMenuView) rootView.findViewById(R.id.action_menu_view);
        setSupportActionBar(toolbar);
        actionMenuView.setOnMenuItemClickListener(this);
        actionMenuView.getMenu().clear();
        setContentView(rootView);

        if (!initTitle()) {
            toolbar.setVisibility(View.GONE);
        }
        ButterKnife.bind(this);
        init();
        initPageView();
        initPageViewListener();
        initContent(inflater);
        initSystem();
        initLogSystem();
        initializeDependencyInjector();
        process(savedInstanceState);

    }

    /**
     * 逻辑处理
     *
     * @param savedInstanceState
     */
    protected void process(Bundle savedInstanceState) {
    }

    /**
     * 监听器处理
     */
    protected void initPageViewListener() {
    }

    /**
     * 初始化上下文
     */
    protected void init() {
        mContext = this;
        mApplication = AvengersApplication.getInstance();
        mHandler = new Handler(this);
    }

    protected  void initPageView(){

    }

    protected void initializeDependencyInjector() {
//        AvengersApplication beikeApplication = (AvengersApplication) getApplication();
//        mComponent= DaggerActivityComponent.builder()
//                .activityModule(new ActivityModule(this))
//                .beikeApplicationComponent(beikeApplication.getComponent())
//                .build();
//        mComponent.inject(this);
    }

    private void initContent(LayoutInflater inflater) {
        mContentContainer = (LinearLayout) findViewById(R.id.content_container);
        View contentView = inflater.inflate(getLayoutId(), null);
        mContentContainer.addView(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void initSystem() {
        //初始化SharedPreference
        PreferencesUtil.initContext(this);
        //个推初始化
//        PushManager.getInstance().initialize(this);
    }

    protected void setNavigationIcon(int drawableId, View.OnClickListener clickListener) {
        toolbar.setNavigationIcon(drawableId);
        toolbar.setNavigationOnClickListener(clickListener);
    }

    protected void setBackPressBtn() {
        setBackPressBtn(R.drawable.ic_back);
    }

    protected void setBackPressBtn(int id) {
        toolbar.setNavigationIcon(id);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    protected void setActionMenu(int menuid) {
        //清除之前的MenuItem
        actionMenuView.getMenu().clear();
        //添加新的MenuItem
        getMenuInflater().inflate(menuid, actionMenuView.getMenu());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return OnMenuClick(item);
    }

    protected void setTitle(String title) {
        if (title == null) {
            return;
        }
        if (toolbar != null && !TextUtils.isEmpty(title) && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void setTitle(int id) {
        String strTitle = getResources().getString(id);
        setTitle(strTitle);
    }

    /**
     * 获得子Activity的布局ID
     *
     * @return 子Activity的布局ID
     */
    public abstract int getLayoutId();

    /**
     * 初始化标题
     *
     * @return
     */
    public abstract boolean initTitle();

    /**
     * 返回逻辑
     *
     * @return 是否已Handled后退事件
     */
    public boolean onBack() {
        return false;
    }

    /**
     * Menu点击
     *
     * @param item
     * @return <code>true</code> if the event was handled, <code>false</code> otherwise.
     */
    public boolean OnMenuClick(MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                if (!onBack()) {
                    super.onBackPressed();
                }
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public void setSelectedFragment(EBaseFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }

    private void initLogSystem() {
        L.initLog();
    }

    public void slideInFromBottom() {
        overridePendingTransition(R.anim.slide_in_from_bottom,
                R.anim.slide_out_scale);
    }

    public void slideOutToBottom() {
        overridePendingTransition(R.anim.slide_in_scale, R.anim.slide_out_to_bottom);
    }

    @Override
    protected void onDestroy() {
        isDestroy = true;
        dismissLoadingDialog();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (!isDestroy) {
            performHandleMessage(msg);
        }
        return true;
    }

    /**
     * 接收处理mHandler的消息
     */
    protected void performHandleMessage(Message msg) {

    }

    /**
     * 显示加载对话框
     *
     * @param msg          消息
     * @param isCancelable 是否可被用户关闭
     */
    public void showLoadingDialog(String msg, boolean isCancelable) {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            return;
        } else {
            mLoadingDialog = new ProgressDialog(mContext);
            mLoadingDialog.setMessage(msg);
            mLoadingDialog.setIndeterminate(true);
            mLoadingDialog.setCancelable(isCancelable);
            mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mLoadingDialog.show();
        }
    }

    /**
     * 关闭加载对话框
     */
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    /**
     * 启动Activity
     */
    public void launchActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(mContext, cls));
    }

}
