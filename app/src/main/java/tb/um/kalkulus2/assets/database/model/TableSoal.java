package tb.um.kalkulus2.assets.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TableSoal")
public class TableSoal {
    @PrimaryKey()
    private int idTeori;
    private String butir_soal;
    private String type;
    //1 : kalkulus, 2: orkom


    public TableSoal(int idTeori, String butir_soal, String type) {
        this.idTeori = idTeori;
        this.butir_soal = butir_soal;
        this.type = type;
    }

    public int getIdTeori() {
        return idTeori;
    }

    public void setIdTeori(int idTeori) {
        this.idTeori = idTeori;
    }

    public String getButir_soal() {
        return butir_soal;
    }

    public void setButir_soal(String butir_soal) {
        this.butir_soal = butir_soal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
