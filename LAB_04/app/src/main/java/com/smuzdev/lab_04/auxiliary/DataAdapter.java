package com.smuzdev.lab_04.auxiliary;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.smuzdev.lab_04.R;

import java.io.File;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Users users;
    private OnItemClickListener onItemClickListener;

    public DataAdapter(Context context, Users users, OnItemClickListener onItemClickListener) {
        this.users = users;
        this.inflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //name of xml file
        View view = inflater.inflate(R.layout.recycler_items, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        Person person = users.personArrayList.get(position);
        File imgFile = new File(Environment.getExternalStorageDirectory() + "/LAB_04/" + person.name.hashCode() + ".png");
        holder.nameView.setText("Name: " + person.name);
        holder.surnameView.setText("Surname: " + person.surname);
        holder.emailView.setText("Email: " + person.email);
        holder.twitterView.setText("Twitter: " + person.twitter);
        holder.phoneView.setText("Phone: " + person.phone);
        holder.imageView.setImageURI(Uri.fromFile(imgFile));
    }

    @Override
    public int getItemCount() {
        return users.personArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imageView;
        final TextView nameView, surnameView, emailView, phoneView, twitterView;
        OnItemClickListener onItemClickListener;

        ViewHolder(View view, OnItemClickListener onItemClickListener){

            super(view);
            nameView = (TextView)view.findViewById(R.id.name);
            surnameView = (TextView)view.findViewById(R.id.surname);
            emailView = (TextView)view.findViewById(R.id.email);
            twitterView = (TextView) view.findViewById(R.id.twitter);
            phoneView = (TextView)view.findViewById(R.id.phone);
            imageView = (ImageView)view.findViewById(R.id.image);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.OnItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }
}
