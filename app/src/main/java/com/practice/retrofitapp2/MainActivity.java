package com.practice.retrofitapp2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.practice.retrofitapp2.api.gitapi;
import com.practice.retrofitapp2.model.gitmodel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{
    Button click;
    TextView tv;
    EditText edit_user;
    ProgressBar pbar;
    String API = "https://api.github.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        click = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.textView);
        edit_user = (EditText) findViewById(R.id.edit_text);
        pbar = (ProgressBar) findViewById(R.id.progressBar);
        pbar.setVisibility(View.INVISIBLE);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String user = edit_user.getText().toString();
                pbar.setVisibility(View.VISIBLE);

                //Retrofit section start from here...
                Retrofit restAdapter = new Retrofit.Builder()
                        .baseUrl(API)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();                                        //create an adapter for retrofit with base url

                gitapi gitService = restAdapter.create(gitapi.class);                            //creating a service for adapter with our GET class

                //Now ,we need to call for response
                //Retrofit using gson for JSON-POJO conversion

                /*git.getFeed(user,new Callback<gitmodel>()
                {
                    @Override
                    public void success(gitmodel gitmodel, Response response) {
                        //we get json object from github server to our POJO or model class

                        tv.setText("Github Name :"+gitmodel.getName()+"\nWebsite :"+gitmodel.getBlog()+"\nCompany Name :" + gitmodel.getCompany());

                        pbar.setVisibility(View.INVISIBLE);                               //disable progressbar
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        tv.setText(error.getMessage());
                        pbar.setVisibility(View.INVISIBLE);                               //disable progressbar
                    }
                });
                */

                Call<gitmodel> call = gitService.getFeed(user);
                call.enqueue(new Callback<gitmodel>()
                {
                    @Override
                    public void onResponse(Call<gitmodel> call, Response<gitmodel> response) {

                        try
                        {


                            gitmodel gitModel = response.body();

                            tv.setText("Github Name :" + gitModel.getName() + "\nWebsite :" + gitModel.getBlog() + "\nCompany Name :" + gitModel.getCompany());

                            pbar.setVisibility(View.INVISIBLE);                               //disable progressbar
                            if(response.errorBody()!= null)
                            {
                                Log.e("Error Body Not Null","f");
                                response.errorBody().string();
                            }

                        }
                        catch (IOException e)
                        {


                            e.printStackTrace();
                            Log.e("ERROR IN RES","error");
                        }

                    }

                    @Override
                    public void onFailure(Call<gitmodel> call, Throwable t)
                    {
                        Log.e("FAILURE IN RES","error");


                    }
                });

            }
        });










    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
