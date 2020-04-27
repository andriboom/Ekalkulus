package tb.um.kalkulus2.assets.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tb.um.kalkulus2.R;
import tb.um.kalkulus2.assets.database.JawabanDatabase;
import tb.um.kalkulus2.assets.database.JawabanUserDatabase;
import tb.um.kalkulus2.assets.database.model.TableJawaban;
import tb.um.kalkulus2.assets.database.model.TableJawabanUser;
import tb.um.kalkulus2.assets.database.model.TableSoal;

public class KunciJawabanAdapter extends RecyclerView.Adapter<KunciJawabanAdapter.MyViewHolder> {
    private Context context;
    private List<TableSoal> tableTeoriList;
    private JawabanDatabase jawabanTeoriDatabase;
    private JawabanUserDatabase jawabanTeoriUserDatabase;

    public KunciJawabanAdapter(Context context, List<TableSoal> tableTeoriList, JawabanDatabase jawabanTeoriDatabase, JawabanUserDatabase jawabanTeoriUserDatabase) {
        this.context = context;
        this.tableTeoriList = tableTeoriList;
        this.jawabanTeoriDatabase = jawabanTeoriDatabase;
        this.jawabanTeoriUserDatabase = jawabanTeoriUserDatabase;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kunci_jawaban, parent, false));
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.e("ajg", "onBindViewHolder: "+position );
        TableSoal tableTeori = tableTeoriList.get(position);
        holder.tvNomor.setText((position +1)+".");
        holder.tvButirSoal.setText(tableTeori.getButir_soal());
        List<TableJawaban> jawabanTeoriList = jawabanTeoriDatabase.jawabanDAO().getJawabanBySoal(tableTeori.getIdTeori());
        if (jawabanTeoriList!=null){
            RadioGroup radioGroup = new RadioGroup(context);
            radioGroup.setOrientation(LinearLayout.VERTICAL);
            for (int i=0;i<jawabanTeoriList.size();i++){
                TableJawaban tableJawabanTeori = jawabanTeoriList.get(i);

                RadioButton rd = new RadioButton(context);
                rd.setId(tableJawabanTeori.getIdJawaban());
                rd.setEnabled(false);
                rd.setText(tableJawabanTeori.getJawaban());
                if (tableJawabanTeori.isStatusJawaban()){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        rd.setTextColor(context.getColor(R.color.salah));
                    }else{
                        rd.setTextColor(R.color.salah);
                    }
                }
                radioGroup.addView(rd);
            }
            holder.gridJawaban.removeAllViews();
            holder.gridJawaban.addView(radioGroup);
            //check save jawaban
            TableJawabanUser jawabanUserCheck = jawabanTeoriUserDatabase.jawabanUserDAO().getJawabanUser(tableTeori.getIdTeori());
            if (jawabanUserCheck!=null){
                radioGroup.check(jawabanUserCheck.getJawabanUser());
            }
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
