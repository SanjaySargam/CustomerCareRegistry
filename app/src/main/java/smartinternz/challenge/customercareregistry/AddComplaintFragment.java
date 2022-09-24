package smartinternz.challenge.customercareregistry;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddComplaintFragment extends Fragment {
    TextView name;
    EditText title,desc;
    Button addCompBtn;
    String titleStr,descStr;
    private Dialog progressDialog;
    public AddComplaintFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_complaint, container, false);
        name=view.findViewById(R.id.name);
        name.setText("Welcome "+FireBaseData.myProfile.getName());
        title=view.findViewById(R.id.titleComplaint);
        desc=view.findViewById(R.id.compDesc);
        addCompBtn=view.findViewById(R.id.addComplaint);

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
                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {

            }
        });

        addCompBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()){
                    addComp();
                    clear();
                }
            }
        });


        return view;
    }
    public boolean validation(){
        titleStr=title.getText().toString().trim();
        descStr=desc.getText().toString().trim();

        if (titleStr.isEmpty()){
            title.setError("Enter title");
            return false;
        }
        if (descStr.isEmpty()){
            desc.setError("Enter Description");
            return false;
        }


        return true;
    }
    public void addComp(){
        FireBaseData.allComplaints(titleStr, descStr, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                FireBaseData.addComplaints(titleStr, descStr, new MyCompleteListener() {
                    @Override
                    public void onSuccess() {

                        FireBaseData.getUserData(new MyCompleteListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure() {
                                Toast.makeText(getContext(),"Not added Successfully",Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(),"Not added Successfully",Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext(),"Not added Successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void clear(){
        title.getText().clear();
        desc.getText().clear();
    }


}