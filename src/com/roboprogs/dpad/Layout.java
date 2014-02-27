package com.roboprogs.dpad;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Programatic layout setup.
 *  Doing this to work around seeming bug in layout generation tool,
 *  which may or may not be terminal-ide specific.
 */
public
class					Layout
	{

	/** handle to logging panel */
	public final
	TextView			logBox;

	/** handle to placeholder panel */
	public final
	TextView			placeholder;

	/** up button */
	public final
	Button				up;

	/** down button */
	public final
	Button				down;

	/** left button */
	public final
	Button				left;

	/** right button */
	public final
	Button				right;

    /**
	 * Create our layout, and save handles to relevant widgets
	 *
	 * @param activity
	 * 		the activity to which this layout belongs.
	 * @param root
	 * 		top level layout container to which components are added.
	 */
    public
	Layout
		(
		Activity		activity,
		ViewGroup		root
		)
		{
		RelativeLayout	block;

		block = new RelativeLayout( activity);
		this.logBox = new TextView( activity);
		this.logBox.setTextColor( 0xEE0000);
		this.logBox.setBackgroundColor( 0x000000);
		this.logBox.setText( "TODO: put graphics here");
		this.logBox.setPadding( 3, 3, 3, 3);
		block.addView( this.logBox);
		root.addView( block);

		this.placeholder = null;

		this.up = null;
		this.down = null;
		this.left = null;
		this.right = null;
		}  // _____________________________________________

	}  // =================================================


// vim: ts=4 sw=4
// *** EOF ***
