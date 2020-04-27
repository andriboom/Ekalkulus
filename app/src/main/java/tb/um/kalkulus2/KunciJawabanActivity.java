package tb.um.kalkulus2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import butterknife.BindView;
import butterknife.ButterKnife;
import tb.um.kalkulus2.assets.AppData;
import tb.um.kalkulus2.assets.adapter.KunciJawabanAdapter;
import tb.um.kalkulus2.assets.database.JawabanDatabase;
import tb.um.kalkulus2.assets.database.JawabanUserDatabase;
import tb.um.kalkulus2.assets.database.SoalDatabase;

public class KunciJawabanActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_teori_kunci)
    RecyclerView rvKunci;
    private Context context;
    private RecyclerView.Adapter mAdapter;
    private SoalDatabase teoriDatabase;
    private JawabanDatabase jawabanTeoriDatabase;
    private JawabanUserDatabase jawabanTeoriUserDatabase;
    private Intent intentData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kunci_jawaban);
        ButterKnife.bind(this);
        context = this;
        intentData = getIntent();
        initDatabase();
        initComponent();
    }

    private void initComponent() {
        //init toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Kunci Jawaban");
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        rvKunci.setLayoutManager(new LinearLayoutManager(context));
        loadDatabaseData();
    }


    private void initDatabase() {
        teoriDatabase = Room.databaseBuilder(context, SoalDatabase.class, AppData.DATABASE_SOAL)
                .allowMainThreadQueries()
                .build();
        jawabanTeoriDatabase = Room.databaseBuilder(context, JawabanDatabase.class, AppData.DATAVASE_JAWABAN)
                .allowMainThreadQueries()
                .build();
        jawabanTeoriUserDatabase = Room.databaseBuilder(context, JawabanUserDatabase.class, AppData.DATABASE_JAWABAN_USER)
                .allowMainThreadQueries()
                .build();
    }

    private void loadDatabaseData() {
        Log.e("kuda", "loadDatabaseData: "+teoriDatabase.soalDAO().getAllTeori(intentData.getStringExtra("type")).toString() );
        mAdapter = new KunciJawabanAdapter(
                context,
                teoriDatabase.soalDAO().getAllTeori(intentData.getStringExtra("type")),
                jawabanTeoriDatabase,
                jawabanTeoriUserDatabase);
        rvKunci.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        rvKunci.post(() -> {
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
