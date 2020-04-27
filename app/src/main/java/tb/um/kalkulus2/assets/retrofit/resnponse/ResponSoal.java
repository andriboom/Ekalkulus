package tb.um.kalkulus2.assets.retrofit.resnponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tb.um.kalkulus2.assets.model.Soal;

public class ResponSoal {
    @SerializedName("status")
    private boolean status;
    @SerializedName("teori")
    private List<Soal> soalList;
    @SerializedName("message")
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Soal> getSoalList() {
        return soalList;
    }

    public void setSoalList(List<Soal> soalList) {
        this.soalList = soalList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
