package smartinternz.challenge.customercareregistry;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyComplaintsFragment extends Fragment {
    RecyclerView recyclerView;
    private Dialog progressDialog;
    TextView no_comp;

    public MyComplaintsFragment() {
    }

    public static MyComplaintsFragment newInstance(String param1, String param2) {
        MyComplaintsFragment fragment = new MyComplaintsFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_complaints, container, false);
        recyclerView = view.findViewById(R.id.rv_comp);
        no_comp=view.findViewById(R.id.noComp);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading");
        progressDialog.show();
        if (FireBaseData.myProfile.getTotalComp() != 0) {
            FireBaseData.loadComplaints(new MyCompleteListener() {
                @Override
                public void onSuccess() {
                    progressDialog.dismiss();
                    MyComplaintsAdapter adapter = new MyComplaintsAdapter(FireBaseData.complaintsModelList);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure() {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Failed to load Complaints", Toast.LENGTH_SHORT).show();
                }
            });



        }
        else {
            progressDialog.dismiss();
            no_comp.setVisibility(View.VISIBLE);
        }
        return view;
    }
}