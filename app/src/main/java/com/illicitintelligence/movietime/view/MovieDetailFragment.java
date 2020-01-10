package com.illicitintelligence.movietime.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.illicitintelligence.movietime.R;
import com.illicitintelligence.movietime.model.Movie;
import com.illicitintelligence.movietime.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment {
    @BindView(R.id.movie_icon_imageview)
    ImageView movieIconImageView;
    @BindView(R.id.movie_title_textview)
    TextView movieTitleTextView;
    @BindView(R.id.movie_description_textview)
    TextView movieDescriptionTextView;
    @BindView(R.id.movie_release_date_textview)
    TextView movieDateTextView;
    @BindView(R.id.movie_rating_textview)
    TextView movieRatingTextView;
    @BindView(R.id.base_image_imageview)
    ImageView movieBaseImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_detail_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        postponeEnterTransition();
        Bundle args = this.getArguments();
        assert args != null;
        Movie movie = args.getParcelable(Constants.RETRIEVE_MOVIE_KEY);
        assert movie != null;
        Glide.with(this)
                .load(Constants.BASE_URL_IMAGE+movie.getPosterPath())
                .dontAnimate()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(movieIconImageView);
        movieTitleTextView.setText(movie.getTitle());
        movieDescriptionTextView.setText(movie.getOverview());
        movieDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
        movieDateTextView.setText(movie.getReleaseDate());
        String ratingText = getString(R.string.rating_label) + ((movie.getVoteAverage() / 10) * 100) +"%";
        movieRatingTextView.setText(ratingText);
        Glide.with(this)
                .load(Constants.BASE_URL_IMAGE+movie.getBackdropPath())
                .into(movieBaseImageView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        assert getFragmentManager() != null;
        getFragmentManager().popBackStack();
    }
}
