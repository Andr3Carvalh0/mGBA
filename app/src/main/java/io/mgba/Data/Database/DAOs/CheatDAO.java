package io.mgba.Data.Database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import java.io.File;
import java.util.List;
import io.mgba.Data.Database.Cheat;

@Dao
public interface CheatDAO {

    @Query("SELECT * FROM Cheats WHERE idFK = :key")
    List<Cheat> getGamesCheats(File key);

    @Query("DELETE FROM Cheats WHERE id = :key")
    @Transaction
    void deleteCheat(int key);

    @Insert
    @Transaction
    void insertCheat(Cheat cheat);

}
