package com.raiborges.teste19;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TabTrackDao {


    @Query("SELECT * FROM TabTrack")
    List<TabTrack> getAll();

    @Insert
    void insertAll(TabTrack... tabTrack);

    @Delete
    void delete(TabTrack tabTrack);

    @Update
    void updateUsers(TabTrack... tabTracks);


}
