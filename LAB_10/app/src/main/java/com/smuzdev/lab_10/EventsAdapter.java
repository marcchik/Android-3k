package com.smuzdev.lab_10;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smuzdev.lab_10.databinding.RecyclerItemBinding;
import com.smuzdev.lab_10.model.Events;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Events> eventsList;

    private OnItemClickListener listener;

    public EventsAdapter(Context context, List<Events> events) {
        this.inflater = LayoutInflater.from(context);
        this.eventsList = events;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerItemBinding itemBinding = RecyclerItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder holder, int position) {
        Events events = eventsList.get(position);
        holder.bind(events);
        holder.viewID = eventsList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Events events);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        RecyclerItemBinding binding;
        long viewID;

        public ViewHolder(RecyclerItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
            viewID = -1;

            itemView.setOnCreateContextMenuListener(this);
        }

        public void bind(Events events) {
            binding.setEvents(events);
            binding.executePendingBindings();
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.add(0, 0, (int) this.viewID, "Delete");
            menu.add(0, 1, (int) this.viewID, "Change");
        }
    }

}
