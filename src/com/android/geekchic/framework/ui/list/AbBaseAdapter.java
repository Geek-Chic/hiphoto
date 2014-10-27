/**
 * @Title: AbBaseAdapter.java
 * @Package com.android.geekchic.framework.ui.list
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: 24 Oct, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.android.geekchic.framework.ui.list;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.geekchic.common.log.LogUtils;



/**
 * @ClassName: AbBaseAdapter
 * @Descritpion: [用一句话描述作用] 
 * @author evil
 * @date 24 Oct, 2014
 */
public abstract class AbBaseAdapter<T, H extends AbAdapterHelper> extends
        BaseAdapter
{
    /**
     * TAG
     */
    protected static final String TAG = LogUtils.makeLogTag(AbBaseAdapter.class.getSimpleName());
    
    /**
     * 上下文
     */
    protected final Context context;
    
    /**
     * Item的viewID
     */
    protected final int layoutResId;
    
    /**
     * 源数据
     */
    protected final List<T> data;
    
    /**
     * 是否显示加载进度条
     */
    protected boolean displayIndeterminateProgress = false;
    
    public AbBaseAdapter(Context context, int layoutResId)
    {
        this(context, layoutResId, null);
    }
    
    public AbBaseAdapter(Context context, int layoutResId, List<T> data)
    {
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.context = context;
        this.layoutResId = layoutResId;
    }
    
    /**
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return data.size();
    }
    
    /**
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public T getItem(int position)
    {
        // TODO Auto-generated method stub
        return data.get(position);
    }
    
    /**
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }
    
    /**
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final H helper = getAdapterHelper(position, convertView, parent);
        T item = getItem(position);
        helper.setAssociatedObject(item);
        convert(helper, item);
        return helper.getView();
    }
    
    @Override
    public boolean isEnabled(int position)
    {
        return position < data.size();
    }
    
    public void add(T elem)
    {
        data.add(elem);
        notifyDataSetChanged();
    }
    
    public void addAll(List<T> elem)
    {
        data.addAll(elem);
        notifyDataSetChanged();
    }
    
    public void set(T oldElem, T newElem)
    {
        set(data.indexOf(oldElem), newElem);
    }
    
    public void set(int index, T elem)
    {
        data.set(index, elem);
        notifyDataSetChanged();
    }
    
    public void remove(T elem)
    {
        data.remove(elem);
        notifyDataSetChanged();
    }
    
    public void remove(int index)
    {
        data.remove(index);
        notifyDataSetChanged();
    }
    
    public void replaceAll(List<T> elem)
    {
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
    }
    
    public boolean contains(T elem)
    {
        return data.contains(elem);
    }
    
    /** Clear data list */
    public void clear()
    {
        data.clear();
        notifyDataSetChanged();
    }
    
    /**
     * Implement this method and use the helper to adapt the view to the given item.
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void convert(H helper, T item);
    
    /**
     * You can override this method to use a custom BaseAdapterHelper in order to fit your needs
     * @param position    The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return An instance of BaseAdapterHelper
     */
    protected abstract H getAdapterHelper(int position, View convertView,
            ViewGroup parent);
}
