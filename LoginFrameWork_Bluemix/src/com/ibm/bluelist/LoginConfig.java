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

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.util.Log;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Configurations for the IncpadLoginActivity.
 */
public class LoginConfig {
  public static final String APP_LOGO = "com.Incpad.ui.IncpadLoginActivity.APP_LOGO";
  public static final String Incpad_LOGIN_ENABLED = "com.Incpad.ui.IncpadLoginActivity.Incpad_LOGIN_ENABLED";
  public static final String Incpad_LOGIN_BUTTON_TEXT = "com.Incpad.ui.IncpadLoginActivity.Incpad_LOGIN_BUTTON_TEXT";
  public static final String Incpad_SIGNUP_BUTTON_TEXT = "com.Incpad.ui.IncpadLoginActivity.Incpad_SIGNUP_BUTTON_TEXT";
  public static final String Incpad_LOGIN_HELP_TEXT = "com.Incpad.ui.IncpadLoginActivity.Incpad_LOGIN_HELP_TEXT";
  public static final String Incpad_LOGIN_INVALID_CREDENTIALS_TOAST_TEXT = "com.Incpad.ui.IncpadLoginActivity.Incpad_LOGIN_INVALID_CREDENTIALS_TEXT";
  public static final String Incpad_LOGIN_EMAIL_AS_USERNAME = "com.Incpad.ui.IncpadLoginActivity.Incpad_LOGIN_EMAIL_AS_USERNAME";
  public static final String Incpad_SIGNUP_MIN_PASSWORD_LENGTH = "com.Incpad.ui.IncpadLoginActivity.Incpad_SIGNUP_MIN_PASSWORD_LENGTH";
  public static final String Incpad_SIGNUP_SUBMIT_BUTTON_TEXT = "com.Incpad.ui.IncpadLoginActivity.Incpad_SIGNUP_SUBMIT_BUTTON_TEXT";
  public static final String FACEBOOK_LOGIN_ENABLED = "com.Incpad.ui.IncpadLoginActivity.FACEBOOK_LOGIN_ENABLED";
  public static final String FACEBOOK_LOGIN_BUTTON_TEXT = "com.Incpad.ui.IncpadLoginActivity.FACEBOOK_LOGIN_BUTTON_TEXT";
  public static final String FACEBOOK_LOGIN_PERMISSIONS = "com.Incpad.ui.IncpadLoginActivity.FACEBOOK_LOGIN_PERMISSIONS";
  public static final String TWITTER_LOGIN_ENABLED = "com.Incpad.ui.IncpadLoginActivity.TWITTER_LOGIN_ENABLED";
  public static final String TWITTER_LOGIN_BUTTON_TEXT = "com.Incpad.ui.IncpadLoginActivity.TWITTER_LOGIN_BUTTON_TEXT";

  // For internally serializing to/from string array (the public analog above is for resource from activity meta-data).
  private static final String FACEBOOK_LOGIN_PERMISSIONS_STRING_ARRAY = "com.Incpad.ui.IncpadLoginActivity.FACEBOOK_LOGIN_PERMISSIONS_STRING_ARRAY";

  private static final String LOG_TAG = "com.Incpad.ui.IncpadLoginConfig";

  // Use boxed types so that we can differentiate between a setting not set,
  // versus its default value.  This is useful for merging options set from code
  // with options set by activity metadata.
  private Integer appLogo;
  private Boolean IncpadLoginEnabled;
  private CharSequence IncpadLoginButtonText;
  private CharSequence IncpadSignupButtonText;
  private CharSequence IncpadLoginHelpText;
  private CharSequence IncpadLoginInvalidCredentialsToastText;
  private Boolean IncpadLoginEmailAsUsername;
  private Integer IncpadSignupMinPasswordLength;
  private CharSequence IncpadSignupSubmitButtonText;

  private Boolean facebookLoginEnabled;
  private CharSequence facebookLoginButtonText;
  private Collection<String> facebookLoginPermissions;

  private Boolean twitterLoginEnabled;
  private CharSequence twitterLoginButtonText;

