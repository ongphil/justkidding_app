package justkidding.justkidding;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class LoginActivity extends Activity {

    private EditText EmailField;
    private EditText PasswordField;

    Button buttonConnection;
    Button Inscription;
    String email ;
    String password;

    private FirebaseAuth Auth;
    private FirebaseFirestore Firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailField = (EditText) findViewById(R.id.editTextEmail);
        PasswordField = (EditText) findViewById(R.id.editTextPassword);

        buttonConnection = (Button)findViewById(R.id.buttonConnection);
        Inscription = (Button)findViewById(R.id.buttonSignup);

        Auth= FirebaseAuth.getInstance();

        Inscription.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent signupactivity = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signupactivity);
            }
        });

        ///LOGIN FOR SPECIFIC USER
//        buttonConnection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                email = EmailField.getText().toString();
//                password = PasswordField.getText().toString();
//
//                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
//                {
//                    Auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Intent sendToMain = new Intent (LoginActivity.this, MainActivity.class);
//                                startActivity(sendToMain);
//                                finish();
//
//                            } else {
//                                Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        });
//                }
//            }
//        });

    }

    public void logIn(View view) {
        class LogIn extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    /// Connexion à la BDD pour trouver l'utilisateur
                    String result = "success";

                    return result;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result == "success") {
                    /// Configuration de la session avec l'utilisateur trouver
                    /// Changement de page
                    Intent intentConnection = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intentConnection);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,"Problème d'authentification",Toast.LENGTH_LONG).show();
                }

            }
        }
        LogIn logIn = new LogIn();
        logIn.execute();
    }
}

