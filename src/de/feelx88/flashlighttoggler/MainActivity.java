package de.feelx88.flashlighttoggler;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.hardware.*;

public class MainActivity extends Activity {

	private Camera mCamera;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCamera = Camera.open();
    }
    
    @Override
    protected void onStop() {
    	
    	toggleFlash( false );
    	
    	super.onStop();
    }
    
    @Override
    protected void onStart() {
    	
    	toggleFlash( true );
    	
    	super.onStart();
    }
    
    private void toggleFlash( boolean on )
    {
    	try {
			mCamera.reconnect();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Camera.Parameters params = mCamera.getParameters();
    	if( on )
    		params.setFlashMode( Camera.Parameters.FLASH_MODE_TORCH );
    	else
    		params.setFlashMode( Camera.Parameters.FLASH_MODE_OFF );
        mCamera.setParameters( params );
    }
    
    @Override
    protected void onDestroy() {
    	toggleFlash( false );
    	super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
