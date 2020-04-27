package tb.um.kalkulus2.assets.database.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TableJawabanUser")
public class TableJawabanUser {
    @PrimaryKey(autoGenerate = true)
    private int idJawabanUser;
    private int soalId;
    private int jawabanUser;

    public TableJawabanUser(int soalId, int jawabanUser) {
        this.soalId = soalId;
        this.jawabanUser = jawabanUser;
    }

    public int getIdJawabanUser() {
        return idJawabanUser;
    }

    public void setIdJawabanUser(int idJawabanUser) {
        this.idJawabanUser = idJawabanUser;
    }

    public int getSoalId() {
        return soalId;
    }

    public void setSoalId(int soalId) {
        this.soalId = soalId;
    }

    public int getJawabanUser() {
        return jawabanUser;
    }

    public void setJawabanUser(int jawabanUser) {
        this.jawabanUser = jawabanUser;
    }
}
