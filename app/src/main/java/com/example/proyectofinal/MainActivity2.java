package com.example.proyectofinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    Button btnEditar, btnEliminar;
    EditText etNombre, etEdad, etTelefono, etCorreo, etID;
    String id;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        requestQueue = Volley.newRequestQueue(this);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            id = extras.getString("id");
        }

        initUI();

        leerUsuario();

        btnEliminar.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
    }

    private void initUI(){
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        etTelefono = findViewById(R.id.etTelefono);
        etCorreo = findViewById(R.id.etCorreo);
        etID = findViewById(R.id.etID);

        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
    }

    private void leerUsuario(){
        String URL = "http://192.168.0.5/proyectofinal/buscar.php?id" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String nombre, edad, telefono, correo;
                        try {
                            nombre = response.getString("nombre");
                            edad = response.getString("edad");
                            telefono = response.getString("telefono");
                            correo = response.getString("correo");

                            etNombre.setText(nombre);
                            etEdad.setText(edad);
                            etTelefono.setText(telefono);
                            etCorreo.setText(correo);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void  onClick(View v){
        int id = v.getId();

        if (id == R.id.btnEditar){

            String nombre = etNombre.getText().toString().trim();
            String edad = etEdad.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();

            actualizarUsuario(nombre, edad, telefono, correo);

        }else if (id == R.id.btnEliminar){
            String idUsuario = etID.getText().toString().trim();

            eliminarUsuario(idUsuario);
        }
    }

    private void actualizarUsuario(final String nombre, final String edad, final String telefono, final String correo) {
        String URL = "http://192.168.0.5/proyectofinal/editar.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity2.this, "Actualización Exitosa", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("nombre", nombre);
                params.put("edad", edad);
                params.put("telefono", telefono);
                params.put("correo", correo);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void eliminarUsuario(final String idUsuario) {
        String URL = "http://192.168.0.5/proyectofinal/eliminar.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("id", idUsuario);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
