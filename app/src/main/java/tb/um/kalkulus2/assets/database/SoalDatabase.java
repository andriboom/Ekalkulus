package tb.um.kalkulus2.assets.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import tb.um.kalkulus2.assets.AppData;
import tb.um.kalkulus2.assets.database.dao.SoalDAO;
import tb.um.kalkulus2.assets.database.model.TableSoal;

@Database(entities = {TableSoal.class},version = AppData.DATABASE_VERSION)
public abstract class SoalDatabase extends RoomDatabase {
    public abstract SoalDAO soalDAO();
}
