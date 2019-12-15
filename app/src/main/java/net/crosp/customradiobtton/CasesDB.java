package net.crosp.customradiobtton;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.PrimaryKey;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities =
        {
        Cases.class,
        ResultsForCase.class,
        SignsForCase.class
        } , version = 1)
@TypeConverters(DateConverter.class)
public abstract class CasesDB extends RoomDatabase {
    CasesDBDAO mCasesDBDAO;

    public abstract CasesDBDAO getmCasesDBDAO();

}
