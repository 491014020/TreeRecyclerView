package com.baozi.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baozi.demo.R;
import com.baozi.demo.item.clickload.ClickLoadGroupItem;
import com.baozi.treerecyclerview.adpater.TreeRecyclerAdapter;
import com.baozi.treerecyclerview.base.BaseRecyclerAdapter;
import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 点击懒加载的demo
 */
public class ClickLoadFragment extends Fragment {
    RecyclerView view;
    private TreeRecyclerAdapter adapter = new TreeRecyclerAdapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = (RecyclerView) inflater.inflate(R.layout.layout_rv_content, container, false);
            view.setBackgroundColor(Color.GRAY);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化recyclerView
        view.setLayoutManager(new GridLayoutManager(view.getContext(), 4));
        view.setAdapter(adapter);
        TreeItem treeItem = ItemHelperFactory.createTreeItem(new String[]{}, ClickLoadGroupItem.class, null);
        adapter.getItemManager().replaceAllItem(Arrays.asList(treeItem));
        adapter.setOnItemClickListener((viewHolder, position) -> {
            ClickLoadGroupItem item = (ClickLoadGroupItem) adapter.getData(position);
            if (item == null || item.getData() == null) {
                return;
            }
            if (item.getChild() == null || item.getChild().isEmpty()) {
                item.setData(new String[]{"1", "2", "3"});
                item.setExpand(true);
            }
            TreeItemGroup data = (TreeItemGroup) adapter.getData(position);
            data.setExpand(false);
            data.setChild(new ArrayList<>());
            data.setExpand(true);
//            int itemPosition = adapter.getItemManager().getItemPosition(data);
//            adapter.getItemManager().replaceItem(itemPosition,new ArrayList());
        });
    }
}
