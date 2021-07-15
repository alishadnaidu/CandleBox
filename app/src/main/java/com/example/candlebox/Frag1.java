package com.example.candlebox;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Frag1 extends Fragment {

    public static final String TAG = "Frag1";

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag1, container, false);

        TextView tvGreeting1 = (TextView) view.findViewById(R.id.tvGreeting1);
        TextView tvTester = (TextView) view.findViewById(R.id.tvHoursFrag);

        // set personalized greeting based on the current time
        Frag3.returnTime();
        tvGreeting1.setText(Frag3.time + ParseUser.getCurrentUser().getUsername());

        // set the hours based on calculations from MainActivity
        tvTester.setText(String.valueOf(MainActivity.totalHours));
        Log.i(TAG, String.valueOf(MainActivity.totalHours));
        return view;
    }

    public static Frag1 newInstance() {
        return new Frag1();
    }
}