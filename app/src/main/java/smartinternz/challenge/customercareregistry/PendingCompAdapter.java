package smartinternz.challenge.customercareregistry;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class PendingCompAdapter extends RecyclerView.Adapter<PendingCompAdapter.ViewHolder> {
    private List<PendingModel> pendingModelList;

    public PendingCompAdapter(List<PendingModel> pendingModelList) {
        this.pendingModelList=pendingModelList;
    }

    @NonNull
    @Override
    public PendingCompAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_complaints,parent,false);
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_layout,parent,false);

        return new PendingCompAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingCompAdapter.ViewHolder holder, int position) {
        String title=pendingModelList.get(position).getTitle();
        String description=pendingModelList.get(position).getDesc();
        String username=pendingModelList.get(position).getUsername();
        String emailId=pendingModelList.get(position).getEmailId();
        holder.setData(title,description,username,emailId);

    }

    @Override
    public int getItemCount() {
        return pendingModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title_,desc_,username,emailId;
        private Switch aSwitch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_=itemView.findViewById(R.id.title__);
            desc_=itemView.findViewById(R.id.description__);
            username=itemView.findViewById(R.id.user_name);
            emailId=itemView.findViewById(R.id.email_id);
            aSwitch=itemView.findViewById(R.id.status_);
            aSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (aSwitch.isChecked()){
                        aSwitch.setChecked(true);
                        aSwitch.setText("Resolved");
                        aSwitch.setTextColor(Color.GREEN);
                    }
                    else {
                        aSwitch.setChecked(false);
                        aSwitch.setText("Pending");
                        aSwitch.setTextColor(Color.RED);
                    }
                }
            });


        }
        public void setData(String title,String desc,String name,String email_id){
            title_.setText(title);
            desc_.setText(desc);
            username.setText(name);
            emailId.setText(email_id);




        }

    }
}
