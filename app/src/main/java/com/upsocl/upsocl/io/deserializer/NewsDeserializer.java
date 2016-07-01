package com.upsocl.upsocl.io.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.upsocl.upsocl.domain.News;
import com.upsocl.upsocl.io.model.JsonKeys;

import java.lang.reflect.Type;

/**
 * Created by leninluque on 13-11-15.
 */
public class NewsDeserializer implements JsonDeserializer<News> {
    @Override
    public News deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        JsonObject news = json.getAsJsonObject();

        return extractNewsFromJsonArray(news);
    }

    private News extractNewsFromJsonArray(JsonObject item){

        News currentNews = new News();
        currentNews.setTitle(item.get(JsonKeys.NEWS_TITLE).getAsString());
        currentNews.setImage(item.get(JsonKeys.NEWS_IMAGES_URL).getAsString());
        currentNews.setContent(item.get(JsonKeys.NEWS_CONTENT).getAsJsonObject().get(JsonKeys.RENDERED).getAsString());
        currentNews.setId(item.get(JsonKeys.NEWS_ID).getAsInt());

        return currentNews;
    }
}
