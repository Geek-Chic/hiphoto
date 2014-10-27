/**
 * @Title: AbAdapter.java
 * @Package com.android.geekchic.framework.ui.list
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: 24 Oct, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.android.geekchic.framework.ui.list;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ClassName: AbAdapter
 * @Descritpion: 基本Adapter
 * @author evil
 * @date 24 Oct, 2014
 */
public abstract class AbAdapter<T> extends AbBaseAdapter<T, AbAdapterHelper>
{
    
    /**
     * AbAdapter构造函数
     * @param context
     * @param layoutResId
     */
    public AbAdapter(Context context, int layoutResId)
    {
        super(context, layoutResId);
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public AbAdapter(Context context, int layoutResId, List<T> data)
    {
        super(context, layoutResId, data);
    }
    
    /**
     * @see com.android.geekchic.framework.ui.list.AbBaseAdapter#getAdapterHelper(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    protected AbAdapterHelper getAdapterHelper(int position, View convertView,
            ViewGroup parent)
    {
        return AbAdapterHelper.get(context,
                convertView,
                parent,
                layoutResId,
                position);
    }
    
}
