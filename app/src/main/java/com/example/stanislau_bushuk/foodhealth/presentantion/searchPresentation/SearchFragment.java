package com.example.stanislau_bushuk.foodhealth.presentantion.searchPresentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.stanislau_bushuk.foodhealth.R;
import com.example.stanislau_bushuk.foodhealth.presentantion.searchPresentation.adapter.RecyclerAdapter;
import com.example.stanislau_bushuk.foodhealth.model.pojo.Hits;
import com.example.stanislau_bushuk.foodhealth.presentantion.searchPresentation.presenters.SearchPresenter;
import com.example.stanislau_bushuk.foodhealth.presentantion.searchPresentation.view.ViewSearch;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchFragment extends MvpAppCompatFragment implements ViewSearch {
    @BindView(R.id.search_list_recycler_view)
    RecyclerView listRecyclerView;

    @BindView(R.id.search_progressbar_progressbar)
    ProgressBar searchProgressBar;

    @InjectPresenter
    SearchPresenter presenter;
    RecyclerAdapter recyclerAdapter;
    private ArrayList<Hits> hitsList;

    public SearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        hitsList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(hitsList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listRecyclerView.setLayoutManager(mLayoutManager);
        listRecyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        return view;
    }


    @Override
    public void showList(List<Hits> hitsList) {
        if (hitsList != null) {
            this.hitsList.clear();
            this.hitsList.addAll(hitsList);
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgressBar() {
        searchProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeProgressBar() {
        searchProgressBar.setVisibility(View.INVISIBLE);
    }


}
