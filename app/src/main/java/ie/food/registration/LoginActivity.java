package ie.food.registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import es.dmoral.toasty.Toasty;
import ie.food.R;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

    public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

        FirebaseAuth mAuth;
        EditText editTextEmail, editTextPassword;
        ProgressBar progressBar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            mAuth = FirebaseAuth.getInstance();

            editTextEmail = (EditText) findViewById(R.id.editTextEmail);
            editTextPassword = (EditText) findViewById(R.id.editTextPassword);
            progressBar = (ProgressBar) findViewById(R.id.progressbar);

            findViewById(R.id.textViewSignup).setOnClickListener(this);
            findViewById(R.id.buttonLogin).setOnClickListener(this);

            //google sign in

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

        }



        private void userLogin() {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty()) {
                editTextEmail.setError("Email is required");
                editTextEmail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Please enter a valid email");
                editTextEmail.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                editTextPassword.setError("Password is required");
                editTextPassword.requestFocus();
                return;
            }

            if (password.length() < 6) {
                editTextPassword.setError("Minimum lenght of password should be 6");
                editTextPassword.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        finish();
                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        //stops going back to login activity
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                       // Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Toasty.error( LoginActivity.this,"Account Doesnt Excist, Please Sign Up",
                                Toast.LENGTH_LONG, true).show();

                    }
                }
            });
        }

        @Override
        protected void onStart() {
            super.onStart();

            if (mAuth.getCurrentUser() != null) {
                finish();
                startActivity(new Intent(this, ProfileActivity.class));
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.textViewSignup:
                    finish();
                    startActivity(new Intent(this, SignUpActivity.class));
                    break;

                case R.id.buttonLogin:
                    userLogin();
                    break;
            }
        }
    }