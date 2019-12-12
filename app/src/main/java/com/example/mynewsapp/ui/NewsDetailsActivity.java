package com.example.mynewsapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.mynewsapp.R;
import com.example.mynewsapp.di.FactoryMethod;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewsDetailsActivity extends AppCompatActivity {
    private String url;
    private WebView webView;
    private ProgressBar loader;
    private Toolbar toolbar;
    private ImageView imageViewLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        // initializing views
        init();

        // loadingNewsFeed in webview
        loadNeewFeed();

        //new like click event
        newsLikeClick();

        //finishing activity on clicking back arrow
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    private void newsLikeClick() {

        imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FactoryMethod.getNewsDetailsViewModelObj().updateLikeInFirebase(NewsDetailsActivity.this);

            }
        });
    }





    private void loadNeewFeed() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loader.setVisibility(View.VISIBLE);
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                loader.setVisibility(View.GONE);
                imageViewLike.setVisibility(View.VISIBLE);

                FactoryMethod.getNewsDetailsViewModelObj().updateInfirebase();

            }
        });

        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    private void init() {
        url=getIntent().getStringExtra("URL");
        loader = findViewById(R.id.loader);
        imageViewLike=findViewById(R.id.imageLike);
        webView = findViewById(R.id.webView);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("News Detail");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.share) {

            FactoryMethod.getNewsDetailsViewModelObj().shareNews(url, NewsDetailsActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
