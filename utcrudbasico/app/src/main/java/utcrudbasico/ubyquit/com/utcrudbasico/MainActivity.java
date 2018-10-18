package utcrudbasico.ubyquit.com.utcrudbasico;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //Declaramos los elementos de la interfaz
    private Button btnCrear;
    private Button btnVer;
    private Button btnEliminar;
    private EditText editNombre;
    private EditText editComentario;
    private EditText txtNombre;
    private EditText txtComentario;

    //Declarar el spinner y su adapter
    private Spinner spinComentarios;
    private ArrayAdapter spinnerAdapter;

    //Lista de comentarios y comentario actual
    private ArrayList <Comentario> lista;
    private Comentario c;

    //Controlador de la DB
    private MyOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos los elementos de la interfaz
        editNombre = (EditText) findViewById(R.id.editNombre);
        editComentario = (EditText) findViewById(R.id.editComentario);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtComentario = (EditText) findViewById(R.id.txtComentario);

        //Los elementos del panel inferios que no seran editables
        txtNombre.setEnabled(false);
        txtComentario.setEnabled(false);

        btnCrear = (Button) findViewById(R.id.btnCrear);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnVer = (Button) findViewById(R.id.btnVer);

        btnCrear.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnVer.setOnClickListener(this);

        //inicializamos la db
        db = new MyOpenHelper(this);

        //Inciamos el spinner y la lista de comentatios
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lista);
        spinComentarios.setAdapter(spinnerAdapter);
        spinComentarios.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v){
        //Acciones para cada boton
        switch (v.getId()){
            case R.id.btnCrear:
                //Insertamos un nuevo elemento a la base de datos
                db.insertar(editNombre.getText().toString(), editComentario.getText().toString());
                //Actualizamos la lista de comentarios
                lista=db.getComments();
                //Actualizamos el adapter y lo asociamos de nuevo al spinner
                spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,lista);
                spinComentarios.setAdapter(spinnerAdapter);
                //limpiar el formalario
                editNombre.setText("");
                editComentario.setText("");

                break;

            case R.id.btnVer:
                //Si hay algun comentario selecionado mostrarmos sus valores en la parte inferios
                if (c != null){
                    txtNombre.setText(c.getNombre());
                    txtComentario.setText(c.getComentario());
                }
                break;

            case R.id.btnEliminar:
                if(c!=null){
                //Di hay algun comentatio selecciando lo borramos de la db y actulizamo el spiner
                db.borrar(c.getId());
                lista = db.getComments();
                //Actualizamos el adapter y lo asociamos de nuevo al spinner
                spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,lista);
                spinComentarios.setAdapter(spinnerAdapter);
                //limpiar el formalario
                editNombre.setText("");
                editComentario.setText("");
                //Eliminamos el comentario actual
                c=null;
                }
                break;
        }

    }

    //Controlador de elementos  seleccionando en el spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinComentarios){
            //si hay elementos en la base de datos, establecemos el comentario actul a partir del
            //del indice del elemento seleccionado en el spinner
            if (lista.size()>0){
                c = lista.get(position);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView <?> parent){

    }
}
