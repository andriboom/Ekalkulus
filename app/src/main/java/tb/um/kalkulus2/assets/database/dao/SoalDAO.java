package tb.um.kalkulus2.assets.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import tb.um.kalkulus2.assets.database.model.TableSoal;

@Dao
public interface SoalDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTeori(TableSoal tableTeori);
    @Query("SELECT COUNT(*) FROM TableSoal WHERE type=:type")
    int getTotal(String type);
    @Query("SELECT * FROM TableSoal WHERE type=:type")
    List<TableSoal> getAllTeori(String type);
    @Query("DELETE FROM TableSoal WHERE type=:type")
    void deleteAllTeori(String type);
}
