package com.illicitintelligence.movietime.network;

import com.illicitintelligence.movietime.model.RepoResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

 //   https://api.themoviedb.org/3/search/movie
 //   ?api_key=5520ed41729e7e1df80b88f1ba50d622
 //   &language=en-US
 //   &query=fight
 //   &page=1
 //   &include_adult=false
    @GET("/3/search/movie")
    Call<RepoResult> getMovies(
            @Query("api_key") String apiKey,
            @Query("query") String keyWords);

}
