package com.example.artem.firstappwithnotification.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.artem.firstappwithnotification.fbdatabase.Fbdatabase;
import com.example.artem.firstappwithnotification.installationid.AndroidIDGenerator;
import com.example.artem.firstappwithnotification.mainactivity.MainActivity;
import com.example.artem.firstappwithnotification.R;
import com.example.artem.firstappwithnotification.personaldata.PersonalData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoggingIn extends AppCompatActivity {

    private static final String TAG = "EmailPasswordAuth";
    private EditText mEmailForLogIn;
    private EditText mPasswordForLogIn;
    private Button mLogIn;
    private Button mRegister;
    private Button mResetPassword;
    private ProgressBar mProgressBar;
    private FirebaseAuth mFireBaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging_in);

        //String email = "polin/@mail.ru";
        //Fbdatabase fbdatabase = new Fbdatabase();
        //Log.i("NEW_ID","id: " + fbdatabase.getEmailAsId(email));


        //new Fbdatabase().addKeyToDataBase("555555", AndroidIDGenerator.getUniqueID(this));
        //String email = "polin@mail.ru";
        //PersonalData personalData = new PersonalData(email.replace(".",""));
        //new Fbdatabase(personalData).addPersonIdInDB();


        mEmailForLogIn = (EditText) findViewById(R.id.activity_logging_in__email);
        mPasswordForLogIn = (EditText) findViewById(R.id.activity_logging_in__password);
        mLogIn = (Button) findViewById(R.id.activity_logging_in__button_to_log_in);
        mRegister = (Button) findViewById(R.id.activity_logging_in__button_to_register);
        mResetPassword = (Button) findViewById(R.id.activity_logging_in__button_to_reset_password);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_logging_in__progress_bar);

        mFireBaseAuth = FirebaseAuth.getInstance();

        mRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoggingIn.this,Registration.class));
            }
        });

        mResetPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoggingIn.this,ResetPassword.class));
            }
        });

        mLogIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = mEmailForLogIn.getText().toString().trim();
                final String password = mPasswordForLogIn.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Введите электронный адрес",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Введите пароль",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                mFireBaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LoggingIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgressBar.setVisibility(View.GONE);

                                if(!task.isSuccessful()){
                                    if(password.length() < 6){
                                        Toast.makeText(getApplicationContext(),
                                                "Неверный пароль",
                                                Toast.LENGTH_SHORT).show();
                                        return;
                                    }else{

                                        try{
                                            throw task.getException();
                                        }catch(FirebaseAuthInvalidUserException e){
                                            Toast.makeText(getApplicationContext(),
                                                    "Пользователь с таким электронным адресом не найден",
                                                    Toast.LENGTH_SHORT).show();

                                        }catch(FirebaseAuthInvalidCredentialsException e){
                                            Toast.makeText(getApplicationContext(),
                                                    "Неверно указан пароль",
                                                    Toast.LENGTH_SHORT).show();
                                        }catch(Exception e){
                                            Toast.makeText(getApplicationContext(),
                                                    "Ошибка авторизации",Toast.LENGTH_SHORT).show();
                                            Log.i("MISTAKE","exception:" + task.getException());
                                        }
                                    }
                                } else{
                                    Fbdatabase fbdatabase = new Fbdatabase();
                                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                    fbdatabase.addKeyToDataBase(AndroidIDGenerator.getUniqueID(getApplicationContext()),refreshedToken);

                                    Intent intent = new Intent(LoggingIn.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                }
        });
    }
}
