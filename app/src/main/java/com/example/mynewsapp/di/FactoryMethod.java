package com.example.mynewsapp.di;

import android.content.Context;

import com.example.mynewsapp.adapter.NewsFeedAdapter;
import com.example.mynewsapp.model.NewsFeedModel;
import com.example.mynewsapp.ui.MainActivity;
import com.example.mynewsapp.ui.NewsDetailsActivity;
import com.example.mynewsapp.viewmodel.MainActivityViewModel;
import com.example.mynewsapp.viewmodel.NewsDetailsActivityViewModel;

import java.util.ArrayList;

public class FactoryMethod {

    private Context context;
    ArrayList<NewsFeedModel>newsFeedModelArrayList;

    public FactoryMethod(Context context){
        this.context=context;
    }

    public NewsFeedAdapter getNewsfeedObj( ArrayList<NewsFeedModel> newsFeedModelArrayList){

        return new NewsFeedAdapter(newsFeedModelArrayList,context);
    }

    public static NewsDetailsActivityViewModel getNewsDetailsViewModelObj(){
        return new NewsDetailsActivityViewModel();
    }


    public static ArrayList<NewsFeedModel>getArrayListObj()
    {
        return new ArrayList<NewsFeedModel>();
    }

    public static NewsFeedModel getNewsFeedModelObj(){
        return new NewsFeedModel();
    }

    public  MainActivityViewModel getMainActivityViewModelObj(Context context){
        return new MainActivityViewModel(context);
    }

    public static MainActivity getMainActivityObj(){
        return new MainActivity();
    }
}
