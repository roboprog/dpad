package com.roboprogs.dpad;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Main view for "DPAD" test bed applet.
 * <p>
 * Program entry point is the onCreate() method.
 * Main tasks:
 * <ul>
 * 		<li>Add custom view to enable access to android canvas</li>
 * 		<li>Wire up event handlers to catch button presses</li>
 * </ul>
 * </p>
 *
 * @see #onCreate()
 */
public
class					MainActivity
	extends				Activity
	{

	/** number of left/right moves */
	private static final
	int					NUM_HORIZONTAL = 16;

	/** number of up/down moves */
	private static final
	int					NUM_VERTICAL = 16;

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

		extendLayout();
		wireEvents();

		// our logging supplement needs events wired up fist

		info( "Event handlers in place");
		info( "Canvas view: " + this.canvasView);
		}  // _____________________________________________

	/**
	 * Complete layout by adding graphics-enabled view.
	 */
	private
	void				extendLayout()
		{
		ViewGroup		canvasContainer;

        setContentView( R.layout.main);
		this.canvasView = new CanvasView( this);
		canvasContainer = (ViewGroup) findViewById( R.id.dpad_canvas);
		canvasContainer.addView( this.canvasView);
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
		info( "Button " + btn.getText() + " pressed");
		this.curX = updatePosition( btn, this.curX,
				this.right, this.left, NUM_HORIZONTAL);
		this.curY = updatePosition( btn, this.curY,
				this.down, this.up, NUM_VERTICAL);
		info( "Position: " + this.curX + ", " + this.curY);

		// request redraw on view used for Canvas:
		MainActivity.this.canvasView.invalidate();
		}  // _____________________________________________

	/**
	 * Update X or Y position when a button is pressed,
	 * 	accounting for wrapping.
	 *
	 * @param btn
	 * 		the button instance that was pressed/clicked
	 * @param cur
	 * 		the current position on this axis
	 * @param inc
	 * 		the button to increment position on this axis
	 * @param dec
	 * 		the button to decrement position on this axis
	 * @param limit
	 * 		the limit for this axis
	 */
	private
	int					updatePosition
		(
		Button			btn,
		int				cur,
		Button			inc,
		Button			dec,
		int				limit
		)
		{
		int				newPos;

		newPos = cur;

		// check for increment/decrement buttons for this axis,
		//  ignore other buttons
		newPos += ( btn == inc) ?
					1 :
				( btn == dec) ?
					-1 :
					0;

		// check for under/overflow and wrap if needed
		newPos = ( newPos < 0) ?
					( limit - 1) :
				( newPos >= limit) ?
					0 :
					newPos;

		return newPos;
		}  // _____________________________________________

	/**
	 * Spout a log message.
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
		this.logBox.setText( this.logText);
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
			float		y;
			float		x;
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
			x = (float) ( ( MainActivity.this.curX + 1) *
					( maxX / ( NUM_HORIZONTAL + 1) ) );
			y = (float) ( ( MainActivity.this.curY + 1) *
					( maxY / ( NUM_VERTICAL + 1) ) );
			radius = (float) ( maxY / 24.0);
			paint = new Paint();
			paint.setColor( Color.BLACK);
			paint.setStyle( Paint.Style.FILL);
			canvas.drawCircle( x, y, radius, paint);
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
			updatePosition( btn);
			}  // _________________________________________

		}  // =============================================

	}  // =================================================


// vim: ts=4 sw=4
// *** EOF ***
