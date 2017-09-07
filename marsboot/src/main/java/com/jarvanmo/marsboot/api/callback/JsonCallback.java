package com.jarvanmo.marsboot.api.callback;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.jarvanmo.common.R;
import com.jarvanmo.common.api.response.JsonResponse;
import com.jarvanmo.common.util.MLog;
import com.jarvanmo.common.widget.MToast;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mo on 17-3-21.
 * Copyright © 2017, cnyanglao, Co,. Ltd. All Rights Reserve
 */

public abstract class JsonCallback<D> implements Callback<JsonResponse<D>> {

    private final static String tag = "JsonCallback";

    @Override
    public void onResponse(@NonNull Call<JsonResponse<D>> call, @NonNull Response<JsonResponse<D>> response) {

        JsonResponse<D> responseBody = response.body();
        if (response.isSuccessful() && responseBody != null && responseBody.getCode() == 0) {
            onResponseSuccessfully(response.headers(), responseBody.getDatas(), responseBody.getTotalSize());
        } else {
            onResponseFailed(call.request().url().toString(), response.code());
            onResponseFailed(call.request().url().toString(), responseBody == null ? "response body is null" : responseBody.getMsg() + "");
            onFailureOccurs();
        }

        onFinish();

    }

    @CallSuper
    @Override
    public void onFailure(@NonNull Call<JsonResponse<D>> call, @NonNull Throwable t) {
        MLog.d(tag, "error occurs  while trying to request:\n" + call.request().url().toString() + "\nmessage:" + t.getMessage());
        onConnectionFailure();
        onFailureOccurs();
        onFinish();
    }

    public void onConnectionFailure() {
        MToast.show(R.string.network_connection_problem, MToast.TYPE_WARNING);
    }

    public abstract void onResponseSuccessfully(Headers headers, D data, int offset);

    public void onResponseFailed(String url, String errMessage) {
        MLog.d(tag, "error occurs  while trying to request:\n" + url + "\nmessage:" + errMessage);
        if (errMessage != null) {
            MToast.show(errMessage, MToast.TYPE_WARNING);
        }
    }

    public void onResponseFailed(String url, int code) {

    }

    public void onFailureOccurs() {
    }

    public void onFinish() {

    }

}
