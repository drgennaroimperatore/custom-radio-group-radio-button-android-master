package net.crosp.customradiobtton;

import androidx.room.Dao;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface ADDBDAO {
    @Query("SELECT Signs.Id, Signs.Probability, Signs.Name, Signs.Type_Of_Value FROM Signs, SignCores, Animals WHERE Animals.Id = :animalID AND SignCores.AnimalID = Animals.Id AND SignCores.SignID = Signs.Id")
    public List<Signs> getAllSignsForAnimal(int animalID);

    @Query("DELETE FROM Animals")
    public void deleteAllAnimals();

    @Query("DELETE FROM Diseases")
    public void deleteAllDiseases();

    @Query("DELETE FROM Signs")
    public void deleteAllSigns();

    @Query("DELETE FROM Likelihoods")
    public void deleteAllLikelihoods();

    @Query("DELETE FROM SignCores")
    public void deleteAllSignCores();

    @Query("DELETE FROM PriorsDiseases")
    public void deleteAllPriorsDiseases();

    @Query("SELECT Name FROM Animals WHERE Id =:Id")
    public List <String> getAnimalNameFromID(int Id);

    @Query("SELECT Animals.Id FROM Animals WHERE Animals.Name=:name")
    public List <Integer> getAnimalIDFromName(String name);

    @Query("SELECT * FROM PriorsDiseases WHERE PriorsDiseases.AnimalID = :animalID AND PriorsDiseases.DiseaseID = :diseaseID ")
    public List<PriorsDiseases> getPriorForDisease(int animalID, int diseaseID);

    @Query("SELECT * FROM Diseases")
    List<Diseases> getAllDiseases();


    @Query("SELECT Animals.Id FROM Animals")
    public List<Integer> TestAnimalsTable();

    @Query("SELECT Likelihoods.Id FROM Likelihoods")
    public List<Integer> TestLikelihoodsTable();

    @Query("SELECT Diseases.Name FROM Diseases")
    public List<String> TestDiseasesTable();

    @Query("SELECT PriorsDiseases.Id FROM PriorsDiseases")
    public List<Integer> TestPriorsDiseasesTable();

    @Query("SELECT Signs.Name FROM Signs")
    public List<String> TestSignsTable();

    @Query("SELECT SignCores.Id FROM SignCores")
    public List<Integer> TestSignCoresTable();


    @RawQuery
    public int PopulateAnimals(SupportSQLiteQuery query);


    @Query("SELECT * From Likelihoods WHERE Likelihoods.AnimalId=:animalID AND Likelihoods.SignId = :signID AND Likelihoods.DiseaseId =:diseaseID")
    List<Likelihoods> getLikelihoodValue(int animalID, int signID, int diseaseID);
}
