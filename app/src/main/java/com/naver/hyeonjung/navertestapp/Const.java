package com.naver.hyeonjung.navertestapp;

public class Const {
    private static final boolean IS_DEV = true;
    private static final String DEV_SERVER_DOMAIN = "https://openapi.naver.com";
    private static final String PROD_SERVER_DOMAIN = "https://openapi.naver.com";
    public static final String SERVER_DOMAIN = IS_DEV ? DEV_SERVER_DOMAIN : PROD_SERVER_DOMAIN;
}
