package net.crosp.customradiobtton;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Cases
{
    @PrimaryKey
    public int ID;
    public String UserUUID;
    public int AnimalID;
    public int DiseasePredictedByAppID;
    public Date DateCaseLogged;
    public Date DateCaseObserved;
    public float LikelihoodDPbyApp;
    public int DiseaseChosenByUserID;
    public String Region,District,Woreda;
    public String Comments;

}
