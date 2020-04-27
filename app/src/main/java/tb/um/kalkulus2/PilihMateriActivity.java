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

public class PilihMateriActivity extends AppCompatActivity {
    private Context context;
    @BindView(R.id.btn_kalkulus)
    ImageButton btnKalkulus;
    @BindView(R.id.btn_orkom)
    ImageButton btnOrkom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_materi);
        ButterKnife.bind(this);
        context = this;
    }

    @OnClick({R.id.btn_kalkulus, R.id.btn_orkom})
    public void onViewClicked(View view) {
        Intent i = new Intent(context,TheoryActivity.class);
        switch (view.getId()) {
            case R.id.btn_kalkulus:
                i.putExtra("type","1");
                i.putExtra("name","Kalkulus");
                startActivity(i);
                break;
            case R.id.btn_orkom:
                i.putExtra("type","2");
                i.putExtra("name","Arsitektur & Organisasi Komputer");
                startActivity(i);
                break;
        }
    }
}
