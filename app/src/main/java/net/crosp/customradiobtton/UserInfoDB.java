package net.crosp.customradiobtton;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = UserInfo.class, version = 1)
public abstract class UserInfoDB extends RoomDatabase {
    UserInfoDBDao mDao;

    private static UserInfoDB mInstance;

    public static UserInfoDB getInstance (Context context)
    {
        if(mInstance==null)
        {
            mInstance = Room.databaseBuilder(context, UserInfoDB.class, "UserInfoDB").allowMainThreadQueries().build();
        }

        return mInstance;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

    public abstract UserInfoDBDao getDao();


}
