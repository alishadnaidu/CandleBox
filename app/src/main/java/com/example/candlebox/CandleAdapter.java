package com.example.candlebox;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;


public class CandleAdapter extends RecyclerView.Adapter<CandleAdapter.ViewHolder>{
    private Context context;
    private List<Candles> candles;

    public CandleAdapter(Context context, List<Candles> candles) {
        this.context = context;
        this.candles = candles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_candle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Candles candle = candles.get(position);
        holder.bind(candle);
    }

    // Clean all elements of the recycler view
    public void clear() {
        candles.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Candles> list) {
        candles.addAll(list);
        notifyDataSetChanged();
    }

    //check if candle contain toxic ingredient, have sustainability message reflect message
    private String checkToxicity(String ingredients) {
        String message;
        if (ingredients.toLowerCase().contains("paraffin")) {
            message = "Your candle contains paraffin, which releases carcinogenic soot when burned.";
        }
        else {
            message = "Your candle is non-toxic. Great job picking it out!";
        }
        return message;
    }

    @Override
    public int getItemCount() {
        return candles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        TextView tvRecentCandleName;
        TextView tvRecentCandleIngredients;
        public TextView tvRecentToxicity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecentCandleName = itemView.findViewById(R.id.tvRecentCandleName);
            tvRecentCandleIngredients = itemView.findViewById(R.id.tvRecentCandleIngredients);
            tvRecentToxicity = itemView.findViewById(R.id.tvRecentToxicity);
        }

        public void bind(Candles candle) {
            // Bind the candle data to the view elements
            tvRecentCandleName.setText(candle.getCandleName());
            tvRecentCandleIngredients.setText(candle.getIngredients());
            tvRecentToxicity.setText(checkToxicity(candle.getIngredients()));
        }

        @Override
        public void onClick(View v) {

        }
    }
}

