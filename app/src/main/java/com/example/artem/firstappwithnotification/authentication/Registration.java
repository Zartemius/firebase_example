package com.example.artem.firstappwithnotification.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.artem.firstappwithnotification.fbdatabase.Fbdatabase;
import com.example.artem.firstappwithnotification.mainactivity.MainActivity;
import com.example.artem.firstappwithnotification.R;
import com.example.artem.firstappwithnotification.personaldata.PersonalData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Registration extends AppCompatActivity {

    private EditText mEmailForReg;
    private EditText mPasswordForReg;
    private FirebaseAuth mFireBaseAuth;
    private ProgressBar mProgressBar;
    private Button mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mFireBaseAuth = FirebaseAuth.getInstance();
        mEmailForReg = (EditText) findViewById(R.id.activity_registration__email);
        mPasswordForReg = (EditText) findViewById(R.id.activity_registration__password);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_registration__progress_bar);
        mSignUp = (Button) findViewById(R.id.activity_registration__button);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailForReg.getText().toString().trim();
                String password = mPasswordForReg.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),
                            "Введите электронный адрес",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),
                            "Введите пароль",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 6){
                    Toast.makeText(getApplicationContext(),
                            "Пароль слишком короткий. Введите минимум 6 знаков!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                mFireBaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressBar.setVisibility(View.GONE);
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),
                                    "Регистрация не прошла" + task.getException(),
                                    Toast.LENGTH_SHORT).show();

                            mProgressBar.setVisibility(View.GONE);
                        }else{
                           PersonalData personalData = new PersonalData(email,getApplicationContext());
                           Fbdatabase fbdatabase = new Fbdatabase(personalData);
                           fbdatabase.addPersonalDataToDataBase();

                            //Log.i("UID","uid is " + uid);

                           Toast.makeText(getApplicationContext(), "Успешно!", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(Registration.this, MainActivity.class));
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
    }
}
