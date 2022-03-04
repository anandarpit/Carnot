package com.example.carnot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.carnot.retrofit.GovDataApi;
import com.example.carnot.retrofit.models.GovDataModel;
import com.example.carnot.retrofit.models.Record;
import com.example.carnot.room.DataModel;
import com.example.carnot.room.MyDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textFetched;
    private Button button, button2;
    private Call<GovDataModel> call;
    private EditText state;

    private Retrofit retrofit;
    MyDatabase database, db;
//    private String SORT = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        text = findViewById(R.id.text);
        textFetched = findViewById(R.id.textFetched);
        button = findViewById(R.id.fetchButton);
        button2 = findViewById(R.id.fetchButton2);
        state = findViewById(R.id.stateField);
//        village = findViewById(R.id.village);
//        district = findViewById(R.id.district);
//        radioGroup = findViewById(R.id.radioGroup);


        database = db.getDatabase(getApplicationContext());

        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        button.setOnClickListener(view -> {
            if(!state.getText().toString().isEmpty()){
                fetchData();
            }
            else{
                textFetched.setText("State cannot be empty");
            }
        });

        button2.setOnClickListener(view -> {
            changeActivity();
        });
    }

    private void changeActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(intent);
    }

    private void fetchData() {
        call = retrofit.create(GovDataApi.class).getData(
                Constants.API_KEY,
                Constants.FORMAT,
                state.getText().toString(),
                Constants.API_OFFSET,
                Constants.API_LIMIT);
        call.enqueue(new Callback<GovDataModel>() {
            @Override
            public void onResponse(Call<GovDataModel> call, Response<GovDataModel> response) {
                if(!response.isSuccessful()){
                    textFetched.setText("Code: " + response.code());
                    return;
                }
                saveData(response.body().getRecord());
            }

            @Override
            public void onFailure(Call<GovDataModel> call, Throwable t) {
                textFetched.setText(t.getMessage());
            }
        });
    }

    private void saveData(List<Record> records) {
        Log.i(Constants.TAG, "Records Count: "+ records.size());
        List<DataModel> lisData = new ArrayList<DataModel>();
        for(Record rec : records) {
            DataModel dataModel = new DataModel(
                    rec.getState(),
                    rec.getDistrict(),
                    rec.getMarket(),
                    rec.getCommodity(),
                    rec.getVariety(),
                    rec.getArrival_date(),
                    rec.getMin_price(),
                    rec.getMax_price(),
                    rec.getModal_price()
            );
            lisData.add(dataModel);
        }
        Log.i(Constants.TAG, "Count: "+ lisData.size());
        AsyncTask.execute(() ->{
            database.getDao().deleteAllData();
            database.getDao().insert(lisData);
            changeActivity();});
    }
}