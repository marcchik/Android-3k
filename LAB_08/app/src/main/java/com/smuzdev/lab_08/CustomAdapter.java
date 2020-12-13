package com.smuzdev.lab_08;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList thing_id, thing_title, thing_description, thing_discoveredPlace, thing_image;

    Animation translate_anim;

    CustomAdapter(Activity activity, Context context,
                  ArrayList thing_id,
                  ArrayList thing_title,
                  ArrayList thing_description,
                  ArrayList thing_discoveredPlace,
                  ArrayList thing_image) {
        this.activity = activity;
        this.context = context;
        this.thing_id = thing_id;
        this.thing_title = thing_title;
        this.thing_description = thing_description;
        this.thing_discoveredPlace = thing_discoveredPlace;
        this.thing_image = thing_image;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_raw, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.thing_id_txt.setText(String.valueOf(thing_id.get(position)));
        holder.thing_title_txt.setText(String.valueOf(thing_title.get(position)));
        holder.thing_description_txt.setText(String.valueOf(thing_description.get(position)));
        holder.thing_discoveredPlace_txt.setText(String.valueOf(thing_discoveredPlace.get(position)));

        Bitmap bitmapImage = DbBitmapUtility.getImage((byte[]) thing_image.get(position));
        holder.thing_image.setImageBitmap(bitmapImage);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(thing_id.get(position)));
                intent.putExtra("title", String.valueOf(thing_title.get(position)));
                intent.putExtra("description", String.valueOf(thing_description.get(position)));
                intent.putExtra("discoveredPlace", String.valueOf(thing_discoveredPlace.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return thing_id.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView thing_id_txt, thing_title_txt, thing_description_txt, thing_discoveredPlace_txt;
        ImageView thing_image;
        LinearLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thing_id_txt = itemView.findViewById(R.id.thing_id_txt);
            thing_title_txt = itemView.findViewById(R.id.thing_title_txt);
            thing_description_txt = itemView.findViewById(R.id.thing_description_txt);
            thing_discoveredPlace_txt = itemView.findViewById(R.id.thing_discovered_place_txt);
            thing_image = itemView.findViewById(R.id.ivThingImage);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}

