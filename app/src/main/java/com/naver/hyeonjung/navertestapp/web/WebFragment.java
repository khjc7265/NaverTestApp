package com.naver.hyeonjung.navertestapp.web;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naver.hyeonjung.navertestapp.BusEvent.TransferData;
import com.naver.hyeonjung.navertestapp.R;
import com.naver.hyeonjung.navertestapp.base.BaseAdapter;
import com.naver.hyeonjung.navertestapp.base.BaseFragment;
import com.naver.hyeonjung.navertestapp.databinding.FragmentWebBinding;
import com.naver.hyeonjung.navertestapp.ui.EndlessRecyclerViewScrollListener;
import com.naver.hyeonjung.navertestapp.util.LOG;
import com.naver.hyeonjung.navertestapp.vo.Item;


public class WebFragment extends BaseFragment implements WebContract.View {


    private static WebFragment instance;

    private WebContract.Presenter mPresenter;
    private int size = 30;
    private FragmentWebBinding binding;
    private int offset;

    public static WebFragment getInstance() {
        if (instance == null) instance = new WebFragment();
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        init();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web, container, false);
        binding.list.setAdapter(mAdapter);
        binding.setItemList(mItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        binding.list.setLayoutManager(layoutManager);

        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                LOG.d("onLoadMore");
                offset = page;
                mPresenter.search(mActivity.getKeyword(), offset, size);
            }
        };
        binding.list.addOnScrollListener(endlessRecyclerViewScrollListener);


        binding.searchRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 1;
                mItems.clear();
                endlessRecyclerViewScrollListener.refresh();
                mPresenter.search(mActivity.getKeyword(), offset, size);
            }
        });

        mAdapter.setItemClick(new BaseAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                goWebBrowser(mItems.get(position).getWeb().getLink());
            }
        });

        mPresenter.setItems(mItems);
        mPresenter.subscribe();

        return binding.getRoot();
    }


    @Override
    public void setPresenter(WebContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public Context getViewContext(){
        return mActivity;
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        binding.searchRefreshLayout.setRefreshing(refreshing);
    }


    @Override
    public void errSearchVisible(boolean visible, int stringRes) {
        if (visible) {
            binding.errSearch.bringToFront();
            binding.errSearch.setVisibility(View.VISIBLE);
            binding.errSearch.setText(mActivity.getString(stringRes));

        } else {
            binding.errSearch.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showSnackbar(int stringRes) {
        mActivity.showSnackbar(stringRes);
    }

    @Override
    public void successAdd(Item item) {
        td.setMessage(TransferData.ADD_ITEM);
        td.setItem(item);
        bus.post(td);
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    public void search(String keyword) {
        mItems.clear();
        mPresenter.search(keyword, 1,size);
    }

    private void goWebBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

}
