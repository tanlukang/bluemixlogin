/*
 *  Copyright (c) 2014, Facebook, Inc. All rights reserved.
 *
 *  You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 *  copy, modify, and distribute this software in source code or binary form for use
 *  in connection with the web services and APIs provided by Facebook.
 *
 *  As with any software that integrates with the Facebook platform, your use of
 *  this software is subject to the Facebook Developer Principles and Policies
 *  [http://developers.facebook.com/policy/]. This copyright notice shall be
 *  included in all copies or substantial portions of the software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.ibm.bluelist;


import com.facebook.Session;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;


public class LoginActivity extends FragmentActivity implements
    LoginFragment.IncpadLoginFragmentListener,
    LoginHelpFragment.IncpadOnLoginHelpSuccessListener,
    OnLoginSuccessListener, OnLoadingListener {

  public static final String LOG_TAG = "IncpadLoginActivity";

  // All login UI fragment transactions will happen within this parent layout element.
  // Change this if you are modifying this code to be hosted in your own activity.
  private final int fragmentContainer = android.R.id.content;

  private ProgressDialog progressDialog;
  private Bundle configOptions;

  // Although Activity.isDestroyed() is in API 17, we implement it anyways for older versions.
  private boolean destroyed = false;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    // Combine options from incoming intent and the activity metadata
    configOptions = getMergedOptions();
   

    // Show the login form
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction().add(fragmentContainer,
          LoginFragment.newInstance(configOptions)).commit();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
    destroyed = true;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Required for making Facebook login work
    Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
  }

  /**
   * Called when the user clicked the sign up button on the login form.
   */
  @Override
  public void onSignUpClicked(String username, String password) {
    // Show the signup form, but keep the transaction on the back stack
    // so that if the user clicks the back button, they are brought back
    // to the login form.
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(fragmentContainer,
        SignupFragment.newInstance(configOptions, username, password));
    transaction.addToBackStack(null);
    transaction.commit();
  }

  /**
   * Called when the user clicked the log in button on the login form.
   */
  @Override
  public void onLoginHelpClicked() {
    // Show the login help form for resetting the user's password.
    // Keep the transaction on the back stack so that if the user clicks
    // the back button, they are brought back to the login form.
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(fragmentContainer, LoginHelpFragment.newInstance(configOptions));
    transaction.addToBackStack(null);
    transaction.commit();
  }

  /**
   * Called when the user successfully completes the login help flow.
   */
  @Override
  public void onLoginHelpSuccess() {
    // Display the login form, which is the previous item onto the stack
    getSupportFragmentManager().popBackStackImmediate();
  }

  /**
   * Called when the user successfully logs in or signs up.
   */
  @Override
  public void onLoginSuccess() {
    // This default implementation returns to the parent activity with
    // RESULT_OK.
    // You can change this implementation if you want a different behavior.
    setResult(RESULT_OK);
    finish();
  }

  /**
   * Called when we are in progress retrieving some data.
   *
   * @param showSpinner
   *     Whether to show the loading dialog.
   */
  @Override
  public void onLoadingStart(boolean showSpinner) {
    if (showSpinner) {
      progressDialog = ProgressDialog.show(this, null,
          getString(R.string.com_incpad_ui_progress_dialog_text), true, false);
    }
  }

  /**
   * Called when we are finished retrieving some data.
   */
  @Override
  public void onLoadingFinish() {
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
  }

  /**
   * @see android.app.Activity#isDestroyed()
   */
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  @Override
  public boolean isDestroyed() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      return super.isDestroyed();
    }
    return destroyed;
  }

  private Bundle getMergedOptions() {
    // Read activity metadata from AndroidManifest.xml
    ActivityInfo activityInfo = null;
    try {
      activityInfo = getPackageManager().getActivityInfo(
          this.getComponentName(), PackageManager.GET_META_DATA);
    } catch (NameNotFoundException e) {
      
        Log.w(LOG_TAG, e.getMessage());
    }

   
    Bundle mergedOptions = new Bundle();
    if (activityInfo != null && activityInfo.metaData != null) {
      mergedOptions.putAll(activityInfo.metaData);
    }
    if (getIntent().getExtras() != null) {
      mergedOptions.putAll(getIntent().getExtras());
    }

    return mergedOptions;
  }
}
