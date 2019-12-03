package net.crosp.customradiobtton;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserInfoDBDao {

    @Query("SELECT * FROM UserInfo")
    List<UserInfo> getUserInfo();

    @Insert
    void InsertUserInfo(UserInfo ui);
}
