/*
 * 文件名: ProfileFragment
 * 版    权：  Copyright Daman Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: evil
 * 创建时间: 23/10/15
 *
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.android.hiphoto.views.activities.frag;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

import com.android.hiphoto.R;
import com.android.hiphoto.views.activities.base.EBaseFragment;

import butterknife.Bind;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author evil
 * @version [Daman Client V20150420, 23/10/15]
 */
public class ProfileFragment extends EBaseFragment {

    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Bind(R.id.rootLayout)
    CoordinatorLayout rootLayout;

    @Bind(R.id.fabBtn)
    FloatingActionButton fabBtn;

    @Override
    public int getLayoutId() {
        return R.layout.frag_profile;
    }

    public Toolbar getToolbar() {
        return mToolBar;
    }
}
