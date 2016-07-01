package com.upsocl.upsocl.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.upsocl.upsocl.R;
import com.upsocl.upsocl.domain.News;
import com.upsocl.upsocl.io.WordpressApiAdapter;
import com.upsocl.upsocl.ui.adapters.NewsAdapter;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by leninluque on 09-11-15.
 */
public class NewsFragment extends Fragment implements Callback<ArrayList<News>> {
    private RecyclerView newsList;
    private NewsAdapter adapter;
    private Integer page;
    private LinearLayoutManager llm;
    private ProgressBar spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        page = 1;
        loadPosts(page);

        newsList = (RecyclerView) root.findViewById(R.id.news_list);
        newsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewsAdapter(getActivity());
        newsList.setAdapter(adapter);
        spinner = (ProgressBar) getActivity().findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);

        newsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            llm = (LinearLayoutManager) recyclerView.getLayoutManager();
            int size = llm.getItemCount();
            if (size == llm.findLastCompletelyVisibleItemPosition() + 1) {
                Log.d(getTag(), "SI load more findLastCompletelyVisibleItemPosition->" + llm.findLastCompletelyVisibleItemPosition() + "---size:" + size);
                page = page + 1;
                spinner.setVisibility(View.VISIBLE);
                loadPosts(page);
            } else {
                Log.d(getTag(), "NO load more findLastCompletelyVisibleItemPosition->" + llm.findLastCompletelyVisibleItemPosition() + "---size:" + size);
            }
            }
        });

        return root;
    }

    @Override
    public void success(ArrayList<News> newses, Response response) {
        adapter.addAll(newses);
        spinner.setVisibility(View.GONE);
    }

    @Override
    public void failure(RetrofitError error) {
        adapter.addAll(new ArrayList<News>());
    }

    public void loadPosts(Integer paged){
        WordpressApiAdapter.getApiService().getListNews(paged, this);
    }


}
