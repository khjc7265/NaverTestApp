package com.naver.hyeonjung.navertestapp.network;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Setter;

class ResultData<T> {
//
//    @Getter
//    @Setter
//    private Meta meta;

    @Setter
    private ArrayList<T> items;

    public <T> List<T> getItems(Class<T> toValueType) {
        List<T> result = new ArrayList<>();
        if (items !=null){
            Gson gson = new Gson();
            for (int i = 0; i < items.size(); i++) {
                result.add(gson.fromJson(gson.toJsonTree(items.get(i)), toValueType));
            }
            return result;
        } else {
            Gson gson = new Gson();
            result.add(gson.fromJson(gson.toJsonTree(this), toValueType));
        }
        return result;
    }


//    @Data
//    class Meta{
//        boolean is_end;
//        int total_count;
//        int pageable_count;
//    }

}
