package tb.um.kalkulus2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_materi)
    ImageButton btnMateri;
    @BindView(R.id.btn_latihan)
    ImageButton btnLatihan;
    @BindView(R.id.btn_bantuan)
    ImageButton btnBantuan;
    @BindView(R.id.btn_tentang)
    ImageButton btnTentang;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
    }

    @OnClick({R.id.btn_materi, R.id.btn_latihan, R.id.btn_bantuan, R.id.btn_tentang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_materi:
                startActivity(new Intent(context,PilihMateriActivity.class));
                break;
            case R.id.btn_latihan:
                startActivity(new Intent(context,PilihLatihanActivity.class));
                break;
            case R.id.btn_bantuan:
                startActivity(new Intent(context,InformationActivity.class));
                break;
            case R.id.btn_tentang:
                startActivity(new Intent(context,AboutActivity.class));
                break;
        }
    }
}
