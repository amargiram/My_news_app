package com.example.mynewsapp.ui;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynewsapp.R;
import com.example.mynewsapp.adapter.NewsFeedAdapter;
import com.example.mynewsapp.di.FactoryMethod;
import com.example.mynewsapp.model.NewsFeedModel;
import com.example.mynewsapp.viewmodel.MainActivityViewModel;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
   private RequestQueue queue;
   private String URL;
   private ArrayList<NewsFeedModel>newsFeedModelArrayList;
   private NewsFeedModel newsFeedModel;
   private RecyclerView recyclerViewNewsFeed;
   private Gson gson;
   private ProgressDialog progressDialog;
   private BroadcastReceiver connectivityReceiver;
   private  MainActivityViewModel mViewModel=new MainActivityViewModel(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
//        mViewModel.init();
//
//
//        NewsFeedAdapter adapter=new FactoryMethod(MainActivity.this).getNewsfeedObj(newsFeedModelArrayList);
//        recyclerViewNewsFeed.setAdapter(adapter);
//
//        mViewModel.getNews().observe(this, new Observer<NewsFeedModel>() {
//            @Override
//            public void onChanged(@Nullable NewsFeedModel movieModels) {
//
//
//
//            }
//        });

// initializing views
        init();




//implementing broadcast receiver for connectivity change
        connectivityReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent == null || intent.getExtras() == null)
                    return;

                ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                try {
                    NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {

                        progressDialog=new ProgressDialog(context);
                        progressDialog.setMessage("Loading...!!!");
                        progressDialog.show();

                        // network call
                        loadNewsFeed();
                        //    new FactoryMethod(getBaseContext()).getMainActivityViewModelObj(getBaseContext()).loadNewsFeed();

                    }
                    else {
                        Toast.makeText(context,"Please connect to internet...!!!",Toast.LENGTH_LONG).show();

                    }

                }catch (Exception e)
                {

                }



            }
        };

        //register broadcastReceiver

        registerReceiver(connectivityReceiver,new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

    }

    public void loadNewsFeed() {

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    progressDialog.dismiss();

                try {
                    JSONObject rootObject=new JSONObject(response);


                    JSONArray newsArray=rootObject.getJSONArray("articles");


                    for (int i=0;i<newsArray.length();i++)
                    {

                        JSONObject finalObject=newsArray.getJSONObject(i);

                        newsFeedModel = gson.fromJson(finalObject.toString(), NewsFeedModel.class);

                        newsFeedModelArrayList.add(newsFeedModel);

                    }

                    NewsFeedAdapter adapter=new FactoryMethod(MainActivity.this).getNewsfeedObj(newsFeedModelArrayList);
                    recyclerViewNewsFeed.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
            }
        });


        request.setRetryPolicy(new DefaultRetryPolicy(900000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }



    private void init() {
        queue = Volley.newRequestQueue(this);
        newsFeedModelArrayList=FactoryMethod.getArrayListObj();
        gson = new Gson();

        recyclerViewNewsFeed=(RecyclerView)findViewById(R.id.recyclerVIewNewsFeed);
        recyclerViewNewsFeed.setHasFixedSize(true);
        recyclerViewNewsFeed.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewNewsFeed.setLayoutManager(layoutManager);

        URL = getResources().getString( R.string.server_url );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(connectivityReceiver);
    }


}
