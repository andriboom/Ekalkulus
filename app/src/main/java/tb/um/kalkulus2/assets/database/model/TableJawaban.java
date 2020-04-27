package tb.um.kalkulus2.assets.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TableJawaban")
public class TableJawaban {
    @PrimaryKey()
    private int idJawaban;
    private String jawaban;
    private int bankSoalId;
    private boolean statusJawaban;

    public TableJawaban(int idJawaban, String jawaban, int bankSoalId, boolean statusJawaban) {
        this.idJawaban = idJawaban;
        this.jawaban = jawaban;
        this.bankSoalId = bankSoalId;
        this.statusJawaban = statusJawaban;
    }

    public int getIdJawaban() {
        return idJawaban;
    }

    public void setIdJawaban(int idJawaban) {
        this.idJawaban = idJawaban;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public int getBankSoalId() {
        return bankSoalId;
    }

    public void setBankSoalId(int bankSoalId) {
        this.bankSoalId = bankSoalId;
    }

    public boolean isStatusJawaban() {
        return statusJawaban;
    }

    public void setStatusJawaban(boolean statusJawaban) {
        this.statusJawaban = statusJawaban;
    }
}
