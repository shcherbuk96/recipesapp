package com.example.stanislau_bushuk.foodhealth.presentantion.searchPresentation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.stanislau_bushuk.foodhealth.R;
import com.example.stanislau_bushuk.foodhealth.model.pojo.Hits;
import com.example.stanislau_bushuk.foodhealth.presentantion.searchPresentation.adapter.RecyclerAdapter;
import com.example.stanislau_bushuk.foodhealth.presentantion.searchPresentation.presenters.SearchPresenter;
import com.example.stanislau_bushuk.foodhealth.presentantion.searchPresentation.view.ViewSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;


public class SearchFragment extends MvpAppCompatFragment implements ViewSearch {

    @BindView(R.id.search_progressbar_progressbar)
    ProgressBar searchProgressBar;

    @BindView(R.id.search_search_view)
    SearchView searchView;

    @BindView(R.id.search_list_recycler_view)
    RecyclerView listRecyclerView;

    @BindView(R.id.search_random_text_view)
    TextView searchText;

    @InjectPresenter
    SearchPresenter presenter;

    private RecyclerAdapter recyclerAdapter;
    private boolean readyScroll = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        final List<Hits> hitsList = new ArrayList<>();
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listRecyclerView.setLayoutManager(mLayoutManager);
        recyclerAdapter = new RecyclerAdapter(hitsList, getContext());
        listRecyclerView.setAdapter(recyclerAdapter);
        presenter.searchObservable(searchView);
        final Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(final ObservableEmitter emitter) throws Exception {
                final RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        if(((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition()+1==recyclerView.getAdapter().getItemCount())
                            emitter.onNext(recyclerView.getAdapter().getItemCount());
                    }

                };
                listRecyclerView.addOnScrollListener(scrollListener);
            }
        });
        observable.debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(final Disposable d) {

            }

            @Override
            public void onNext(final Integer integer) {
                Timber.e("pos "+integer);
            }

            @Override
            public void onError(final Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });



        listRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                if (readyScroll) {
                    presenter.checkUpdate((recyclerView.getLayoutManager()).getItemCount(),
                            (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition()), String.valueOf(searchText.getText()));
                }
            }
        });
    }


    @Override
    public void showList(final List<Hits> hitsList) {
        recyclerAdapter.updateAdapter(hitsList);
    }

    @Override
    public void updateList(final List<Hits> hitsList) {
        recyclerAdapter.updateList(hitsList);
        readyScroll = true;
    }

    @Override
    public void progressBarVisible(final int visible) {
        searchProgressBar.setVisibility(visible);
    }

    @Override
    public void setSearchText(final String text) {
        searchText.setText(text);
    }

    @Override
    public void setReadyScroll() {
        readyScroll = false;
    }

    @Override
    public void setSnackBar() {
        Toast.makeText(getActivity(), getResources().getText(R.string.error_connection_api), Toast.LENGTH_LONG).show();
    }
}
