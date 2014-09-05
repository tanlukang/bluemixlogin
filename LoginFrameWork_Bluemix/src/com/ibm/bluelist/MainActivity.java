/*
 * Copyright 2014 IBM Corp. All Rights Reserved
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.bluelist;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity  extends Activity  {

	List<UserItem> itemList;
	LoginInApplication blApplication;
	ArrayAdapter<UserItem> lvArrayAdapter;
	ActionMode mActionMode = null;
	int listItemPosition;
	public static final String CLASS_NAME="MainActivity";

	  private TextView titleTextView;
	  private TextView emailTextView;
	  private TextView nameTextView;
	  public static String currentUser = null;
	  private Button loginOrLogoutButton;
	
	@Override
	/**
	 * onCreate called when main activity is created.
	 * 
	 * Sets up the itemList, application, and sets listeners.
	 *
	 * @param savedInstanceState
	 */
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.activity_profile);
	    titleTextView = (TextView) findViewById(R.id.profile_title);
	    emailTextView = (TextView) findViewById(R.id.profile_email);
	    nameTextView = (TextView) findViewById(R.id.profile_name);
	    loginOrLogoutButton = (Button) findViewById(R.id.login_or_logout_button);
	    titleTextView.setText(R.string.profile_title_logged_in);

	    loginOrLogoutButton.setOnClickListener(new OnClickListener() {
	      @Override
	      public void onClick(View v) {
	        if (currentUser != null) {
	          // User clicked to log out.
	          User.logOut();
	          currentUser = null;
	          showProfileLoggedOut();
	        } else {
	          // User clicked to log in.
	          LoginBuilder loginBuilder = new LoginBuilder(
	              MainActivity.this);
	          startActivity(loginBuilder.build());
	        }
	      }
	    });
	}

	@Override
	  protected void onStart() {
	    super.onStart();

	    if (currentUser != null) {
	      showProfileLoggedIn();
	    } else {
	      showProfileLoggedOut();
	    }
	  }
	
	/**
	   * Shows the profile of the given user.
	   */
	  private void showProfileLoggedIn() {
	    titleTextView.setText(R.string.profile_title_logged_in);
	    //emailTextView.setText(currentUser.getEmail());
	    //String fullName = currentUser.getString("name");
	    String fullName = MainActivity.currentUser;
	    if (fullName != null) {
	      nameTextView.setText(fullName);
	    }
	    loginOrLogoutButton.setText(R.string.profile_logout_button_label);
	  }

	  /**
	   * Show a message asking the user to log in, toggle login/logout button text.
	   */
	  private void showProfileLoggedOut() {
	    titleTextView.setText(R.string.profile_title_logged_out);
	    emailTextView.setText("");
	    nameTextView.setText("");
	    loginOrLogoutButton.setText(R.string.profile_login_button_label);
	  }
	/**
	 * Removes text on click of x button.
	 *
	 * @param  v the edittext view.
	 */
	public void clearText(View v) {
		EditText itemToAdd = (EditText) findViewById(R.id.itemToAdd);
		itemToAdd.setText("");
	}
	
	
	/**
	 * On return from other activity, check result code to determine behavior.
	 */
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
		switch (resultCode)
		{
		/* If an edit has been made, notify that the data set has changed. */
		case LoginInApplication.EDIT_ACTIVITY_RC:
    		break;
		}
    }
}
