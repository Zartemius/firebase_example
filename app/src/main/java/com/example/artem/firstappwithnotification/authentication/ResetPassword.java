package com.example.artem.firstappwithnotification.authentication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.artem.firstappwithnotification.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText mUserEmail;
    private Button mResetPassword;
    private FirebaseAuth mFireBaseAuth;
    private ProgressBar mProgressBar;
    private Button mGetBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mUserEmail = (EditText) findViewById(R.id.activity_reset_password__email_of_user);
        mResetPassword = (Button) findViewById(R.id.activity_reset_password__button_to_reset_password);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_reset_password__progress_bar);
        mGetBackButton = (Button) findViewById(R.id.activity_reset_password__button_to_get_back);

        mFireBaseAuth = FirebaseAuth.getInstance();

        mGetBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mUserEmail.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Введите электронный адрес",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                mFireBaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Пароль отправлен на ваш электронный адрес",
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),
                                            "Не удалось отправить пароль на ваш электронный адрес",
                                            Toast.LENGTH_SHORT).show();
                                }
                                mProgressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}
