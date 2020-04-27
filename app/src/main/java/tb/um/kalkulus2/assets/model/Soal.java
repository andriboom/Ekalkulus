package tb.um.kalkulus2.assets.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Soal {
    @SerializedName("id_bank_soal")
    private String idBankSoal;
    @SerializedName("butir_soal")
    private String butirSoal;
    @SerializedName("jawaban")
    private List<Jawaban> jawaban;

    public String getIdBankSoal() {
        return idBankSoal;
    }

    public void setIdBankSoal(String idBankSoal) {
        this.idBankSoal = idBankSoal;
    }

    public String getButirSoal() {
        return butirSoal;
    }

    public void setButirSoal(String butirSoal) {
        this.butirSoal = butirSoal;
    }

    public List<Jawaban> getJawaban() {
        return jawaban;
    }

    public void setJawaban(List<Jawaban> jawaban) {
        this.jawaban = jawaban;
    }
}
