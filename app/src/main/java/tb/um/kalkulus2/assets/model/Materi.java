package tb.um.kalkulus2.assets.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Materi implements Serializable {
    @SerializedName("judul")
    private String judul;
    @SerializedName("deskripsi")
    private String deskripsi;
    @SerializedName("url_video")
    private String urlVideo;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }
}
