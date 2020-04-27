package tb.um.kalkulus2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tb.um.kalkulus2.assets.adapter.MateriAdapter;
import tb.um.kalkulus2.assets.model.Materi;
import tb.um.kalkulus2.assets.retrofit.ApiServices;
import tb.um.kalkulus2.assets.retrofit.ApiUtils;

public class TheoryActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_materi)
    RecyclerView rvMateri;
    RecyclerView.Adapter mAdapter;
    @BindView(R.id.swip_materi)
    SwipeRefreshLayout swipMateri;
    private Context context;
    private Intent intentData;
    private ApiServices apiServices = ApiUtils.getApiServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);
        ButterKnife.bind(this);
        context = this;
        intentData = getIntent();
        initComponent();
        loadData();
    }

    private void initComponent() {
        //init toolbar
        toolbar.setTitle("Materi "+intentData.getStringExtra("name"));
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        rvMateri.setLayoutManager(new LinearLayoutManager(context));
        swipMateri.setOnRefreshListener(this::loadData);
        rvMateri.setHasFixedSize(true);
    }

    private void loadData() {
        swipMateri.setRefreshing(true);
        apiServices.getAllMateri(intentData.getStringExtra("type")).enqueue(new Callback<List<Materi>>() {
            @Override
            public void onResponse(@NotNull Call<List<Materi>> call, @NotNull Response<List<Materi>> response) {
                if (response.isSuccessful()){
                    try{
                        mAdapter = new MateriAdapter(context,response.body());
                        rvMateri.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        rvMateri.post(() -> swipMateri.setRefreshing(false));
                    }catch (Exception e){
                        swipMateri.setRefreshing(false);
                        e.printStackTrace();
                        Toast.makeText(context, "Terjadi kesalahan pada aplikasi", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    swipMateri.setRefreshing(false);
                    Toast.makeText(context, "gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Materi>> call, @NotNull Throwable t) {
                swipMateri.setRefreshing(false);
                t.printStackTrace();
                Toast.makeText(context, "Terjadi kesalahan saat menghubungkan ke server", Toast.LENGTH_SHORT).show();
            }
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
