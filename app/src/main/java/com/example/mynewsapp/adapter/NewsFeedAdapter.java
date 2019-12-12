package com.example.mynewsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mynewsapp.ui.NewsDetailsActivity;
import com.example.mynewsapp.R;
import com.example.mynewsapp.model.NewsFeedModel;

import java.util.ArrayList;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    private Context context;

    private ArrayList<NewsFeedModel> newsFeedModelList;

    public NewsFeedAdapter(ArrayList<NewsFeedModel> newsFeedModelList, Context context){

        this.newsFeedModelList = newsFeedModelList;
        this.context=context;

    }

    @NonNull
    @Override
    public NewsFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_feed_item, parent, false);

        NewsFeedAdapter.ViewHolder viewHolder = new NewsFeedAdapter.ViewHolder(view,context,newsFeedModelList);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedAdapter.ViewHolder holder, int position) {


        holder.bind(newsFeedModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return newsFeedModelList.size();    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title,author,description,date;
        private ImageView newsImage;
        private ProgressBar progressBar;
        private Context context;
        private ArrayList<NewsFeedModel> newsFeedModelList;
        private ViewHolder(View itemView, final Context context, final ArrayList<NewsFeedModel>newsFeedModelList) {

            super(itemView);

            this.context=context;
            this.newsFeedModelList=newsFeedModelList;
            itemView.setOnClickListener(this);

            title = (TextView) itemView.findViewById(R.id.title) ;
            author = (TextView) itemView.findViewById(R.id.author) ;
            description = (TextView) itemView.findViewById(R.id.sdetails) ;
            date = (TextView) itemView.findViewById(R.id.time) ;
            newsImage=(ImageView)itemView.findViewById(R.id.galleryImage);
            progressBar = itemView.findViewById(R.id.prograss_load_photo);



        }

        @Override
        public void onClick(View view) {

            int position=getAdapterPosition();
            NewsFeedModel newsFeedModel=this.newsFeedModelList.get(position);
            Intent intent=new Intent(context, NewsDetailsActivity.class);
            intent.putExtra("URL",newsFeedModel.getUrl());
            context.startActivity(intent);


        }

        private void bind(NewsFeedModel newsFeedModeL) {



            title.setText(newsFeedModeL.getTitle());
            author.setText(newsFeedModeL.getAuthor());
            description.setText(newsFeedModeL.getDescription());

            String dateL=newsFeedModeL.getPublishedAt();
            String local=dateL.substring(0,10);

            date.setText(local);


          //  date.setText( Utils.DateToTimeFormat(newsFeedModeL.getPublishedAt()));



            Glide.with(context)
                    .load(newsFeedModeL.getUrlToImage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(newsImage);

//            try {
//                Picasso.get().load(newsFeedModeL.getUrlToImage()).into(newsImage);
//            }catch (Exception e){
//
//            }

        }
    }
}

