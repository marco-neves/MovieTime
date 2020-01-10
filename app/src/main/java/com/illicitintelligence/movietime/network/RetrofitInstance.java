package com.illicitintelligence.movietime.network;

import com.illicitintelligence.movietime.model.RepoResult;
import com.illicitintelligence.movietime.util.Constants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private MovieService movieServiceInstance;

    public RetrofitInstance() {
        movieServiceInstance = getInstanceOfMoviesDBRetrofit(getInstance());
    }

    MovieService getInstanceOfMoviesDBRetrofit(Retrofit retrofit){
        return retrofit.create(MovieService.class);
    }

    private Retrofit getInstance(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Call<RepoResult> getMovies(String toSearch){
        return movieServiceInstance.getMovies(Constants.API_KEY,toSearch);
    }

}
