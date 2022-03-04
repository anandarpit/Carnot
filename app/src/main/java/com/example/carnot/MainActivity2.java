package com.example.carnot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.carnot.room.DataModel;
import com.example.carnot.room.MyDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity2 extends AppCompatActivity {

    private String SORT = null;

    private EditText village, district;
    private Button fetchData;
    private RadioButton radioPrice, radioDate;
    private RadioGroup radioGroup;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    MyDatabase database, db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.dataRecyclerView);

        village = findViewById(R.id.village);
        district = findViewById(R.id.district);
        fetchData = findViewById(R.id.getData);

        fetchData.setOnClickListener(view -> {
            database = db.getDatabase(getApplicationContext());
            try {
                List<DataModel> dataList=database.getAllData();
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter = new RecyclerViewAdapter(dataList);
                recyclerView.setAdapter(adapter);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radioDate:
                if (checked)
                    SORT = "DATE";
                break;
            case R.id.radioPrice:
                if (checked)
                    SORT = "PRICE";
                break;
        }
    }
    }
