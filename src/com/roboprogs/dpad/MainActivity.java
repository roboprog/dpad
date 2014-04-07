package com.roboprogs.dpad;

import android.app.Activity;
// import android.media.MediaPlayer;
// import android.net.Uri;
import android.os.Bundle;
// import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Main view for "DPAD" test bed applet.
 *  TODO: create an application class to store game state.
 */
public
class					MainActivity
	extends				Activity
	{

	/** handle to logging panel */
	private
	TextView			logBox;

	/** accumulated log (emulation) text */
	private
	String				logText = "";

	/** handle to placeholder panel */
	private
	TextView			placeholder;

	/** up button */
	private
	Button				up;

	/** down button */
	private
	Button				down;

	/** left button */
	private
	Button				left;

	/** right button */
	private
	Button				right;

    /** Called when the activity is first created. */
    @Override
    public
	void				onCreate
		(
		Bundle			icicle
		)
		{
        super.onCreate( icicle);

		ViewGroup		root;

        setContentView( R.layout.main);
		root = (ViewGroup) findViewById( R.id.dpad_root);
		// root = null;
		// new Layout( this, root);  // TODO:  save this
		wireEvents();
		info( "Event handlers in place");
		info( "Root view group: " + root);
		}  // _____________________________________________

	/**
	 * Set up event handlers for screen controls
	 */
	private
	void				wireEvents()
		{
		BtnTracker		btnTracker;

		this.logBox = (TextView) findViewById( R.id.dpad_log);
		this.placeholder = (TextView) findViewById( R.id.dpad_canvas);

		btnTracker = new BtnTracker();
		this.up = (Button) findViewById( R.id.dpad_up);
		this.up.setOnClickListener( btnTracker);
		this.down = (Button) findViewById( R.id.dpad_down);
		this.down.setOnClickListener( btnTracker);
		this.left = (Button) findViewById( R.id.dpad_left);
		this.left.setOnClickListener( btnTracker);
		this.right = (Button) findViewById( R.id.dpad_right);
		this.right.setOnClickListener( btnTracker);

		}  // _____________________________________________

	/**
	 * Update display.
	 */
	private
	void				updateStatus()
		{
		this.logBox.setText( this.logText);
		this.placeholder.setText( "TODO");
		// TODO:  update other controls
		}  // _____________________________________________

	/**
	 * Spout a log message.
	 *  TODO: copy the text into a scrolling text box control, as well.
	 */
	private
	void				info
		(
		String			msg
		)
		{
		Log.i( MainActivity.class.getName(), msg);

		// adb + logcat display other activity, but not our msgs,
		//  so implement a workaround
		// (newest on top, so don't have to scroll down)
		this.logText = ( "I) " + msg + "\n" + this.logText);
		// TODO: timestamp
		updateStatus();
		}  // _____________________________________________

	/**
	 * Handler for DPAD button presses.
	 */
	private
	class				BtnTracker
		implements		View.OnClickListener
		{

		/** catch button press/click events */
		@Override
		public
		void			onClick
			(
			View		v
			)
			{
			Button		btn;

			btn = (Button) v;
			info( "Button " + btn.getText() + " pressed");
			}  // _________________________________________

		}  // =============================================

	}  // =================================================


// vim: ts=4 sw=4
// *** EOF ***
