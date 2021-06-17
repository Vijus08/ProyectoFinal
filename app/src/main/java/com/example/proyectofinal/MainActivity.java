package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etNombre, etEdad, etTelefono, etCorreo, etID;
    Button btnCrear, btnBuscar, segundaActividad;

    RequestQueue requestQueue;

    private static final String URL1 = "http://192.168.0.5/proyectofinal/save.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        segundaActividad = findViewById(R.id.segundaActividad);

        segundaActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        requestQueue = Volley.newRequestQueue(this);


        initUI();

        btnCrear.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);
    }

    private void initUI(){
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        etTelefono = findViewById(R.id.etTelefono);
        etCorreo = findViewById(R.id.etCorreo);
        etID = findViewById(R.id.etID);

        btnCrear = findViewById(R.id.btnCrear);
        btnBuscar = findViewById(R.id.btnBuscar);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();

        if (id == R.id.btnCrear){
            String nombre = etNombre.getText().toString().trim();
            String edad = etEdad.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String email = etCorreo.getText().toString().trim();

            crearUsuario(nombre, edad, telefono, email);

        }else  if (id == R.id.btnBuscar){
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("id", etID.getText().toString().trim());
            startActivity(intent);
        }
    }

    private void crearUsuario(final String nombre, final String edad, final String telefono, final String email) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Toast.makeText(MainActivity.this, "Correcto", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                }

        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("edad", edad);
                params.put("telefono", telefono);
                params.put("email", email);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}