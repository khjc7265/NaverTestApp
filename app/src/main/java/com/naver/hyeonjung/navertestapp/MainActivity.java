package com.naver.hyeonjung.navertestapp;

import android.databinding.ObservableArrayList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.naver.hyeonjung.navertestapp.network.SearchManager;
import com.naver.hyeonjung.navertestapp.image.ImageFragment;
import com.naver.hyeonjung.navertestapp.image.ImagePresenter;
import com.naver.hyeonjung.navertestapp.util.Utils;
import com.naver.hyeonjung.navertestapp.vo.Item;
import com.naver.hyeonjung.navertestapp.web.WebFragment;
import com.naver.hyeonjung.navertestapp.web.WebPresenter;

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity {


    private static final String SEARCH_KEYWORD = "SEARCH_KEYWORD";

    public interface OnBackPressed {
        boolean onBack();
    }

    class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case MESSAGE_BACKKEY_TIMEOUT:
                isBackPressed = false;
                break;
        }
    }

    final int MESSAGE_BACKKEY_TIMEOUT = 2;
    final long TIMEOUT_BACKKEY_DELAY = 2000;

    private ImageFragment imageFragment;
    private WebFragment webFragment;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private CoordinatorLayout mainContent;
    private RelativeLayout detailImageView;
    private ImageView detailImage;
    private ImageView goRight;
    private ImageView goLeft;
    private RelativeLayout clear;

    private boolean isBackPressed;
    MyHandler mHandler;
    private EditText etSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new MyHandler(this);

        mainContent = findViewById(R.id.main_content);
        detailImageView = findViewById(R.id.detailImageView);
        detailImage = findViewById(R.id.detailImage);
        goRight = findViewById(R.id.goRight);
        goLeft = findViewById(R.id.goLeft);
        clear = findViewById(R.id.clear);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        imageFragment = ImageFragment.getInstance();
        webFragment = WebFragment.getInstance();

        new ImagePresenter(imageFragment, SearchManager.getInstance());
        new WebPresenter(webFragment, SearchManager.getInstance());

        mSectionsPagerAdapter.addFragment(imageFragment, getString(R.string.first_title));
        mSectionsPagerAdapter.addFragment(webFragment, getString(R.string.second_title));


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(pageChangeListener);

        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        etSearch = findViewById(R.id.etSearch);
        etSearch.setOnEditorActionListener(editorActionListener);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSearch.setText("");
            }
        });

        if (savedInstanceState != null) {
            String keyword = savedInstanceState.getString(SEARCH_KEYWORD);
            imageFragment.search(keyword);
            webFragment.search(keyword);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Realm.getDefaultInstance().close();
        SearchManager.getInstance().destroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String email = etSearch.getText().toString();
        outState.putString(SEARCH_KEYWORD, email);
    }


    @Override
    public void onBackPressed() {
        if (detailImageView.getVisibility() == View.VISIBLE) {
            detailImageView.setVisibility(View.GONE);
        } else {
            if (!isBackPressed) {
                isBackPressed = true;
                showToast(R.string.backpress_text);
                mHandler.sendEmptyMessageDelayed(MESSAGE_BACKKEY_TIMEOUT, TIMEOUT_BACKKEY_DELAY);
            } else {
                mHandler.removeMessages(MESSAGE_BACKKEY_TIMEOUT);
                super.onBackPressed();
            }
        }
    }


    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Utils.hideKeyboard(MainActivity.this);
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public void showToast(final int message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showSnackbar(@StringRes int message) {
        final Snackbar snackbar = Snackbar.make(mainContent, getString(message), Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.DKGRAY);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                snackbar.show();
            }
        });
    }

    public String getKeyword() {
        return etSearch.getText().toString();
    }

    TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                    || actionId == EditorInfo.IME_ACTION_SEARCH) {
                Utils.hideKeyboard(MainActivity.this);
                imageFragment.search(etSearch.getText().toString());
                webFragment.search(etSearch.getText().toString());
            }
            return true;
        }
    };


    public void showImage(final ObservableArrayList<Item> mItems, final int position) {
        if (position < 0) {
            showToast(R.string.fitst_page);
            return;
        }
        if (position >= mItems.size()) {
            showToast(R.string.last_page);
            return;
        }
        detailImageView.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(mItems.get(position).getImage().getLink())
                .into(detailImage);
        goRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImage(mItems, position + 1);
            }
        });

        goLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImage(mItems, position - 1);
            }
        });
    }


}
