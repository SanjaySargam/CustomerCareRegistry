package smartinternz.challenge.customercareregistry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FireBaseData.g_firestore= FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        app_name=findViewById(R.id.app_name);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAuth.getCurrentUser()!=null){

                    FireBaseData.getUserData(new MyCompleteListener() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }

                        @Override
                        public void onFailure() {
                            Intent intent= new Intent(SplashActivity.this,SignUpActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }
                    });


                }
                else {
                    Intent intent= new Intent(SplashActivity.this,SignUpActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }
        },1000);
    }
}