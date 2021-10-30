package com.assignment.movieflex;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.assignment.movieflex.databinding.ActivityDetailsBinding;
import com.bumptech.glide.Glide;

/*Movie details activity*/

public class Details extends AppCompatActivity {

    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.detToolbar);

        //Reading & displaying values from intent
        String bp = getIntent().getStringExtra("bp");
        Glide.with(this).load(getResources().getString(R.string.backdrop_prefix_url) + bp).into(binding.bgImg);
        binding.detCard.dTitle.setText(getIntent().getStringExtra("title"));
        binding.detCard.dVote.setText(getIntent().getStringExtra("vote"));
        binding.detCard.dDate.setText(getIntent().getStringExtra("date"));
        binding.detCard.dOve.setText(getIntent().getStringExtra("overview"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}