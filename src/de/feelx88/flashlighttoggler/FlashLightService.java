package de.feelx88.flashlighttoggler;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.IBinder;


public class FlashLightService extends Service {

	private Camera mCamera;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		mCamera = Camera.open();
		
		Camera.Parameters params = mCamera.getParameters();
    	params.setFlashMode( Camera.Parameters.FLASH_MODE_TORCH );
        mCamera.setParameters( params );
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mCamera.release();
	}

}
