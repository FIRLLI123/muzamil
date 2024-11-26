package com.example.muzamil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

public class Data_kelas extends AppCompatActivity {

    TextView idkelas, nis, profesi, nama, jadwal, jam_mulai, jam_telat, jam_berakhir, tanggal, jam;


    Button kunjungi;

    TextView namasalesbackup2, jam2, tanggal2;

    LinearLayout caridatabarang;

    LinearLayout blank_gambar;


    ListView listdataoutlet1;

    public static String idlist, nislist, namagurulist, jadwallist, jam_mulailist, jam_telatlist, jam_berakhirlist, tanggallist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();


    private ProgressDialog pDialog;
    private Context context;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_kelas);

        // Inisialisasi context dan ProgressDialog
        context = Data_kelas.this;
        pDialog = new ProgressDialog(context);

        // Inisialisasi variabel yang digunakan untuk menyimpan data
        idlist = "";
        nislist = "";
        namagurulist = "";
        jadwallist = "";
        jam_mulailist = "";
        jam_telatlist = "";
        jam_berakhirlist = "";
        tanggallist = "";

        // Inisialisasi tampilan layout
        blank_gambar = (LinearLayout) findViewById(R.id.blank_gambar);

        // Inisialisasi TextView untuk menampilkan data terkait kelas
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

        // Inisialisasi ListView untuk menampilkan data outlet
        listdataoutlet1 = (ListView) findViewById(R.id.listdataoutlet);

        // Inisialisasi LinearLayout untuk tombol pencarian data barang
        caridatabarang = (LinearLayout) findViewById(R.id.caridatabarang);

        // Menambahkan event listener untuk tombol pencarian data barang
        caridatabarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list(); // Memanggil method list() saat tombol diklik
            }
        });

        // Menambahkan TextWatcher pada TextView jadwal untuk memonitor perubahan teks
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
                    list(); // Memanggil method list() jika jadwal tidak kosong
            }
        });

        // Mengatur tanggal dan jam saat ini pada tampilan
        tanggal.setText(getCurrentDate());
        jam.setText(getCurrentClock());

        // Mengambil data yang diteruskan melalui Intent
        Intent i = getIntent();
        String kiriman = i.getStringExtra("nis");
        nis.setText(kiriman);
        String kiriman2 = i.getStringExtra("profesi");
        profesi.setText(kiriman2);
        String kiriman3 = i.getStringExtra("nama");
        nama.setText(kiriman3);

        // Memanggil method list() untuk menampilkan data saat pertama kali dibuka
        list();
    }



    // Method untuk mendapatkan tanggal saat ini dalam format "yyyy/MM/dd" (dengan locale Bahasa Indonesia)
    public String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new Locale("id", "ID")); // Format tanggal dalam bahasa Indonesia
        return dateFormat.format(c.getTime()); // Mengembalikan tanggal yang diformat sebagai string
    }

    // Method untuk mendapatkan waktu saat ini dalam format "hh:mm:ss"
    public String getCurrentClock(){
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss"); // Format waktu
        String strdate1 = sdf1.format(c1.getTime()); // Memformat waktu saat ini
        return strdate1; // Mengembalikan waktu yang diformat sebagai string
    }

    // Method untuk memuat data dan menampilkannya di ListView
    private void list(){
        // Menghapus data yang ada dan mereset adapter
        aruskas.clear();
        listdataoutlet1.setAdapter(null);

        // Mengirimkan request POST ke API
        AndroidNetworking.post( Config.host + "data_kelas.php" )
                .addBodyParameter("nis", nis.getText().toString()) // Menambahkan NIS ke request
                .addBodyParameter("tanggal", tanggal.getText().toString()) // Menambahkan tanggal ke request
                .addBodyParameter("jam", jam.getText().toString()) // Menambahkan jam ke request
                .setPriority(Priority.MEDIUM) // Menetapkan prioritas request
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Menangani response sukses dari API

                        // Menggunakan locale yang berbeda untuk pemformatan mata uang jika diperlukan
                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        try {
                            // Mengurai array JSON dari response
                            JSONArray jsonArray = response.optJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                // Mengambil objek JSON dari response
                                JSONObject responses = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                // Memasukkan data dari objek JSON ke dalam HashMap
                                map.put("id", responses.optString("id"));
                                map.put("jadwal", responses.optString("jadwal"));
                                map.put("jam_mulai", responses.optString("jam_mulai"));
                                map.put("jam_telat", responses.optString("jam_telat"));
                                map.put("jam_berakhir", responses.optString("jam_berakhir"));

                                // Menambahkan map ke dalam list data
                                aruskas.add(map);
                            }

                            // Memanggil method adapter untuk memperbarui ListView
                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace(); // Menampilkan error jika ada kesalahan parsing JSON
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // Menangani error saat mendapatkan response dari API
                    }
                });
    }

    // Method untuk mengatur data ke dalam ListView menggunakan SimpleAdapter
    private void Adapter(){
        if (aruskas.isEmpty()) {
            // Jika aruskas kosong, tampilkan blank_gambar
            blank_gambar.setVisibility(View.VISIBLE);
            listdataoutlet1.setVisibility(View.GONE); // Menyembunyikan ListView jika data kosong
        } else {
            blank_gambar.setVisibility(View.GONE); // Sembunyikan blank_gambar jika ada data
            listdataoutlet1.setVisibility(View.VISIBLE); // Menampilkan ListView jika data ada

            // Membuat SimpleAdapter untuk menampilkan data ke dalam ListView
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, aruskas, R.layout.list_kelas,
                    new String[] {"id","jadwal","jam_mulai","jam_telat","jam_berakhir"},
                    new int[] {R.id.idlistt,R.id.jadwallist,R.id.jammulailist,R.id.jamtelatlist, R.id.jamberakhirlist});

            listdataoutlet1.setAdapter(simpleAdapter); // Menetapkan adapter ke ListView

            // Menangani item click pada ListView
            listdataoutlet1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Mengambil data dari item yang diklik
                    idlist = ((TextView) view.findViewById(R.id.idlistt)).getText().toString();
                    jadwallist = ((TextView) view.findViewById(R.id.jadwallist)).getText().toString();
                    jam_mulailist = ((TextView) view.findViewById(R.id.jammulailist)).getText().toString();
                    jam_telatlist = ((TextView) view.findViewById(R.id.jamtelatlist)).getText().toString();
                    jam_berakhirlist = ((TextView) view.findViewById(R.id.jamberakhirlist)).getText().toString();

                    // Menetapkan nilai yang dipilih ke dalam TextView
                    idkelas.setText(idlist);
                    jadwal.setText(String.valueOf(jadwallist));
                    jam_mulai.setText(String.valueOf(jam_mulailist));
                    jam_telat.setText(jam_telatlist);
                    jam_berakhir.setText(jam_berakhirlist);

                    // Mengambil data lain untuk dikirimkan ke Absensi Activity
                    String b = nama.getText().toString();
                    String c = nis.getText().toString();
                    String d = profesi.getText().toString();
                    String e = jadwal.getText().toString();
                    String f = jam_mulai.getText().toString();
                    String g = jam_telat.getText().toString();
                    String h = jam_berakhir.getText().toString();

                    // Membuat intent untuk berpindah ke Absensi Activity dan mengirim data
                    Intent i = new Intent(getApplicationContext(), Absensi.class);
                    i.putExtra("nama", b);
                    i.putExtra("nis", c);
                    i.putExtra("profesi", d);
                    i.putExtra("kelas", e);
                    i.putExtra("jam_mulai", f);
                    i.putExtra("jam_telat", g);
                    i.putExtra("jam_berakhir", h);
                    startActivity(i); // Menjalankan activity Absensi
                }
            });
        }
    }




}