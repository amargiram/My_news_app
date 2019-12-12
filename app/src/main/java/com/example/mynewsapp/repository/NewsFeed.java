package com.example.mynewsapp.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynewsapp.model.NewsFeedModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

  public class NewsFeed  {

      private Context context;

    private Gson gson = new Gson();
    private String URL ="https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=13d4eceecc134785884c6d7fb33e7e0b";
      MutableLiveData<NewsFeedModel>newsFeedModelMutableLiveData=new MutableLiveData<>();
    public NewsFeed(Context context){
        this.context=context;

    }
    public MutableLiveData<NewsFeedModel> getNews(){

         RequestQueue queue = Volley.newRequestQueue(context);



            StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {
                        JSONObject rootObject=new JSONObject(response);


                        JSONArray newsArray=rootObject.getJSONArray("articles");


                        for (int i=0;i<newsArray.length();i++)
                        {

                            JSONObject finalObject=newsArray.getJSONObject(i);

                            newsFeedModelMutableLiveData = gson.fromJson(finalObject.toString(), NewsFeedModel.class);



                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            });


            request.setRetryPolicy(new DefaultRetryPolicy(900000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);

            return newsFeedModelMutableLiveData;
        }



    }

