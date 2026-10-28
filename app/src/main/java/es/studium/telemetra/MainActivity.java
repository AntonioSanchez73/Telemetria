package es.studium.telemetra;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.os.LocaleListCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnEnviar;
    Button btnLimpiar;

    EditText txtNombre;
    EditText txtApellidos;
    EditText txtEdad;

    RadioButton radioHombre;
    RadioButton radioMujer;

    Spinner spinCivil;
    Switch switHijos;

    TextView txtMensaje;
    Spinner spinIdioma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // VALORES
        btnEnviar   = findViewById(R.id.btnEnviar);
        btnLimpiar  = findViewById(R.id.btnLimpiar);
        txtNombre   = findViewById(R.id.txtNombre);
        txtApellidos= findViewById(R.id.txtApellido);
        txtEdad     = findViewById(R.id.txtEdad);
        radioHombre = findViewById(R.id.radioHombre);
        radioMujer  = findViewById(R.id.radioMujer);
        spinCivil   = findViewById(R.id.spinCivil);
        switHijos   = findViewById(R.id.switchHijos);
        txtMensaje  = findViewById(R.id.txtMensaje);
        spinIdioma  = findViewById(R.id.spinIdioma);

        // -- BOTON ENVIAR ------------------------------- //
        btnEnviar.setOnClickListener(v -> {

            String  nombre      = "";
            String  apellidos   = "";
            Integer edad        = 0;
            String  genero      = "";
            String  civil       = "";
            Boolean hijos       = false;

            ArrayList<String> mensajeFaltan = new ArrayList<>();
            if (estaVacio(txtNombre.getText().toString())){ mensajeFaltan.add("el nombre");
            } else { nombre = txtNombre.getText().toString(); }

            if (estaVacio(txtApellidos.getText().toString())){ mensajeFaltan.add("los apellidos");
            } else { apellidos = txtApellidos.getText().toString(); }

            if (estaVacio(txtEdad.getText().toString())){ mensajeFaltan.add("la edad");
            } else { edad = Integer.parseInt(txtEdad.getText().toString()); }

            if (radioHombre.isChecked()){
                genero = "Hombre";
            } else if (radioMujer.isChecked()) {
                genero = "Mujer";
            }
            if (!estaVacio(spinCivil.getSelectedItem().toString())){
                civil = spinCivil.getSelectedItem().toString();
            }
            hijos = switHijos.isChecked();

            if (mensajeFaltan.size() == 0){
                // Los campos están completos
                String mensajeFinal = nombre + ", " + apellidos + ", " + mayorEdad(edad) + ", " + genero + ", " + civil + " y " + tieneHijos(hijos) + ".";
                Toast.makeText(this, mensajeFinal, Toast.LENGTH_SHORT).show();
            } else {
                // Faltan campos por rellenar
                txtMensaje.setText(mensajeAlert(mensajeFaltan));
            }
        });

        // -- BOTON LIMPIAR ------------------------------- //
        btnLimpiar.setOnClickListener(v -> {
            txtNombre.setText("");
            txtApellidos.setText("");
            txtEdad.setText("");
            radioHombre.setChecked(false);
            radioMujer.setChecked(false);
            spinCivil.setSelection(0);
            switHijos.setChecked(false);
            txtMensaje.setText("");
        });

        // -- SELECCIONAR IDIOMA --------------------------- //
        spinIdioma.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                        String idioma;
                        switch (position) {
                            case 0:
                                idioma = "es";
                                break;
                            case 1:
                                idioma = "en";
                                break;
                            case 2:
                                idioma = "pt";
                                break;
                            default:
                                idioma = "es";
                        }
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(idioma));
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );

    }

    protected String mensajeAlert(ArrayList<String> mensajeFaltan){
        String mensaje = "Faltan por rellenar: ";
        Boolean primero = true;
        for (int i = 0; i < mensajeFaltan.size(); i++){
            if (i == mensajeFaltan.size() -1){
                if (primero){
                    primero = false;
                    mensaje += mensajeFaltan.get(i);
                } else {
                    mensaje += " y " + mensajeFaltan.get(i);
                }
            } else {
                if (primero){
                    primero = false;
                    mensaje += mensajeFaltan.get(i);
                } else {
                    mensaje += ", " + mensajeFaltan.get(i);
                }
            }
        }
        return mensaje;
    }

    protected Boolean estaVacio(String valor){
        Boolean vacio = false;
        if (valor == null || valor.equals("") || valor.equals("0")){
            vacio = true;
        }
        return vacio;
    }

    protected String mayorEdad(Integer edad){
        String mayor = "no es mayor de edad";
        if (edad >= 18){
            mayor = "mayor de edad";
        }
        return mayor;
    }

    protected String tieneHijos(Boolean hijos){
        String tiene = "no tiene hijos";
        if (hijos){
            tiene = "tiene hijos";
        }
        return tiene;
    }
}