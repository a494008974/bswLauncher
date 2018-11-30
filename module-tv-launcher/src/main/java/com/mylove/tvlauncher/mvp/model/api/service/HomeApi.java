package com.mylove.tvlauncher.mvp.model.api.service;

import com.mylove.tvlauncher.mvp.model.entity.HomeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.mylove.tvlauncher.mvp.model.api.Api.HOME_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;


/**
 * Created by zhou on 2018/11/28.
 */

public interface HomeApi {

    @Headers({DOMAIN_NAME_HEADER + HOME_DOMAIN_NAME})

    @GET("/index.php/win8/getContentVer1")
    Observable<HomeResponse> getDataList(@Query("data") String data);
}
