package smartinternz.challenge.customercareregistry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private FrameLayout main_frame;
    private final NavigationBarView.OnItemSelectedListener  onNavigationItemSelectedListener=
            item -> {

                switch (item.getItemId()){
                    case R.id.navigation_home:
                        setFragment(new AddComplaintFragment());
                        return true;

                    case R.id.navigation_complaints:
                        setFragment(new MyComplaintsFragment());
                        return true;

                    case R.id.navigation_profile:
                        setFragment(new MyProfileFragment());
                        return true;
                    default:

                }

                return false;
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_frame=findViewById(R.id.main_frame);
        BottomNavigationView bottom_NavigationView = findViewById(R.id.bottom_nav_bar);
        bottom_NavigationView.setOnItemSelectedListener(onNavigationItemSelectedListener);
        setFragment(new AddComplaintFragment());
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(),fragment);
        transaction.commit();
    }
}