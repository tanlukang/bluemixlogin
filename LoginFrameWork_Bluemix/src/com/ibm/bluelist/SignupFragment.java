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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;
import com.ibm.mobile.services.data.IBMQueryCondition;

import android.app.Activity;
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
import android.widget.Toast;


/**
 * Fragment for the user signup screen.
 */
public class SignupFragment extends LoginFragmentBase implements OnClickListener {
  public static final String USERNAME = "com.Incpad.ui.IncpadSignupFragment.USERNAME";
  public static final String PASSWORD = "com.Incpad.ui.IncpadSignupFragment.PASSWORD";
  public static final String CLASS_NAME="SignupFragment";

  private EditText usernameField;
  private EditText passwordField;
  private EditText confirmPasswordField;
  private EditText emailField;
  private EditText nameField;
  private Button createAccountButton;
  private OnLoginSuccessListener onLoginSuccessListener;

  private LoginConfig config;
  private int minPasswordLength;

  private static final String LOG_TAG = "IncpadSignupFragment";
  private static final int DEFAULT_MIN_PASSWORD_LENGTH = 6;
  private static final String USER_OBJECT_NAME_FIELD = "name";
  public String cur_username = "";
  public String cur_name = "";
  public String cur_email = "";
  public String cur_password = "";

  public static SignupFragment newInstance(Bundle configOptions, String username, String password) {
    SignupFragment signupFragment = new SignupFragment();
    Bundle args = new Bundle(configOptions);
    args.putString(SignupFragment.USERNAME, username);
    args.putString(SignupFragment.PASSWORD, password);
    signupFragment.setArguments(args);
    return signupFragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                           Bundle savedInstanceState) {

    Bundle args = getArguments();
    config = LoginConfig.fromBundle(args, getActivity());

    minPasswordLength = DEFAULT_MIN_PASSWORD_LENGTH;
    if (config.getIncpadSignupMinPasswordLength() != null) {
      minPasswordLength = config.getIncpadSignupMinPasswordLength();
    }

    String username = (String) args.getString(USERNAME);
    String password = (String) args.getString(PASSWORD);

    View v = inflater.inflate(R.layout.com_incpad_ui_incpad_signup_fragment,
        parent, false);
    ImageView appLogo = (ImageView) v.findViewById(R.id.app_logo);
    usernameField = (EditText) v.findViewById(R.id.signup_username_input);
    passwordField = (EditText) v.findViewById(R.id.signup_password_input);
    confirmPasswordField = (EditText) v
        .findViewById(R.id.signup_confirm_password_input);
    emailField = (EditText) v.findViewById(R.id.signup_email_input);
    nameField = (EditText) v.findViewById(R.id.signup_name_input);
    createAccountButton = (Button) v.findViewById(R.id.create_account);

    usernameField.setText(username);
    passwordField.setText(password);
    
    usernameField.setTextColor(Color.WHITE);
    passwordField.setTextColor(Color.WHITE);
    confirmPasswordField.setTextColor(Color.WHITE);
    emailField.setTextColor(Color.WHITE);
    nameField.setTextColor(Color.WHITE);
    
    if (appLogo != null && config.getAppLogo() != null) {
      appLogo.setImageResource(config.getAppLogo());
    }

    if (config.isIncpadLoginEmailAsUsername()) {
      usernameField.setHint(R.string.com_incpad_ui_email_input_hint);
      usernameField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
      if (emailField != null) {
        emailField.setVisibility(View.GONE);
      }
    }

    if (config.getIncpadSignupSubmitButtonText() != null) {
      createAccountButton.setText(config.getIncpadSignupSubmitButtonText());
    }
    createAccountButton.setOnClickListener(this);

    return v;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
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
  public void onClick(View v) {
    String username = usernameField.getText().toString();
    String password = passwordField.getText().toString();
    String passwordAgain = confirmPasswordField.getText().toString();

    String email = null;
    if (config.isIncpadLoginEmailAsUsername()) {
      email = usernameField.getText().toString();
    } else if (emailField != null) {
      email = emailField.getText().toString();
    }

    String name = null;
    if (nameField != null) {
      name = nameField.getText().toString();
    }

    if (username.length() == 0) {
      showToast(R.string.com_incpad_ui_no_username_toast);
    } else if (password.length() == 0) {
      showToast(R.string.com_incpad_ui_no_password_toast);
    } else if (password.length() < minPasswordLength) {
      showToast(getResources().getQuantityString(
          R.plurals.com_incpad_ui_password_too_short_toast,
          minPasswordLength, minPasswordLength));
    } else if (passwordAgain.length() == 0) {
      showToast(R.string.com_incpad_ui_reenter_password_toast);
    } else if (!password.equals(passwordAgain)) {
      showToast(R.string.com_incpad_ui_mismatch_confirm_password_toast);
      confirmPasswordField.selectAll();
      confirmPasswordField.requestFocus();
    } else if (email != null && email.length() == 0) {
      showToast(R.string.com_incpad_ui_no_email_toast);
    } else if (name != null && name.length() == 0) {
      showToast(R.string.com_incpad_ui_no_name_toast);
    } else {
    	cur_username = username ;
		cur_password = password;
		cur_email = email;
		cur_name = name;
		checkUserName();
		//createUser(username,password,email,name);
    }
  }
  
  public void checkUserName(){
	  try {
			IBMQuery<UserItem> query = IBMQuery.queryForClass(UserItem.class);
			// Query all the Item objects from the server.
			query.find().continueWith(new Continuation<List<UserItem>, Void>() {

				@Override
				public Void then(Task<List<UserItem>> task) throws Exception {
                  final List<UserItem> objects = task.getResult();
                   // Log if the find was cancelled.
                  if (task.isCancelled()){
                      Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
                  }
					 // Log error message, if the find task fails.
					else if (task.isFaulted()) {
						Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
					}

					
					 // If the result succeeds, load the list.
					else {
                      // Clear local itemList.
                      // We'll be reordering and repopulating from DataService.
                      for(IBMDataObject item:objects) {
                          if ( ((UserItem) item).getUserName().equals(cur_username) ){
                        	  showToast("Username existed");
                        	  return null;
                          }
                      }
                      createUser(cur_username,cur_password,cur_email,cur_name);
					}
					return null;
				}
			},Task.UI_THREAD_EXECUTOR);
			
		}  catch (IBMDataException error) {
			Log.e(CLASS_NAME, "Exception : " + error.getMessage());
		}
  }
  
  /**
	 * Called on done and will add item to list.
	 *
	 * @param  v edittext View to get item from.
	 * @throws NoSuchAlgorithmException 
	 * @throws IBMDataException 
	 */
	public void createUser(String username,String pswd,String email,String name) throws NoSuchAlgorithmException {
		
		
		String str_username = username;
		String str_pswd = pswd;
		String str_email = email;
		String str_name = name;
		
		UserItem item = new UserItem();
		//if (!username.equals("")) {
			item.setName(str_name);
			item.setUserName(str_username);
			item.setPassword(getMD5(str_pswd));
			item.setEmail(str_email);
			loadingStart();
			// Use the IBMDataObject to create and persist the Item object.
			item.save().continueWith(new Continuation<IBMDataObject, Void>() {

				@Override
				public Void then(Task<IBMDataObject> task) throws Exception {
                  // Log if the save was cancelled.
                  if (task.isCancelled()){
                      Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
                  }
					 // Log error message, if the save task fails.
					else if (task.isFaulted()) {
						Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
					}

					 // If the result succeeds, load the list.
					else {
						loadingFinish();//listItems();
						signupSuccess();
					}
					return null;
				}

			});
			
			// Set text field back to empty after item is added.
			//itemToAdd.setText("");
		//}
	}
	
	public static  String getMD5(String val) throws NoSuchAlgorithmException{  
      MessageDigest md5 = MessageDigest.getInstance("MD5");  
      md5.update(val.getBytes());  
      byte[] m = md5.digest();//º”√‹  
      return getString(m);  
} 
	 private static String getString(byte[] b){  
	        StringBuffer sb = new StringBuffer();  
	         for(int i = 0; i < b.length; i ++){  
	          sb.append(b[i]);  
	         }  
	         return sb.toString();  
	}  

  @Override
  protected String getLogTag() {
    return LOG_TAG;
  }

  private void signupSuccess() {
    onLoginSuccessListener.onLoginSuccess();
  }
}
