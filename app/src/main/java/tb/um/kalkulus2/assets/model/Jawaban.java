package tb.um.kalkulus2.assets.model;

import com.google.gson.annotations.SerializedName;

public class Jawaban {
    @SerializedName("id_bank_jawaban")
    private String idBankjawaban;
    @SerializedName("jawaban")
    private String jawaban;
    @SerializedName("bank_soal_id")
    private String bankSoalId;
    @SerializedName("ket_jawaban") //o : salah dan 1: benar
    private String ket_jawaban;

    public String getIdBankjawaban() {
        return idBankjawaban;
    }

    public void setIdBankjawaban(String idBankjawaban) {
        this.idBankjawaban = idBankjawaban;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public String getBankSoalId() {
        return bankSoalId;
    }

    public void setBankSoalId(String bankSoalId) {
        this.bankSoalId = bankSoalId;
    }

    public String getKet_jawaban() {
        return ket_jawaban;
    }

    public void setKet_jawaban(String ket_jawaban) {
        this.ket_jawaban = ket_jawaban;
    }
}
