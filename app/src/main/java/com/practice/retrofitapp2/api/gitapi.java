package com.practice.retrofitapp2.api;


import com.practice.retrofitapp2.model.gitmodel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Himanshu Sagar on 31-05-2016.
 */
public interface gitapi
{
    @GET("users/{user}")      //here is the other url part.best way is to start using /

    //public void getFeed(@Path("user") String user, Callback<gitmodel> response);     //string user is for passing values from edittext for eg: user=basil2style,google

    //response is the response from the server which is now in the POJO
    Call<gitmodel> getFeed(@Path("user") String user);

}

