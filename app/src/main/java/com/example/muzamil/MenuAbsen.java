package com.example.muzamil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.muzamil.helper.Config;
//import com.example.muzamil.helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MenuAbsen extends AppCompatActivity {
TextView nis, nama, profesi, id_siswa, nama_siswa;

TextView masuk,telat,izinn,tidakmasuk, mata_pelajaran, selengkapnya, tanggal;

LinearLayout kehadiran, izin, data_absen, logout;

    Handler mHandler;
    private ProgressDialog pDialog;
    private Context context;

    private AlertDialog alert;

    ListView listtest1;
    public static String LINK, idlist, tanggallist, jamlist, kecamatanlist, absenlist, keteranganlist, statuslist, pendinglist;
    ArrayList<HashMap<String, String>> aruskas = new ArrayList<HashMap<String, String>>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_absen);

        this.mHandler = new Handler();
        m_Runnable.run();

        context = MenuAbsen.this;
        pDialog = new ProgressDialog(context);

        listtest1 = (ListView) findViewById(R.id.listtest);

        nis = (TextView) findViewById(R.id.nis);
        nama = (TextView) findViewById(R.id.nama);
        profesi = (TextView) findViewById(R.id.profesi);
        id_siswa = (TextView) findViewById(R.id.id_siswa);
        nama_siswa = (TextView) findViewById(R.id.nama_siswa);
        mata_pelajaran = (TextView) findViewById(R.id.mata_pelajaran);


        masuk = (TextView) findViewById(R.id.masuk);
        telat = (TextView) findViewById(R.id.telat);
        izinn = (TextView) findViewById(R.id.izinn);
        tidakmasuk = (TextView) findViewById(R.id.tidakmasuk);

        tanggal = (TextView) findViewById(R.id.tanggal);

        selengkapnya = (TextView) findViewById(R.id.selengkapnya);


        kehadiran = (LinearLayout) findViewById(R.id.kehadiran);
        izin = (LinearLayout) findViewById(R.id.izin);
        data_absen = (LinearLayout) findViewById(R.id.data_absen);
        logout = (LinearLayout) findViewById(R.id.logout);


        Intent i = getIntent();
        String kiriman = i.getStringExtra("nis");
        nis.setText(kiriman);

        tanggal.setText(getCurrentDate());



        kehadiran.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                if (profesi.getText().toString().equals("GURU")) {
                    String a = nis.getText().toString();
                    String b = nama.getText().toString();
                    String c = profesi.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Data_kelas.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("nis",""+a+"");
                    i.putExtra("nama",""+b+"");
                    i.putExtra("profesi",""+c+"");


                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else if (profesi.getText().toString().equals("IT")) {
                    String a = nis.getText().toString();
                    String b = nama.getText().toString();
                    String c = profesi.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Data_kelas.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("nis",""+a+"");
                    i.putExtra("nama",""+b+"");
                    i.putExtra("profesi",""+c+"");
                }else if (profesi.getText().toString().equals("null")) {
                    Toast.makeText(getApplicationContext(), "KONEKSI LEMAH TUNGGU SEBENTAR", Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(getApplicationContext(), "TIDAK MEMILIKI AKSES", Toast.LENGTH_LONG).show();

                }



            }

        });

        izin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                if (profesi.getText().toString().equals("ortu")) {
                    String a = nis.getText().toString();
                    String b = nama.getText().toString();
                    String c = profesi.getText().toString();
                    String d = nama_siswa.getText().toString();
                    String e = id_siswa.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Data_kelas_ortu.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("nis",""+a+"");
                    i.putExtra("nama",""+b+"");
                    i.putExtra("profesi",""+c+"");
                    i.putExtra("nama_siswa",""+d+"");
                    i.putExtra("id_siswa",""+e+"");



                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else if (profesi.getText().toString().equals("IT")) {
                    String a = nis.getText().toString();
                    String b = nama.getText().toString();
                    String c = profesi.getText().toString();
                    String d = nama_siswa.getText().toString();
                    String e = id_siswa.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Data_kelas_ortu.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("nis",""+a+"");
                    i.putExtra("nama",""+b+"");
                    i.putExtra("profesi",""+c+"");
                    i.putExtra("nama_siswa",""+d+"");
                    i.putExtra("id_siswa",""+e+"");


                }else if (profesi.getText().toString().equals("null")) {
                    Toast.makeText(getApplicationContext(), "KONEKSI LEMAH TUNGGU SEBENTAR", Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(getApplicationContext(), "TIDAK MEMILIKI AKSES", Toast.LENGTH_LONG).show();

                }



            }

        });



        data_absen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String profesiValue = profesi.getText().toString();
                Log.d("Profesi Value", profesiValue); // Log untuk mengecek nilai profesi

                if (profesiValue.equals("ortu")) {
                    String a = id_siswa.getText().toString();
                    String b = nama_siswa.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Data_absen.class);
                    i.putExtra("id_siswa", a);
                    i.putExtra("nama_siswa", b);

                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else if (profesiValue.equals("KEPSEK")) {
                    Log.d("Profesi Check", "KEPSEK detected"); // Log untuk memastikan blok ini dijalankan

                    String a = nis.getText().toString();
                    String b = nama.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Data_absen_kepsek.class);
                    i.putExtra("nis", a);
                    i.putExtra("nama", b);
                    i.putExtra("profesi", profesiValue);

                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else if (profesiValue.equals("null")) {
                    Toast.makeText(getApplicationContext(), "KONEKSI LEMAH TUNGGU SEBENTAR", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "TIDAK MEMILIKI AKSES", Toast.LENGTH_LONG).show();
                }
            }
        });



        selengkapnya.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                if (profesi.getText().toString().equals("ortu")) {
                    String a = id_siswa.getText().toString();
                    String b = nama_siswa.getText().toString();
                    String c = profesi.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Data_absen.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("id_siswa",""+a+"");
                    i.putExtra("nama_siswa",""+b+"");


                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else if (profesi.getText().toString().equals("IT")) {
                    String a = nis.getText().toString();
                    String b = nama.getText().toString();
                    String c = profesi.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Data_absen.class);
                    //Intent i = new Intent(getApplicationContext(), Menuutamanew.class);
                    i.putExtra("nis",""+a+"");
                    i.putExtra("nama",""+b+"");
                    i.putExtra("profesi",""+c+"");
                }else if (profesi.getText().toString().equals("null")) {
                    Toast.makeText(getApplicationContext(), "KONEKSI LEMAH TUNGGU SEBENTAR", Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(getApplicationContext(), "TIDAK MEMILIKI AKSES", Toast.LENGTH_LONG).show();

                }



            }

        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan dialog konfirmasi
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuAbsen.this);
                builder.setMessage("Apakah Anda ingin logout?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetSharedPreferences();
                                // Kembali ke activity Login_V3
                                Intent intent = new Intent(MenuAbsen.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Menutup dialog
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        user();
        absenhadir();
        list();



    }

    private void resetSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Hapus semua data yang tersimpan
        editor.apply();
    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {


            // Inisialisasi TextView
            nis = (TextView) findViewById(R.id.nis);
            nama = (TextView) findViewById(R.id.nama);
            profesi = (TextView) findViewById(R.id.profesi);
            id_siswa = (TextView) findViewById(R.id.id_siswa);
            nama_siswa = (TextView) findViewById(R.id.nama_siswa);

            // Panggil fungsi-fungsi lainnya
            //user();
            absenhadir();
            absentelat();
            absenizin();
            absentidakmasuk();

            // Mengecek apakah alert belum ditampilkan
            if (alert == null) {
                // Membuat dan menampilkan AlertDialog untuk loading
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuAbsen.this);
                builder.setMessage("Loading...")
                        .setCancelable(false); // Agar tidak bisa ditutup oleh user
                alert = builder.create();
                alert.show(); // Menampilkan alert
            }

            // Mengecek profesi setelah delay
            if (profesi.equals("null")) {
                // Tampilkan pesan koneksi lemah setelah 10 detik
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (profesi == null) {
                            alert.setMessage("Koneksi mu lemah, aplikasi akan keluar");

                            // Menunggu 2 detik lalu keluar
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish(); // Menutup aplikasi
                                }
                            }, 2000); // Delay 2 detik untuk menampilkan pesan "koneksi lemah"
                        }
                    }
                }, 10000); // 10 detik delay
            } else {
                // Jika profesi tidak null, tutup alert dan tampilkan Toast
                if (alert != null && alert.isShowing()) {
                    alert.dismiss();
                    Toast.makeText(MenuAbsen.this, "Aplikasi siap digunakan", Toast.LENGTH_SHORT).show();
                }
            }

            MenuAbsen.this.mHandler.postDelayed(m_Runnable, 2000);
        }

    };



    private void absenhadir() {

        AndroidNetworking.post(Config.host + "count_absenhadir.php")
                .addBodyParameter("id_siswa", id_siswa.getText().toString())
//                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
//                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //itempenjualanperdana1.setText((response.optString("item")));
                        masuk.setText((response.optString("id")));

                        if ( masuk.getText().toString().equals("null")){
                            masuk.setText("0");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }
    private void absentelat() {

        AndroidNetworking.post(Config.host + "count_absentelat.php")
                .addBodyParameter("id_siswa", id_siswa.getText().toString())
//                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
//                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //itempenjualanperdana1.setText((response.optString("item")));
                        telat.setText((response.optString("id")));

                        if ( telat.getText().toString().equals("null")){
                            telat.setText("0");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }
    private void absenizin() {

        AndroidNetworking.post(Config.host + "count_absenizin.php")
                .addBodyParameter("id_siswa", id_siswa.getText().toString())
//                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
//                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //itempenjualanperdana1.setText((response.optString("item")));
                        izinn.setText((response.optString("id")));

                        if ( izinn.getText().toString().equals("null")){
                            izinn.setText("0");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }
    private void absentidakmasuk() {

        AndroidNetworking.post(Config.host + "count_absentidakmasuk.php")
                .addBodyParameter("id_siswa", id_siswa.getText().toString())
//                .addBodyParameter("tanggaldari", tanggaldari.getText().toString())
//                .addBodyParameter("tanggalsampai", tanggalsampai.getText().toString())
//                .addBodyParameter("namasales", namasalesinputperdana1.getText().toString())
//                .addBodyParameter("tanggal", tanggalinputperdana1.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

                        //itempenjualanperdana1.setText((response.optString("item")));
                        tidakmasuk.setText((response.optString("id")));

                        if ( tidakmasuk.getText().toString().equals("null")){
                            tidakmasuk.setText("0");

                        }else{


                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }




                });

    }



    private void user() {
//        pDialog.setMessage("Verifikasi...");
//        showDialog();
//        pDialog.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(Config.host + "user_depan.php")
                .addBodyParameter("nis", nis.getText().toString())
                //.addBodyParameter("dana", dana.getText().toString())

                //.addBodyParameter("bulan", bulan1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //idoutlet1.setText((response.optString("idoutlet")));
                        //username.setText((response.optString("username")));
                        nama.setText((response.optString("nama")));
                        profesi.setText((response.optString("profesi")));
                        id_siswa.setText((response.optString("id_siswa")));
                        nama_siswa.setText((response.optString("nama_siswa")));
                        mata_pelajaran.setText((response.optString("mata_pelajaran")));
                        list();

                        //hideDialog();

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });




    }

    public String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", new Locale("id", "ID")); // Format tanggal dalam bahasa Indonesia
        return dateFormat.format(c.getTime());
    }

    private void list() {
        // Bersihkan data sebelum mengisi kembali
        aruskas.clear();
        listtest1.setAdapter(null);

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
                                    HashMap<String, String> map = new HashMap<>();
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

                                    // Kondisi untuk notifikasi jika profesi adalah "ortu" dan keterangan adalah "telat"
                                    String profesi = responses.optString("profesi");
                                    String keterangan = responses.optString("keterangan");
                                    String namaSiswa = responses.optString("nama_siswa");

                                    if ("ortu".equalsIgnoreCase(profesi) && "telat".equalsIgnoreCase(keterangan)) {
                                        showNotification("Perhatian", namaSiswa + " terlambat hari ini.");
                                    }
                                }
                            }

                            Adapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // Handle error
                    }
                });
    }

    private void Adapter() {
        MenuAbsen.CustomAdapter customAdapter = new MenuAbsen.CustomAdapter(this, aruskas, R.layout.list_absen_fix,
                new String[]{"id", "nis", "nama", "profesi", "jam", "tanggal", "kelas", "id_siswa", "nama_siswa", "keterangan"},
                new int[]{R.id.id_list, R.id.nis_list, R.id.nama_list, R.id.profesi_list, R.id.jam_list, R.id.tanggal_list, R.id.kelas_list, R.id.id_siswa_list, R.id.nama_siswa_list, R.id.keterangan_list});

        listtest1.setAdapter(customAdapter);

        listtest1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Item click handling
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



            return view;
        }
    }

    private void showNotification(String title, String message) {
        Intent intent = new Intent(this, MenuAbsen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = getString(R.string.notification_channel_id);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.logo1)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }



}