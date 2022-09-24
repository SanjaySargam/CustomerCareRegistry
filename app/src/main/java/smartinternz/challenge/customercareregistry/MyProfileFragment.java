package smartinternz.challenge.customercareregistry;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.Locale;

public class MyProfileFragment extends Fragment {
    LinearLayout logout;
    TextView profileText,profileName;
    TextView comp_raised,comp_resolved;
    private Dialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProfileFragment newInstance(String param1, String param2) {
        MyProfileFragment fragment = new MyProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_profile, container, false);

        //init
        logout=view.findViewById(R.id.logoutB);
        profileText=view.findViewById(R.id.profile_img_text);
        profileName=view.findViewById(R.id.name);
        comp_raised=view.findViewById(R.id.compRaised);
        comp_resolved=view.findViewById(R.id.compResolved);

        profileText.setText(FireBaseData.myProfile.getName().toUpperCase().substring(0,1));
        profileName.setText(FireBaseData.myProfile.getName());
        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading...");
        progressDialog.show();
        FireBaseData.getUserData(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                comp_raised.setText(String.valueOf(FireBaseData.myProfile.getComplaintsRaised()));
                comp_resolved.setText(String.valueOf(FireBaseData.myProfile.getComplaintsResolved()));
                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent logoutIntent=new Intent(getContext(),LoginActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);
                getActivity().finish();

            }
        });




        return view;
    }
}