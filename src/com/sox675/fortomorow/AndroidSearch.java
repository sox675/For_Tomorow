package com.sox675.fortomorow;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class AndroidSearch extends Activity {

	EditText buscar;
	ListView listView;
	ArrayList datos;
	public String user = null;
	private static final int DIALOG_CONFIRM = 1;

	// FILTRO

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		traerdatos();

	}

	private void traerdatos() {
		buscar = (EditText) findViewById(R.id.EditTextBuscar);

	}

	public void buscar(View view) {

		AndroidSQLite senaWizard = new AndroidSQLite(getApplicationContext(),
				"ForTomorowBD", null, 1);

		SQLiteDatabase db = senaWizard.getWritableDatabase();
		datos = new ArrayList();
		listView = (ListView) findViewById(R.id.ListViewBusqueda);

		String ingreso = buscar.getText().toString() + "%";

		String[] args = new String[] { ingreso };
		Cursor c = db
				.rawQuery(
						"select nombre_completo from usuarios where nombre_completo like ?",
						args);
		if (c.moveToFirst()) {
			do {

				datos.add(c.getString(0));
			} while (c.moveToNext());

		} else {
			datos.add("No Se Encontraron Datos!!");
		}

		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, datos);
		listView.setAdapter(adaptador);

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				showDialog(DIALOG_CONFIRM);
				user = (String) arg0.getItemAtPosition(arg2);

			}
		});

	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialogo = null;

		switch (id) {
		case DIALOG_CONFIRM:
			dialogo = crearDialogConfirm();
			break;
		default:
			dialogo = null;
			break;
		}
		return dialogo;
	}

	private Dialog crearDialogConfirm() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Visualizar Turnos");
		builder.setMessage("¿Desea Ver Estos Turnos ?");
		builder.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						
						AndroidSQLite senaWizard = new AndroidSQLite(
								getApplicationContext(), "ForTomorowBD", null,
								1);

						SQLiteDatabase db = senaWizard.getWritableDatabase();
						String[] argu = new String[] { user };
						Cursor cursor = db
								.rawQuery(
										"select id from usuarios where nombre_completo=?",
										argu);
						String idv = null;

						if (cursor.moveToFirst()) {
							do {
								idv = cursor.getString(0);
							} while (cursor.moveToNext());
						}

						Intent SearchIntent = new Intent(AndroidSearch.this,
								AndroidTurnosAgenos.class);
						Bundle mis = new Bundle();
						mis.putString("idpersona", idv);
						SearchIntent.putExtras(mis);
						startActivity(SearchIntent);

						dialog.cancel();
					}
				});

		builder.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		return builder.create();
	}
}