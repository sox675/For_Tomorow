package com.sox675.fortomorow;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForTomorowActivity extends Activity   {
	Button button_login;
	EditText login;
	EditText password;
	String datos1="";
	String datos2="";
	String idv="";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		traerdatos();
		
	}

	private void traerdatos() {
		button_login = (Button) findViewById(R.id.Button_Login);
		login = (EditText) findViewById(R.id.EditTextLogin);
		password = (EditText) findViewById(R.id.EditTextPassword);
	}

	public void iniciarSesion(View view) {

		String nombrev = login.getText().toString();
		String passv = password.getText().toString();

		AndroidSQLite senaWizard = new AndroidSQLite(getApplicationContext(),
				"ForTomorowBD", null, 1);

		SQLiteDatabase db = senaWizard.getWritableDatabase();
		String[] args = new String[] { nombrev };
		Cursor c = db.rawQuery(
				"select login,password,id from usuarios where login=?", args);

		if (c.moveToFirst()) {
			do {

				datos1 = c.getString(0);
				datos2 = c.getString(1);
				idv = c.getString(2);
			} while (c.moveToNext());

		}
		if (nombrev.equalsIgnoreCase("") || passv.equalsIgnoreCase("")) {
			Toast.makeText(this, "DEBE LLENAR LOS CAMPOS", Toast.LENGTH_SHORT)
					.show();

		} else {
			if (datos1.equals(nombrev) && datos2.equals(passv)) {
				Toast.makeText(this, "Datos Correctos,Iniciando Sesion...!",
						Toast.LENGTH_SHORT).show();
				login.setText("");
				password.setText("");

				Intent intent = new Intent(ForTomorowActivity.this,
						AndroidTabLayoutActivity.class);
				Bundle b = new Bundle();
				b.putString("id", idv);
				intent.putExtras(b);
				startActivity(intent);

			} else {
				Toast.makeText(this, "Login o Pass Incorrectos!!",
						Toast.LENGTH_SHORT).show();
				login.setText("");
				password.setText("");
			}
		}
	}
}