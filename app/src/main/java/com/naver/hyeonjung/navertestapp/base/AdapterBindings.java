package com.naver.hyeonjung.navertestapp.base;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.naver.hyeonjung.navertestapp.vo.Item;

public class AdapterBindings {

    @BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<Item> items) {
        BaseAdapter adapter = (BaseAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(items);
        }
    }

    @BindingAdapter({"bind:imgRes"})
    public static void imgload(AppCompatImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .pl(R.color.lighter_gray)
                .into(imageView);
    }

    @BindingAdapter("bind:myItem")
    public static void bindMyItem(RecyclerView recyclerView, ObservableArrayList<Item> items) {
        BaseAdapter adapter = (BaseAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(items);
        }
    }

    @BindingAdapter("bind:webItem")
    public static void bindWebItem(RecyclerView recyclerView, ObservableArrayList<Item> items) {
        BaseAdapter adapter = (BaseAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(items);
        }
    }
}
