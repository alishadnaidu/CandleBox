package com.example.candlebox;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class Frag2 extends Fragment {
    public static final String TAG = "Frag2";

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag2, container, false);
        TextView tvTester = (TextView) view.findViewById(R.id.tvCO2Frag);
        tvTester.setText(String.valueOf(MainActivity.totalHours));
        Log.i(TAG, String.valueOf(MainActivity.amountOfCO2));
        return view;
    }
}
