/**
 * @Title: AdvancedAbAdapter.java
 * @Package com.android.geekchic.framework.ui.list
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: 24 Oct, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.android.geekchic.framework.ui.list;

import java.util.List;

import android.content.Context;

/**
 * @ClassName: AdvancedAbAdapter
 * @Descritpion: [用一句话描述作用] 
 * @author evil
 * @date 24 Oct, 2014
 */
public abstract class AdvancedAbAdapter<T> extends AbAdapter<T>
{
    
    /**
     * EnhancedAbAdapter构造函数
     * @param context
     * @param layoutResId
     * @param data
     */
    public AdvancedAbAdapter(Context context, int layoutResId, List<T> data)
    {
        super(context, layoutResId, data);
        // TODO Auto-generated constructor stub
    }
    
    protected final void convert(AbAdapterHelper helper, T item)
    {
        boolean itemChanged = helper.associatedObject == null
                || !helper.associatedObject.equals(item);
        helper.associatedObject = item;
        convert(helper, item, itemChanged);
    }
    
    protected abstract void convert(AbAdapterHelper helper, T item,
            boolean itemChanged);
    
}
