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

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Fragment for the login help screen for resetting the user's password.
 */
public class LoginHelpFragment extends LoginFragmentBase implements OnClickListener {

  public interface IncpadOnLoginHelpSuccessListener {
    public void onLoginHelpSuccess();
  }

  private TextView instructionsTextView;
  private EditText emailField;
  private Button submitButton;
  private boolean emailSent = false;
  private IncpadOnLoginHelpSuccessListener onLoginHelpSuccessListener;

  private LoginConfig config;

  private static final String LOG_TAG = "IncpadLoginHelpFragment";

  public static LoginHelpFragment newInstance(Bundle configOptions) {
    LoginHelpFragment loginHelpFragment = new LoginHelpFragment();
    loginHelpFragment.setArguments(configOptions);
    return loginHelpFragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                           Bundle savedInstanceState) {
    config = LoginConfig.fromBundle(getArguments(), getActivity());

    View v = inflater.inflate(R.layout.com_incpad_ui_incpad_login_help_fragment,
        parent, false);
    ImageView appLogo = (ImageView) v.findViewById(R.id.app_logo);
    instructionsTextView = (TextView) v
        .findViewById(R.id.login_help_instructions);
    emailField = (EditText) v.findViewById(R.id.login_help_email_input);
    submitButton = (Button) v.findViewById(R.id.login_help_submit);
    
    emailField.setTextColor(Color.WHITE);

    if (appLogo != null && config.getAppLogo() != null) {
      appLogo.setImageResource(config.getAppLogo());
    }

    submitButton.setOnClickListener(this);
    return v;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    if (activity instanceof OnLoadingListener) {
      onLoadingListener = (OnLoadingListener) activity;
    } else {
      throw new IllegalArgumentException(
          "Activity must implemement IncpadOnLoadingListener");
    }

    if (activity instanceof IncpadOnLoginHelpSuccessListener) {
      onLoginHelpSuccessListener = (IncpadOnLoginHelpSuccessListener) activity;
    } else {
      throw new IllegalArgumentException(
          "Activity must implemement IncpadOnLoginHelpSuccessListener");
    }
  }

  @Override
  public void onClick(View v) {
    if (!emailSent) {
      String email = emailField.getText().toString();
      if (email.length() == 0) {
        showToast(R.string.com_incpad_ui_no_email_toast);
      } else {
    	  showToast("not support now");
        //loadingStart();
//        IncpadUser.requestPasswordResetInBackground(email,
//            new RequestPasswordResetCallback() {
//              @Override
//              public void done(IncpadException e) {
//                if (isActivityDestroyed()) {
//                  return;
//                }
//
//                loadingFinish();
//                if (e == null) {
//                  instructionsTextView
//                      .setText(R.string.com_Incpad_ui_login_help_email_sent);
//                  emailField.setVisibility(View.INVISIBLE);
//                  submitButton
//                      .setText(R.string.com_Incpad_ui_login_help_login_again_button_label);
//                  emailSent = true;
//                } else {
//                  debugLog(getString(R.string.com_Incpad_ui_login_warning_password_reset_failed) +
//                      e.toString());
//                  if (e.getCode() == IncpadException.INVALID_EMAIL_ADDRESS ||
//                      e.getCode() == IncpadException.EMAIL_NOT_FOUND) {
//                    showToast(R.string.com_Incpad_ui_invalid_email_toast);
//                  } else {
//                    showToast(R.string.com_Incpad_ui_login_help_submit_failed_unknown);
//                  }
//                }
//              }
//            });
      }
    } else {
      onLoginHelpSuccessListener.onLoginHelpSuccess();
    }
  }

  @Override
  protected String getLogTag() {
    return LOG_TAG;
  }
}
