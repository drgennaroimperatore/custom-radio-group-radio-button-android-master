package net.crosp.customradiobtton;

import androidx.room.Dao;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface ADDBDAO {
    @Query("SELECT Signs.Name FROM Signs, SignCores, Animals WHERE Animals.Id = :animalID AND SignCores.AnimalID = Animals.Id AND SignCores.SignID = Signs.Id")
    public List<String> getAllSignsForAnimal(int animalID);

    @Query("SELECT Name FROM Animals WHERE Id =:Id")
    public List <String> getAnimalNameFromID(int Id);

    @RawQuery
    public int PopulateAnimals(SupportSQLiteQuery query);




}
