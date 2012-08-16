package com.sox675.fortomorow;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class AndroidService extends Service {

	// 24 HORAS = 86400000
	// 1 HORA = 3600000
	// 60 SEGUNDOS = 60000
	
	private Timer timer = new Timer();
	private static final long UPDATE_INTERVAL = 60000;
	public static ServiceUpdateUIListener UI_UPDATE_LISTENER;
	
	private int count = 0;
	
	public static void setUpdateListener(ServiceUpdateUIListener l) {
		UI_UPDATE_LISTENER = l;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		_startService();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		_shutdownService();
	}
	
	private void _startService() {
		timer.scheduleAtFixedRate(
			new TimerTask() {
				public void run() {
					count++;
					handler.sendEmptyMessage(0);
				}
			},
			0,
			UPDATE_INTERVAL);
	}
	
	private void _shutdownService() {
		if (timer != null) timer.cancel();
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			UI_UPDATE_LISTENER.update(count);
		}
	};

}
