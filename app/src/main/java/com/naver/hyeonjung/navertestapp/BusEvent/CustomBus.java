package com.naver.hyeonjung.navertestapp.BusEvent;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

public class CustomBus extends Bus {
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    CustomBus.super.post(event);
                }
            });
        }
    }
}
