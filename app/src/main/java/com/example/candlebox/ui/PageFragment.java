package com.example.candlebox.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.candlebox.R;

//fragment stats do not update properly after adding stats without this class
public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
}