package com.example.candlebox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CandlesAdapter extends RecyclerView.Adapter<CandlesAdapter.ViewHolder> {

    public static final String TAG = "CandlesAdapter";
    private Context context;
    private List<RecentlyScannedCandles> candles;

    public CandlesAdapter(Context context, List<RecentlyScannedCandles> candles) {
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

        public void bind(RecentlyScannedCandles candle) {
            // Bind the post data to the view elements
            tvRecentCandleName.setText(candle.getRecentCandleName());
            tvRecentCandleIngredients.setText(candle.getRecentIngredients());
            tvRecentCandleToxicity.setText(checkToxicity(candle.getRecentIngredients()));
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
        RecentlyScannedCandles candle = candles.get(position);
        holder.bind(candle);
    }

    //check if candle contain toxic ingredient, have sustainability message reflect message
    public String checkToxicity(String ingredients) {
        String message;
        if (ingredients.toLowerCase().contains("paraffin")) {
            message = "Your candle contains paraffin, which releases carcinogenic soot when burned.";
        }
        else {
            message = "Your candle is non-toxic. Great job picking it out!";
            if (ingredients.toLowerCase().contains("soy")) {
                message = message + " Your candle also has a soy wax base, which is a healthy, eco-friendly alternative!";
            }
            if (ingredients.toLowerCase().contains("beeswax")) {
                message = message + " Your candle also has a beeswax base, which is a healthy, eco-friendly alternative. Say thanks to the bees!";
            }
        }
        return message;
    }
}