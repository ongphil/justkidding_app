package justkidding.justkidding;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class LoginActivity extends Activity {

    Button buttonConnection;
    Button Inscription;
    String email ;
    String child_name;
    int child_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonConnection = (Button)findViewById(R.id.buttonConnection);
        Inscription = (Button)findViewById(R.id.buttonSignup);
        Inscription.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent signupactivity = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signupactivity);
            }
        });

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