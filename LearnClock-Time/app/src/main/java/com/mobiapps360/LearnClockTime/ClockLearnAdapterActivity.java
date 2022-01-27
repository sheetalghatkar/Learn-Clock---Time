
package com.mobiapps360.LearnClockTime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiapps360.LearnClockTime.R;


public class ClockLearnAdapterActivity extends RecyclerView.Adapter<ClockLearnAdapterActivity.ViewHolder> {

    private XyzModel[] listXyzData;

    public ClockLearnAdapterActivity(XyzModel[] listXyzData) {
        this.listXyzData = listXyzData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.xyz_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final XyzModel XyzModelData = listXyzData[position];
        holder.textViewTitle.setText(listXyzData[position].getTitle());
    }

    @Override
    public int getItemCount() {
        return listXyzData.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewTitle = itemView.findViewById(R.id.textViewTitle);
        }

    }
}

