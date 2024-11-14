package com.example.muzamil;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.muzamil.helper.Config;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Data_kelas_ortu extends AppCompatActivity {

    TextView idkelas, nis, profesi, nama, jadwal, jam_mulai, jam_telat, jam_berakhir, tanggal, jam, id_siswa, nama_siswa;


    Button kunjungi;

    TextView namasalesbackup2, jam2, tanggal2;

    LinearLayout caridatabarang, lewati;

    LinearLayout blank_gambar;


    ListView listdataoutlet1;

    public static String idlist, nislist, namagurulist, jadwallist, jam_mulailist, jam_telatlist, jam_berakhirlist, tanggallist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    private ProgressDialog pDialog;
    private Context context;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_kelas_ortu);

        context = Data_kelas_ortu.this;
        pDialog = new ProgressDialog(context);

        idlist = "";
        nislist = "";
        namagurulist = "";
        jadwallist = "";
        jam_mulailist = "";
        jam_telatlist = "";
        jam_berakhirlist = "";
        tanggallist = "";


        blank_gambar = (LinearLayout) findViewById(R.id.blank_gambar);

        idkelas = (TextView) findViewById(R.id.idkelas);
        nis = (TextView) findViewById(R.id.nis);
        nama = (TextView) findViewById(R.id.nama);
        profesi = (TextView) findViewById(R.id.profesi);
        jadwal = (TextView) findViewById(R.id.jadwal);
        jam_mulai = (TextView) findViewById(R.id.jam_mulai);
        jam_telat = (TextView) findViewById(R.id.jam_telat);
        jam_berakhir = (TextView) findViewById(R.id.jam_berakhir);
        tanggal = (TextView) findViewById(R.id.tanggal);
        jam = (TextView) findViewById(R.id.jam);
        id_siswa = (TextView) findViewById(R.id.id_siswa);
        nama_siswa = (TextView) findViewById(R.id.nama_siswa);



        listdataoutlet1 = (ListView) findViewById(R.id.listdataoutlet);

        caridatabarang = (LinearLayout) findViewById(R.id.caridatabarang);

        lewati = (LinearLayout) findViewById(R.id.lewati);


        caridatabarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });

        lewati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String a = idkelas.getText().toString();
                String b = nama.getText().toString();
                String c = nis.getText().toString();
                String d = profesi.getText().toString();
                String e = jadwal.getText().toString();

                String f = jam_mulai.getText().toString();
                String g = jam_telat.getText().toString();
                String h = jam_berakhir.getText().toString();

                String j = id_siswa.getText().toString();
                String k = nama_siswa.getText().toString();

                Intent i = new Intent(getApplicationContext(), Data_absen_ortu.class);
                i.putExtra("nama",""+b+"");
                i.putExtra("nis",""+c+"");
                i.putExtra("profesi",""+d+"");
                i.putExtra("kelas",""+e+"");

