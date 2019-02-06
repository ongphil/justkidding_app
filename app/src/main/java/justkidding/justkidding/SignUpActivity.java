package justkidding.justkidding;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText Email_Add;
    private EditText PassWord;
    private EditText Child_name;
    private EditText Child_birthday;
    private Button SignUp;

    String email;
    String password;
    String child_name;
    String child_birthday;

    Map<String, ArrayList<Map<String, ArrayList<Map<String, String>>>>> activities_idea;


    private FirebaseAuth mAuth;
    private FirebaseFirestore Firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth= FirebaseAuth.getInstance();
        Firestore = FirebaseFirestore.getInstance();

        Email_Add = findViewById(R.id.editTextEmail);
        PassWord = findViewById(R.id.editTextPassword);
        Child_name = findViewById(R.id.editTextChildName);
        Child_birthday = findViewById(R.id.editTextChildBirthday);
        SignUp = findViewById(R.id.Button_SignUp);
        activities_idea = new HashMap<String, ArrayList<Map<String, ArrayList<Map<String, String>>>>>();


        SignUp.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try{
                email = Email_Add.getText().toString();
                password = PassWord.getText().toString();
                child_name = Child_name.getText().toString();
                child_birthday = Child_birthday.getText().toString();

                ArrayList<Map<String, ArrayList<Map<String, String>>>> histoires_array= new ArrayList<Map<String, ArrayList<Map<String, String>>>>();

                ArrayList<Map<String, String>> animaux_array = new ArrayList<Map<String, String>>();
                Map<String, String> idea_animaux1 = new HashMap<String, String>();
                idea_animaux1.put("Message", "semble avoir apprécié le thème des Animaux. Pourquoi ne pas envisager une ballade au zoo de Vincennes pour découvrir les animaux du monde ?");
                idea_animaux1.put("Sent", "false");
                animaux_array.add(idea_animaux1);
                Map<String, String> idea_animaux2 = new HashMap<String, String>();
                idea_animaux2.put("Message", "semble avoir apprécié le thème des Animaux. Pourquoi ne pas envisager d'aller voir les animaux de très près au safari du Zoo de Thoiry ?");
                idea_animaux2.put("Sent", "false");
                animaux_array.add(idea_animaux2);
                Map<String, String> idea_animaux3 = new HashMap<String, String>();
                idea_animaux3.put("Message", "semble avoir apprécié le thème des Animaux. Pourquoi ne pas envisager de se plonger dans le monde aquatique à l'Aquarium de Paris ?");
                idea_animaux3.put("Sent", "false");
                animaux_array.add(idea_animaux3);
                Map<String, String> idea_animaux4 = new HashMap<String, String>();
                idea_animaux4.put("Message", "semble avoir apprécié le thème des Animaux. Pourquoi ne pas envisager une sortie au Jardin des plantes ?");
                idea_animaux4.put("Sent", "false");
                animaux_array.add(idea_animaux4);

                ArrayList<Map<String, String>> chevaliers_array = new ArrayList<Map<String, String>>();
                Map<String, String> idea_chevaliers1 = new HashMap<String, String>();
                idea_chevaliers1.put("Message", "semble avoir apprécié le thème des Chevaliers. Pourquoi ne pas envisager d'aller explorer le château fort de Vincennes ?");
                idea_chevaliers1.put("Sent", "false");
                chevaliers_array.add(idea_chevaliers1);
                Map<String, String> idea_chevaliers2 = new HashMap<String, String>();
                idea_chevaliers2.put("Message", "semble avoir apprécié le thème des Chevaliers. Pourquoi ne pas envisager de passer une journée au Puy du Fou afin de profite de spectacles et activités familiales à thématique historique ?");
                idea_chevaliers2.put("Sent", "false");
                chevaliers_array.add(idea_chevaliers2);
                Map<String, String> idea_chevaliers3 = new HashMap<String, String>();
                idea_chevaliers3.put("Message", "semble avoir apprécié le thème des Chevaliers. Pourquoi ne pas envisager de visiter la cité médiévale Provins et ses monuments historiques ?");
                idea_chevaliers3.put("Sent", "false");
                chevaliers_array.add(idea_chevaliers3);

                Map<String, ArrayList<Map<String, String>>> theme_map = new HashMap <String, ArrayList<Map<String, String>>>();
                theme_map.put("Animaux", animaux_array);
                theme_map.put("Chevaliers", chevaliers_array);

                histoires_array.add(theme_map);

                activities_idea.put("Histoires", histoires_array);

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(child_name) && !TextUtils.isEmpty(child_birthday)) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String user_id = mAuth.getCurrentUser().getUid();
                                Map<String, Object> user = new HashMap<>();
                                user.put("Email", email);
                                user.put("Child_name", child_name);
                                user.put("Child_age", child_birthday);
                                user.put("Jouet_ID", "b8:27:eb:a4:76:ca");
                                user.put("Activities_idea", activities_idea);

                                Firestore.collection("Users").document(user_id).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent newintent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(newintent);
                                        finish();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("TAG", "Failure to create user");
                                    }
                                });


                            } else {
                                Toast.makeText(SignUpActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
                catch(NumberFormatException e)
                {
                    Toast.makeText(SignUpActivity.this, "L'âge de l'enfant doit être un nombre", Toast.LENGTH_SHORT).show();
                }
        }
        });
    }
}
