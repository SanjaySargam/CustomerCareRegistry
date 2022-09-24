package smartinternz.challenge.customercareregistry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity {
    private FrameLayout main_frame;
    private final NavigationBarView.OnItemSelectedListener  onNavigationItemSelectedListener=
            item -> {

                switch (item.getItemId()){
                    case R.id.navigation_pendingComp:
                        setFragment(new PendingComplaints());
                        return true;

                    case R.id.navigation_resolvedComp:
                        setFragment(new ResolvedComplaints());
                        return true;

                    case R.id.navigation_profile2:
                        setFragment(new ProfileFragment2());
                        return true;
                    default:

                }

                return false;
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        main_frame=findViewById(R.id.main_frame_2);
        BottomNavigationView bottom_NavigationView = findViewById(R.id.bottom_nav_bar2);
        bottom_NavigationView.setOnItemSelectedListener(onNavigationItemSelectedListener);
        setFragment(new PendingComplaints());
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(),fragment);
        transaction.commit();
    }
}