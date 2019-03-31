package com.example.indreshprakash.testing.backend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.indreshprakash.testing.R;
import com.example.indreshprakash.testing.nearby.hospitals;
import com.example.indreshprakash.testing.nearby.nearatms;
import com.example.indreshprakash.testing.nearby.petrol;
import com.example.indreshprakash.testing.nearby.repair;
import com.example.indreshprakash.testing.nearby.restaurants;
import com.example.indreshprakash.testing.nearby.shop;
import com.example.indreshprakash.testing.nearby.shopmall;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    private BottomSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet, container, false);
        ImageView hospimg = v.findViewById(R.id.hospbtn);
        ImageView restimg = v.findViewById(R.id.restbtn);
        ImageView atmimg = v.findViewById(R.id.atmbtn);
        ImageView gasimg = v.findViewById(R.id.gasbtn);
        ImageView signalimg = v.findViewById(R.id.signalbtn);
        ImageView repairimg = v.findViewById(R.id.repairbtn);

        Intent i = new Intent(getContext(), hospitals.class);
        Intent j = new Intent(getContext(), restaurants.class);
        Intent k = new Intent(getContext(), nearatms.class);
        Intent l = new Intent(getContext(), petrol.class);
        Intent m = new Intent(getContext(), repair.class);
        Intent n = new Intent(getContext(), shopmall.class);


        hospimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked();
                startActivity(i);

            }
        });

        restimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked();
                startActivity(j);
            }
        });
        atmimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked();
                startActivity(k);

            }
        });

        gasimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked();
                startActivity(l);
            }
        });
        signalimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked();
                startActivity(n);

            }
        });
        repairimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked();
                startActivity(m);

            }
        });
        return  v;

    }

    public interface BottomSheetListener {
        void onButtonClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
            +"must implement BottomSheetListener");

        }
    }
}