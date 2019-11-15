package net.crosp.customradiobtton;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;


@Database(entities =
        {       Animals.class,
                Diseases.class,
                Likelihoods.class,
                PriorsDiseases.class,
                SignCores.class,
                Signs.class}, version = 1, exportSchema = false)
public abstract class ADDB extends RoomDatabase {
   ADDBDAO mADDBDAO;

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

    public abstract ADDBDAO getADDBDAO();


}