  public Integer getAppLogo() {
    return appLogo;
  }

  public void setAppLogo(Integer appLogo) {
    this.appLogo = appLogo;
  }

  public boolean isIncpadLoginEnabled() {
    if (IncpadLoginEnabled != null) {
      return IncpadLoginEnabled;
    } else {
      return false;
    }
  }

  public void setIncpadLoginEnabled(boolean IncpadLoginEnabled) {
    this.IncpadLoginEnabled = IncpadLoginEnabled;
  }

  public CharSequence getIncpadLoginButtonText() {
    return IncpadLoginButtonText;
  }

  public void setIncpadLoginButtonText(CharSequence IncpadLoginButtonText) {
    this.IncpadLoginButtonText = IncpadLoginButtonText;
  }

  public CharSequence getIncpadSignupButtonText() {
    return IncpadSignupButtonText;
  }

  public void setIncpadSignupButtonText(CharSequence IncpadSignupButtonText) {
    this.IncpadSignupButtonText = IncpadSignupButtonText;
  }

  public CharSequence getIncpadLoginHelpText() {
    return IncpadLoginHelpText;
  }

  public void setIncpadLoginHelpText(CharSequence IncpadLoginHelpText) {
    this.IncpadLoginHelpText = IncpadLoginHelpText;
  }

  public CharSequence getIncpadLoginInvalidCredentialsToastText() {
    return IncpadLoginInvalidCredentialsToastText;
  }

  public void setIncpadLoginInvalidCredentialsToastText(
      CharSequence IncpadLoginInvalidCredentialsToastText) {
    this.IncpadLoginInvalidCredentialsToastText = IncpadLoginInvalidCredentialsToastText;
  }

  public boolean isIncpadLoginEmailAsUsername() {
    if (IncpadLoginEmailAsUsername != null) {
      return IncpadLoginEmailAsUsername;
    } else {
      return false;
    }
  }

  public void setIncpadLoginEmailAsUsername(boolean IncpadLoginEmailAsUsername) {
    this.IncpadLoginEmailAsUsername = IncpadLoginEmailAsUsername;
  }

  public Integer getIncpadSignupMinPasswordLength() {
    return IncpadSignupMinPasswordLength;
  }

  public void setIncpadSignupMinPasswordLength(Integer IncpadSignupMinPasswordLength) {
    this.IncpadSignupMinPasswordLength = IncpadSignupMinPasswordLength;
  }

  public CharSequence getIncpadSignupSubmitButtonText() {
    return IncpadSignupSubmitButtonText;
  }

  public void setIncpadSignupSubmitButtonText(
      CharSequence IncpadSignupSubmitButtonText) {
    this.IncpadSignupSubmitButtonText = IncpadSignupSubmitButtonText;
  }

  public boolean isFacebookLoginEnabled() {
    if (facebookLoginEnabled != null) {
      return facebookLoginEnabled;
    } else {
      return false;
    }
  }

  public void setFacebookLoginEnabled(boolean facebookLoginEnabled) {
    this.facebookLoginEnabled = facebookLoginEnabled;
  }

  public CharSequence getFacebookLoginButtonText() {
    return facebookLoginButtonText;
  }

  public void setFacebookLoginButtonText(CharSequence facebookLoginButtonText) {
    this.facebookLoginButtonText = facebookLoginButtonText;
  }

  public Collection<String> getFacebookLoginPermissions() {
    if (facebookLoginPermissions != null) {
      return Collections.unmodifiableCollection(facebookLoginPermissions);
    } else {
      return null;
    }
  }

  public void setFacebookLoginPermissions(Collection<String> permissions) {
    if (permissions != null) {
      facebookLoginPermissions = new ArrayList<String>(permissions.size());
      facebookLoginPermissions.addAll(permissions);
    }
  }

  public boolean isTwitterLoginEnabled() {
    if (twitterLoginEnabled != null) {
      return twitterLoginEnabled;
    } else {
      return false;
    }
  }

