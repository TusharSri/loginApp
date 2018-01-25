package com.example.apple.amazon.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.amazon.R;
import com.example.apple.amazon.Utils.SymmetricProgressBar;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email_edittext)
    EditText emailEditText;
    @BindView(R.id.password_edittext)
    EditText passwordEditText;
    @BindView(R.id.forgot_pass_textview)
    TextView forgotPasswordTextView;
    @BindView(R.id.dont_have_accout_textview)
    TextView dontHaveAccount;
    @BindView(R.id.sign_in_button)
    Button signInButton;
    @BindView(R.id.sign_up_button)
    Button SignUpButton;
    @BindView(R.id.relative_container)
    RelativeLayout relativeContainer;
    @BindView(R.id.icon_imageview)
    ImageView iconImageview;

    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    Animation zoomIn;
    Animation zoomOut;
    int RC_SIGN_IN = 1221;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        auth = FirebaseAuth.getInstance();

        final SymmetricProgressBar progressBar = new SymmetricProgressBar(this);

        ((ViewGroup) this.findViewById(android.R.id.content)).addView(progressBar, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 8));

        progressBar.startAnimation();

        Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.animate_login);
        relativeContainer.setAnimation(bottomUp);

        zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        zoomOut = AnimationUtils.loadAnimation(this, R.anim.zoomout);

/*        iconImageview.startAnimation(zoomIn);
        zoomIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                iconImageview.startAnimation(zoomOut);

            }
        });

        zoomOut.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                iconImageview.startAnimation(zoomIn);

            }
        });*/

    }

    @OnClick(R.id.sign_up_button)
    public void onSignUpClicked() {
        userSignUp();
    }

    @OnClick(R.id.sign_in_button)
    public void onSignInClicked() {
        userSignIn();
    }

    @OnClick(R.id.forgot_pass_textview)
    public void forgotPasswordClicked() {
        forgotPassword();
    }

    private void forgotPassword() {

        String email = emailEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        /*if (task.isSuccessful()) {
                            Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
*/
                        progressDialog.hide();
                    }
                });
    }

    private void userSignUp() {
        final String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();
        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(LoginActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            String name = auth.getCurrentUser().getDisplayName();
                            Uri photoUrl = auth.getCurrentUser().getPhotoUrl();

                            SharedPreferences.Editor editor = getSharedPreferences("User_Data", MODE_PRIVATE).edit();
                            editor.putString("name", name);
                            editor.putString("url", photoUrl.toString());
                            editor.putString("email", email);
                            editor.apply();

                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            finish();
                        }
                    }
                });

    }

    private void userSignIn() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressDialog.hide();
                        if (!task.isSuccessful()) {
                            // there was an error
                            Toast.makeText(LoginActivity.this, "Either Username or password is Incorrect", Toast.LENGTH_LONG).show();

                        } else {
                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != auth.getCurrentUser()) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }
    }

    @OnClick(R.id.dont_have_accout_textview)
    public void googleLogin() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
// Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivity.this, null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(…);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
// Google Sign In was successful, save Token and a state then authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();

                SharedPreferences.Editor editor = getSharedPreferences("User_Data", MODE_PRIVATE).edit();
                editor.putString("name", account.getDisplayName());
                editor.putString("url", account.getPhotoUrl().toString());
                editor.putString("email", account.getEmail());
                editor.apply();
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish();

            } else {
                Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }
    }
}