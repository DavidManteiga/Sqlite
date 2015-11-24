package com.example.dm2.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {
private EditText id,nombre, email;
    private Button insertar,modificar,eliminar,listar;
    private TextView txtlistado;
    UsuariosSQLite usubd;

    String nombre_introducido;
    String email_itroducido;
    String id_introducido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        usubd= new UsuariosSQLite(this,"BDUsuarios",null,4);

        txtlistado=(TextView) findViewById(R.id.txtlistado);

        id = (EditText) findViewById(R.id.id);
        nombre = (EditText) findViewById(R.id.nombre);
        email = (EditText)findViewById(R.id.email);

        insertar = (Button) findViewById(R.id.insertar);
        modificar = (Button) findViewById(R.id.modificar);
        eliminar = (Button) findViewById(R.id.eliminar);
        listar = (Button) findViewById(R.id.listar);

        insertar.setOnClickListener(this);
        modificar.setOnClickListener(this);
        eliminar.setOnClickListener(this);
        listar.setOnClickListener(this);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        ///////////////////////Insertar/////////////////////
        if (v.getId()==findViewById(R.id.insertar).getId()) {
            nombre_introducido=nombre.getText().toString();
            email_itroducido= email.getText().toString();


            SQLiteDatabase db=usubd.getWritableDatabase();

            String query="insert into Usuarios (nombre,email) values('"+nombre_introducido+"','"+email_itroducido+"')";
            db.execSQL(query);

            db.close();
            Toast.makeText(this, "Nuevo usuario agregado", Toast.LENGTH_SHORT).show();

        }


        ///////////////////////Modificar/////////////////////
        if (v.getId()==findViewById(R.id.modificar).getId()) {

            nombre_introducido=nombre.getText().toString();
            email_itroducido= email.getText().toString();
            id_introducido=id.getText().toString();
            SQLiteDatabase db=usubd.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("nombre",nombre_introducido);
            valores.put("email",email_itroducido);

//Actualizamos el registro en la base de datos
            db.update("Usuarios", valores, "idUsuario=" + Integer.parseInt(id_introducido) + "", null);

            db.close();
            Toast.makeText(this, "Usuario modificado", Toast.LENGTH_SHORT).show();

        }


        ///////////////////////Eliminar/////////////////////
        if (v.getId()==findViewById(R.id.eliminar).getId()) {

            id_introducido=id.getText().toString();
            SQLiteDatabase db=usubd.getWritableDatabase();
            db.delete("Usuarios", "idUsuario=" + Integer.parseInt(id_introducido) + "", null);
            db.close();
            Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show();

        }


        ///////////////////////Listar/////////////////////
        if (v.getId()==findViewById(R.id.listar).getId()) {
            txtlistado.setText("");
            SQLiteDatabase db=usubd.getReadableDatabase();
            Cursor c=db.rawQuery(" SELECT * FROM Usuarios  ", null);
            if(c.moveToFirst())
            {
                do {
                    int id_usu=c.getInt(0);
                    String nombre_usu=c.getString(1);
                    String email_usu=c.getString(2);
                    txtlistado.append(id_usu+" - "+nombre_usu+" - "+email_usu+"\n");
                }while(c.moveToNext());
            }

            db.close();
        }
    }
}
