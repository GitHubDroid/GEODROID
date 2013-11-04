package eu.geodroid.library.markers;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import eu.geopaparazzi.library.database.GPLog;
import eu.geodroid.library.util.LibraryConstants;

/**
 * Utilities for interaction with the Makers-for-android project. 
 * 
 * @author Andrea Antonello (www.hydrologis.com)
 */
public class MarkersUtilities {
    private static final boolean LOG_HOW = GPLog.LOG_ABSURD;
    public static final String ACTION_EDIT = "android.intent.action.EDIT";
    public static final String APP_PACKAGE = "org.dsandler.apps.markers";
    public static final String APP_MAIN_ACTIVITY = "com.google.android.apps.markers.MarkersActivity";
    public static final String EXTRA_KEY = MediaStore.EXTRA_OUTPUT;
    private static boolean hasApp = false;

    public static boolean appInstalled( final Context context ) {
        if (!hasApp) {
            List<PackageInfo> installedPackages = new ArrayList<PackageInfo>();
            { // try to get the installed packages list. Seems to have troubles over different
              // versions, so trying them all
                try {
                    installedPackages = context.getPackageManager().getInstalledPackages(0);
                } catch (Exception e) {
                    // ignore
                }
                if (installedPackages.size() == 0)
                    try {
                        installedPackages = context.getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES);
                    } catch (Exception e) {
                        // ignore
                    }
            }

            if (installedPackages.size() > 0) {
                // if a list is available, check if the status gps is installed
                for( PackageInfo packageInfo : installedPackages ) {
                    String packageName = packageInfo.packageName;
                    if (LOG_HOW)
                        GPLog.addLogEntry("MARKERSUTILITIES", packageName);
                    if (packageName.startsWith(APP_PACKAGE)) {
                        hasApp = true;
                        if (LOG_HOW)
                            GPLog.addLogEntry("MARKERSUTILITIES", "Found package: " + packageName);
                        break;
                    }
                }
            } else {
                /*
                 * if no package list is available, for now try to fire it up anyways.
                 * This has been a problem for a user on droidx with android 2.2.1.
                 */
                hasApp = true;
            }
        }
        return hasApp;
    }

    public static void openMarketToInstall( final Context context ) {
        new AlertDialog.Builder(context).setTitle("Install Markers")
                .setMessage("Select ok to install the sketch application from google play.")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){
                    public void onClick( DialogInterface dialog, int whichButton ) {
                    }
                }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    public void onClick( DialogInterface dialog, int whichButton ) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("market://search?q=" + APP_PACKAGE));
                        context.startActivity(intent);
                    }
                }).show();
    }
    
    /**
     * Launches Marker in edit mode on a given image, saving over the same image.
     * 
     * @param context
     * @param image the image to edit and save to.
     */
    public static void launchOnImage( final Context context, File image ) {
        if (MarkersUtilities.appInstalled(context)) {
            Intent intent = new Intent(MarkersUtilities.ACTION_EDIT);
            intent.setDataAndType(Uri.fromFile(image), "image/*"); //$NON-NLS-1$
            intent.putExtra(MarkersUtilities.EXTRA_KEY, image.getAbsolutePath());
            context.startActivity(intent);
        } else {
            MarkersUtilities.openMarketToInstall(context);
        }
    }

    /**
     * Opens Marker in new sketch mode, supplying a file to save to.
     * 
     * <p>If position data are supplied, they should be used to create a properties file.
     * 
     * @param context
     * @param image the image file to save to. 
     * @param gpsLocation the position of the sketch or <code>null</code>.
     */
    public static void launch( Context context, File image, double[] gpsLocation ) {
        if (MarkersUtilities.appInstalled(context)) {
            Intent sketchIntent = new Intent();
            sketchIntent.setComponent(new ComponentName(MarkersUtilities.APP_PACKAGE, MarkersUtilities.APP_MAIN_ACTIVITY));
            sketchIntent.putExtra(MarkersUtilities.EXTRA_KEY, image.getAbsolutePath());
            if (gpsLocation != null) {
                sketchIntent.putExtra(LibraryConstants.LATITUDE, gpsLocation[1]);
                sketchIntent.putExtra(LibraryConstants.LONGITUDE, gpsLocation[0]);
                sketchIntent.putExtra(LibraryConstants.ELEVATION, gpsLocation[2]);
            }
            context.startActivity(sketchIntent);
        } else {
            MarkersUtilities.openMarketToInstall(context);
        }
    }

}
