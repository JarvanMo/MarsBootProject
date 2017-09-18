package com.jarvanmo.marsboot.tools.update;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by mo on 17-9-13.
 * 剑气纵横三万里 一剑光寒十九洲
 */

public class PostParametersCreator extends ParametersCreator {

    RequestBody requestBody;

    PostParametersCreator(String url) {
        super(url);
    }

    PostParametersCreator(HttpUrl url) {
        super(url);
    }


    public PostParametersCreator headers(Headers headers) {
        this.headers = headers;
        return this;
    }

    public PostParametersCreator query(RequestParams requestParams) {
        this.requestParams = requestParams;
        return this;
    }

    public PostParametersCreator formUrlEncoded(RequestParams fields) {

        return this;
    }


    public PostParametersCreator formData(RequestParams requestParams) {
        requestBody = buildFormData(requestParams);
        return this;
    }


    public PostParametersCreator requestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }


    @Override
    Request generate() {
        Request.Builder builder = new Request.Builder();
        builder.url(buildQueryParameter());
        builder.post(requestBody);

        if(headers !=null){
            builder.headers(headers);
        }


        return builder.build();
    }


    private RequestBody buildFormData(RequestParams requestParams) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        if (requestParams != null) {
            Iterator it;
            final ConcurrentHashMap<String, String> urlParams = requestParams.urlParams;
            final ConcurrentHashMap<String, RequestParams.FileWrapper> fileParams = requestParams.fileParams;

            it = urlParams.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();

//                        builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
//                                RequestBody.create(null, (String) entry.getValue()));
                builder.addFormDataPart((String) entry.getKey(), (String) entry.getValue());


            }

            it = fileParams.entrySet().iterator();
            RequestBody fileBody;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                RequestParams.FileWrapper fileWrapper = (RequestParams.FileWrapper) entry.getValue();
                fileBody = RequestBody.create(MediaType.parse(fileWrapper.contentType), fileWrapper.file);
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + entry.getKey() + "\"; filename=\"" + fileWrapper.file.getName() + "\""),
                        fileBody);

            }
        }

        return builder.build();
    }
}
