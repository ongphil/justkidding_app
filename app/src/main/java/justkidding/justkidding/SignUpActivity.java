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


        SignUp.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try{
                email = Email_Add.getText().toString();
                password = PassWord.getText().toString();
                child_name = Child_name.getText().toString();
                child_birthday = Child_birthday.getText().toString();

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
