package nateproductions.androidlab05;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText ID;
    private TextInputLayout Password;
    private TextView Info;
    private Button Login;
    private int counter;
    private int lockOut;
    private HashMap<String, String> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        counter = 3;
        lockOut = 30;

        ID = (EditText)findViewById(R.id.etID);
        Password = (TextInputLayout)findViewById(R.id.tilPassword);
        Info = (TextView)findViewById(R.id.tvInfo);
        Info.setText("Number of attempts remaining: " + counter);
        Login = (Button)findViewById(R.id.btnLogin);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(ID.getText().toString(), Password.getEditText().getText().toString());
            }
        });


        accounts = new HashMap<String, String>();
        accounts.put("Test", "1234");
        accounts.put("011010792", "CMPE#137");
    }

    private void validate(String userName, String userPassword){
        if(accounts.containsKey(userName) && accounts.get(userName).equals(userPassword)){
            //Go to home activity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }else if (!(accounts.containsKey(userName))) {
            invalidToast((View)findViewById(R.id.btnLogin), "Name");
            decrementCounter();
        }else if (!accounts.get(userName).equals(userPassword)){
            invalidToast((View)findViewById(R.id.btnLogin), "Password");
            decrementCounter();
        }
    }

    private void invalidToast(View view, String userInput){
        Context context = getApplicationContext();
        CharSequence text = "";

        switch (userInput){
            case "Name": text = "Invalid Name";
            break;
            case "Password": text = "Invalid Password";
            break;
            default: text = "Too many attempts! Please try again after 30 Seconds";
        }

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void decrementCounter() {
        //Decrement counter
        counter--;

        Info.setText("Number of attempts remaining: " + String.valueOf(counter));

        if(counter == 0) {
            invalidToast((View)findViewById(R.id.btnLogin), "Too many attempts");

            //Disable login button
            Login.setEnabled(false);
            Handler handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable(){
                public void run() {
                    // Enable it
                    Login.setEnabled(true);
                }}, 30000);
        }
    }
}
