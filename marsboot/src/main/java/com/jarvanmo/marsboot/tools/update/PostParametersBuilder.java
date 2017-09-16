package com.jarvanmo.marsboot.tools.update;

import okhttp3.Headers;
import okhttp3.RequestBody;

/**
 * Created by mo on 17-9-13.
 * 剑气纵横三万里 一剑光寒十九洲
 */

public class PostParametersBuilder {

    private Headers headers;
    private RequestParams requestParams;

   public PostParametersBuilder headers(Headers headers){
       this.headers = headers;
       return this;
   }

   public PostParametersBuilder query(RequestParams requestParams){
       this.requestParams = requestParams;
       return this;
   }

   public PostParametersBuilder formUrlEncoded(RequestParams requestParams){
       return this;
   }


    public PostParametersBuilder requestBody(RequestBody requestBody){
        return this;
    }
}
