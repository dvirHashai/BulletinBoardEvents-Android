/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */
package com.cambio.finalprojectandroid.activitiys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cambio.finalprojectandroid.R;
import com.cambio.finalprojectandroid.model.CallBackInterface;
import com.cambio.finalprojectandroid.model.Model;
import com.cambio.finalprojectandroid.utils.SessionManager;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login_btn = (Button) findViewById(R.id.login_btn);
        if (Model.instance == null) {
            Model.getInstance();
        }


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userEmail = ((EditText) findViewById(R.id.login_email)).getText().toString();
                final String userPassword = ((EditText) findViewById(R.id.login_password)).getText().toString();
                if ((userEmail != null && !userEmail.equals("")) || (userPassword != null && !userPassword.equals(""))) {
                    if (userEmail != null && !userEmail.equals("")) {
                        if (userPassword != null && !userPassword.equals("")) {
                            Model.instance.getModelFirebase().loginAccount(LoginActivity.this, userEmail, userPassword, new CallBackInterface.LoginUserCallBack() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("TAG", "signInWithEmail:success -> " + userEmail);
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        intent.putExtra("email", userPassword);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "Please Insert Password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Please Insert Email", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please Insert Email And Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


}