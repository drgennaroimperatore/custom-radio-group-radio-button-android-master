package net.crosp.customradiobtton;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CasesDBDAO {

    @Insert
    public void insertCase(Cases c);

    @Delete
    public void deleteCase(Cases c);

    @Insert
    public void insertResultsForCase(ResultsForCase rfc);

    @Delete
    public void deleteResultsForCase(ResultsForCase rfc);

    @Insert
    public void insertSignsForCase(SignsForCase sfc);

    @Delete
    public void deleteSignsForCase(SignsForCase sfc);

    @Query("SELECT * FROM Cases")
    public List<Cases> getAllCases();

    @Query("DELETE FROM Cases")
    public void DeleteAllCases();

    @Query("SELECT * FROM ResultsForCase WHERE CaseID = :caseID")
    public List<ResultsForCase> getAllResultsForCase(int caseID);

    @Query("SELECT * FROM SignsForCase WHERE CaseID = :caseID")
    public List<SignsForCase> getAllSignsForCase(int caseID);

    @Query("SELECT * FROM Cases WHERE AnimalID = :animalID")
    public List<Cases> getAllCasesForSpecificSpecies(int animalID);

    //To do :
    // date based queries,
    // animal based queries
    // disease based queries etc...
}
