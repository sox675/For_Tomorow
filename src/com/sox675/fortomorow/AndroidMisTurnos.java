package com.sox675.fortomorow;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidMisTurnos extends Activity implements ServiceUpdateUIListener {

	TextView lunes;
	TextView martes;
	TextView miercoles;
	TextView jueves;
	TextView viernes;
	TextView sabado;
	TextView domingo;
	ArrayList datos;
	private static final int NOTIF_ALERTA_ID = 1;
	String turnoprogramado ="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mis_turnos);
		traerdatos();
		cargarturnos();
		startService();
		AndroidService.setUpdateListener(this);

	}


	private void startService() {
		Intent svc = new Intent(this, AndroidService.class);
		startService(svc);
	}
	
	public void update(int count) {
		//-----------NOTIFICATIONS---------------------
		
		//Obtenemos una referencia al servicio de notificaciones
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager notManager = 
			(NotificationManager) getSystemService(ns);
		
		//Configuramos la notificación
		int icono = android.R.drawable.stat_sys_warning;
		CharSequence textoEstado = "Turno Para Mañana!";
		long hora = System.currentTimeMillis();

		Notification notif = 
			new Notification(icono, textoEstado, hora);
		
		//Configuramos el Intent
		Context contexto = getApplicationContext();
		CharSequence titulo = "Su Turno Para Mañana Es:";
		CharSequence descripcion = turnoprogramado;
		
		Intent notIntent = new Intent(contexto, 
				contexto.getClass());
		
		PendingIntent contIntent = PendingIntent.getActivity(
				contexto, 0, notIntent, 0);

		notif.setLatestEventInfo(
				contexto, titulo, descripcion, contIntent);
		
		//AutoCancel: cuando se pulsa la notificaión ésta desaparece
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		
		//Añadir sonido, vibración y luces
		//notif.defaults |= Notification.DEFAULT_SOUND;
		//notif.defaults |= Notification.DEFAULT_VIBRATE;
		//notif.defaults |= Notification.DEFAULT_LIGHTS;
		
		//Enviar notificación
		notManager.notify(NOTIF_ALERTA_ID, notif);
		
		//-----------NOTIFICATIONS---------------------
	}

	private void cargarturnos() {
		Bundle bundle = getIntent().getExtras();
		String idusuario = bundle.getString("idpersona");
		datos = new ArrayList();

		AndroidSQLite senaWizard = new AndroidSQLite(getApplicationContext(),
				"ForTomorowBD", null, 1);

		SQLiteDatabase db = senaWizard.getWritableDatabase();
		String[] args = new String[] { idusuario };
		Cursor c = db.rawQuery("select turno from turnos where usuario_id=?",
				args);
		Integer contador = 0;
		
		java.util.Date fecha = new Date();
		Integer diav = fecha.getDay();

		if (c.moveToFirst()) {
			do {
				contador = contador + 1;
				datos.add(c.getString(0));
				
				if (diav==0 && contador==1 ) {
					turnoprogramado = c.getString(0);
				}else if (diav==1 && contador==2) {
					turnoprogramado =c.getString(0);
				}
				else if (diav==2 && contador==3) {
					turnoprogramado =c.getString(0);
				}
				else if (diav==3 && contador==4) {
					turnoprogramado =c.getString(0);
				}
				else if (diav==4 && contador==5) {
					turnoprogramado =c.getString(0);
				}
				else if (diav==5 && contador==6) {
					turnoprogramado =c.getString(0);
				}
				else if (diav==6 && contador==7) {
					turnoprogramado =c.getString(0);
				}

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
	}
}