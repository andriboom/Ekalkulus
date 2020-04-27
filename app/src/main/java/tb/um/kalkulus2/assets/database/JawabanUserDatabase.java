package tb.um.kalkulus2.assets.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import tb.um.kalkulus2.assets.AppData;
import tb.um.kalkulus2.assets.database.dao.JawabanUserDAO;
import tb.um.kalkulus2.assets.database.model.TableJawaban;
import tb.um.kalkulus2.assets.database.model.TableJawabanUser;

@Database(entities = {TableJawabanUser.class},version = AppData.DATABASE_VERSION)
public abstract class JawabanUserDatabase extends RoomDatabase {
    public abstract JawabanUserDAO jawabanUserDAO();
}
