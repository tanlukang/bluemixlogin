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

import java.util.List;

import net.londatiga.android.twitter.Twitter;
import net.londatiga.android.twitter.TwitterRequest;
import net.londatiga.android.twitter.TwitterUser;
import net.londatiga.android.twitter.oauth.OauthAccessToken;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import bolts.Continuation;
import bolts.Task;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

/**
 * Fragment for the user login screen.
 */
public class LoginFragment extends LoginFragmentBase {

  public interface IncpadLoginFragmentListener {
    public void onSignUpClicked(String username, String password);

    public void onLoginHelpClicked();

    public void onLoginSuccess();
  }

  private static final String LOG_TAG = "IncpadLoginFragment";
  private static final String USER_OBJECT_NAME_FIELD = "name";

  private View IncpadLogin;
  private EditText usernameField;
  private EditText passwordField;
  private TextView IncpadLoginHelpButton;
  private Button IncpadLoginButton;
  private Button IncpadSignupButton;
  private Button facebookLoginButton;
  private Button twitterLoginButton;
  private IncpadLoginFragmentListener loginFragmentListener;
  private OnLoginSuccessListener onLoginSuccessListener;

  private LoginConfig config;
  
  private Twitter mTwitter;
	
	public static final String CONSUMER_KEY = "0jW64swvlejgAdJI4QzM3g";
	public static final String CONSUMER_SECRET = "Pr70LNXfRHt2FOWGvklYRozQTVrkR5zphI1OH2JQIYE";
	public static final String CALLBACK_URL = "http://www.inceptionpad.net/twitterhandler.php";

  public static LoginFragment newInstance(Bundle configOptions) {
    LoginFragment loginFragment = new LoginFragment();
    loginFragment.setArguments(configOptions);
    return loginFragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                           Bundle savedInstanceState) {
    config = LoginConfig.fromBundle(getArguments(), getActivity());

    View v = inflater.inflate(R.layout.com_incpad_ui_incpad_login_fragment,
        parent, false);
    ImageView appLogo = (ImageView) v.findViewById(R.id.app_logo);
    IncpadLogin = v.findViewById(R.id.incpad_login);
    usernameField = (EditText) v.findViewById(R.id.login_username_input);
    passwordField = (EditText) v.findViewById(R.id.login_password_input);
    IncpadLoginHelpButton = (Button) v.findViewById(R.id.incpad_login_help);
    IncpadLoginButton = (Button) v.findViewById(R.id.incpad_login_button);
    IncpadSignupButton = (Button) v.findViewById(R.id.incpad_signup_button);
    facebookLoginButton = (Button) v.findViewById(R.id.facebook_login);
    twitterLoginButton = (Button) v.findViewById(R.id.twitter_login);
    
    usernameField.setTextColor(Color.WHITE);
    passwordField.setTextColor(Color.WHITE);

    if (appLogo != null && config.getAppLogo() != null) {
      appLogo.setImageResource(config.getAppLogo());
    }
    //if (allowIncpadLoginAndSignup()) {
      setUpIncpadLoginAndSignup();
    //}
    //if (allowFacebookLogin()) {
      setUpFacebookLogin();
    //}
    //if (allowTwitterLogin()) {
      setUpTwitterLogin();
    //}
    return v;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    if (activity instanceof IncpadLoginFragmentListener) {
      loginFragmentListener = (IncpadLoginFragmentListener) activity;
    } else {
      throw new IllegalArgumentException(
          "Activity must implemement IncpadLoginFragmentListener");
    }

    if (activity instanceof OnLoginSuccessListener) {
      onLoginSuccessListener = (OnLoginSuccessListener) activity;
    } else {
      throw new IllegalArgumentException(
          "Activity must implemement IncpadOnLoginSuccessListener");
    }

    if (activity instanceof OnLoadingListener) {
      onLoadingListener = (OnLoadingListener) activity;
    } else {
      throw new IllegalArgumentException(
          "Activity must implemement IncpadOnLoadingListener");
    }
  }

  @Override
  protected String getLogTag() {
    return LOG_TAG;
  }

