package tb.um.kalkulus2.assets.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tb.um.kalkulus2.R;
import tb.um.kalkulus2.assets.PrefManager;
import tb.um.kalkulus2.assets.database.JawabanDatabase;
import tb.um.kalkulus2.assets.database.JawabanUserDatabase;
import tb.um.kalkulus2.assets.database.model.TableJawaban;
import tb.um.kalkulus2.assets.database.model.TableJawabanUser;
import tb.um.kalkulus2.assets.database.model.TableSoal;

import static android.content.ContentValues.TAG;

public class SoalAdapter extends RecyclerView.Adapter<SoalAdapter.MyViewHolder>{

    private Context context;
    private List<TableSoal> tableTeoriList;
    private JawabanDatabase jawabanTeoriDatabase;
    private JawabanUserDatabase jawabanTeoriUserDatabase;
    private PrefManager prefManager;

    public SoalAdapter(Context context, List<TableSoal> tableTeoriList, JawabanDatabase jawabanTeoriDatabase, JawabanUserDatabase jawabanTeoriUserDatabase) {
        this.context = context;
        this.tableTeoriList = tableTeoriList;
        this.jawabanTeoriDatabase = jawabanTeoriDatabase;
        this.jawabanTeoriUserDatabase = jawabanTeoriUserDatabase;
        prefManager = new PrefManager(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_soal, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TableSoal tableTeori = tableTeoriList.get(position);
        holder.tvNomor.setText((position +1)+".");
        holder.tvButirSoal.setText(tableTeori.getButir_soal());
        List<TableJawaban> jawabanTeoriList = jawabanTeoriDatabase.jawabanDAO().getJawabanBySoal(tableTeori.getIdTeori());
        if (jawabanTeoriList!=null){
            ArrayList<String> dataJawab = new ArrayList<>();
            ArrayList<TableJawaban> dataJawabTeori = new ArrayList<>();
            RadioGroup radioGroup = new RadioGroup(context);
            radioGroup.setOrientation(LinearLayout.VERTICAL);

            for (int i=0;i<jawabanTeoriList.size();i++){
                TableJawaban tableJawabanTeori = jawabanTeoriList.get(i);
                dataJawab.add(tableJawabanTeori.getJawaban());
                dataJawabTeori.add(tableJawabanTeori);

                RadioButton rd = new RadioButton(context);
                rd.setId(tableJawabanTeori.getIdJawaban());
                rd.setText(tableJawabanTeori.getJawaban());
                radioGroup.addView(rd);
            }
            holder.gridJawaban.removeAllViews();
            holder.gridJawaban.addView(radioGroup);
            //check save jawaban
            TableJawabanUser jawabanUserCheck = jawabanTeoriUserDatabase.jawabanUserDAO().getJawabanUser(tableTeori.getIdTeori());
            if (jawabanUserCheck!=null){
                radioGroup.check(jawabanUserCheck.getJawabanUser());
            }
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (!prefManager.checkFInishLatihan()){
                    TableJawabanUser jawabanUser = jawabanTeoriUserDatabase.jawabanUserDAO().getJawabanUser(tableTeori.getIdTeori());
                    if (jawabanUser!=null){
                        Log.e(TAG, "jawaban user: "+jawabanUser.getJawabanUser() );
                        //update
                        jawabanTeoriUserDatabase.jawabanUserDAO().updateJawabanTeoriUser(checkedId,tableTeori.getIdTeori());
                    }else{
                        //simpan
                        jawabanTeoriUserDatabase.jawabanUserDAO().insertJawabanTeoriUser(new TableJawabanUser(tableTeori.getIdTeori(),checkedId));
                    }
                }else{
                    Toast.makeText(context, "Harus memulai lagi, untuk berlatih..", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (tableTeoriList!=null)?tableTeoriList.size():0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNomor,tvButirSoal;
        private LinearLayout gridJawaban;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomor = itemView.findViewById(R.id.tv_nomor);
            tvButirSoal = itemView.findViewById(R.id.tv_butir_soal);
            gridJawaban = itemView.findViewById(R.id.layout_jawaban);
        }
    }
}
