package com.example.indreshprakash.testing.backend;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.indreshprakash.testing.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GeofenceTrasitionService extends IntentService  implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{


    private static final String TAG = GeofenceTrasitionService.class.getSimpleName();
    public static final int GEOFENCE_NOTIFICATION_ID = 0;
    public GeofenceTrasitionService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Retrieve the Geofencing intent
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        Log.i("Geofence Requested",geofencingEvent.toString());

        // Handling errors
        if ( geofencingEvent.hasError() ) {
            String errorMsg = getErrorString(geofencingEvent.getErrorCode() );
            Log.e( TAG, errorMsg );
            return;
        }

        // Retrieve GeofenceTrasition
        int geoFenceTransition = geofencingEvent.getGeofenceTransition();
        // Check if the transition type
        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT ) {
            // Get the geofence that were triggered
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            // Create a detail message with Geofences received
            String geofenceTransitionDetails = getGeofenceTrasitionDetails(this,geoFenceTransition, triggeringGeofences );
            // Send notification details as a String
            sendNotification( geofenceTransitionDetails );
        }
    }
   // Uri sounduri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
  // Uri sounduri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mass);
// Create a detail message with Geofences received
    private String getGeofenceTrasitionDetails(Context context,int geoFenceTransition, List<Geofence> triggeringGeofences) {
        // get the ID of each geofence triggered
        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
        for ( Geofence geofence : triggeringGeofences ) {
            triggeringGeofencesList.add( geofence.getRequestId() );
        }

        String status = null;
        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ) {
            status = "Entering ";
          MediaPlayer mediaplayer = MediaPlayer.create(this, R.raw.headblackspot);
            mediaplayer.start();
            if(speed>=0.2){
                String message ="location is : Latitude = "+mCurrentLocation.getLatitude()+" Longitude = "+mCurrentLocation.getLongitude()+"";
                SmsManager.getDefault().sendTextMessage("9629247899",null,message,null,null);
                Toast.makeText(this,"Alert sent Succesfully",Toast.LENGTH_LONG).show();
            }
        }
        else if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT ) {
            status = "Exiting ";
            MediaPlayer mediaplayer = MediaPlayer.create(this, R.raw.leaveblackspot);
            mediaplayer.start();
        }
        return status + "Blackspot";
    }

    // Send a notification
   /* private void sendNotification( String msg ) {
        Log.i(TAG, "sendNotification: " + msg );

        // Intent to start the main Activity
        Intent notificationIntent = MainActivity.makeNotificationIntent(
                getApplicationContext(), msg
        );


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Creating and sending Notification
        NotificationManager notificatioMng =
                (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
        notificatioMng.notify(
                GEOFENCE_NOTIFICATION_ID,
                createNotification(msg, notificationPendingIntent));
    }

    // Create a notification
    public static final String NOTIFICATION_ID="channel";
    private Notification createNotification(String msg, PendingIntent notificationPendingIntent) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_ID);
        notificationBuilder
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setColor(Color.RED)
                .setContentTitle(msg)
                .setContentText("Geofence Notification!")
                .setContentIntent(notificationPendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        return notificationBuilder.build();
    }*/
    private static final String CHANNEL_ID = "channel_01";

    private void sendNotification(String notificationDetails) {
        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            // Create the channel for the notification
        //    AudioAttributes attributes=new AudioAttributes.Builder()
          //          .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            //        .build();
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
           // mChannel.setSound(sounduri,attributes);
            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);
        }

        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), com.example.indreshprakash.testing.backend.SmartDrive.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(com.example.indreshprakash.testing.backend.SmartDrive.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_launcher_background)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher_background))
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
            //    .setSound(sounduri)
                .setContentText(getString(R.string.geofence_transition_notification_text))
                .setContentIntent(notificationPendingIntent)
                .setVibrate(new long[]{5000,5000,5000,5000,5000});

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID); // Channel ID
        }

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }

    // Handle errors
    private static String getErrorString(int errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GeoFence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Too many GeoFences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Too many pending intents";
            default:
                return "Unknown error.";
        }
    }

    private static final long INTERVAL = 1000 * 2;
    private static final long FASTEST_INTERVAL = 1000 * 1;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation, lStart, lEnd;
    static double distance = 0;
    double speed;


    private final IBinder mBinder = new GeofenceTrasitionService.LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        return mBinder;
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onConnected(Bundle bundle) {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } catch (SecurityException e) {
        }
    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        distance = 0;
    }


    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onLocationChanged(Location location) {
        com.example.indreshprakash.testing.backend.speed.locate.dismiss();
        mCurrentLocation = location;
        if (lStart == null) {
            lStart = mCurrentLocation;
            lEnd = mCurrentLocation;
        } else
            lEnd = mCurrentLocation;

        //Calling the method below updates the  live values of distance and speed to the TextViews.
        updateUI();
        //calculating the speed with getSpeed method it returns speed in m/s so we are converting it into kmph
        speed = location.getSpeed() * 18 / 5;

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public class LocalBinder extends Binder {

        public GeofenceTrasitionService getService() {
            return GeofenceTrasitionService.this;
        }


    }

    //The live feed of Distance and Speed are being set in the method below .
    private void updateUI() {
        if (com.example.indreshprakash.testing.backend.speed.p == 0) {
            distance = distance + (lStart.distanceTo(lEnd) / 1000.00);
            com.example.indreshprakash.testing.backend.speed.endTime = System.currentTimeMillis();
            long diff = com.example.indreshprakash.testing.backend.speed.endTime - com.example.indreshprakash.testing.backend.speed.startTime;
            diff = TimeUnit.MILLISECONDS.toMinutes(diff);
            com.example.indreshprakash.testing.backend.speed.time.setText("Total Time: " + diff + " minutes");
            if (speed > 0.0)
                com.example.indreshprakash.testing.backend.speed.speed.setText("Current speed: " + new DecimalFormat("#.##").format(speed) + " km/hr");
            else
                com.example.indreshprakash.testing.backend.speed.speed.setText(".......");

            com.example.indreshprakash.testing.backend.speed.dist.setText(new DecimalFormat("#.###").format(distance) + " Km's.");

            lStart = lEnd;

        }

    }


    @Override
    public boolean onUnbind(Intent intent) {
        stopLocationUpdates();
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        lStart = null;
        lEnd = null;
        distance = 0;
        return super.onUnbind(intent);
    }
}