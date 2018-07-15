package com.naver.hyeonjung.navertestapp.BusEvent;

public final class BusProvider {

    private static CustomBus BUS = new CustomBus();

    public static CustomBus getInstance() {
        if (BUS == null) BUS = new CustomBus();
        return BUS;
    }

    private BusProvider() {

    }
}
