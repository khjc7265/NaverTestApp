package com.naver.hyeonjung.navertestapp.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.naver.hyeonjung.navertestapp.databinding.ItemViewImageBinding;
import com.naver.hyeonjung.navertestapp.databinding.ItemViewWebBinding;
import com.naver.hyeonjung.navertestapp.vo.Item;
import com.naver.hyeonjung.navertestapp.BR;

import java.util.List;
import java.util.Random;


public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {
    private static final int VIEW_TYPE_IMAGE = 0;
    private static final int VIEW_TYPE_WEB = 1;

    public interface ItemClick {
        void onClick(View view, int position);
    }

    private ItemClick itemClick;
    private List<Item> mItems;
    private Context mContext;
    private Random mRandom;

    public BaseAdapter(Context context) {
        mContext = context;
        mRandom = new Random();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_IMAGE) {
            ViewDataBinding binding = ItemViewImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new BaseViewHolder(binding, viewType);
        } else {
            ViewDataBinding binding = ItemViewWebBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new BaseViewHolder(binding, viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position).getType() == Item.IMAGE_TYPE) {
            return VIEW_TYPE_IMAGE;
        } else {
            return VIEW_TYPE_WEB;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, final int position) {
        Item item = mItems.get(position);
        holder.bind(item);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemClick != null){
                    itemClick.onClick(view,position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }


    void setItem(List<Item> items) {
        if (items == null) {
            return;
        }
        this.mItems = items;
        notifyDataSetChanged();
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;
        View view;

        BaseViewHolder(ViewDataBinding binding, int viewType) {
            super(binding.getRoot());
            this.view = binding.getRoot();

            if (viewType == VIEW_TYPE_IMAGE) {
                binding.getRoot().getLayoutParams().height = getRandomIntInRange(1000, 500);
            }
            this.binding = binding;
        }

        void bind(Item item) {
            binding.setVariable(BR.item, item);
        }

        int getRandomIntInRange(int max, int min) {
            return mRandom.nextInt((max - min) + min) + min;
        }
    }

}
