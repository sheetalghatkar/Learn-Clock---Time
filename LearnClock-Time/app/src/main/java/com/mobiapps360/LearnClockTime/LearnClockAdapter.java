
package com.mobiapps360.LearnClockTime;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mobiapps360.LearnClockTime.R;

import java.util.ArrayList;


public class LearnClockAdapter extends RecyclerView.Adapter<LearnClockAdapter.ViewHolder> {

    private LearnClockDataModel[] listLearnClockData;
    private Context context;

    public LearnClockAdapter(Context context) {
        this.context = context;
    }

    public LearnClockAdapter(LearnClockDataModel[] listLearnClockData) {
        this.listLearnClockData = listLearnClockData;
    }

    public void setListMenuItem(LearnClockDataModel[] listLearnClockData) {
        this.listLearnClockData = listLearnClockData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.learn_clock_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final LearnClockDataModel learnClockDataModel = listLearnClockData[position];
        holder.textViewCardNumber.setText(Html.fromHtml("<b>" + String.valueOf(position+1) + "</b>"+"/14"));
        if (position == 1) {
            holder.imageViewLearnClock.setImageResource(learnClockDataModel.getImageId());
        } else if ((position == 6) || (position == 13) || (position == 10)) {
            Glide.with(context)
                    .asGif()
                    .load(learnClockDataModel.getImageId()) //Your gif resource
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .skipMemoryCache(true)
                    .listener(new RequestListener<GifDrawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                            resource.setLoopCount(1);
                            return false;
                        }
                    })
                    .into(holder.imageViewLearnClock);
        } else {
            Glide.with(context).load(learnClockDataModel.getImageId()).into(holder.imageViewLearnClock);
        }
    }

    @Override
    public int getItemCount() {
        return listLearnClockData.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewLearnClock;
        public TextView textViewCardNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewLearnClock = itemView.findViewById(R.id.imageViewLearnClock);
            this.textViewCardNumber = itemView.findViewById(R.id.textViewCardNumber);
        }
    }
}

