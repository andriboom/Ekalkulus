package tb.um.kalkulus2.assets.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import tb.um.kalkulus2.assets.AppData;
import tb.um.kalkulus2.assets.database.dao.JawabanDAO;
import tb.um.kalkulus2.assets.database.model.TableJawaban;

@Database(entities = {TableJawaban.class}, version = AppData.DATABASE_VERSION)
public abstract class JawabanDatabase extends RoomDatabase {
    public abstract JawabanDAO jawabanDAO();
}
