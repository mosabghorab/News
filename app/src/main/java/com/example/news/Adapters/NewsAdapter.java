package com.example.news.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.Models.News;
import com.example.news.Models.News_fav;
import com.example.news.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private Context context;
    private List<News> newsList;
    FirebaseFirestore firebaseFirestore;

    public NewsAdapter(Context context, List<News> news) {
        this.context = context;
        this.newsList = news;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.news_costum_design, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder newsViewHolder, final int i) {
        final News news = newsList.get(i);
        newsViewHolder.textViewTitle.setText(news.getTitle());
        newsViewHolder.textViewPubDate.setText(news.getPubDate());
        newsViewHolder.textViewLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news.getOnSiteLink()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        if (news.getImageUrl() != null && !news.getImageUrl().trim().equals(""))
            Picasso.get().load(news.getImageUrl()).into(newsViewHolder.imageViewImageUrl);

        newsViewHolder.add_fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    newsViewHolder.add_fav.setTextOn("Not Added");
                    newsViewHolder.add_fav.setBackgroundColor(Color.WHITE);

                    firebaseFirestore = FirebaseFirestore.getInstance();
                    CollectionReference collectionReference = firebaseFirestore.collection("News_fav");
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getEmail();
                    News_fav news_fav = new News_fav();
                    news_fav.setUser_id(uid);
                    news_fav.setNew_id(news.getTitle());
                    collectionReference.add(news_fav);

                }else {

                    newsViewHolder.add_fav.setTextOff("Added");
                    newsViewHolder.add_fav.setBackgroundColor(Color.RED);

                }
            }
        });




//        newsViewHolder.add_fav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}

class NewsViewHolder extends RecyclerView.ViewHolder {
    ImageView imageViewImageUrl;
    TextView textViewTitle;
    TextView textViewPubDate;
    TextView textViewLink;
    public ToggleButton add_fav;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewImageUrl = itemView.findViewById(R.id.image_view_news_image);
        textViewPubDate = itemView.findViewById(R.id.text_view_pub_date);
        textViewTitle = itemView.findViewById(R.id.text_view_title);
        textViewLink = itemView.findViewById(R.id.text_view_link);
        add_fav = itemView.findViewById(R.id.add_fav);
    }
}

