package com.naver.hyeonjung.navertestapp.base;

import android.databinding.ObservableArrayList;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.naver.hyeonjung.navertestapp.BusEvent.BusProvider;
import com.naver.hyeonjung.navertestapp.BusEvent.TransferData;
import com.naver.hyeonjung.navertestapp.MainActivity;
import com.naver.hyeonjung.navertestapp.ui.EndlessRecyclerViewScrollListener;
import com.naver.hyeonjung.navertestapp.util.LOG;
import com.naver.hyeonjung.navertestapp.vo.Item;
import com.squareup.otto.Bus;


public class BaseFragment extends Fragment implements MainActivity.OnBackPressed {

    public MainActivity mActivity;
    public Bus bus;
    public TransferData td;
    public EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    public VideoView videoView;
    public ObservableArrayList<Item> mItems;
    public BaseAdapter mAdapter;

    public void init() {

        bus = BusProvider.getInstance();
        td = TransferData.getInstance();

        try {
            bus.unregister(this);
        } catch (Exception e) {
            LOG.d(e.getMessage());
        }
        bus.register(this);

        setRetainInstance(true);

        mActivity = (MainActivity) getActivity();

        mAdapter = new BaseAdapter(getContext());
        mItems = new ObservableArrayList<>();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bus != null) bus.unregister(this);
    }

    @Override
    public boolean onBack() {
        return true;
    }


}
