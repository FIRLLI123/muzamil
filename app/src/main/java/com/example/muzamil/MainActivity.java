package com.example.muzamil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
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

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
public class MainActivity extends AppCompatActivity {
    TextView idoutlogin1;
    TextView namaoutlogin1;
    TextView update1, namasales1;



    //EditText e1;
    //EditText e2;
    String text1, text2;

    private EditText e1;
    private EditText e2;
    private Context context;
    private AppCompatButton buttonLogin;
    private ProgressDialog pDialog;
    private LinearLayout button;

    LinearLayout call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        //call = (LinearLayout) findViewById(R.id.call);

        pDialog = new ProgressDialog(context);
        e1 = (EditText) findViewById(R.id.username);//kolom login
        e2 = (EditText) findViewById(R.id.password);//kolom password
        button = (LinearLayout) findViewById(R.id.Button);//button login
        //idoutlogin1 = (TextView) findViewById(R.id.idoutlogin);//text id outlet
        //namaoutlogin1 = (TextView) findViewById(R.id.namaoutlogin); //text namaoutlet
        update1 = (TextView) findViewById(R.id.update); //text untuk update aplkasi
        namasales1 = (TextView) findViewById(R.id.namasales); //id untuk update
///
//        Intent kolomlogin = getIntent();
//        String kiriman = kolomlogin.getStringExtra("namasales");
//        e1.setText(kiriman);
//        String kiriman2 = kolomlogin.getStringExtra("idoutlet");
//        idoutlogin1.setText(kiriman2);
//        String kiriman3 = kolomlogin.getStringExtra("namaoutlet");
//        namaoutlogin1.setText(kiriman3);


        // Periksa apakah ada nilai username dan password tersimpan dalam SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("nis", "");
        String savedPassword = sharedPreferences.getString("password", "");

        // Isi EditText dengan nilai username dan password yang tersimpan
        e1.setText(savedUsername);
        e2.setText(savedPassword);

        // Cek apakah username dan password ada di SharedPreferences
        if (!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
            // Jika sudah ada, pindah ke MenuAbsen
            loginn(); // Menutup activity saat ini
        } else {
            // Jika belum ada, biarkan pengguna memasukkan username dan password
            e1.setText(savedUsername); // Mengisi EditText username
            e2.setText(savedPassword); // Mengisi EditText password
        }


//        call.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View arg0) {
//
////                String nama_user_alert = nama_user.getText().toString();
////                String posisi_alert = posisi.getText().toString();
////                String perusahaan_alert = perusahaan.getText().toString();
//
//                String noHp = "6282249495858";
//                String pesan = "Hallo Admin Lussi, bisa bantu saya?";
//
//
//                String pakePesandanNomor =
//                        "https://api.whatsapp.com/send?phone=" + noHp + "&text=" + pesan;
//                Intent i = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse(pakePesandanNomor));
//                startActivity(i);
//
//            }
//
//        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginn();

                //e1 = (EditText) findViewById(R.id.editText2);//kolom login

            }
        });

        //updateaplikasi();

    }

    private void loginn() {
        //Getting values from edit texts
        final String username = e1.getText().toString().trim();
        final String password = e2.getText().toString().trim();
        pDialog.setMessage("Login Process...");
        showDialog();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success from server
                        if (response.contains(AppVar.LOGIN_SUCCESS)) {
                            hideDialog();
                            saveCredentials(username, password);
                            gotoCourseActivity();


                        } else {
                            hideDialog();
                            //Displaying an error message on toast
                            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        hideDialog();
                        Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(AppVar.KEY_EMAIL, username);
                params.put(AppVar.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void gotoCourseActivity() {
        String a = e1.getText().toString();

        Intent intent = new Intent(context, MenuAbsen.class);
        intent.putExtra("nis",""+a+"");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }


    private void saveCredentials(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nis", username);
        editor.putString("password", password);
        editor.apply();
    }



    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }





    public void updateaplikasi(){
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();
        KasAdapter2();
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA :"+ millisUntilFinished / 1000);
                showDialog();
                pDialog.setCanceledOnTouchOutside(false);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                hideDialog();

                if (update1.getText().toString().equals("22")) {
//            buttonabsendatang1.setEnabled(false);
//            buttonabsendatang1.setBackgroundColor(getResources().getColor(R.color.abu));
                    Toast.makeText(getApplicationContext(), "INVEN siap di gunakan",
                            Toast.LENGTH_LONG).show();

                    //1
                    //jika form Email belum di isi / masih kosong
                    //link1.setError("harus diisi");
                    // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();

                }else if (update1.getText().toString().equals("null")) {
//            buttonabsendatang1.setEnabled(false);
//            buttonabsendatang1.setBackgroundColor(getResources().getColor(R.color.abu));
                    pDialog.setMessage("Koneksi mu Bermasalah, Silahkan Keluar Jarvis Terlebih Dahulu lalu Cek Koneksi mu");
                    showDialog();

                    //1
                    //jika form Email belum di isi / masih kosong
                    //link1.setError("harus diisi");
                    // Toast.makeText(getApplicationContext(), "Kolom Tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else {

                    validasiaplikasi();

                }

                //mTextField.setText("done!");
            }
        }.start();

        //






    }




    private void KasAdapter2() {
//        pDialog.setMessage("TUNGGU SEBENTAR, SEDANG VERIFIKASI DATA");
//        showDialog();
        AndroidNetworking.post(Config.host + "updateaplikasi.php")
                .addBodyParameter("id", namasales1.getText().toString())
                //.addBodyParameter("tanggal", tanggal1.getText().toString())
                //.addBodyParameter("bulan1", bulan1.getText().toString())
                //.addBodyParameter("bulan2", bulan2.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response



                        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                        //username1.setText((response.optString("username")));
                        update1.setText((response.optString("update")));
                        //nama221.setText((response.optString("nama")));
                        hideDialog();
                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });





    }


    public void validasiaplikasi() {
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle("Hallo Team Reka Mitra");
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this)

                .setTitle(R.string.app_name)
                .setMessage("Jarvis mu versi lama, silahkan update terlebih dahulu ya...")
                .setPositiveButton("Update sekarang", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_VIEW);
                        //i.addCategory(Intent.CATEGORY_APP_BROWSER);
                        i.setData(Uri.parse("http://rekamitrayasa.com/download"));
                        startActivity(i);

                    }
                })
                .setNegativeButton("Keluar Aplikasi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);


    }




}