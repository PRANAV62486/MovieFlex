package com.assignment.movieflex.Services;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/*View pager 2 adapter
 * View pager 2 helps us to have a smooth scrolling and custom adapter to add fragments*/
public class ViewPager2Adapter extends FragmentStateAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPager2Adapter(@NonNull FragmentManager fragmentManager, Lifecycle b) {
        super(fragmentManager, b);
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

}