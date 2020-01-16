package net.crosp.customradiobtton;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities =
        {
        Cases.class,
        ResultsForCase.class,
        SignsForCase.class
        } , version = 3)
@TypeConverters({DateConverter.class, SignPresenceConverter.class})
public abstract class CasesDB extends RoomDatabase {
    CasesDBDAO mCasesDBDAO;

    private static CasesDB mInstance;

    public abstract CasesDBDAO getmCasesDBDAO();

    public static CasesDB getInstance(Context context)
    {
        if(mInstance==null)
            mInstance = Room.databaseBuilder(context, CasesDB.class, "CasesDB").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        return mInstance;
    }

}
