package com.roboprogs.dpad;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
// import android.graphics.RectF;
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

	/** number of left/right moves */
	private static final
	int					NUM_HORIZONTAL = 4;

	/** number of up/down moves */
	private static final
	int					NUM_VERTICAL = 4;

	/** horizontal position */
	private
	int					curX = 0;

	/** vertical position */
	private
	int					curY = 0;

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
	 * Update position when a button is pressed.
	 *
	 * @param btn
	 * 		the button instance that was pressed/clicked
	 */
	private
	void				updatePosition
		(
		Button			btn
		)
		{
		this.curX += ( btn == this.right) ?
					1 :
				( btn == this.left) ?
					-1 :
					0;
		this.curX = ( this.curX < 0) ?
					( NUM_HORIZONTAL - 1) :
				( this.curX >= NUM_HORIZONTAL) ?
					0 :
					this.curX;

		this.curY += ( btn == this.down) ?
					1 :
				( btn == this.up) ?
					-1 :
					0;
		this.curY = ( this.curY < 0) ?
					( NUM_VERTICAL - 1) :
				( this.curY >= NUM_VERTICAL) ?
					0 :
					this.curY;
		info( "Position: " + this.curX + ", " + this.curY);
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
			Rect		clipBounds;
			int			maxX;
			int			maxY;
			float		top;
			float		left;
			float		radius;
			Paint		paint;

			// clear the screen
			canvas.drawRGB( 0xa0, 0xa0, 0xa0);

			// get metrics
			// TODO:  only recalc when needed
			clipBounds = canvas.getClipBounds();
			// info( "Bounds: " + clipBounds);
			maxX = clipBounds.width();
			maxY = clipBounds.height();

			// draw marker
			left = (float) ( maxX / 2.0);
			top = (float) ( maxY / 2.0);
			radius = (float) ( maxY / 24.0);
			paint = new Paint();
			paint.setColor( Color.BLACK);
			paint.setStyle( Paint.Style.FILL);
			canvas.drawCircle( left, top, radius, paint);
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
			updatePosition( btn);

			// request redraw on view used for Canvas:
			MainActivity.this.canvasView.invalidate();
			}  // _________________________________________

		}  // =============================================

	}  // =================================================


// vim: ts=4 sw=4
// *** EOF ***
