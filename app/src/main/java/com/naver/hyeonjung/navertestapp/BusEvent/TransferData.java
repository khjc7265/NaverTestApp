package com.naver.hyeonjung.navertestapp.BusEvent;


import com.naver.hyeonjung.navertestapp.vo.Item;

import java.util.HashMap;

public class TransferData {

    public static final int DISCONNECTED_NETWORK = 0;
    public static final int ADD_ITEM = 1;

    private static TransferData instance;
    private int message = -1;
    private HashMap<String, Object> map;
    private Item item;

    private TransferData() {
    }

    public static TransferData getInstance() {
        if (instance == null) {
            instance = new TransferData();
        }
        return instance;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getMessage() {
        return message;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
