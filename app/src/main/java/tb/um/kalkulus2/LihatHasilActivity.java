package tb.um.kalkulus2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;

import com.timqi.sectorprogressview.ColorfulRingProgressView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tb.um.kalkulus2.assets.AppData;
import tb.um.kalkulus2.assets.database.JawabanDatabase;
import tb.um.kalkulus2.assets.database.JawabanUserDatabase;
import tb.um.kalkulus2.assets.database.SoalDatabase;
import tb.um.kalkulus2.assets.database.model.TableJawaban;
import tb.um.kalkulus2.assets.database.model.TableJawabanUser;

public class LihatHasilActivity extends AppCompatActivity {

    @BindView(R.id.tv_nilai)
    TextView tvNilai;
    @BindView(R.id.chart)
    ColorfulRingProgressView chart;
    @BindView(R.id.tv_benar)
    TextView tvBenar;
    @BindView(R.id.tv_salah)
    TextView tvSalah;
    @BindView(R.id.btn_lihat_key)
    CardView btnLihatKey;
    @BindView(R.id.btn_kembali)
    CardView btnKembali;
    private Context context;
    private SoalDatabase teoriDatabase;
    private JawabanDatabase jawabanTeoriDatabase;
    private JawabanUserDatabase jawabanTeoriUserDatabase;
    private int benar=0,salah=0,totalsoal=0;
    private Intent intentData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_hasil);
        ButterKnife.bind(this);
        context = this;
        intentData = getIntent();
        initDatabase();
        hitungNilai();
    }

    @SuppressLint("SetTextI18n")
    private void hitungNilai() {
        totalsoal = jawabanTeoriUserDatabase.jawabanUserDAO().getTotalJawabanTeoriUser();
        List<TableJawabanUser> tempJawabanTeori = jawabanTeoriUserDatabase.jawabanUserDAO().getAllJawabanUser();
        for (int i=0;i<tempJawabanTeori.size();i++){
            TableJawaban jawabanTeori = jawabanTeoriDatabase.jawabanDAO().getJawabanByidSoaldanJawaban(tempJawabanTeori.get(i).getSoalId(),tempJawabanTeori.get(i).getJawabanUser());
            if (jawabanTeori.isStatusJawaban()){
                //benar
                benar ++;
            }else{
//                salajh
                salah ++;
            }
        }
        tvNilai.setText(""+benar);
        tvBenar.setText(benar+" Soal");
        tvSalah.setText(salah+" Soal");
        chart.setPercent((100/totalsoal)*benar);
    }

    private void initDatabase() {
        teoriDatabase = Room.databaseBuilder(context, SoalDatabase.class, AppData.DATABASE_SOAL)
                .allowMainThreadQueries()
                .build();
        jawabanTeoriDatabase = Room.databaseBuilder(context, JawabanDatabase.class,AppData.DATAVASE_JAWABAN)
                .allowMainThreadQueries()
                .build();
        jawabanTeoriUserDatabase = Room.databaseBuilder(context, JawabanUserDatabase.class,AppData.DATABASE_JAWABAN_USER)
                .allowMainThreadQueries()
                .build();
    }

    @OnClick({R.id.btn_lihat_key, R.id.btn_kembali})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_lihat_key:
                Intent i = new Intent(context,KunciJawabanActivity.class);
                i.putExtra("type",intentData.getStringExtra("type"));
                startActivity(i);
                break;
            case R.id.btn_kembali:
                finish();
                break;
        }
    }

}
