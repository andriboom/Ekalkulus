package tb.um.kalkulus2.assets.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import tb.um.kalkulus2.assets.database.model.TableJawaban;

@Dao
public interface JawabanDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDataTeori(TableJawaban tableJawabanTeori);
    @Query("SELECT * FROM TableJawaban WHERE bankSoalId=:idsoal")
    List<TableJawaban> getJawabanBySoal(int idsoal);
    @Query("SELECT * FROM TableJawaban WHERE bankSoalId=:idsoal and idJawaban=:idjawaban")
    TableJawaban getJawabanByidSoaldanJawaban(int idsoal,int idjawaban);
    @Query("DELETE FROM TableJawaban")
    void deleteJawabanTeori();
}
