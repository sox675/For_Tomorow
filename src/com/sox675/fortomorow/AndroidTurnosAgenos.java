package com.sox675.fortomorow;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class AndroidTurnosAgenos extends Activity {

	TextView lunes;
	TextView martes;
	TextView miercoles;
	TextView jueves;
	TextView viernes;
	TextView sabado;
	TextView domingo;
	ArrayList datos;
	TextView nombre;
	String nombrev="";	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.turnos_agenos);	

		traerdatos();
		cargarturnos();

	}

	private void cargarturnos() {
		Bundle bundle = getIntent().getExtras();
		String idusuario = bundle.getString("idpersona");
		datos = new ArrayList();

		AndroidSQLite senaWizard = new AndroidSQLite(getApplicationContext(),
				"ForTomorowBD", null, 1);

		SQLiteDatabase db = senaWizard.getWritableDatabase();
		String[] args = new String[] { idusuario };
		Cursor c = db.rawQuery("select tu.turno,us.nombre_completo from turnos tu,usuarios us where us.id = tu.usuario_id and tu.usuario_id=?",
				args);
		Integer contador = 0;

		if (c.moveToFirst()) {
			do {
				contador = contador + 1;
				datos.add(c.getString(0));
				nombrev = c.getString(1);
				nombre.setText(nombrev+"");
				switch (contador) {
				case 1:
					lunes.setText(c.getString(0));
					break;
				case 2:
					martes.setText(c.getString(0));
					break;
				case 3:
					miercoles.setText(c.getString(0));
					break;
				case 4:
					jueves.setText(c.getString(0));
					break;
				case 5:
					viernes.setText(c.getString(0));
					break;
				case 6:
					sabado.setText(c.getString(0));
					break;
				case 7:
					domingo.setText(c.getString(0));
					break;

				default:					
					break;
				}
			} while (c.moveToNext());

		}else {
			lunes.setText("No Tiene Turno Asignado");
			martes.setText("No Tiene Turno Asignado");
			miercoles.setText("No Tiene Turno Asignado");
			jueves.setText("No Tiene Turno Asignado");
			viernes.setText("No Tiene Turno Asignado");
			sabado.setText("No Tiene Turno Asignado");
			domingo.setText("No Tiene Turno Asignado");
			nombre.setText("User No Tiene Turnos");
		}
		

	}

	private void traerdatos() {
		lunes = (TextView) findViewById(R.id.TextViewLu);
		martes = (TextView) findViewById(R.id.TextViewMa);
		miercoles = (TextView) findViewById(R.id.TextViewMi);
		jueves = (TextView) findViewById(R.id.TextViewJu);
		viernes = (TextView) findViewById(R.id.TextViewVi);
		sabado = (TextView) findViewById(R.id.TextViewSa);
		domingo = (TextView) findViewById(R.id.TextViewDo);
		nombre = (TextView) findViewById(R.id.TextViewVariable);
	}
}