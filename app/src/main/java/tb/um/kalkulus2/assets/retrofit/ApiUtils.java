package tb.um.kalkulus2.assets.retrofit;

import tb.um.kalkulus2.assets.AppData;

public class ApiUtils {
    private static String BASE_URL = AppData.URL;

    public static ApiServices getApiServices(){
        return RetrofitClient.getClient(BASE_URL).create(ApiServices.class);
    }
}
