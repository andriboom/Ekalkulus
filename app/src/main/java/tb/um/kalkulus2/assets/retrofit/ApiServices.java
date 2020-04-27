package tb.um.kalkulus2.assets.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tb.um.kalkulus2.assets.model.Materi;
import tb.um.kalkulus2.assets.retrofit.resnponse.ResponSoal;

public interface ApiServices {
    // get all materi
    @FormUrlEncoded
    @POST("materi")
    Call<List<Materi>> getAllMateri(@Field("type") String type);

    //get Teori
    @FormUrlEncoded
    @POST("quiz/teori")
    Call<ResponSoal> getSoalTeori(@Field("total") String total,@Field("type") String type);
}
