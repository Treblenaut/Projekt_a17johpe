package com.example.a17johpe.projekt_a17johpe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by a17johpe on 2018-05-18.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    public List<MarvelCharacter> mDataset;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(MarvelCharacter item);
    }

    private final OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public TextView mTextView2;
        public TextView mTextView3;
        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.recycler_text1);
            mTextView2 = v.findViewById(R.id.recycler_text2);
            mTextView3 = v.findViewById(R.id.recycler_text3);
        }
    }

    public CustomAdapter(List<MarvelCharacter> myDataset, OnItemClickListener inListener) {
        mDataset = myDataset;
        listener = inListener;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_listview, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).getName());
        holder.mTextView2.setText(mDataset.get(position).getHeroName());
        holder.mTextView3.setText(mDataset.get(position).getTeam());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mDataset.get(position));
            }
        });
    }

    public void add(MarvelCharacter m) {
        mDataset.add(m);
    }

    public void clear () {
        mDataset.clear();
        //notifyItemChanged();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
