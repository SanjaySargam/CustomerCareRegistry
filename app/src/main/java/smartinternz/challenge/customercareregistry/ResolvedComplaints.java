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

public class ResolvedComplaints extends Fragment {
    RecyclerView recyclerView;
    private Dialog progressDialog;
    TextView no_pending_comp;

    public ResolvedComplaints() {
    }
    public static ResolvedComplaints newInstance(String param1, String param2) {
        ResolvedComplaints fragment = new ResolvedComplaints();
        Bundle args = new Bundle();
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
        View view= inflater.inflate(R.layout.fragment_resolved_complaints, container, false);
        recyclerView = view.findViewById(R.id.rv_resolved_comp);
        no_pending_comp=view.findViewById(R.id.noResolvedComp);
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
        FireBaseData.loadResolvedComplaints(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
                PendingCompAdapter adapter = new PendingCompAdapter(FireBaseData.pendingModelList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                no_pending_comp.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Failed to load Complaints", Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }
}