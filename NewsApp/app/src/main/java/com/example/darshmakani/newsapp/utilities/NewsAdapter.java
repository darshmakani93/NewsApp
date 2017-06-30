package com.example.darshmakani.newsapp.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;


import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.darshmakani.newsapp.R;

import com.example.darshmakani.newsapp.model.NewsItem;

import java.util.ArrayList;
/**
 * Created by Darsh Makani on 6/26/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

            private ArrayList<NewsItem> data;
                 final private ItemClickListener listener;

    public NewsAdapter(ArrayList<NewsItem> data, ItemClickListener listener) {

        this.data = data;
        this.listener = listener;
    }

    public interface ItemClickListener {


               void onListItemClick(int clickedItemIndex);
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
            int layoutIdForListItem = R.layout.news_item;


        LayoutInflater inflater = LayoutInflater.from(context);
            boolean attachToParent = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, attachToParent);
        return new NewsAdapterViewHolder(view);
    }
    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {

        holder.bind(position);
    }
    @Override
    public int getItemCount() {

        return data.size();
    }

    class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mTitle;

            public final TextView mDescription;

        public final TextView mTime;

        public NewsAdapterViewHolder(View itemView) {

            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.title);
            mDescription = (TextView) itemView.findViewById(R.id.description);
            mTime = (TextView) itemView.findViewById(R.id.time);


            itemView.setOnClickListener(this);
        }

        public void bind(int pos) {

            NewsItem newsItem = data.get(pos);

            mTitle.setText(newsItem.getTitle());
            mDescription.setText(newsItem.getDescription());
            mTime.setText(newsItem.getPublishedAt());
        }

        @Override
        public void onClick(View v) {


            listener.onListItemClick(getAdapterPosition());
        }
    }

    public void setData(ArrayList<NewsItem> data) {

        this.data = data;

    }
}
