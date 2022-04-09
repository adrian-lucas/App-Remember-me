package com.cdp.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cdp.agenda.db.DbContactos;

import java.util.Calendar;

public class NuevoActivity extends AppCompatActivity {

    EditText txtTitulo, txtDireccion, txtDescripcion;
    TextView eHora,eFecha;
    Button btnGuarda, bFecha, bHora;
    Activity actividad;
    private int dia, mes, anio, hora, minutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        bFecha = (Button) findViewById(R.id.bFecha);
        bHora = (Button) findViewById(R.id.bHora);
        eFecha = (TextView) findViewById(R.id.eFecha);
        eHora = (TextView) findViewById(R.id.eHora);
        //bFecha.setOnClickListener((View.OnClickListener) this);
        //bHora.setOnClickListener((View.OnClickListener) this);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnGuarda = findViewById(R.id.btnGuarda);
        actividad=this;
        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!txtTitulo.getText().toString().equals("") && !txtDireccion.getText().toString().equals("")) {

                    DbContactos dbContactos = new DbContactos(NuevoActivity.this);
                    long id = dbContactos.insertarContacto(txtTitulo.getText().toString(), eHora.getText().toString(),eFecha.getText().toString(),txtDireccion.getText().toString(), txtDescripcion.getText().toString());

                    if (id > 0) {
                        Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        limpiar();
                        Intent intent = new Intent(actividad, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(NuevoActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(NuevoActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void limpiar() {
        txtTitulo.setText("");
        txtDireccion.setText("");
        txtDescripcion.setText("");
        eHora.setText("");
        eFecha.setText("");
    }

  //  @Override
    public void onClick(View v) {
        if (v == bFecha) {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anio = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    eFecha.setText(year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                }
            }, 2022, mes, dia);
            datePickerDialog.show();
        }
        if (v == bHora) {
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    eHora.setText(hourOfDay + ":" + minute);
                }
            }, hora, minutos, false);
            timePickerDialog.show();
        }
    }
}