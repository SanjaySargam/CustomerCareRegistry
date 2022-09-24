package smartinternz.challenge.customercareregistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.util.Logger;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button signUp;
    Dialog progressDialog;
    TextView dialogText;
    EditText username,email_id,phone_no,pass,conf_pass;
    TextView adm_login,login;
    String nameStr,passStr,confirmPassStr,emailStr,mobStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();
        FireBaseData.g_firestore= FirebaseFirestore.getInstance();
        //init
        signUp=findViewById(R.id.signUpBtn);
        username=findViewById(R.id.username1);
        email_id=findViewById(R.id.emailID1);
        phone_no=findViewById(R.id.mobileNo);
        pass=findViewById(R.id.password1);
        conf_pass=findViewById(R.id.confirm_pass1);
        adm_login=findViewById(R.id.adminLogin);
        login=findViewById(R.id.login);

        progressDialog=new Dialog(SignUpActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText=progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Registering User...");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    signUpNewUser();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        adm_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminLoginIntent=new Intent(SignUpActivity.this,AdminLoginActivity.class);
                startActivity(adminLoginIntent);
            }
        });






    }
    private boolean validate(){
        nameStr=username.getText().toString().trim();
        passStr=pass.getText().toString().trim();
        confirmPassStr=conf_pass.getText().toString().trim();
        emailStr=email_id.getText().toString().trim();
        mobStr=phone_no.getText().toString().trim();

        if (nameStr.isEmpty()){
            username.setError("Enter Your Name");
            return false;
        }
        if (emailStr.isEmpty()){
            email_id.setError("Enter EmailID");
            return false;
        }

        if (passStr.isEmpty()){
            pass.setError("Enter Password");
            return false;
        }
        if (confirmPassStr.isEmpty()){
            conf_pass.setError("Enter Password");
            return false;
        }
        if (passStr.compareTo(confirmPassStr)!=0){
            Toast.makeText(SignUpActivity.this,"Password and Confirm Password must be same !",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mobStr.isEmpty()){
            phone_no.setError("Enter mobile number");
            return false;
        }
        else {
            if (!((phone_no.length() == 10) && (TextUtils.isDigitsOnly(mobStr)))) {
                phone_no.setError("Enter Valid Phone Number");
                return false;

            }
        }



        return true;
    }
    public void signUpNewUser(){
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(emailStr,passStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Sign Up Successfull", Toast.LENGTH_SHORT).show();
                            FireBaseData.createUserData(emailStr, nameStr, mobStr, new MyCompleteListener() {
                                @Override
                                public void onSuccess() {
                                    FireBaseData.getUserData(new MyCompleteListener() {
                                        @Override
                                        public void onSuccess() {
                                            progressDialog.dismiss();
                                            Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            SignUpActivity.this.finish();
                                        }

                                        @Override
                                        public void onFailure() {
                                            Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    });

                                }

                                @Override
                                public void onFailure() {
                                    Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    };
                    });
                }



    }