  private void setUpIncpadLoginAndSignup() {
    IncpadLogin.setVisibility(View.VISIBLE);

    if (config.isIncpadLoginEmailAsUsername()) {
      usernameField.setHint(R.string.com_incpad_ui_email_input_hint);
      usernameField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    }

    if (config.getIncpadLoginButtonText() != null) {
      IncpadLoginButton.setText(config.getIncpadLoginButtonText());
    }

    IncpadLoginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        if (username.length() == 0) {
          showToast(R.string.com_incpad_ui_no_username_toast);
        } else if (password.length() == 0) {
          showToast(R.string.com_incpad_ui_no_password_toast);
        } else {
          loadingStart(true);
          try {
  			IBMQuery<UserItem> query = IBMQuery.queryForClass(UserItem.class);
  			// Query all the Item objects from the server.
  			query.find().continueWith(new Continuation<List<UserItem>, Void>() {

  				@Override
  				public Void then(Task<List<UserItem>> task) throws Exception {
                    final List<UserItem> objects = task.getResult();
                     // Log if the find was cancelled.
                    if (task.isCancelled()){
                        Log.e("LoginFragment", "Exception : Task " + task.toString() + " was cancelled.");
                    }
  					 // Log error message, if the find task fails.
  					else if (task.isFaulted()) {
  						Log.e("LoginFragment", "Exception : " + task.getError().getMessage());
  					}
  					 // If the result succeeds, load the list.
  					else {
                        // Clear local itemList.
                        // We'll be reordering and repopulating from DataService.
                        for(IBMDataObject item:objects) {
                            if ( ((UserItem) item).getUserName().equals(usernameField.getText().toString()) ){
                          	  String pswd = SignupFragment.getMD5(passwordField.getText().toString());
                          	  if ( ((UserItem) item).getPassword().equals(pswd) ){
                          		loadingFinish();
                          		MainActivity.currentUser = usernameField.getText().toString();
                          		loginSuccess();
                          	  }
                          	  else{
                          		  showToast("Wrong Password");
                          	  }
                          	  return null;
                            }
                        }
                        showToast("Username does not exist!");
                		return null;
  					}
  					return null;
  				}
  			},Task.UI_THREAD_EXECUTOR);
  			
  		}  catch (IBMDataException error) {
  			Log.e("LoginFragment", "Exception : " + error.getMessage());
  		}
        }
      }
    });

    if (config.getIncpadSignupButtonText() != null) {
      IncpadSignupButton.setText(config.getIncpadSignupButtonText());
    }

    IncpadSignupButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        loginFragmentListener.onSignUpClicked(username, password);
      }
    });

    if (config.getIncpadLoginHelpText() != null) {
      IncpadLoginHelpButton.setText(config.getIncpadLoginHelpText());
    }

    IncpadLoginHelpButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        loginFragmentListener.onLoginHelpClicked();
      }
    });
  }

  private void setUpFacebookLogin() {
    facebookLoginButton.setVisibility(View.VISIBLE);

    if (config.getFacebookLoginButtonText() != null) {
      facebookLoginButton.setText(config.getFacebookLoginButtonText());
    }

    facebookLoginButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        loadingStart(true);
        Session.openActiveSession(getActivity(), true, new Session.StatusCallback() {

            // callback when session changes state
    		@Override
    		public void call(Session session, SessionState state,
    				Exception exception) {
    			// TODO Auto-generated method stub
    			if (session.isOpened()) {
    	            // make request to the /me API
    	            Request.newMeRequest(session, new Request.GraphUserCallback() {
    	              // callback after Graph API response with user object
    				@Override
    				public void onCompleted(GraphUser user, Response response) {
    					// TODO Auto-generated method stub
    					if (user != null) {
    		                  //TextView welcome = (TextView) findViewById(R.id.welcome);
    		                  //welcome.setText("Hello " + user.getName() + "!");
    						  loadingFinish();
    		                  MainActivity.currentUser = user.getName();
    		                  loginSuccess();
    		                }
    				}
    	            }).executeAsync();
    	          }
    		}
          });
      }
    });
  }

  private void setUpTwitterLogin() {
    twitterLoginButton.setVisibility(View.VISIBLE);

    if (config.getTwitterLoginButtonText() != null) {
      twitterLoginButton.setText(config.getTwitterLoginButtonText());
    }
    mTwitter = new Twitter(getActivity(), CONSUMER_KEY, CONSUMER_SECRET, CALLBACK_URL);
    twitterLoginButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
    	  signinTwitter();
      }
    });
  }
  
  private void signinTwitter() {
		mTwitter.signin(new Twitter.SigninListener() {				
			@Override
			public void onSuccess(OauthAccessToken accessToken, String userId, String screenName) {
				getCredentials();
			}
			
			@Override
			public void onError(String error) {
				showToast(error);
			}
		});
	}
	
	private void getCredentials() {
		final ProgressDialog progressDlg = new ProgressDialog(getActivity());
		
		progressDlg.setMessage("Getting credentials...");
		progressDlg.setCancelable(false);
		
		progressDlg.show();
		
		TwitterRequest request = new TwitterRequest(mTwitter.getConsumer(), mTwitter.getAccessToken());
		
		request.verifyCredentials(new TwitterRequest.VerifyCredentialListener() {
			
			@Override
			public void onSuccess(TwitterUser user) {
				progressDlg.dismiss();				
				MainActivity.currentUser = user.screenName;
                loginSuccess();
			}
			
			@Override
			public void onError(String error) {
				progressDlg.dismiss();
				
				showToast(error);
			}
		});
	}

  private boolean allowIncpadLoginAndSignup() {
    if (!config.isIncpadLoginEnabled()) {
      return false;
    }

    if (usernameField == null) {
      debugLog(R.string.com_incpad_ui_login_warning_layout_missing_username_field);
    }
    if (passwordField == null) {
      debugLog(R.string.com_incpad_ui_login_warning_layout_missing_password_field);
    }
    if (IncpadLoginButton == null) {
      debugLog(R.string.com_incpad_ui_login_warning_layout_missing_login_button);
    }
    if (IncpadSignupButton == null) {
      debugLog(R.string.com_incpad_ui_login_warning_layout_missing_signup_button);
    }
    if (IncpadLoginHelpButton == null) {
      debugLog(R.string.com_incpad_ui_login_warning_layout_missing_login_help_button);
    }

    boolean result = (usernameField != null) && (passwordField != null)
        && (IncpadLoginButton != null) && (IncpadSignupButton != null)
        && (IncpadLoginHelpButton != null);

    if (!result) {
      debugLog(R.string.com_incpad_ui_login_warning_disabled_username_password_login);
    }
    return result;
  }

  private boolean allowFacebookLogin() {
    if (!config.isFacebookLoginEnabled()) {
      return false;
    }

    if (facebookLoginButton == null) {
      debugLog(R.string.com_incpad_ui_login_warning_disabled_facebook_login);
      return false;
    } else {
      return true;
    }
  }

  private boolean allowTwitterLogin() {
    if (!config.isTwitterLoginEnabled()) {
      return false;
    }

    if (twitterLoginButton == null) {
      debugLog(R.string.com_incpad_ui_login_warning_disabled_twitter_login);
      return false;
    } else {
      return true;
    }
  }

  private void loginSuccess() {
    onLoginSuccessListener.onLoginSuccess();
  }

}
