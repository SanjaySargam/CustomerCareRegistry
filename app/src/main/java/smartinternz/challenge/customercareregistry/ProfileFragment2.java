package smartinternz.challenge.customercareregistry;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment2 extends Fragment {
    LinearLayout logout;
    TextView profileText,profileName;
    TextView pending,resolved;
    private Dialog progressDialog;

    public ProfileFragment2() {
    }

    public static ProfileFragment2 newInstance(String param1, String param2) {
        ProfileFragment2 fragment = new ProfileFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile2, container, false);
        logout=view.findViewById(R.id.logoutBB);
        profileText=view.findViewById(R.id.profile_img_text_);
        profileName=view.findViewById(R.id.name_);
        pending=view.findViewById(R.id.Pending);
        resolved=view.findViewById(R.id.Resolved);
        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading...");


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logoutIntent=new Intent(getContext(),LoginActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);
                getActivity().finish();

            }
        });

        return view;
    }
}