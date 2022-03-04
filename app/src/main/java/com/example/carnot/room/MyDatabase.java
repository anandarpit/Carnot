package com.example.carnot.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Database(entities = {DataModel.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

        private static MyDatabase myDatabase;
        public abstract Dao getDao();
        public static synchronized MyDatabase getDatabase(Context context)
        {
             if(myDatabase == null){
                myDatabase =  Room.databaseBuilder(context, MyDatabase.class, "data_model" )
                        .fallbackToDestructiveMigration()
                        .build();
            }
             return myDatabase;
        }

    public List<DataModel> getAllData() throws ExecutionException, InterruptedException {
        return new GetUsersAsyncTask().execute().get();
    }


    private class GetUsersAsyncTask extends AsyncTask<Void, Void,List<DataModel>>
    {
        @Override
        protected List<DataModel> doInBackground(Void... url) {
            return myDatabase.getDao().getAllData();
        }
    }

}