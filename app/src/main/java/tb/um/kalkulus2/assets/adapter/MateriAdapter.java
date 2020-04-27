package tb.um.kalkulus2.assets.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tb.um.kalkulus2.DetailTheoryActivity;
import tb.um.kalkulus2.R;
import tb.um.kalkulus2.assets.model.Materi;

public class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.MyViewHolder> {
    private Context context;
    private List<Materi> materiList;

    public MateriAdapter(Context context, List<Materi> materiList) {
        this.context = context;
        this.materiList = materiList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_materi, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Materi data = materiList.get(position);
        holder.tvJudul.setText(data.getJudul());
        holder.tvDeskripsi.setText(data.getDeskripsi());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailTheoryActivity.class);
            intent.putExtra("data",data);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (materiList != null) ? materiList.size() : 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_judul)
        TextView tvJudul;
        @BindView(R.id.tv_deskripsi)
        TextView tvDeskripsi;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
