package com.example.mainproject.Navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mainproject.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Aboutus extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aboutus, container, false);
        CircleImageView github = view.findViewById(R.id.github);
        CircleImageView linkedin = view.findViewById(R.id.linkin);
        CircleImageView insta = view.findViewById(R.id.instagram);


        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://github.com/Priyanshii11");
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.linkedin.com/in/priyanshii-khare-984b95250");

            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://instagram.com/Priyanshii.11");
            }
        });

        return view;
    }


    private void gotoUrl(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
    }
