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

import java.util.Collection;

import android.content.Context;
import android.content.Intent;

public class LoginBuilder {

  private Context context;
  private LoginConfig config = new LoginConfig();

  public LoginBuilder(Context context) {
    this.context = context;
  }

  /**
   * Customize the logo shown in the login screens
   *
   * @param id
   *     The resource ID for the logo drawable.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setAppLogo(int id) {
    config.setAppLogo(id);
    return this;
  }

  /**
   * Whether to show Incpad username/password UI elements on the login screen,
   * and the associated signup screen. Default is false.
   *
   * @param enabled
   *     Whether to show the username/password login.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setIncpadLoginEnabled(boolean enabled) {
    config.setIncpadLoginEnabled(enabled);
    return this;
  }

  /**
   * Customize the text of the Incpad username/password login button.
   *
   * @param text
   *     The text to display on the button.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setIncpadLoginButtonText(CharSequence text) {
    config.setIncpadLoginButtonText(text);
    return this;
  }

  /**
   * Customize the text of the Incpad username/password login button.
   *
   * @param id
   *     The resource ID for the text to display on the login button.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setIncpadLoginButtonText(int id) {
    return setIncpadLoginButtonText(maybeGetString(id));
  }

  /**
   * Customize the text on the Incpad signup button.
   *
   * @param text
   *     The text to display on the button.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setIncpadSignupButtonText(CharSequence text) {
    config.setIncpadSignupButtonText(text);
    return this;
  }

  /**
   * Customize the text on the Incpad signup button.
   *
   * @param id
   *     The resource ID for the text to display on the button.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setIncpadSignupButtonText(int id) {
    return setIncpadSignupButtonText(maybeGetString(id));
  }

  /**
   * Customize the text for the link to resetting the user's forgotten password.
   *
   * @param text
   *     The text to display on the link.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setIncpadLoginHelpText(CharSequence text) {
    config.setIncpadLoginHelpText(text);
    return this;
  }

  /**
   * Customize the text for the link to resetting the user's forgotten password.
   *
   * @param id
   *     The resource ID for the text to display on the link.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setIncpadLoginHelpText(int id) {
    return setIncpadLoginHelpText(maybeGetString(id));
  }

  /**
   * Customize the toast shown when the user enters an invalid username/password
   * pair.
   *
   * @param text
   *     The text to display on the toast.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setIncpadLoginInvalidCredentialsToastText(
      CharSequence text) {
    config.setIncpadLoginInvalidCredentialsToastText(text);
    return this;
  }

  /**
   * Customize the toast shown when the user enters an invalid username/password
   * pair.
   *
   * @param id
   *     The resource ID for the text to display on the toast.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setIncpadLoginInvalidCredentialsToastText(int id) {
    return setIncpadLoginInvalidCredentialsToastText(maybeGetString(id));
  }

  /**
   * Use the user's email as their username. By default, the user's username is
   * separate from their email; we ask the user for their username and email on
   * the signup form, and ask for their username on the login form. If this
   * option is set to true, we'll not ask for their username on the signup and
   * login forms; users will just enter their email on both (internally we'll
   * send the user email as the username when calling the Incpad SDK).
   *
   * @param emailAsUsername
   *     Whether to use email as the user's username in the Incpad SDK.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setIncpadLoginEmailAsUsername(boolean emailAsUsername) {
    config.setIncpadLoginEmailAsUsername(emailAsUsername);
    return this;
  }

  /**
   * Sets the minimum required password length on the user signup page.
   *
   * @param minPasswordLength
   *     The minimum required password length for signups
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setIncpadSignupMinPasswordLength(int minPasswordLength) {
    config.setIncpadSignupMinPasswordLength(minPasswordLength);
    return this;
  }

  /**
   * Customize the submit button on the Incpad signup screen. This signup screen
   * is only shown if you enable Incpad username/password login.
   *
   * @param text
   *     The text to display on the user signup submission button.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setIncpadSignupSubmitButtonText(CharSequence text) {
    config.setIncpadSignupSubmitButtonText(text);
    return this;
  }

  /**
   * Customize the submit button on the Incpad signup screen. This signup screen
   * is only shown if you enable Incpad username/password login.
   *
   * @param id
   *     The resource ID fo the text to display on the user signup
   *     submission button.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setIncpadSignupSubmitButtonText(int id) {
    return setIncpadSignupSubmitButtonText(maybeGetString(id));
  }

  /**
   * Whether to show the Facebook login option on the login screen. Default is
   * false.
   *
   * @param enabled
   *     Whether to show the facebook login.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setFacebookLoginEnabled(boolean enabled) {
    config.setFacebookLoginEnabled(enabled);
    return this;
  }

  /**
   * Customize the text on the Facebook login button.
   *
   * @param text
   *     The text to display on the Facebook login button.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setFacebookLoginButtonText(CharSequence text) {
    config.setFacebookLoginButtonText(text);
    return this;
  }

  /**
   * Customize the text on the Facebook login button.
   *
   * @param id
   *     The resource ID for the text to display on the Facebook login
   *     button.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setFacebookLoginButtonText(int id) {
    config.setFacebookLoginButtonText(maybeGetString(id));
    return this;
  }

  /**
   * Customize the requested permissions for Facebook login
   *
   * @param permissions
   *     The Facebook permissions being requested.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setFacebookLoginPermissions(
      Collection<String> permissions) {
    config.setFacebookLoginPermissions(permissions);
    return this;
  }

  /**
   * Whether to show the Twitter login option on the login screen. Default is
   * false.
   *
   * @param enabled
   *     Whether to show the Twitter login.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setTwitterLoginEnabled(boolean enabled) {
    config.setTwitterLoginEnabled(enabled);
    return this;
  }

  /**
   * Customize the text on the Twitter login button.
   *
   * @param loginButtonText
   *     The text to display on the Twitter login button.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setTwitterLoginButtontext(
      CharSequence loginButtonText) {
    config.setTwitterLoginButtonText(loginButtonText);
    return this;
  }

  /**
   * Customize the text on the Twitter login button.
   *
   * @param id
   *     The text to display on the Twitter login button.
   * @return The caller instance to allow chaining.
   */
  public LoginBuilder setTwitterLoginButtontext(int id) {
    config.setTwitterLoginButtonText(maybeGetString(id));
    return this;
  }

  /**
   * Construct an intent that can be used to start IncpadLoginActivity with the
   * specified customizations.
   *
   * @return The intent for starting IncpadLoginActivity
   */
  public Intent build() {
    Intent intent = new Intent(context, LoginActivity.class);
    intent.putExtras(config.toBundle());
    return intent;
  }

  private String maybeGetString(int id) {
    return id != 0 ? context.getString(id) : null;
  }
}
