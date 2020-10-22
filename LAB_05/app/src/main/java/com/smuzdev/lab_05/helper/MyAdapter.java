package com.smuzdev.lab_05.helper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.smuzdev.lab_05.R;
import com.smuzdev.lab_05.activities.DetailActivity;
import com.smuzdev.lab_05.models.Dish;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<DishViewHolder> {

    private Context mContext;
    private List<Dish> dishList;

    public MyAdapter(Context mContext, List<Dish> dishList) {
        this.mContext = mContext;
        this.dishList = dishList;
    }

    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

       View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_row_item, viewGroup, false);

       return new DishViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DishViewHolder dishViewHolder, int position) {

        dishViewHolder.imageView.setImageResource(dishList.get(position).getDishImage());
        dishViewHolder.mTitle.setText(dishList.get(position).getDishName());
        dishViewHolder.mDescription.setText(dishList.get(position).getDishDescription());
        dishViewHolder.mCookingTime.setText(dishList.get(position).getDishCookingTime());

        dishViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("Image", dishList.get(dishViewHolder.getAdapterPosition()).getDishImage());
                intent.putExtra("Description", dishList.get(dishViewHolder.getAdapterPosition()).getDishDescription());
                mContext.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }
}

class DishViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView mTitle, mDescription, mCookingTime;
    CardView mCardView;

    public DishViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        mDescription = itemView.findViewById(R.id.tvDescription);
        mCookingTime = itemView.findViewById(R.id.tvCookingTime);

        mCardView = itemView.findViewById(R.id.myCardView);


    }
}
