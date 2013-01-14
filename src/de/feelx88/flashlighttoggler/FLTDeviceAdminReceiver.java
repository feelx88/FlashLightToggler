/**
Copyright 2013 Felix Müller.

This file is part of FlashLightToggler.

FlashLightToggler is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

FlashLightToggler is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with FlashLightToggler.  If not, see <http://www.gnu.org/licenses/>.
**/

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
