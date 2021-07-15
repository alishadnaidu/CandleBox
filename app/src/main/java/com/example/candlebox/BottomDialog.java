package com.example.candlebox;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BottomDialog extends BottomSheetDialogFragment {

    public static final String TAG = "BottomDialog";
    public static TextView title, candleName, rawDataDisplay, ingredientsList;
    private String fetchRawBarcode;
    private ImageView ivCandle;
    private String candleDatabaseName = "";

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_barcode, container, false);
        title = view.findViewById(R.id.allAboutYourCandle);
        candleName = view.findViewById(R.id.candleName);
        rawDataDisplay = view.findViewById(R.id.rawdatadisplay);
        ingredientsList = view.findViewById(R.id.ingredientsList);
        ivCandle = view.findViewById(R.id.ivCandle);

        rawDataDisplay.setText(String.valueOf(fetchRawBarcode));

        getCandleData();

        return view;
    }

    /*
    public void fetchUrl(String url) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                fetchUrl = url;
            }
        });
    }

     */

    public void fetchRawBarcode(String rawBarcode) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                fetchRawBarcode = rawBarcode;
            }
        });
    }

    private void getCandleData() {
        ParseQuery<Candles> query = ParseQuery.getQuery(Candles.class);
        query.include(Candles.KEY_RAWBARCODEVALUE);
        //find the candle associated with the raw barcode value
        query.whereEqualTo(Candles.KEY_RAWBARCODEVALUE, fetchRawBarcode);
        query.findInBackground(new FindCallback<Candles>() {
            @Override
            public void done(List<Candles> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting candle ID", e);
                    return;
                }
                for (Candles candle: objects) {
                    Log.i(TAG, "Candle name: " + candle.getCandleName() + ", Raw barcode: " +
                            candle.getRawBarcodeValue());
                    candleDatabaseName = candle.getCandleName();
                    candleName.setText(candleDatabaseName);
                }

            }
        });
    }
}
