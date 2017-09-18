package com.jarvanmo.marsboot.tools.update;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by mo on 17-9-16.
 *
 * @author mo
 */

public abstract class ParametersCreator {

    HttpUrl url;
    Headers headers;
    RequestParams requestParams;

    ParametersCreator(String url) {
        this(new HttpUrl.Builder().scheme(url).build());
    }

    ParametersCreator(HttpUrl url) {
        this.url = url;
    }

    /**
     * add requestParams to query parameter for url
     * */
    HttpUrl buildQueryParameter() {
        if (requestParams == null) {
            return url;
        }

        final ConcurrentHashMap<String, String> urlParams = requestParams.urlParams;
        HttpUrl.Builder builder = url.newBuilder();
        for (Map.Entry<String, String> entry : urlParams.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue());
        }

        url = builder.build();
        return url;

    }

    abstract Request generate();
}
