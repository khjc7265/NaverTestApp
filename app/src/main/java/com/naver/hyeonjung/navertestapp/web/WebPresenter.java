package com.naver.hyeonjung.navertestapp.web;


import android.databinding.ObservableArrayList;

import com.naver.hyeonjung.navertestapp.R;
import com.naver.hyeonjung.navertestapp.network.SearchManager;
import com.naver.hyeonjung.navertestapp.network.base.BaseApiService;
import com.naver.hyeonjung.navertestapp.network.code.ResponseCodes;
import com.naver.hyeonjung.navertestapp.util.LOG;
import com.naver.hyeonjung.navertestapp.util.Utils;
import com.naver.hyeonjung.navertestapp.vo.Image;
import com.naver.hyeonjung.navertestapp.vo.Item;
import com.naver.hyeonjung.navertestapp.vo.Web;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WebPresenter implements WebContract.Presenter {

    private WebContract.View view;
    private SearchManager mSearchManager;
    //    private Realm mRealm;
    private ObservableArrayList<Item> mItems;
    private ExecutorService service = Executors.newFixedThreadPool(2);

    public WebPresenter(WebContract.View searchView, SearchManager searchManager) {
        this.view = searchView;
        this.mSearchManager = searchManager;
//        this.mRealm = mRealm;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        view = null;
    }


    @Override
    public void setItems(ObservableArrayList<Item> mItems) {
        this.mItems = mItems;
    }


    @Override
    public void search(String keyword, int start, int display) {

        if (Utils.getConnectivityStatus(view.getViewContext()) == Utils.NetworkUtil.TYPE_NOT_CONNECTED) {
            view.showSnackbar(R.string.update_error_no_network_connection);
            return;
        }

        view.errSearchVisible(false, -1);
        mSearchManager.searchWeb(keyword, start, display, new BaseApiService.GetTaskCallBack<List<Web>>() {
            @Override
            public void onTaskLoaded(List<Web> task) {
                LOG.d("searchWeb success");
                view.setRefreshing(false);

                if (task.size() == 0) {
                    view.errSearchVisible(true, R.string.no_result);
                    return;
                }

                for (Web web : task) {
                    Item item = new Item();
                    item.setType(Item.WEB_TYPE);
                    item.setWeb(web);
                    mItems.add(item);
                }
            }

            @Override
            public void onDataNotAvailable(ResponseCodes codes) {
                LOG.d("searchWeb failed");
                view.setRefreshing(false);
                if (codes.getCode() == ResponseCodes.PAGE_IS_MAX.getCode()) {
                    view.showSnackbar(R.string.max_page);

                } else if (codes.getCode() == ResponseCodes.SIZE_IS_MAX.getCode()) {
                    view.errSearchVisible(true, R.string.max_size);

                } else {
                    view.errSearchVisible(true, R.string.no_result);
                }

            }
        });

    }


}
