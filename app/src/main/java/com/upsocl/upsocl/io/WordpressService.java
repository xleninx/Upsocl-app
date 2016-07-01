package com.upsocl.upsocl.io;

import com.upsocl.upsocl.domain.News;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by leninluque on 11-11-15.
 */
public interface WordpressService {

    @GET(ApiConstants.LIST_POSTS)
    void getListNews(@Query(ApiConstants.PAGE) int page, Callback<ArrayList<News>> response);

}
