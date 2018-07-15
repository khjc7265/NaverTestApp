package com.naver.hyeonjung.navertestapp.image;

import android.content.Context;
import android.databinding.ObservableArrayList;
import com.naver.hyeonjung.navertestapp.base.BasePresenter;
import com.naver.hyeonjung.navertestapp.base.BaseView;
import com.naver.hyeonjung.navertestapp.vo.Item;

public interface ImageContract {
    interface View extends BaseView<Presenter> {

        boolean isActive();

        void setRefreshing(boolean refreshing);

        void errSearchVisible(boolean visible, int stringRes);

        void showSnackbar(int stringRes);

        void successAdd(Item item);

        Context getViewContext();
    }

    interface Presenter extends BasePresenter {

        void setItems(ObservableArrayList<Item> mItems);

        void search(String keyword, String type, int offset, int size);

    }
}
