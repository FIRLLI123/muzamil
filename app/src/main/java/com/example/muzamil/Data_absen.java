package com.example.muzamil;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Data_absen extends AppCompatActivity {
    ListView listtest1;
    TextView id_siswa, nama_siswa, tanggal;
    Button caritanggal;
    public static String LINK, idlist, tanggallist, jamlist, kecamatanlist, absenlist, keteranganlist, statuslist, pendinglist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_absen);

        listtest1 = (ListView) findViewById(R.id.listtest);

        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        id_siswa = (TextView) findViewById(R.id.id_siswa);
        nama_siswa = (TextView) findViewById(R.id.nama_siswa);
        tanggal = (TextView) findViewById(R.id.tanggal);

        caritanggal = (Button) findViewById(R.id.caritanggal);

        // Mendapatkan data yang diteruskan melalui Intent
        Intent i = getIntent();
        String kiriman = i.getStringExtra("id_siswa");
        id_siswa.setText(kiriman);
        String kiriman2 = i.getStringExtra("nama_siswa");
        nama_siswa.setText(kiriman2);

        // Menampilkan tanggal saat ini pada TextView
        tanggal.setText(getCurrentDate());

        // Menangani klik pada button caritanggal
        caritanggal.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Memanggil method list untuk menampilkan data
                list();
            }

        });

        // Menangani klik pada TextView tanggal untuk memilih tanggal
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog1(); // Menampilkan DatePickerDialog
            }
        });

        // Memanggil method list untuk menampilkan data saat pertama kali dibuka
        list();
    }

    private void showDateDialog1(){
        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Inisialisasi DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tanggal.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    // Mengambil tanggal saat ini dan mengembalikannya dalam format yang diinginkan
    public String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new Locale("id", "ID")); // Format tanggal dalam bahasa Indonesia
        return dateFormat.format(c.getTime());
    }

    private void list() {
        // Bersihkan data sebelum mengisi kembali
        aruskas.clear();
        listtest1.setAdapter(null);

        // Mengirimkan request POST ke server untuk mendapatkan data absensi berdasarkan tanggal dan id_siswa
        AndroidNetworking.post(Config.host + "list_absen_ortu.php")
                .addBodyParameter("tanggal", tanggal.getText().toString())
                .addBodyParameter("id_siswa", id_siswa.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.optJSONArray("result");

                            // Proses data absen jika ada
                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject responses = jsonArray.getJSONObject(i);
                                    // Membuat HashMap untuk menyimpan data absen
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("id", responses.optString("id"));
                                    map.put("nis", responses.optString("nis"));
                                    map.put("nama", responses.optString("nama"));
                                    map.put("profesi", responses.optString("profesi"));
                                    map.put("jam", responses.optString("jam"));
                                    map.put("tanggal", responses.optString("tanggal"));
                                    map.put("kelas", responses.optString("kelas"));
                                    map.put("id_siswa", responses.optString("id_siswa"));
                                    map.put("nama_siswa", responses.optString("nama_siswa"));
                                    map.put("keterangan", responses.optString("keterangan"));
                                    aruskas.add(map);
                                }
                            }

                            // Menghubungkan data dengan adapter setelah proses data selesai
                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // Menangani kesalahan jika terjadi masalah saat request
                    }
                });
    }

    private void Adapter(){
        // Membuat CustomAdapter dan menghubungkannya dengan ListView
        Data_absen.CustomAdapter customAdapter = new Data_absen.CustomAdapter(this, aruskas, R.layout.list_absen_fix,
                new String[]{"id","nis", "nama", "profesi", "jam", "tanggal", "kelas", "id_siswa", "nama_siswa", "keterangan"},
                new int[]{R.id.id_list, R.id.nis_list, R.id.nama_list, R.id.profesi_list, R.id.jam_list, R.id.tanggal_list, R.id.kelas_list, R.id.id_siswa_list, R.id.nama_siswa_list, R.id.keterangan_list});

        listtest1.setAdapter(customAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Menangani klik pada item listview (bisa ditambahkan fungsionalitas lebih lanjut)
            }
        });
    }

    private class CustomAdapter extends SimpleAdapter {

        public CustomAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            // Mendapatkan data untuk absen dan jam dari aruskas
            String absenListAbsen = aruskas.get(position).get("absen");
            String jamListAbsen = aruskas.get(position).get("jam");

            // Bisa ditambahkan logika tambahan untuk memodifikasi tampilan berdasarkan data

            return view;
        }
    }
}



