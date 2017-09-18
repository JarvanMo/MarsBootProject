package com.jarvanmo.marsboot.tools.update;

import android.content.Context;

import okhttp3.HttpUrl;
import okhttp3.MediaType;

/**
 * Created by mo on 17-9-11.
 * 剑气纵横三万里 一剑光寒十九洲
 */

public class AppUpdater {

    public static final String CONTENT_TYPE = "Content-Type";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public static class Builder {

        public Builder(Context context) {
        }


        public GetParametersCreator get(String url) {
            return new GetParametersCreator(url);
        }

        public PostParametersCreator post(String url) {
            return new PostParametersCreator(url);
        }

        public GetParametersCreator get(HttpUrl url) {
            return new GetParametersCreator(url);
        }

        public PostParametersCreator post(HttpUrl url) {
            return new PostParametersCreator(url);
        }

    }


}
