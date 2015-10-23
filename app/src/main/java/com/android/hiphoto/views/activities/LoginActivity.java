/*
 * 文件名: LoginActivity
 * 版    权：  Copyright Daman Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: evil
 * 创建时间: 23/10/15
 *
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.android.hiphoto.views.activities;

import com.android.hiphoto.R;
import com.android.hiphoto.views.activities.base.EBaseActivity;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author evil
 * @version [Daman Client V20150420, 23/10/15]
 */
public class LoginActivity extends EBaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.login;
    }

    @Override
    public boolean initTitle() {
        setTitle("Login");
        return true;
    }
}
