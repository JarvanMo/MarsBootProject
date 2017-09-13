package com.jarvanmo.marsboot.tools.update;

import okhttp3.Headers;

/**
 * Created by mo on 17-9-13.
 * 剑气纵横三万里 一剑光寒十九洲
 */

public class GetParametersBuilder {

    private Headers headers;
    private RequestParams requestParams;

   public GetParametersBuilder headers(Headers headers){
       this.headers = headers;
       return this;
   }

   public GetParametersBuilder query(RequestParams requestParams){
       this.requestParams = requestParams;
       return this;
   }
}
