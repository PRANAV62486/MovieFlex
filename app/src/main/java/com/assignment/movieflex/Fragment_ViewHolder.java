package com.assignment.movieflex;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.assignment.movieflex.API_Manager.API_CALL;
import com.assignment.movieflex.API_Manager.Model;
import com.assignment.movieflex.Services.RecyclerViewAdapter;
import com.assignment.movieflex.databinding.FragmentViewHolderBinding;

import java.util.ArrayList;
import java.util.List;

/*This is a common fragment for both "now playing" and "top rated" movie
 * Yes, you are right, we can create a separate fragments but since everything is same in both case
 * therefore It is good practice to have only one fragment and using its instances with different data
 * (Helps to decrease apk size)*/

public class Fragment_ViewHolder extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private String mURL;

    public Fragment_ViewHolder() {
        // Required empty public constructor
    }

    public static Fragment_ViewHolder newInstance(String mUrl) {
        Fragment_ViewHolder fragment = new Fragment_ViewHolder();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mURL = getArguments().getString(ARG_PARAM1);
        }
    }

    FragmentViewHolderBinding binding;
    List<Model> mList;
    RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewHolderBinding.inflate(inflater, container, false);

        //call api and display values in recycler view
        API_Handling();

        //pull to refresh
        binding.swipeRefresh.setOnRefreshListener(() -> {
            API_Handling();
            binding.swipeRefresh.setRefreshing(false);
        });

        return binding.getRoot();
    }

    private void API_Handling() {
        API_CALL call = new API_CALL(getContext());

        call.getData(mURL, new API_CALL.callback_result() {
            @Override
            public void onResponse(List<Model> list) {
                mList = list;

                //adding animation to recycler view
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_up_to_down);
                binding.NPRecyclerView.setLayoutAnimation(animation);

                adapter = new RecyclerViewAdapter(getContext(), list);
                binding.NPRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.NPRecyclerView.setAdapter(adapter);

                //Initialing searching feature only after successful completion of api call
                searchButton();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchButton() {
        //Search button
        binding.NPSearch.search.setOnTouchListener((view, x) -> {
            binding.NPSearch.cancel.setVisibility(View.VISIBLE);
            return false;
        });

        binding.NPSearch.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        binding.NPSearch.cancel.setOnClickListener(view -> {
            adapter.updateList(mList);
            binding.NPSearch.cancel.setVisibility(View.GONE);
            binding.NPSearch.search.setText("");
            binding.NPSearch.search.clearFocus();
        });
    }

    private void filter(String text) {
        ArrayList<Model> filteredList = new ArrayList<>();
        for (Model m : mList) {
            if (m.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(m);
            }
        }
        adapter.updateList(filteredList);
    }

}