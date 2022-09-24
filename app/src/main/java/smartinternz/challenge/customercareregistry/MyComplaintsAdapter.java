package smartinternz.challenge.customercareregistry;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyComplaintsAdapter extends RecyclerView.Adapter<MyComplaintsAdapter.ViewHolder> {
private List<MyComplaintsModel>complaintsModelList;

public MyComplaintsAdapter(List<MyComplaintsModel> complaintsModelList){
        this.complaintsModelList=complaintsModelList;

}

    @NonNull
    @Override
    public MyComplaintsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_layout,parent,false);

        return new MyComplaintsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyComplaintsAdapter.ViewHolder holder, int position) {
            String title=complaintsModelList.get(position).getTitle();
            String description=complaintsModelList.get(position).getDesc();

            boolean status=complaintsModelList.get(position).isResolved();
            holder.setData(title,description,status);

    }

    @Override
    public int getItemCount() {
        return complaintsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView title_,desc_,status_;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_=itemView.findViewById(R.id.title_);
            desc_=itemView.findViewById(R.id.description_);
            status_=itemView.findViewById(R.id.status);
        }
        public void setData(String title,String desc,boolean status){
            title_.setText(title);
            desc_.setText(desc);
            if (status){
                status_.setText("resolved");
                status_.setTextColor(Color.GREEN);
            }
            else {

                status_.setText("pending..");
                status_.setTextColor(Color.RED);
            }


        }


    }

}