  public void setTwitterLoginEnabled(boolean twitterLoginEnabled) {
    this.twitterLoginEnabled = twitterLoginEnabled;
  }

  public CharSequence getTwitterLoginButtonText() {
    return twitterLoginButtonText;
  }

  public void setTwitterLoginButtonText(CharSequence twitterLoginButtonText) {
    this.twitterLoginButtonText = twitterLoginButtonText;
  }

  /**
   * Converts this object into a Bundle object. For options that are not
   * explicitly set, we do not include them in the Bundle so that this bundle
   * can be merged with any default configurations and override only those keys
   * that are explicitly set.
   *
   * @return The Bundle object containing configurations.
   */
  public Bundle toBundle() {
    Bundle bundle = new Bundle();

    if (appLogo != null) {
      bundle.putInt(APP_LOGO, appLogo);
    }

    if (IncpadLoginEnabled != null) {
      bundle.putBoolean(Incpad_LOGIN_ENABLED, IncpadLoginEnabled);
    }
    if (IncpadLoginButtonText != null) {
      bundle.putCharSequence(Incpad_LOGIN_BUTTON_TEXT, IncpadLoginButtonText);
    }
    if (IncpadSignupButtonText != null) {
      bundle.putCharSequence(Incpad_SIGNUP_BUTTON_TEXT, IncpadSignupButtonText);
    }
    if (IncpadLoginHelpText != null) {
      bundle.putCharSequence(Incpad_LOGIN_HELP_TEXT, IncpadLoginHelpText);
    }
    if (IncpadLoginInvalidCredentialsToastText != null) {
      bundle.putCharSequence(Incpad_LOGIN_INVALID_CREDENTIALS_TOAST_TEXT,
          IncpadLoginInvalidCredentialsToastText);
    }
    if (IncpadLoginEmailAsUsername != null) {
      bundle.putBoolean(Incpad_LOGIN_EMAIL_AS_USERNAME,
          IncpadLoginEmailAsUsername);
    }
    if (IncpadSignupMinPasswordLength != null) {
      bundle.putInt(Incpad_SIGNUP_MIN_PASSWORD_LENGTH,
          IncpadSignupMinPasswordLength);
    }
    if (IncpadSignupSubmitButtonText != null) {
      bundle.putCharSequence(Incpad_SIGNUP_SUBMIT_BUTTON_TEXT,
          IncpadSignupSubmitButtonText);
    }

    if (facebookLoginEnabled != null) {
      bundle.putBoolean(FACEBOOK_LOGIN_ENABLED, facebookLoginEnabled);
    }
    if (facebookLoginButtonText != null) {
      bundle.putCharSequence(FACEBOOK_LOGIN_BUTTON_TEXT,
          facebookLoginButtonText);
    }
    if (facebookLoginPermissions != null) {
      bundle.putStringArray(FACEBOOK_LOGIN_PERMISSIONS_STRING_ARRAY,
          facebookLoginPermissions.toArray(new String[0]));
    }

    if (twitterLoginEnabled != null) {
      bundle.putBoolean(TWITTER_LOGIN_ENABLED, twitterLoginEnabled);
    }
    if (twitterLoginButtonText != null) {
      bundle.putCharSequence(TWITTER_LOGIN_BUTTON_TEXT, twitterLoginButtonText);
    }

    return bundle;
  }