//                i.putExtra("jam_mulai",""+f+"");
//                i.putExtra("jam_telat",""+g+"");
//                i.putExtra("jam_berakhir",""+h+"");

                i.putExtra("id_siswa",""+j+"");
                i.putExtra("nama_siswa",""+k+"");
                startActivity(i);
            }
        });


        jadwal.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)

                    list();

            }
        });

        tanggal.setText(getCurrentDate());
        jam.setText(getCurrentClock());

        Intent i = getIntent();
        String kiriman = i.getStringExtra("nis");
        nis.setText(kiriman);
        String kiriman2 = i.getStringExtra("profesi");
        profesi.setText(kiriman2);
        String kiriman3 = i.getStringExtra("nama");
        nama.setText(kiriman3);
        String kiriman4 = i.getStringExtra("id_siswa");
        id_siswa.setText(kiriman4);
        String kiriman5 = i.getStringExtra("nama_siswa");
        nama_siswa.setText(kiriman5);


        //prosesdasboard1();

        list();

    }


    public String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new Locale("id", "ID")); // Format tanggal dalam bahasa Indonesia
        return dateFormat.format(c.getTime());
    }


    public String getCurrentClock(){
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");
        String strdate1 = sdf1.format(c1.getTime());
        return strdate1;


    }

    private void list(){

        //swipe_refresh.setRefreshing(true);
        aruskas.clear();
        listdataoutlet1.setAdapter(null);

        //Log.d("link", LINK );
        AndroidNetworking.post( Config.host + "data_kelas_ortu.php" )
                .addBodyParameter("nis", nis.getText().toString())
                .addBodyParameter("tanggal", tanggal.getText().toString())
                .addBodyParameter("jam", jam.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
/*
                        text_masuk.setText(
                                rupiahFormat.format(response.optDouble("yes")));
                        text_keluar.setText(
                                rupiahFormat.format( response.optDouble("oke") ));
                        text_total.setText(
                                rupiahFormat.format( response.optDouble("saldo") ));
**/
                        try {
                            JSONArray jsonArray = response.optJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //Data_BayarEX item = new Data_BayarEX();
                                JSONObject responses    = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                //map.put("no",         responses.optString("no"));
                                map.put("id",         responses.optString("id"));
                                map.put("jadwal",         responses.optString("jadwal"));
                                map.put("jam_mulai",       responses.optString("jam_mulai"));
                                map.put("jam_telat",       responses.optString("jam_telat"));
                                map.put("jam_berakhir",       responses.optString("jam_berakhir"));




                                //total += Integer.parseInt(responses.getString("harga"))* Integer.parseInt(responses.getString("qty"));
                                //map.put("tanggal",      responses.optString("tanggal"));

                                aruskas.add(map);
                                //bayarList.add(item);
                            }

                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        ttl.setText("Total : Rp "+formatter.format(total));
//                        total = 0;
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void Adapter(){

        if (aruskas.isEmpty()) {
            // Jika aruskas kosong, tampilkan blank_gambar
            // Misalnya:
            blank_gambar.setVisibility(View.VISIBLE);
            listdataoutlet1.setVisibility(View.GONE);
        } else {
            blank_gambar.setVisibility(View.GONE);
            listdataoutlet1.setVisibility(View.VISIBLE);

            SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_kelas,
                    new String[] {"id","jadwal","jam_mulai","jam_telat","jam_berakhir"},
                    new int[] {R.id.idlistt,R.id.jadwallist,R.id.jammulailist,R.id.jamtelatlist, R.id.jamberakhirlist});

            listdataoutlet1.setAdapter(simpleAdapter);

            listdataoutlet1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    idlist = ((TextView) view.findViewById(R.id.idlistt)).getText().toString();
                    jadwallist = ((TextView) view.findViewById(R.id.jadwallist)).getText().toString();
                    jam_mulailist = ((TextView) view.findViewById(R.id.jammulailist)).getText().toString();
                    jam_telatlist = ((TextView) view.findViewById(R.id.jamtelatlist)).getText().toString();
                    jam_berakhirlist = ((TextView) view.findViewById(R.id.jamberakhirlist)).getText().toString();



                    // Set the parsed values to the appropriate TextViews
                    idkelas.setText(idlist);
//                    nama.setText(namagurulist);
//                    nis.setText(String.valueOf(nislist)); // Set as plain numeric value
                    jadwal.setText(String.valueOf(jadwallist)); // Set as plain numeric value
                    jam_mulai.setText(String.valueOf(jam_mulailist)); // Set as plain numeric value
                    jam_telat.setText(jam_telatlist);
                    jam_berakhir.setText(jam_berakhirlist);


                    //String a = idkelas.getText().toString();
                    String b = nama.getText().toString();
                    String c = nis.getText().toString();
                    String d = profesi.getText().toString();
                    String e = jadwal.getText().toString();

                    String f = jam_mulai.getText().toString();
                    String g = jam_telat.getText().toString();
                    String h = jam_berakhir.getText().toString();

                    String j = id_siswa.getText().toString();
                    String k = nama_siswa.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Data_absen_ortu.class);
                    i.putExtra("nama",""+b+"");
                    i.putExtra("nis",""+c+"");
                    i.putExtra("profesi",""+d+"");
                    i.putExtra("kelas",""+e+"");

//                i.putExtra("jam_mulai",""+f+"");
//                i.putExtra("jam_telat",""+g+"");
//                i.putExtra("jam_berakhir",""+h+"");

                    i.putExtra("id_siswa",""+j+"");
                    i.putExtra("nama_siswa",""+k+"");
                    startActivity(i);



//                        Intent resultIntent = new Intent();
//                        resultIntent.putExtra("nama",""+b+"");
//                        resultIntent.putExtra("nis",""+c+"");
//                        resultIntent.putExtra("profesi",""+d+"");
//                        resultIntent.putExtra("jadwal",""+e+"");
//
//                        //resultIntent.putExtra("selectedBarang", selectedBarang);
//                        setResult(RESULT_OK, resultIntent);
//                        finish();



                }
            });
        }
    }



}