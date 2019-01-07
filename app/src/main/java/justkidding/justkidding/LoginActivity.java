package justkidding.justkidding;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import static android.content.ContentValues.TAG;


public class LoginActivity extends Activity {

    Button buttonConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonConnection = (Button)findViewById(R.id.buttonConnection);
    }

    public void logIn(View view) {
        class LogIn extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    /// Connexion à la BDD pour trouver l'utilisateur
                    String result = "success";
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Users")
                            .document("ug5CDvhnTf29prJutrVx")
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String email = documentSnapshot.getString("Email");
                                    String password = documentSnapshot.getString("Password");
                                    String child_name = documentSnapshot.getString("Child_name");
                                    int child_age = documentSnapshot.getLong("Child_age").intValue();
                                    Map<String, Object> activity = (Map<String, Object>) documentSnapshot.get("Activity");
                                }
                            });
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

