package net.crosp.customradiobtton;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Cases.class, parentColumns = "ID", childColumns = "CaseID")})

public class SignsForCase {
    @PrimaryKey (autoGenerate = true)
    public int ID;
    public int CaseID;
}
