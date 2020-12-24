package com.example.news.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.Models.News;
import com.example.news.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private Context context;
    private List<News> newsList;

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
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, final int i) {
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

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewImageUrl = itemView.findViewById(R.id.image_view_news_image);
        textViewPubDate = itemView.findViewById(R.id.text_view_pub_date);
        textViewTitle = itemView.findViewById(R.id.text_view_title);
        textViewLink = itemView.findViewById(R.id.text_view_link);
    }
}

