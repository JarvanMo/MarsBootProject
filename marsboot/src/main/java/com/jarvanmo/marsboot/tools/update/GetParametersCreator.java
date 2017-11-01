package com.jarvanmo.marsboot.tools.update;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by mo on 17-9-13.
 * 剑气纵横三万里 一剑光寒十九洲
 */

public class GetParametersCreator extends ParametersCreator {



    UpdateInfoTransformer updateInfoTransformer;

    GetParametersCreator(String url) {
        super(url);
    }

    GetParametersCreator(HttpUrl url) {
        super(url);
    }


    public GetParametersCreator headers(Headers headers) {
        this.headers = headers;
        return this;
    }

    public GetParametersCreator query(RequestParams requestParams) {
        this.requestParams = requestParams;
        return this;
    }

    @Override
    Request generate() {
        Request.Builder builder = new Request.Builder();
        builder.url(buildQueryParameter());
        builder.get();

        if(headers !=null){
            builder.headers(headers);
        }

        return builder.build();
    }

}