  /**
   * Constructs a IncpadLoginConfig object from a bundle. Unrecognized keys are
   * ignored.
   * <p/>
   * This can be used to pass an IncpadLoginConfig object between activities, or
   * to read settings from an activity's meta-data in Manefest.xml.
   *
   * @param bundle
   *     The Bundle representation of the IncpadLoginConfig object.
   * @param context
   *     The context for resolving resource IDs.
   * @return The IncpadLoginConfig instance.
   */
  public static LoginConfig fromBundle(Bundle bundle, Context context) {
    LoginConfig config = new LoginConfig();
    Set<String> keys = bundle.keySet();

    if (keys.contains(APP_LOGO)) {
      config.setAppLogo(bundle.getInt(APP_LOGO));
    }

    if (keys.contains(Incpad_LOGIN_ENABLED)) {
      config.setIncpadLoginEnabled(bundle.getBoolean(Incpad_LOGIN_ENABLED));
    }
    if (keys.contains(Incpad_LOGIN_BUTTON_TEXT)) {
      config.setIncpadLoginButtonText(bundle.getCharSequence(Incpad_LOGIN_BUTTON_TEXT));
    }
    if (keys.contains(Incpad_SIGNUP_BUTTON_TEXT)) {
      config.setIncpadSignupButtonText(bundle.getCharSequence(Incpad_SIGNUP_BUTTON_TEXT));
    }
    if (keys.contains(Incpad_LOGIN_HELP_TEXT)) {
      config.setIncpadLoginHelpText(bundle.getCharSequence(Incpad_LOGIN_HELP_TEXT));
    }
    if (keys.contains(Incpad_LOGIN_INVALID_CREDENTIALS_TOAST_TEXT)) {
      config.setIncpadLoginInvalidCredentialsToastText(bundle
          .getCharSequence(Incpad_LOGIN_INVALID_CREDENTIALS_TOAST_TEXT));
    }
    if (keys.contains(Incpad_LOGIN_EMAIL_AS_USERNAME)) {
      config.setIncpadLoginEmailAsUsername(bundle.getBoolean(Incpad_LOGIN_EMAIL_AS_USERNAME));
    }
    if (keys.contains(Incpad_SIGNUP_MIN_PASSWORD_LENGTH)) {
      config.setIncpadSignupMinPasswordLength(bundle.getInt(Incpad_SIGNUP_MIN_PASSWORD_LENGTH));
    }
    if (keys.contains(Incpad_SIGNUP_SUBMIT_BUTTON_TEXT)) {
      config.setIncpadSignupSubmitButtonText(bundle.getCharSequence(Incpad_SIGNUP_SUBMIT_BUTTON_TEXT));
    }

    if (keys.contains(FACEBOOK_LOGIN_ENABLED)) {
      config.setFacebookLoginEnabled(bundle.getBoolean(FACEBOOK_LOGIN_ENABLED));
    }
    if (keys.contains(FACEBOOK_LOGIN_BUTTON_TEXT)) {
      config.setFacebookLoginButtonText(bundle.getCharSequence(FACEBOOK_LOGIN_BUTTON_TEXT));
    }
    if (keys.contains(FACEBOOK_LOGIN_PERMISSIONS) &&
        bundle.getInt(FACEBOOK_LOGIN_PERMISSIONS) != 0) {
      // Only for converting from activity meta-data.
      try {
        config.setFacebookLoginPermissions(stringArrayToCollection(context
            .getResources().getStringArray(
                bundle.getInt(FACEBOOK_LOGIN_PERMISSIONS))));
      } catch (NotFoundException e) {
        //if (Incpad.getLogLevel() <= Incpad.LOG_LEVEL_ERROR) {
          Log.w(LOG_TAG, "Facebook permission string array resource not found");
       // }
      }
    } else if (keys.contains(FACEBOOK_LOGIN_PERMISSIONS_STRING_ARRAY)) {
      // For converting from a bundle produced by this class's toBundle()
      config.setFacebookLoginPermissions(stringArrayToCollection(bundle
          .getStringArray(FACEBOOK_LOGIN_PERMISSIONS_STRING_ARRAY)));
    }

    if (keys.contains(TWITTER_LOGIN_ENABLED)) {
      config.setTwitterLoginEnabled(bundle.getBoolean(TWITTER_LOGIN_ENABLED));
    }
    if (keys.contains(TWITTER_LOGIN_BUTTON_TEXT)) {
      config.setTwitterLoginButtonText(bundle
          .getCharSequence(TWITTER_LOGIN_BUTTON_TEXT));
    }

    return config;
  }

  private static Collection<String> stringArrayToCollection(String[] array) {
    if (array == null) {
      return null;
    }
    return Arrays.asList(array);
  }
}
