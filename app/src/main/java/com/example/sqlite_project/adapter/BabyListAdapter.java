package com.example.sqlite_project.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite_project.listener.RecyclerItemClickListener;
import com.example.sqlite_project.model.Baby;

import java.util.ArrayList;
import java.util.List;
import com.example.sqlite_project.R;
import com.example.sqlite_project.widget.LetterTile;

public class BabyListAdapter extends RecyclerView.Adapter<BabyListAdapter.ContactHolder>{

    private List<Baby> babyList;
    private Context context;

    private RecyclerItemClickListener recyclerItemClickListener;

    public BabyListAdapter(Context context) {
        this.context = context;
        this.babyList = new ArrayList<>();
    }

    private void add(Baby item) {
        babyList.add(item);
        notifyItemInserted(babyList.size() - 1);
    }

    public void addAll(List<Baby> babyList) {
        for (Baby baby : babyList) {
            add(baby);
        }
    }

    public void remove(Baby item) {
        int position = babyList.indexOf(item);
        if (position > -1) {
            babyList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Baby getItem(int position) {
        return babyList.get(position);
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact_item, parent, false);

        final ContactHolder contactHolder = new ContactHolder(view);

        contactHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = contactHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, contactHolder.itemView);
                    }
                }
            }
        });

        return contactHolder;
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        final Baby baby = babyList.get(position);

        final Resources res = context.getResources();
        final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);

        LetterTile letterTile = new LetterTile(context);

        Bitmap letterBitmap = letterTile.getLetterTile(baby.getName(),
                String.valueOf(baby.getId()), tileSize, tileSize);

        holder.thumb.setImageBitmap(letterBitmap);
        holder.name.setText(baby.getName());
        holder.issue.setText(baby.getIssue());
    }

    @Override
    public int getItemCount() {
        return babyList.size();
    }

    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    static class ContactHolder extends RecyclerView.ViewHolder {

        ImageView thumb;
        TextView name;
        TextView issue;

        public ContactHolder(View itemView) {
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            name = (TextView) itemView.findViewById(R.id.name);
            issue = (TextView) itemView.findViewById(R.id.phone);

        }
    }
}
