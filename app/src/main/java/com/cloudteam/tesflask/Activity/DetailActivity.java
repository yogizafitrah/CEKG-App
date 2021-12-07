package com.cloudteam.tesflask.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudteam.tesflask.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity{
    private int xId;
    private String xNama,xJk,xAlamat,xTgl;
    private String xTspt,xBpm,xIrr,xIrrLokal;
    private TextView etNama,etJk,etAlamat,etTgl,etTspt,etBpm,etIrr,etIrrLokal;
    private String yNama,yJk,yAlamat,yTgl;
    private String yTspt,yBpm,yIrr,yIrrLokal;
    private String HR;
    private String txHR;

    private LineChart mChart;

    ArrayList<Entry> yValues = new ArrayList<>();
    ArrayAdapter<Entry> arrayAdapter;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayADapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent terima = getIntent();
        xId = terima.getIntExtra("xId", -1);
        xNama = terima.getStringExtra("xNamaPasien");
        xJk = terima.getStringExtra("xJk");
        xAlamat = terima.getStringExtra("xAlamat");
        xTgl = terima.getStringExtra("xTgl");
        xTspt = Double.toString(terima.getDoubleExtra("xTspt", -1));
        xBpm = Double.toString(terima.getDoubleExtra("xBpm", -1));
        xIrr = Double.toString(terima.getDoubleExtra("xIrr", -1));
        xIrrLokal = Double.toString(terima.getDoubleExtra("xIrrLokal", -1));
        HR = terima.getStringExtra("HR");
        Float [] sinyal= new Float[50];
        JSONArray filter = null;
        try {
            filter = new JSONArray(HR);
            for (int i = 0; i!=sinyal.length; i++) {
                sinyal[i]= Float.valueOf(filter.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        etNama = findViewById(R.id.txNAMA);
        etJk = findViewById(R.id.txJK);
        etAlamat = findViewById(R.id.txALAMAT);
        etTgl = findViewById(R.id.txTGL);
        etTspt = findViewById(R.id.txTSPT);
        etBpm = findViewById(R.id.txBPM);
        etIrr = findViewById(R.id.txIRR);
        etIrrLokal = findViewById(R.id.txRRLOKAL);

        mChart = (LineChart) findViewById(R.id.LineChart);
//        mChart.setOnChartGestureListener(DetailActivity.this);
//        mChart.setOnChartValueSelectedListener(DetailActivity.this);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        etNama.setText(xNama);
        etJk.setText(xJk);
        etAlamat.setText(xAlamat);
        etTgl.setText(xTgl);
        etTspt.setText(xTspt);
        etBpm.setText(xBpm);
        etIrr.setText(xIrr);
        etIrrLokal.setText(xIrrLokal);

        for (int i = 0; i != sinyal.length; i++) {
            yValues.add(new Entry(i,sinyal[i]));
        }
//        yValues.add(new Entry(1,50f));
//        yValues.add(new Entry(2,70f));
//        yValues.add(new Entry(3,80f));
//        yValues.add(new Entry(4,65f));
//        yValues.add(new Entry(5,55f));
//        yValues.add(new Entry(6,70f));
        LineDataSet set1 = new LineDataSet(yValues, "Filtered Signal");

        set1.setFillAlpha(200);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        mChart.setData(data);

        predictData();

    }
    private void predictData(){
        String value1 = etTspt.getText().toString();
        String value2 = etBpm.getText().toString();
        String value3 = etIrr.getText().toString();
        String value4 = etIrrLokal.getText().toString();
        OkHttpClient okhttpClient=new OkHttpClient();
        RequestBody formbody = new FormBody.Builder().add("num1",value1).add("num2",value2).add("num3",value3).add("num4",value4).build();
        Request request=new Request.Builder().url("http://cekekg.pocari.id/ecg").post(formbody).build();
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(DetailActivity.this,"Network not found",Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final TextView textView =  findViewById(R.id.txSTATUS);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            textView.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}