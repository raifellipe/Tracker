package com.raiborges.teste19;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TabTrack.class}, version = 1, exportSchema = false)
public abstract class AppDb extends RoomDatabase {

    public abstract TabTrackDao tabTrackDao();

}
