package com.origamitecnologia.appmodel.control;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.origamitecnologia.appmodel.R;
import com.origamitecnologia.appmodel.model.Movie;
import com.origamitecnologia.appmodel.view.CircleTransform;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AdapterTimeline extends RecyclerView.Adapter<AdapterTimeline.ViewHolder> {

    private Context context;
    public List<Movie> dataset;
    private OnItemClickListener onItemClickListener;

    public AdapterTimeline(Context context, List<Movie> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_timeline, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.fillHolder(dataset.get(i));

        Picasso.with(context)
                .load(ServerCentral.getInstance().getImageWithPath(context, dataset.get(i).getPhoto()))
                .fit()
                .centerCrop()
                .into(viewHolder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addItem(Movie movie) {
        dataset.add(movie);
        notifyDataSetChanged();
    }

    public void addItems(Collection<Movie> movies) {
        dataset.addAll(movies);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @InjectView(R.id.ivNotificationPhoto)
        ImageView ivPhoto;
        @InjectView(R.id.tvTitle)
        TextView tvTitle;
        @InjectView(R.id.tvDescription)
        TextView tvDescription;

        private OnItemClickListener onItemClickListener;
        private Movie movie;

        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);
            this.onItemClickListener = listener;
            view.setOnClickListener(this);
            ButterKnife.inject(this, view);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onClickItem(view, movie.getId());
        }

        public void fillHolder(Movie movie) {
            this.movie = movie;

            tvTitle.setText(movie.getTitle());
            tvDescription.setText(movie.getDescription());
        }
    }
}
