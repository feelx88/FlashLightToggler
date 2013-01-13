package de.feelx88.flashlighttoggler;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class FLTDeviceAdminReceiver extends android.app.admin.DeviceAdminReceiver {
	@Override
	public void onEnabled( Context context, Intent intent ) {
		Toast.makeText(context, "Device Admin enabled", Toast.LENGTH_SHORT ).show();
		super.onEnabled(context, intent);
	}
	
	@Override
	public void onDisabled( Context context, Intent intent ) {
		Toast.makeText(context, "Device Admin disabled", Toast.LENGTH_SHORT ).show();
		super.onEnabled(context, intent);
	}
}
