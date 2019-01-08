package justkidding.justkidding;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
    private EditText Child_age;
    private EditText Jouet_ID;
    private Button Histoire;
    private Button Comptine;
    private Button SignUp;

    String email;
    String password;
    String child_name;
    String jouet_id;
    int child_age;

    private boolean pressedH = false;
    private boolean pressedC = false;

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
        Child_age = findViewById(R.id.editTextChildAge);
        Jouet_ID = findViewById(R.id.editTextJouetID);
        Histoire = findViewById(R.id.buttonHistoire);
        Comptine = findViewById(R.id.buttonComptine);
        SignUp = findViewById(R.id.Button_SignUp);

        Histoire.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Drawable roundDrawable = getResources().getDrawable(R.drawable.circle);
                if(!pressedH)
                {
                    roundDrawable.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
                    pressedH= true;
                }
                else
                {
                    roundDrawable.setColorFilter(getResources().getColor(R.color.colorGrey), PorterDuff.Mode.SRC_ATOP);
                    pressedH = false;
                }
                Histoire.setBackground(roundDrawable);
            }
        });

        Comptine.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Drawable roundDrawable = getResources().getDrawable(R.drawable.circle);
                if(!pressedC) {
                    roundDrawable.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
                    pressedC = true;
                }
                else
                {
        roundDrawable.setColorFilter(getResources().getColor(R.color.colorGrey), PorterDuff.Mode.SRC_ATOP);
        pressedC = false;
    }
                Comptine.setBackground(roundDrawable);
}
        });

        SignUp.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                email = Email_Add.getText().toString();
                password = PassWord.getText().toString();
                child_name = Child_name.getText().toString();
                child_age = Integer. parseInt(Child_age.getText().toString());
                jouet_id = Jouet_ID.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(child_name) && !TextUtils.isEmpty(Child_age.getText().toString()))
                {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String user_id = mAuth.getCurrentUser().getUid();
                                Log.d("Test ", "Test user ID " + user_id);
                                Map<String, Object> user = new HashMap<>();
                                user.put("Email", email);
                                user.put("Child_name", child_name);
                                user.put("Child_age", child_age);
                                user.put("Jouet_ID", jouet_id);
                                Map<String, Boolean> activities = new HashMap<>();
                                activities.put("Comptine", pressedC);
                                activities.put ("Histoire", pressedH);
                                user.put("Activity", activities);
                                Firestore.collection("Users").document(user_id).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "User created");
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
        });
    }
}
