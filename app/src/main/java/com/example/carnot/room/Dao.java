package com.example.carnot.room;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insert(List<DataModel> listDataModel);

    @Update
    void update(DataModel model);

    @Delete
    void delete(DataModel model);

    @Query("DELETE FROM data_model")
    void deleteAllData();

    @Query("SELECT * FROM data_model")
    List<DataModel> getAllData();

    @Query("SELECT * FROM data_model")
    List<DataModel> getCustomData();

}
