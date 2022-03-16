package com.mobiapps360.LearnClockTime;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;


public class OtherAppsAdapter extends RecyclerView.Adapter<OtherAppsAdapter.ViewHolder> {

    private OtherAppsItem[] listOtherAppsItem;
    private Context context;
    String getAppLink;

    public OtherAppsAdapter(Context context) {
        this.context = context;
    }

    public OtherAppsAdapter(OtherAppsItem[] listOtherAppsData) {
        this.listOtherAppsItem = listOtherAppsData;
    }

    public void setListMenuItem(OtherAppsItem[] listOtherAppsData) {
        this.listOtherAppsItem = listOtherAppsData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.other_apps_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final OtherAppsItem otherAppsItemModel = listOtherAppsItem[position];
        getAppLink = listOtherAppsItem[position].appLink;
        holder.txt_applink.setText(getAppLink);
        holder.textViewAppTitle.setText(otherAppsItemModel.getAppName());
        int id = context.getResources().getIdentifier("com.mobiapps360.LearnClockTime:drawable/" + otherAppsItemModel.getAppIcon(), null, null);
        holder.imgVwAppIcon.setImageResource(id);
    }

    @Override
    public int getItemCount() {
        return listOtherAppsItem.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgVwAppIcon;
        public TextView textViewAppTitle;
        public AppCompatButton btnGetNow;
        public TextView txt_applink;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgVwAppIcon = itemView.findViewById(R.id.imgVwAppIcon);
            this.textViewAppTitle = itemView.findViewById(R.id.textViewAppTitle);
            this.btnGetNow = itemView.findViewById(R.id.btnGetNow);
            this.txt_applink = itemView.findViewById(R.id.txt_applink);
            //------------------------------------------
            btnGetNow.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            ((AppCompatButton) v).setAlpha((float) 0.5);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            ((AppCompatButton) v).setAlpha((float) 1.0);
                            try {
                                if (context instanceof OtherAppsActivity) {
                                    ((OtherAppsActivity) context).openAppInPlayStore(txt_applink.getText().toString());
                                }
                            } catch (android.content.ActivityNotFoundException anfe) {
                                System.out.println("Catch:"+anfe);
                            }
                        }
                    }
                    return true;
                }
            });
        }
    }
}

