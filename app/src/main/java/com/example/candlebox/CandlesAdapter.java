package com.example.candlebox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CandlesAdapter extends RecyclerView.Adapter<CandlesAdapter.ViewHolder> {

    private Context context;
    private List<Candles> candles;

    public CandlesAdapter(Context context, List<Candles> candles) {
        this.context = context;
        this.candles = candles;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRecentCandleName;
        private TextView tvRecentCandleIngredients;
        private TextView tvRecentCandleToxicity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecentCandleName = itemView.findViewById(R.id.tvRecentCandleName);
            tvRecentCandleIngredients = itemView.findViewById(R.id.tvRecentCandleIngredients);
            tvRecentCandleToxicity = itemView.findViewById(R.id.tvRecentToxicity);
        }

        public void bind(Candles candle) {
            // Bind the post data to the view elements
            tvRecentCandleName.setText(candle.getCandleName());
            tvRecentCandleIngredients.setText(candle.getIngredients());
            tvRecentCandleToxicity.setText("Change this later but...not toxic!");
        }
    }


    @Override
    public int getItemCount() {
        return candles.size();
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
}