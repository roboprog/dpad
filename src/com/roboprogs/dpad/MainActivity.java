/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roboprogs.adhd;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import org.library.*;

/**
 * Event handlers for Busy Box.
 */
public
class					MainActivity
	extends				Activity
	{

	/** accumulated log (emulation) text */
	private
	String				logText = "";

    /** Called when the activity is first created. */
    @Override
    public
	void				onCreate
		(
		Bundle			icicle
		)
		{
        super.onCreate( icicle);

        setContentView( R.layout.main);
		wireEvents();
		info( "Event handlers in place");
		}  // _____________________________________________

	/**
	 * Set up event handlers for screen controls
	 */
	private
	void				wireEvents()
		{
		// TODO
		}  // _____________________________________________

	/**
	 * Spout a log mmessage.
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
		// TODO updateStatus();
		}  // _____________________________________________

	}  // =================================================


// vim: ts=4 sw=4
// *** EOF ***
