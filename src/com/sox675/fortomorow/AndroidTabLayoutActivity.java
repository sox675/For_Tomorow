package com.sox675.fortomorow;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabLayoutActivity extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablayout);

		TabHost tabHost = getTabHost();
		Bundle bundle = getIntent().getExtras();
		String idvariable = bundle.getString("id");

		TabSpec MisTurnos = tabHost.newTabSpec("Mis Turnos");
		MisTurnos.setIndicator("", getResources().getDrawable(R.drawable.mis));
		Intent MisTurnosIntent = new Intent(this, AndroidMisTurnos.class);
		Bundle MisTurnosBundle = new Bundle();
		MisTurnosBundle.putString("idpersona", idvariable);
		MisTurnosIntent.putExtras(MisTurnosBundle);
		MisTurnos.setContent(MisTurnosIntent);

		TabSpec Search = tabHost.newTabSpec("Mis Libros");
		Search.setIndicator("", getResources().getDrawable(R.drawable.search));
		Intent SearchIntent = new Intent(this, AndroidSearch.class);
		Bundle SearchBundle = new Bundle();
		SearchBundle.putString("idpersona", idvariable);
		SearchIntent.putExtras(SearchBundle);
		Search.setContent(SearchIntent);
		
		TabSpec Profile = tabHost.newTabSpec("Edit Profile");
		Profile.setIndicator("", getResources().getDrawable(R.drawable.profile));
		Intent ProfileIntent = new Intent(this, AndroidProfile.class);
		Bundle mis = new Bundle();
		mis.putString("idpersona", idvariable);
		ProfileIntent.putExtras(mis);
		Profile.setContent(ProfileIntent);

		tabHost.addTab(MisTurnos);
		tabHost.addTab(Search);
		tabHost.addTab(Profile);

	}
}