package com.jarvanmo.marsboot.api.response;

/**
 * Created by mo on 17-3-21.
 * Copyright © 2017, cnyanglao, Co,. Ltd. All Rights Reserve
 */

public class JsonResponse<D> {

    private int code;
    private String msg;
    private D datas;
    private int totalSize;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public D getDatas() {
        return datas;
    }

    public void setDatas(D datas) {
        this.datas = datas;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
