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

        pDialog = new ProgressDialog(context);
        e1 = (EditText) findViewById(R.id.username);//kolom login
        e2 = (EditText) findViewById(R.id.password);//kolom password
        button = (LinearLayout) findViewById(R.id.Button);//button login
        update1 = (TextView) findViewById(R.id.update); //text untuk update aplkasi
        namasales1 = (TextView) findViewById(R.id.namasales); //id untuk update



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


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginn();



            }
        });


    }

    private void loginn() {
        //Dapatkan nilai dari edittext
        final String username = e1.getText().toString().trim();
        final String password = e2.getText().toString().trim();
        pDialog.setMessage("Login Process...");
        showDialog();

        //   Buat String Request
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

    //parsing
    private void gotoCourseActivity() {
        String a = e1.getText().toString();
        Intent intent = new Intent(context, MenuAbsen.class);
        intent.putExtra("nis",""+a+"");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

//method Credentials
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




}