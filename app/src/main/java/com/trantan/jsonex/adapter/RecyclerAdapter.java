package com.trantan.jsonex.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trantan.jsonex.R;
import com.trantan.jsonex.model.Repository;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Repository> mRepositories;

    public RecyclerAdapter(List<Repository> repositories) {
        mRepositories = repositories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycler_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mRepositories.get(position));
    }

    @Override
    public int getItemCount() {
        return mRepositories == null ? 0 : mRepositories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameRepo;
        private TextView mLanguage;
        private TextView mWatchers;
        private ImageView mImageOwner;

        public ViewHolder(View itemView) {
            super(itemView);

            mImageOwner = itemView.findViewById(R.id.image_owner);
            mNameRepo = itemView.findViewById(R.id.text_name_repo);
            mLanguage = itemView.findViewById(R.id.text_language);
            mWatchers = itemView.findViewById(R.id.text_watchers);
        }

        public void setData(Repository repository) {
            mNameRepo.setText(repository.getName());
            mWatchers.setText(String.valueOf(repository.getWatchers()));
            mLanguage.setText(repository.getLanguage());
            Glide.with(itemView)
                    .load(repository.getImageOwnerUrl())
                    .into(mImageOwner);

        }
    }
}
