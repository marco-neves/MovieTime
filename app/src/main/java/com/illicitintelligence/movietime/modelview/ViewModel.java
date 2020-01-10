package com.illicitintelligence.movietime.modelview;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.illicitintelligence.movietime.model.RepoResult;
import com.illicitintelligence.movietime.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.illicitintelligence.movietime.util.Constants.TAG_Error;


public class ViewModel extends AndroidViewModel {

    private MutableLiveData<RepoResult> liveData;
    private RetrofitInstance instance = new RetrofitInstance();

    public ViewModel(@NonNull Application application) {
        super(application);
        liveData = new MutableLiveData<>();
    }

    public void getRepo(String toSearch){
        instance.getMovies(toSearch).enqueue(new Callback<RepoResult>() {
            @Override
            public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
                assert response.body() != null;
                liveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RepoResult> call, Throwable t) {
                Log.d(TAG_Error, "onFailure: "+t.getMessage());
                Log.d(TAG_Error, "onFailure: "+call.toString());
            }
        });
    }
    public MutableLiveData<RepoResult> getLiveData(){
        return liveData;
    }
}
