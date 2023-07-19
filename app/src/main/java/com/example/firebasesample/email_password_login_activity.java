
package com.example.firebasesample;

import static com.example.firebasesample.splash_screen_activity.editor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class email_password_login_activity extends AppCompatActivity {
EditText emailet,passwordet;
TextView textView;
Button button;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password_login);
        emailet=findViewById(R.id.email_et);
        passwordet=findViewById(R.id.password_et);
        button=findViewById(R.id.login_button);
        mAuth = FirebaseAuth.getInstance();
        textView=findViewById(R.id.simple_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(email_password_login_activity.this, login_activity.class);
                startActivity(intent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emaillogin();
            }
        });
    }

    private void emaillogin() {
        String email,password;
        email=emailet.getText().toString();
        password=passwordet.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            editor.putBoolean("login",true);
                            editor.putString("email",user.getEmail());
                            editor.putString("name",user.getDisplayName());
                            editor.commit();
                            Intent intent=new Intent(email_password_login_activity.this, product_view_activity.class);
                            intent.putExtra("i",1);
                            startActivity(intent);
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(email_password_login_activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }
}