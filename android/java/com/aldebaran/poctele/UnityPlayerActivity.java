package com.aldebaran.poctele;

import com.aldebaran.qi.AnyObject;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.EmbeddedTools;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.SignalCallback;
import com.aldebaran.qi.helper.proxies.ALAnimatedSpeech;
import com.aldebaran.qi.helper.proxies.ALAudioDevice;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;
import com.unity3d.player.*;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UnityPlayerActivity extends Activity
{
	protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
    private Session session;
    private int savePeople;
    private String saveUrl;

	// Setup activity layout
	@Override protected void onCreate (Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        EmbeddedTools ebt = new EmbeddedTools();
        File cacheDir = getApplicationContext().getCacheDir();
        // Log.d(TAG, "Extracting libraries in " + cacheDir.getAbsolutePath());
        ebt.overrideTempDirectory(cacheDir);
        ebt.loadEmbeddedLibraries();


        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

        mUnityPlayer = new UnityPlayer(this);
        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();


        Thread routine = new Thread(new Runnable() {
            @Override
            public void run() {
                session = new Session();
                try {
                    // session.connect("tcp://198.18.0.1:9559").get();
                    session.connect("tcp://198.18.0.1:9559").get();

                    ALAnimatedSpeech alAnimatedSpeech = new ALAnimatedSpeech(session);
                    //alAnimatedSpeech.say("It works !");

                    ALMemory alMemory = new ALMemory(session);
                    alMemory.subscribeToEvent("LeftBumperPressed",new EventCallback() {
                        @Override
                        public void onEvent(Object o) throws InterruptedException, CallError {
                            UnityPlayer.UnitySendMessage("manager", "ReceiveRotDir", "word");
                        }
                    });

                    alMemory.subscribeToEvent("RightBumperPressed",new EventCallback() {
                        @Override
                        public void onEvent(Object o) throws InterruptedException, CallError {
                            UnityPlayer.UnitySendMessage("manager", "ReceiveRotDir", "speak");
                        }
                    });
                    alMemory.subscribeToEvent("PepperBeats/Brick", new EventCallback() {
                        @Override
                        public void onEvent(Object o) throws InterruptedException, CallError {
                            if(o.toString().equals("POEM")){
                                UnityPlayer.UnitySendMessage("manager", "ReceiveRotDir", "speak");
                                UnityPlayer.UnitySendMessage("manager", "ReceiveRotDir",saveUrl);
                            }else if(o.toString().equals("GIMMEWORD")){
                                UnityPlayer.UnitySendMessage("manager", "ReceiveRotDir", "word");
                            }
                            else if(o.toString().equals("INSPIRATION")){
                                UnityPlayer.UnitySendMessage("manager", "ReceiveRotDir", "word");
                            }
                            else if(o.toString().equals("SING")){
                                UnityPlayer.UnitySendMessage("manager", "ReceiveRotDir", "letter");
                            }
                            else if(o.toString().equals("MUSIC")){
                                UnityPlayer.UnitySendMessage("manager", "ReceiveRotDir", "sing");
                            }
                            else if(o.toString().equals("BEAT")){
                                UnityPlayer.UnitySendMessage("manager", "ReceiveRotDir", "beat");
                            }
                            else if(o.toString().equals("YOURSOUND")){
                                UnityPlayer.UnitySendMessage("manager", "ReceiveRotDir", "yoursound");
                            }
                            else if(o.toString().equals("RECORDING")){
                                UnityPlayer.UnitySendMessage("manager", "ReceiveRotDir", "recording");
                            }
                            else if(o.toString().equals("SILENCE")){
                                UnityPlayer.UnitySendMessage("manager", "ReceiveRotDir", "silence");
                            }
                        }
                    });
                    alMemory.subscribeToEvent("PepperBeats/ImageUrl",new EventCallback() {
                        @Override
                        public void onEvent(Object o) throws InterruptedException, CallError {
                            saveUrl = o.toString();
                            UnityPlayer.UnitySendMessage("manager", "ReceiveRotDir",saveUrl);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        routine.start();
    }


        //TELEPRESENCE
       /* Thread routine = new Thread(new Runnable() {
            @Override
            public void run() {
                session = new Session();
                try {
                    // session.connect("tcp://198.18.0.1:9559").get();
                    session.connect("tcp://198.18.0.1:9559").get();

                    AnyObject service = session.service("ALTelepresence");
                    service.connect("connected", "onSignal::(s)",
                            new SignalCallback<String>() {

                                @Override
                                public void onSignal(String arg0)
                                        throws InterruptedException, CallError {
                                    //UnityPlayer.UnitySendMessage("Main Camera", "ReceiveRotDir", arg0);
                                    System.out.println(arg0);

                                }
                            });
                    String user = String.valueOf(service.call("getConnectedUser").get());
                    //UnityPlayer.UnitySendMessage("Main Camera", "ReceiveRotDir", user);
                    Future<Object> toto = service.call("getConnectedUser");
                    UnityPlayer.UnitySendMessage("Main Camera", "ReceiveRotDir", toto.get().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        routine.start();
    }*/


/*    //Instantiate Custom Service
    AnyObject service = session.service("myCustomService");
    //Suscribe Custom Signal
    service.connect("myCustomSignal", "onSignal::(s)",
            new SignalCallback<String>() {

    @Override
    public void onSignal(String arg0)
    throws InterruptedException, CallError {

        System.out.println(arg0);

    }
});
    //Call Custom Method
    Future<Object> objectName = service.call("myCustomMethod");
    System.out.println(objectName.get().toString());*/




    private void plusAudioVolume(){
        try {
            ALAudioDevice alAudioDevice = new ALAudioDevice(session);
            alAudioDevice.setOutputVolume(alAudioDevice.getOutputVolume()+10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void lessAudioVolume(){
        try {
            ALAudioDevice alAudioDevice = new ALAudioDevice(session);
            alAudioDevice.setOutputVolume(alAudioDevice.getOutputVolume()-10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // Quit Unity
	@Override protected void onDestroy ()
	{
		mUnityPlayer.quit();
		super.onDestroy();
	}

	// Pause Unity
	@Override protected void onPause()
	{
		super.onPause();
		mUnityPlayer.pause();
	}

	// Resume Unity
	@Override protected void onResume()
	{
		super.onResume();
		mUnityPlayer.resume();
	}

	// This ensures the layout will be correct.
	@Override public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mUnityPlayer.configurationChanged(newConfig);
	}

	// Notify Unity of the focus change.
	@Override public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		mUnityPlayer.windowFocusChanged(hasFocus);
	}

	// For some reason the multiple keyevent type is not supported by the ndk.
	// Force event injection by overriding dispatchKeyEvent().
	@Override public boolean dispatchKeyEvent(KeyEvent event)
	{
		if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
			return mUnityPlayer.injectEvent(event);
		return super.dispatchKeyEvent(event);
	}

	// Pass any events not handled by (unfocused) views straight to UnityPlayer
	@Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
	/*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
