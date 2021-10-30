package com.assignment.movieflex.Services;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.movieflex.API_Manager.Model;
import com.assignment.movieflex.Details;
import com.assignment.movieflex.R;
import com.bumptech.glide.Glide;

import java.util.List;

/*Adapter for recycler view
 * This class is responsible for the showing view in recyclerView
 * and, all click listener on button of list items */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<Model> list;
    Context context;

    public RecyclerViewAdapter(Context context, List<Model> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = list.get(position);
        Glide.with(context).load(context.getResources().getString(R.string.poster_prefix_url) + model.getPoster_path()).into(holder.img);

        holder.title.setText(model.getTitle());
        holder.overview.setText(model.getOverview());
        holder.delete.setOnClickListener(view -> {

            list.remove(position);

            notifyItemRemoved(position);//smooth transition
            notifyItemRangeChanged(position, getItemCount());

            //notifyDataSetChanged(); //No animation
        });

        holder.root_view.setOnClickListener(view -> holder.view.performClick());
        holder.view.setOnClickListener(view -> {
            Intent i = new Intent(context, Details.class);
            i.putExtra("bp", model.getBackdrop_path());
            i.putExtra("title", model.getTitle());
            i.putExtra("vote", model.getVote_average());
            i.putExtra("date", model.getRelease_date());
            i.putExtra("overview", model.getOverview());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<Model> newList) {
        list = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        Button view, delete;
        TextView title, overview;
        View root_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            view = itemView.findViewById(R.id.item_view);
            delete = itemView.findViewById(R.id.item_delete);
            title = itemView.findViewById(R.id.item_title);
            overview = itemView.findViewById(R.id.item_overview);
            root_view = itemView.findViewById(R.id.root_view);
        }
    }
}
