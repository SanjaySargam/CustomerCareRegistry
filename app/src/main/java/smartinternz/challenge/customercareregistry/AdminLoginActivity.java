package smartinternz.challenge.customercareregistry;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {
Button login;
EditText email,password;
    private Dialog progressDialog;
    private TextView dialogText;
    private String emailStr,passStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        login=findViewById(R.id.loginBtnA);
        email=findViewById(R.id.emailA);
        password=findViewById(R.id.passwordA);
        progressDialog = new Dialog(AdminLoginActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Signing in...");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    adminLogin();
                }
            }
        });

    }
    private boolean validateData() {
        emailStr=email.getText().toString();
        passStr=password.getText().toString();

        if (emailStr.isEmpty()) {
            email.setError("Enter E-mail ID");
            return false;
        }
        if (passStr.isEmpty()) {
            password.setError("Enter Password");
            return false;
        }


        return true;
    }
    private void adminLogin(){
        progressDialog.show();
        FireBaseData.adminLogin_(emailStr, passStr, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(AdminLoginActivity.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent = new Intent(AdminLoginActivity.this, MainActivity2.class);
                startActivity(intent);
            }

            @Override
            public void onFailure() {
                Toast.makeText(AdminLoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

    }
}