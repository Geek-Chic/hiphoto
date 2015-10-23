/*
 * 文件名: HomeActivity
 * 版    权：  Copyright Daman Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: evil
 * 创建时间: 22/10/15
 *
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.android.hiphoto.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.hiphoto.R;
import com.android.hiphoto.views.activities.base.EBaseActivity;
import com.android.hiphoto.views.activities.frag.ECameraFragment;
import com.android.hiphoto.views.activities.frag.HomeFragment;
import com.android.hiphoto.views.activities.frag.NavigationDrawerFragment;
import com.android.hiphoto.views.activities.frag.ProfileFragment;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author evil
 * @version [Daman Client V20150420, 22/10/15]
 */
public class HomeActivity extends EBaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, ECameraFragment.Contract {
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    private HomeFragment mHomeWebFragment;

    private ECameraFragment mDemoCameraFragment;

    private boolean singleShot = false;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean initTitle() {
        setTitle(getTitle());
//        setActionMenu(R.menu.home);
        return true;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        mHomeWebFragment = new HomeFragment();
        mDemoCameraFragment = ECameraFragment.newInstance(false);
        ProfileFragment fragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section_home);
                break;
            case 2:
                mTitle = getString(R.string.title_section_app_download);
                break;
            case 3:
                mTitle = getString(R.string.title_section_jenkins);
                break;
            case 4:
                mTitle = getString(R.string.title_section_jira);
                break;
        }
    }

    @Override
    public boolean OnMenuClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        super.onMenuItemClick(item);
        return true;
    }

    @Override
    public boolean onBack() {
        ExitApp();
        return true;
    }


    private long exitTime = 0;


    public void ExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, getString(R.string.exit_twice), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            this.finish();
        }
    }

    @Override
    public boolean isSingleShotMode() {
        return singleShot;
    }

    @Override
    public void setSingleShotMode(boolean mode) {
        this.singleShot = mode;
    }
}
