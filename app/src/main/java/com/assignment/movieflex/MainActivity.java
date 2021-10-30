package com.assignment.movieflex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.assignment.movieflex.Services.ViewPager2Adapter;
import com.assignment.movieflex.databinding.ActivityMainBinding;

/**
 * Written by Pranav on 31st of october 2021
 * Contact email: pranavsingh62486@gmail.com
 */

/*SPECIALITY:
 * 1. MATERIAL THEME
 * 2. JETPACK COMPOSE VIEW BINDING
 * 3. SMOOTH DELETE OPTION
 * 4. DARK THEME SUPPORT
 * 5. API CALL MANAGEMENT IN VERY EFFICIENT AND CLEAN WAY
 * 6. ANIMATED RECYCLER VIEW
 * 7. SEARCH FEATURE
 * 8. LATEST VIEW PAGER2
 * 9. EXACT SAME DESIGN
 * 10. ANDROID BEST PRACTICES
 * 11. SMOOTH TRANSITION BETWEEN FRAGMENTS
 * 12. PULL TO REFRESH
 * */

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Adding fragments into viewPager
        setupViewPager();

        //Displaying fragments a
        binding.bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_playing:
                    binding.viewpager2.setCurrentItem(0, true);
                    return true;
                case R.id.nav_top_rated:
                    binding.viewpager2.setCurrentItem(1, true);
                    return true;
            }
            return false;
        });

        //Dark Mode Management
        binding.hide.setOnClickListener(view -> {
            binding.mainLl.setVisibility(View.GONE);
        });
        binding.darkTheme.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(this, "Dark mode activated", Toast.LENGTH_SHORT).show();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Toast.makeText(this, "Dark mode deactivated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewPager() {
        Fragment_ViewHolder first_instance_nowPlaying = Fragment_ViewHolder.newInstance(getResources().getString(R.string.now_playing_url));
        Fragment_ViewHolder second_instance_TopRated = Fragment_ViewHolder.newInstance(getResources().getString(R.string.top_rated_url));

        ViewPager2Adapter adapter = new ViewPager2Adapter(getSupportFragmentManager(), getLifecycle());
        adapter.addFragment(first_instance_nowPlaying);
        adapter.addFragment(second_instance_TopRated);

        binding.viewpager2.setAdapter(adapter);
    }

}