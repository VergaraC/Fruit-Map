package com.example.fruitmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EAdapter extends RecyclerView.Adapter<EAdapter.ViewHolder> implements Filterable {

    private  ArrayList<Item> mExampleList;
    private  ArrayList<Item> copia_list;
    private RecyclerView.OnItemTouchListener mListerner;

    public interface onItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListerner(RecyclerView.OnItemTouchListener listerner ){
        mListerner = listerner;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView titulo;

        public ViewHolder (View itemView, final RecyclerView.OnItemTouchListener listener){
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_item);
            titulo = itemView.findViewById(R.id.text_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
// -->>>>>>>>>>------------------------                           // listener.onItemClick( position);
                        }
                    }
                }
            });
        }
    }
    public  EAdapter(ArrayList<Item> exapleList) {
        mExampleList = exapleList;
        copia_list = new ArrayList<>(exapleList);
    }
    @Override
    public EAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        ViewHolder evh = new ViewHolder(v, mListerner);
        return evh;
    }
    @Override
    public void onBindViewHolder(@NonNull EAdapter.ViewHolder holder, int position) {
        Item currentItem = mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResourece());
        holder.titulo.setText(currentItem.getTitulo());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Item> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(copia_list);
            }else {
                String filterPattern = constraint.toString().trim();
                for (Item item : copia_list) {
                    if (item.getTitulo().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            mExampleList.clear();
            mExampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
