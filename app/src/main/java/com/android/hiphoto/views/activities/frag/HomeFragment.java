/*
 * 文件名: HomeFragment
 * 版    权：  Copyright Daman Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: evil
 * 创建时间: 22/10/15
 *
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.android.hiphoto.views.activities.frag;

import android.content.Intent;
import android.widget.Button;

import com.android.hiphoto.R;
import com.android.hiphoto.views.activities.base.EBaseFragment;

import butterknife.Bind;
import butterknife.OnClick;
import nl.changer.polypicker.ImagePickerActivity;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author evil
 * @version [Daman Client V20150420, 22/10/15]
 */
public class HomeFragment extends EBaseFragment {

    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final int INTENT_REQUEST_GET_N_IMAGES = 14;

    @Bind(R.id.camera)
    Button camera;


    @Override
    public int getLayoutId() {
        return R.layout.frag_home;
    }

    @OnClick(R.id.camera)
    public void camera(){
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
    }
}
