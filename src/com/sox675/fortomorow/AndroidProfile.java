package com.sox675.fortomorow;


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidProfile extends Activity {
	EditText nombres;
	EditText email;
	EditText telefono;
	EditText password;
	EditText confirm_password;
	String nombresv = "";
	String emailv = "";
	String numero_movilv = "";
	String passwordv = "";
	String confirm_passwordv = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		traerdatos();
		cargar();
		

	}
	
	private void cargar() {
		Bundle bundle = getIntent().getExtras();
		String idusuario = bundle.getString("idpersona");

		AndroidSQLite senaWizard = new AndroidSQLite(getApplicationContext(),
				"ForTomorowBD", null, 1);

		SQLiteDatabase db = senaWizard.getWritableDatabase();
		 String[] args = new String[] { idusuario };
		 Cursor c = db.rawQuery(
		 "select nombre_completo,email,numero_movil,password,confirm_password from usuarios where id=?",
		 args);
		
		 if (c.moveToFirst()) {
		 do {
		
		 nombresv = c.getString(0);
		 emailv = c.getString(1);
		 numero_movilv= c.getString(2);
		 passwordv= c.getString(3);
		 confirm_passwordv= c.getString(4);
		 } while (c.moveToNext());
		
		 }
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		cargar();
		
		 nombres.setText(nombresv+"");
		 email.setText(emailv+"");
		 telefono.setText(numero_movilv+"");
		 password.setText(passwordv+"");
		 confirm_password.setText(confirm_passwordv+"");
	}

	private void traerdatos() {

		nombres = (EditText) findViewById(R.id.EditTextNombres);
		email = (EditText) findViewById(R.id.EditTextEmail);
		telefono = (EditText) findViewById(R.id.EditTextTelefono);
		password = (EditText) findViewById(R.id.EditTextPassword);
		confirm_password = (EditText) findViewById(R.id.EditTextConfirm);
	}
	
	public void actualizar(View view) {
        Bundle bundle = getIntent().getExtras();
		String idusuario = bundle.getString("idpersona");
        AndroidSQLite senaWizard = new AndroidSQLite(getApplicationContext(),
				"ForTomorowBD", null, 1);

		SQLiteDatabase db = senaWizard.getWritableDatabase();
		ContentValues valores = new ContentValues();
		
		valores.put("nombre_completo", nombres.getText().toString());
		valores.put("email",email.getText().toString());
		valores.put("numero_movil",telefono.getText().toString());
		valores.put("password",password.getText().toString());
		valores.put("confirm_password",confirm_password.getText().toString());

		String[] args = new String[] { idusuario };
		 
		db.update("usuarios", valores, "id=?", args);
		
		
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout,
		                               (ViewGroup) findViewById(R.id.toast_layout_root));

		ImageView image = (ImageView) layout.findViewById(R.id.image);
		image.setImageResource(R.drawable.clean);
		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText("Datos Actualizados Correctamente!!");

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
	
	
	
}