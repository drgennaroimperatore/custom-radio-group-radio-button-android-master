package net.crosp.customradiobtton;

import androidx.annotation.FractionRes;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Cases.class, parentColumns = "ID", childColumns = "CaseID")})
public class ResultsForCase {
    @PrimaryKey
    public int ID;
    public int CaseID;
}
