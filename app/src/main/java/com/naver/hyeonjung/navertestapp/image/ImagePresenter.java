package com.naver.hyeonjung.navertestapp.image;


import android.databinding.ObservableArrayList;

import com.naver.hyeonjung.navertestapp.R;
import com.naver.hyeonjung.navertestapp.network.SearchManager;
import com.naver.hyeonjung.navertestapp.network.base.BaseApiService;
import com.naver.hyeonjung.navertestapp.network.code.ResponseCodes;
import com.naver.hyeonjung.navertestapp.util.LOG;
import com.naver.hyeonjung.navertestapp.util.Utils;
import com.naver.hyeonjung.navertestapp.vo.Image;
import com.naver.hyeonjung.navertestapp.vo.Item;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ImagePresenter implements ImageContract.Presenter {

    private ImageContract.View view;
    private SearchManager mSearchManager;
    private ObservableArrayList<Item> mItems;
    private ExecutorService service = Executors.newFixedThreadPool(2);

    public ImagePresenter(ImageFragment searchView, SearchManager searchManager) {
        this.view = searchView;
        this.mSearchManager = searchManager;
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
    public void search(String keyword, String sort, int start, int display) {

        if (Utils.getConnectivityStatus(view.getViewContext()) == Utils.NetworkUtil.TYPE_NOT_CONNECTED) {
            view.showSnackbar(R.string.update_error_no_network_connection);
            return;
        }
        view.errSearchVisible(false, -1);
        mSearchManager.searchImage(keyword, sort, start, display, new BaseApiService.GetTaskCallBack<List<Image>>() {
            @Override
            public void onTaskLoaded(List<Image> task) {
                LOG.d("searchImage success");
                view.setRefreshing(false);

                if (task.size() == 0) {
                    view.errSearchVisible(true, R.string.no_result);
                    return;
                }

                for (Image image : task) {
                    Item item = new Item();
                    item.setType(Item.IMAGE_TYPE);
                    item.setImage(image);
                    mItems.add(item);
                }
            }

            @Override
            public void onDataNotAvailable(ResponseCodes codes) {
                LOG.d("searchImage failed");
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
