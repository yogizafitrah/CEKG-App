package com.cloudteam.tesflask.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cloudteam.tesflask.API.APIRequestData;
import com.cloudteam.tesflask.API.RetroServer;
import com.cloudteam.tesflask.Model.ResponseModel;
import com.cloudteam.tesflask.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama,etJk,etAlamat,etTgl,etTspt,etBpm,etIrr,etRrlokal;
    private Button btnSimpan;
    private String nama,jk,alamat,tgl,hr;
    private Double tspt,bpm,irr,irrlokal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etJk = findViewById(R.id.et_jk);
        etTgl = findViewById(R.id.et_tgl);
        etAlamat = findViewById(R.id.et_alamat);
        etTspt = findViewById(R.id.et_tspt);
        etBpm = findViewById(R.id.et_bpm);
        etIrr = findViewById(R.id.et_irr);
        etRrlokal = findViewById(R.id.et_irrlokal);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                jk = etJk.getText().toString();
                tgl = etTgl.getText().toString();
                alamat = etAlamat.getText().toString();
                tspt = Double.valueOf(etTspt.getText().toString());
                bpm = Double.valueOf(etBpm.getText().toString());
                irr = Double.valueOf(etIrr.getText().toString());
                irrlokal = Double.valueOf(etRrlokal.getText().toString());

                if(nama.trim().equals("")){
                    etNama.setError("Nama harus diisi");
                }else if (jk.trim().equals("")){
                    etJk.setError("Jenis Kelamin harus diisi");
                }else if (alamat.trim().equals("")){
                    etAlamat.setError("Alamat harus diisi");
                }else if (tgl.trim().equals("")){
                    etTgl.setError("Tanggal harus diisi");
                }else if (tspt==0){
                    etTspt.setError("Interval PT harus diisi");
                }else if (bpm==0){
                    etBpm.setError("Bpm harus diisi");
                }else if (irr==0){
                    etIrr.setError("Interval RR harus diisi");
                }else  if(irrlokal==0){
                    etRrlokal.setError("RR Lokal harus diisi");
                }else {
                    createData();
                }
            }
        });
    }
    private void createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpanData = ardData.ardCreateData(nama,jk,alamat,tgl,tspt,bpm,irr,irrlokal,hr);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this,"Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}