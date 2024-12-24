package com.example.recyclevapp;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder> {

    private List<DataModel> dataSet;
    private List<DataModel> filteredList;
    private Context context;

    public CustomeAdapter(ArrayList<DataModel> dataSet, Context context) {
        this.dataSet = dataSet;
        this.filteredList = new ArrayList<>(dataSet);
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textView);
            textViewVersion = itemView.findViewById(R.id.textView2);
            textViewVersion.setTextSize(20); // Set smaller text size for descriptions
            textViewName.setTextSize(20); // Set smaller text size for names
            textViewName.setTypeface(null, android.graphics.Typeface.BOLD); // Set bold text for names
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public CustomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomeAdapter.MyViewHolder holder, int position) {
        DataModel data = filteredList.get(position);
        holder.textViewName.setText(data.getName());
        holder.textViewVersion.setText(data.getVersion());
        holder.imageView.setImageResource(data.getImage());

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Selected: " + data.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String query) {
        query = query.toLowerCase();
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(dataSet);
        } else {
            for (DataModel data : dataSet) {
                if (data.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(data);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setupSearchFunctionality(EditText searchBar) {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
