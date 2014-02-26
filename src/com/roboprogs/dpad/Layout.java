package com.roboprogs.dpad;

import android.app.Activity;
// import android.media.MediaPlayer;
// import android.net.Uri;
import android.os.Bundle;
// import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// import org.library.*;

/**
 * Programatic layout setup.
 *  Doing this to work around seeming but in layout generation tool,
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

    /** Create our layout, and save handles to relevant widgets */
    public
	Layout()
		{
		this.logBox = null;
		this.placeholder = null;

		this.up = null;
		this.down = null;
		this.left = null;
		this.right = null;
		}  // _____________________________________________

	}  // =================================================


// vim: ts=4 sw=4
// *** EOF ***
