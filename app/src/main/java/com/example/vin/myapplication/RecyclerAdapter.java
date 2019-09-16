package com.example.vin.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by sab99r
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> mList;

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    public RecyclerAdapter(Context contexts, List<Movie> list) {
        this.mContext = contexts;
        this.mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = mList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Movie movie = mList.get(position);
                if (isLongClick) {
                    Toast.makeText(mContext, "#" + position + " - " + movie.getTitle() + " (Long click)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "#" + position + " - " + movie.getTitle() + " (click)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        private TextView titleTextView;
        public TextView title, year, genre;
        private ItemClickListener clickListener;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            genre = (TextView) itemView.findViewById(R.id.genre);
            year = (TextView) itemView.findViewById(R.id.year);
            //titleTextView = (TextView)itemView.findViewById(R.id.textView);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }



    /*public interface OnItemClickListener {
        void onItemClick(Movie item);
    }

    private final List<Movie> items;
    private final OnItemClickListener listener;

    public RecyclerAdapter(List<Movie> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, year, genre;

        private TextView name;
        private ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            genre = (TextView) itemView.findViewById(R.id.genre);
            year = (TextView) itemView.findViewById(R.id.year);

            *//*name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);*//*
        }

        public void bind(final Movie item, final OnItemClickListener listener) {
            title.setText(item.getTitle());
            genre.setText(item.getGenre());
            year.setText(item.getYear());

            //name.setText(item.name);
            //Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }*/








    /*private List<Movie> moviesList;

    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();
    //private final OnClickListener mOnClickListener = new MyOnClickListener();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public RecyclerAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);
        //itemView.setOnClickListener(mOnClickListener);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }*/


}
