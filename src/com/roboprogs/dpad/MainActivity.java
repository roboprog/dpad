package com.roboprogs.dpad;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
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
	View				canvasView;

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
		ViewGroup		canvasContainer;

        setContentView( R.layout.main);
		root = (ViewGroup) findViewById( R.id.dpad_root);
		// root = null;
		// new Layout( this, root);  // TODO:  save this
		this.canvasView = new CanvasView( this);
		canvasContainer = (ViewGroup) findViewById( R.id.dpad_canvas);
		canvasContainer.addView( this.canvasView);
		wireEvents();
		info( "Event handlers in place");
		info( "Root view group: " + root);
		info( "Canvas view: " + this.canvasView);
		}  // _____________________________________________

	/**
	 * Set up event handlers for screen controls
	 */
	private
	void				wireEvents()
		{
		BtnTracker		btnTracker;

		this.logBox = (TextView) findViewById( R.id.dpad_log);

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
	 * Dummy view to export graphics canvas.
	 */
	private
	class				CanvasView
		extends			View
		{

		/**
		 * Provide init data back to real (super class) constructor.
		 */
		public
		CanvasView
			(
			Context		ctx
			)
			{
			super( ctx);
			}  // _________________________________________

		@Override
		protected
		void			onDraw
			(
			Canvas		canvas
			)
			{
			// TODO:  draw!
			}  // _________________________________________

		}  // =============================================

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

			// request redraw on view used for Canvas:
			MainActivity.this.canvasView.invalidate();
			}  // _________________________________________

		}  // =============================================

	}  // =================================================


// vim: ts=4 sw=4
// *** EOF ***
