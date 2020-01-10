package com.illicitintelligence.movietime.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.illicitintelligence.movietime.R;
import com.illicitintelligence.movietime.adapter.RecyclerAdapter;
import com.illicitintelligence.movietime.model.Movie;
import com.illicitintelligence.movietime.model.RepoResult;
import com.illicitintelligence.movietime.modelview.ViewModel;
import com.illicitintelligence.movietime.network.RetrofitInstance;
import com.illicitintelligence.movietime.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.MovieDetailInterface {

    @BindView(R.id.send_button)
    Button sendButton;
    @BindView(R.id.my_search_edittext)
    EditText editText;
    @BindView(R.id.movie_list_recyclerview)
    RecyclerView recyclerView;

    Handler handler;

    RetrofitInstance instance;

    List<Movie> movies = new ArrayList<>();
    RecyclerAdapter adapter;
    Fragment fragment = new MovieDetailFragment();
    ViewModel viewModel;
    Observer<RepoResult> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_trans));

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        observer = new Observer<RepoResult>() {
            @Override
            public void onChanged(RepoResult repoResult) {
                movies = repoResult.getMovies();
                recyclerViewSetup();
            }
        };
        viewModel.getLiveData().observe(this, observer);

        handler = new Handler();

        instance = new RetrofitInstance();

        adapter = new RecyclerAdapter(this.getApplicationContext(),movies,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void recyclerViewSetup(){
        adapter = new RecyclerAdapter(this.getApplicationContext(),movies,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onClick(View view){
        String toSearch = editText.getText().toString().trim();
        if(toSearch.length()!=0) {
            viewModel.getRepo(toSearch);
        }
    }

    @Override
    public void prepareMovieDetail(Movie movie, ImageView view) {
        Log.d(Constants.TAG_X, "transitionImage: "+movie.getTitle());
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.RETRIEVE_MOVIE_KEY,movie);
        bundle.putString("test","test success");
        Log.d(Constants.TAG_X, "transitionImage: "+view.getTransitionName());
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .addToBackStack(fragment.getTag())
                .addSharedElement(view,Constants.TRANSITION_NAME_BASE+movie.getId())
                .add(R.id.frame_layout,fragment)
                .commit();
    }
}
