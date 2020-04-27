package tb.um.kalkulus2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.appbar.AppBarLayout;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tb.um.kalkulus2.assets.AppData;
import tb.um.kalkulus2.assets.PrefManager;
import tb.um.kalkulus2.assets.adapter.SoalAdapter;
import tb.um.kalkulus2.assets.database.JawabanDatabase;
import tb.um.kalkulus2.assets.database.JawabanUserDatabase;
import tb.um.kalkulus2.assets.database.SoalDatabase;
import tb.um.kalkulus2.assets.database.model.TableJawaban;
import tb.um.kalkulus2.assets.database.model.TableSoal;
import tb.um.kalkulus2.assets.model.Jawaban;
import tb.um.kalkulus2.assets.model.Soal;
import tb.um.kalkulus2.assets.retrofit.ApiServices;
import tb.um.kalkulus2.assets.retrofit.ApiUtils;
import tb.um.kalkulus2.assets.retrofit.resnponse.ResponSoal;

public class QuizActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.rv_teori)
    RecyclerView rvTeori;
    @BindView(R.id.btn_ulangi)
    CardView btnUlangi;
    @BindView(R.id.tv_lihat_hasil)
    TextView tvLihatHasil;
    @BindView(R.id.btn_lihat_hasil)
    CardView btnLihatHasil;
    @BindView(R.id.layout_button)
    LinearLayout layoutButton;
    @BindView(R.id.base_layout)
    NestedScrollView baseLayout;
    private Context context;
    private RecyclerView.Adapter mAdapter;
    private ApiServices apiServices = ApiUtils.getApiServices();
    //database
    private SoalDatabase soalDatabase;
    private JawabanDatabase jawabanDatabase;
    private JawabanUserDatabase jawabanUserDatabase;
    private PrefManager prefManager;
    private Intent intentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        context = this;
        intentData = getIntent();
        initDatabase();
        initComponent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //cek apakah soal sudah ada atau belum
        if (soalDatabase.soalDAO().getTotal(intentData.getStringExtra("type"))!=0){
            //apabila memiliki data
            loadDatabaseData();
        }else{
            getDataFromApi();
        }
        checkCompleteQuiz();
    }

    @SuppressLint("SetTextI18n")
    private void checkCompleteQuiz() {
        if (!prefManager.checkFInishLatihan()){
            btnUlangi.setVisibility(View.GONE);
            btnLihatHasil.setVisibility(View.VISIBLE);
            tvLihatHasil.setText("Simpan Jawaban");
        }else{
            btnUlangi.setVisibility(View.VISIBLE);
            btnLihatHasil.setVisibility(View.VISIBLE);
            tvLihatHasil.setText("Lihat Hasil");
        }
    }

    private void getDataFromApi() {
        apiServices.getSoalTeori(AppData.TOTAL_TEORI, intentData.getStringExtra("type")).enqueue(new Callback<ResponSoal>() {
            @Override
            public void onResponse(@NotNull Call<ResponSoal> call, @NotNull Response<ResponSoal> response) {
                if (response.isSuccessful()){
                    try{
                        assert response.body() != null;
                        if (response.body().isStatus()){
                            //inset into database
                            //insert soal
                            for (int i=0;i<response.body().getSoalList().size();i++) {
                                Soal teori = response.body().getSoalList().get(i);
                                soalDatabase.soalDAO().insertTeori(new TableSoal(Integer.parseInt(teori.getIdBankSoal()), teori.getButirSoal(),intentData.getStringExtra("type")));
                                for (int j = 0; j < teori.getJawaban().size(); j++) {
                                    Jawaban jawabanTeori = teori.getJawaban().get(j);
                                    boolean statusjawaban = false;
                                    if (jawabanTeori.getKet_jawaban().equals("1")) {
                                        statusjawaban = true;
                                    }
                                    jawabanDatabase.jawabanDAO().insertDataTeori(new TableJawaban(Integer.parseInt(jawabanTeori.getIdBankjawaban()), jawabanTeori.getJawaban(), Integer.parseInt(jawabanTeori.getBankSoalId()), statusjawaban));
                                }
                            }
                            //finish simpan ke database
                            loadDatabaseData();
                        }else{
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(context, "terjadi kesalahan pada aplikasi", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponSoal> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadDatabaseData() {
        mAdapter = new SoalAdapter(
                context,
                soalDatabase.soalDAO().getAllTeori(intentData.getStringExtra("type")),
                jawabanDatabase,
                jawabanUserDatabase);
        rvTeori.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        rvTeori.post(() -> mAdapter.notifyDataSetChanged());
        btnLihatHasil.setOnClickListener(v -> {
            if (jawabanUserDatabase.jawabanUserDAO().getTotalJawabanTeoriUser()==soalDatabase.soalDAO().getTotal(intentData.getStringExtra("type"))){
                prefManager.simpanJawaban();
                Intent i = new Intent(context, LihatHasilActivity.class);
                i.putExtra("type",intentData.getStringExtra("type"));
                startActivity(i);
            }else{
                Toast.makeText(context, "Anda Harus menyelesaikan latihan, sebelum menyimpan jawaban..", Toast.LENGTH_SHORT).show();
            }
        });
        btnUlangi.setOnClickListener(v -> {
            if (jawabanUserDatabase.jawabanUserDAO().getTotalJawabanTeoriUser()==soalDatabase.soalDAO().getTotal(intentData.getStringExtra("type"))){
                resetALLData();
            }else{
                Toast.makeText(context, "Anda Harus menyelesaikan latihan sebelum memulai baru..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetALLData() {
        soalDatabase.soalDAO().deleteAllTeori(intentData.getStringExtra("type"));
        jawabanUserDatabase.jawabanUserDAO().deleteAllJawabanTeoriUser();
        jawabanDatabase.jawabanDAO().deleteJawabanTeori();
        prefManager.clearAllData();
        onResume();
    }

    private void initDatabase() {
        soalDatabase = Room.databaseBuilder(context, SoalDatabase.class, AppData.DATABASE_SOAL)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        jawabanDatabase = Room.databaseBuilder(context, JawabanDatabase.class,AppData.DATAVASE_JAWABAN)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        jawabanUserDatabase = Room.databaseBuilder(context, JawabanUserDatabase.class,AppData.DATABASE_JAWABAN_USER)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    private void initComponent() {
        //init toolbar
        toolbar.setTitle("Latihan "+intentData.getStringExtra("name"));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        prefManager = new PrefManager(context);
        rvTeori.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
