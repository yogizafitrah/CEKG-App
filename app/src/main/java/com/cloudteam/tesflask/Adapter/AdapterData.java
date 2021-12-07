package com.cloudteam.tesflask.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudteam.tesflask.API.APIRequestData;
import com.cloudteam.tesflask.API.RetroServer;
import com.cloudteam.tesflask.Activity.DetailActivity;
import com.cloudteam.tesflask.Activity.UbahActivity;
import com.cloudteam.tesflask.Activity.listdata;
import com.cloudteam.tesflask.Model.DataModel;
import com.cloudteam.tesflask.Model.ResponseModel;
import com.cloudteam.tesflask.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    private Context ctx;
    private List<DataModel> listData;
    private  List<DataModel> listPasien;
    private  List<DataModel> listDetail;
    private int idPasien;

    public AdapterData(Context ctx,List<DataModel> listData){
        this.ctx=ctx;
        this.listData=listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = listData.get(position);

        holder.tvId.setText(String.valueOf(dm.getId()));
        holder.tvNama.setText(dm.getNama());
        holder.tvTgl.setText(dm.getTgl());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvId, tvNama, tvJk, tvAlamat,tvTgl,tvTspt,tvBpm,tvIrr,tvIrrLokal;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvTgl = itemView.findViewById(R.id.tv_tgl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    idPasien = Integer.parseInt(tvId.getText().toString());
                    getData2();
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Pilih operasi yang akan dilakukan");
                    dialogPesan.setTitle("Perhatian");
                    dialogPesan.setIcon(R.drawable.logo);
                    dialogPesan.setCancelable(true);

                    idPasien = Integer.parseInt(tvId.getText().toString());

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteData();
                            dialog.dismiss();
                            Handler hand = new Handler();
                            hand.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((listdata)ctx).retrieveData();
                                }
                            },500);

                        }
                    });

                    dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getData();
                            dialog.dismiss();
                        }
                    });

                    dialogPesan.show();
                    return false;
                }
            });
        }

        private  void deleteData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> hapusData = ardData.ardDeleteData(idPasien);

            hapusData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx,"Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void getData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> ambilData = ardData.ardGetData(idPasien);

            ambilData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listPasien = response.body().getData();

                    int varIdPasien = listPasien.get(0).getId();
                    String varNamaPasien = listPasien.get(0).getNama();
                    String varJk = listPasien.get(0).getJk();
                    String varAlamat = listPasien.get(0).getAlamat();
                    String varTgl = listPasien.get(0).getTgl();
                    double varTspt = listPasien.get(0).getTspt();
                    double varBpm = listPasien.get(0).getBpm();
                    double varIrr = listPasien.get(0).getIrr();
                    double varIrrLokal = listPasien.get(0).getIrrlokal();

                    //Toast.makeText(ctx,"Kode : "+kode+" | Pesan : "+pesan+" | Data : "+varNamaPasien, Toast.LENGTH_SHORT).show();
                    Intent kirim = new Intent(ctx, UbahActivity.class);
                    kirim.putExtra("xId",varIdPasien);
                    kirim.putExtra("xNamaPasien",varNamaPasien);
                    kirim.putExtra("xJk",varJk);
                    kirim.putExtra("xAlamat",varAlamat);
                    kirim.putExtra("xTgl",varTgl);
                    kirim.putExtra("xTspt",varTspt);
                    kirim.putExtra("xBpm",varBpm);
                    kirim.putExtra("xIrr",varIrr);
                    kirim.putExtra("xIrrLokal",varIrrLokal);
                    ctx.startActivity(kirim);
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void getData2(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> ambilData = ardData.ardGetData(idPasien);

            ambilData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listDetail = response.body().getData();

                    int varIdPasien = listDetail.get(0).getId();
                    String varNamaPasien = listDetail.get(0).getNama();
                    String varJk = listDetail.get(0).getJk();
                    String varAlamat = listDetail.get(0).getAlamat();
                    String varTgl = listDetail.get(0).getTgl();
                    double varTspt = listDetail.get(0).getTspt();
                    double varBpm = listDetail.get(0).getBpm();
                    double varIrr = listDetail.get(0).getIrr();
                    double varIrrLokal = listDetail.get(0).getIrrlokal();
                    String HR = listDetail.get(0).getHr();

                    //Toast.makeText(ctx,"Kode : "+kode+" | Pesan : "+pesan+" | Data : "+varNamaPasien, Toast.LENGTH_SHORT).show();
                    Intent kirim = new Intent(ctx, DetailActivity.class);
                    kirim.putExtra("xId",varIdPasien);
                    kirim.putExtra("xNamaPasien",varNamaPasien);
                    kirim.putExtra("xJk",varJk);
                    kirim.putExtra("xAlamat",varAlamat);
                    kirim.putExtra("xTgl",varTgl);
                    kirim.putExtra("xTspt",varTspt);
                    kirim.putExtra("xBpm",varBpm);
                    kirim.putExtra("xIrr",varIrr);
                    kirim.putExtra("xIrrLokal",varIrrLokal);
                    kirim.putExtra("HR",HR);
                    ctx.startActivity(kirim);
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
