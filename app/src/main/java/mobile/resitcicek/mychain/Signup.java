package mobile.resitcicek.mychain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private Button signupButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        databaseHelper = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        emailEditText = findViewById(R.id.email);
        signupButton = findViewById(R.id.signupbutton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEditText.getText().length() > 0 && passwordEditText.getText().length() > 0 && emailEditText.getText().length() > 0) {
                    String username = usernameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    Boolean checkusername = databaseHelper.CheckUsername(username);
                    if(checkusername == true) {
                        Boolean checkregister = databaseHelper.Insert(username, password, email);
                        if (checkregister == true) {
                            Toast.makeText(getApplicationContext(), "Successful. You can login.", Toast.LENGTH_SHORT).show();
                            Intent a = new Intent(Signup.this, Login.class);
                            startActivity(a);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Username already taken.", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    String toastMessage = "Please fill all blanks.";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void goLogin(View v) {
        Intent i = new Intent(Signup.this, MainActivity.class);
        startActivity(i);
    }
}