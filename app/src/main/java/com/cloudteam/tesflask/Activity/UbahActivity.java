package com.cloudteam.tesflask.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class UbahActivity extends AppCompatActivity {
    private int xId;
    private String xNama,xJk,xAlamat,xTgl;
    private String xTspt,xBpm,xIrr,xIrrLokal;
    private EditText etNama,etJk,etAlamat,etTgl,etTspt,etBpm,etIrr,etIrrLokal;
    private Button btnUbah;
    private String yNama,yJk,yAlamat,yTgl;
    private String yTspt,yBpm,yIrr,yIrrLokal;
    private  String HR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent terima = getIntent();
        xId = terima.getIntExtra("xId",-1);
        xNama = terima.getStringExtra("xNamaPasien");
        xJk = terima.getStringExtra("xJk");
        xAlamat = terima.getStringExtra("xAlamat");
        xTgl = terima.getStringExtra("xTgl");
        xTspt = Double.toString(terima.getDoubleExtra("xTspt",-1));
        xBpm = Double.toString(terima.getDoubleExtra("xBpm",-1));
        xIrr = Double.toString(terima.getDoubleExtra("xIrr",-1));
        xIrrLokal = Double.toString(terima.getDoubleExtra("xIrrLokal",-1));
        HR= terima.getStringExtra("HR");

        etNama = findViewById(R.id.et_nama);
        etJk = findViewById(R.id.et_jk);
        etAlamat = findViewById(R.id.et_alamat);
        etTgl = findViewById(R.id.et_tgl);
        etTspt = findViewById(R.id.et_tspt);
        etBpm = findViewById(R.id.et_bpm);
        etIrr = findViewById(R.id.et_irr);
        etIrrLokal = findViewById(R.id.et_irrlokal);
        btnUbah = findViewById(R.id.btn_ubah);

        etNama.setText(xNama);
        etJk.setText(xJk);
        etAlamat.setText(xAlamat);
        etTgl.setText(xTgl);
        etTspt.setText(xTspt);
        etBpm.setText(xBpm);
        etIrr.setText(xIrr);
        etIrrLokal.setText(xIrrLokal);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yNama = etNama.getText().toString();
                yJk = etJk.getText().toString();
                yAlamat = etAlamat.getText().toString();

                updateData();
            }
        });
    }
    private void updateData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> ubahData = ardData.ardUpdateData(xId,yNama,yJk,yAlamat,xTgl,xTspt,xBpm,xIrr,xIrrLokal,HR);

        ubahData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this,"Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}