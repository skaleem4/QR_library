package com.google.zxing.client.android;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splashscreen extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread splashtimer = new Thread(){
			public void run(){
				try {
					sleep(1000);
				}
				catch(InterruptedException e){
				  e.printStackTrace();	
				}
				finally{
					Intent startMain = new Intent(Splashscreen.this,CaptureActivity.class);
					startActivity(startMain);
				}
			}
			
		};
		splashtimer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	
	

}
