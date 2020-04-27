package tb.um.kalkulus2.assets.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import tb.um.kalkulus2.assets.database.model.TableJawabanUser;

@Dao
public interface JawabanUserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJawabanTeoriUser(TableJawabanUser tableJawabanUser);
    @Query("UPDATE TableJawabanUser SET jawabanUser=:jawaban WHERE soalId=:soal")
    void updateJawabanTeoriUser(int jawaban,int soal);
    @Query("SELECT * FROM TableJawabanUser WHERE soalId=:id_soal")
    TableJawabanUser getJawabanUser(int id_soal);
    @Query("SELECT COUNT(*) FROM TableJawabanUser")
    int getTotalJawabanTeoriUser();
    @Query("SELECT * FROM TableJawabanUser")
    List<TableJawabanUser> getAllJawabanUser();
    @Query("DELETE FROM TableJawabanUser")
    void deleteAllJawabanTeoriUser();
}
