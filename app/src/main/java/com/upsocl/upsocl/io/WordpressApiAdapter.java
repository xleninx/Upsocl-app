package com.upsocl.upsocl.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.upsocl.upsocl.domain.News;
import com.upsocl.upsocl.io.deserializer.NewsDeserializer;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by leninluque on 11-11-15.
 */
public class WordpressApiAdapter {

    private static WordpressService API_SERVICE;

    public static WordpressService getApiService(){

        if(API_SERVICE == null){
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(ApiConstants.BASE_URL)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setConverter(builWordpressApiGsonConverter())
                    .build();

            API_SERVICE = adapter.create(WordpressService.class);
        }

        return API_SERVICE;
    }

    private static GsonConverter builWordpressApiGsonConverter(){
        Gson gsonConf = new GsonBuilder()
                .registerTypeAdapter(News.class, new NewsDeserializer())
                .create();

        return new GsonConverter(gsonConf);
    }

}
