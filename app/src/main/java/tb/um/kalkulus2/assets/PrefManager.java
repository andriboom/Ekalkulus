package tb.um.kalkulus2.assets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "data_kalkulus";

    private static final String SIMPAN_JAWABAN = "simpan_jawaban";


    @SuppressLint("CommitPrefEdits")
    public PrefManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void simpanJawaban(){
        editor.putBoolean(SIMPAN_JAWABAN,true);
        editor.commit();
    }

    public boolean checkFInishLatihan(){
        return pref.getBoolean(SIMPAN_JAWABAN,false);
    }

    public void clearAllData(){
        editor.clear();
        editor.commit();
    }
}
