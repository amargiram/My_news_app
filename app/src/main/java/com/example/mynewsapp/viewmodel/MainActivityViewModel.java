package com.example.mynewsapp.viewmodel;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynewsapp.adapter.NewsFeedAdapter;
import com.example.mynewsapp.di.FactoryMethod;
import com.example.mynewsapp.model.NewsFeedModel;
import com.example.mynewsapp.repository.NewsFeed;
import com.example.mynewsapp.ui.MainActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public  class MainActivityViewModel extends ViewModel {

    private Context context;
    private MutableLiveData<NewsFeedModel> data;
    private NewsFeed newsFeed;

    public MainActivityViewModel(Context context) {
        this.context=context;
        newsFeed = new NewsFeed(context);
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = newsFeed.getNews();
    }

    public MutableLiveData<NewsFeedModel> getNews() {
        return this.data;
    }






}
