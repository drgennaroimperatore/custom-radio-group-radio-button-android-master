package net.crosp.customradiobtton;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserInfo")
public class UserInfo {
    @PrimaryKey
    int Id;
    String UUID;
    String FirstName, SecondName;
    boolean AccountSetup;
}
