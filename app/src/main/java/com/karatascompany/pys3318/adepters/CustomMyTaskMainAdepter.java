package com.karatascompany.pys3318.adepters;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karatascompany.pys3318.R;
import com.karatascompany.pys3318.models.TaskModel;

import java.util.ArrayList;

/**
 * Created by azizmahmud on 10.3.2018.
 */

public class CustomMyTaskMainAdepter extends RecyclerView.Adapter<CustomMyTaskMainAdepter.ViewHolderMyTask>{

    private LayoutInflater layoutInflater;
    private ArrayList<TaskModel> listMyTaskModels = new ArrayList<>();
    private CustomMyTaskMainAdepter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(CustomMyTaskMainAdepter.OnItemClickListener listener){
        mListener = listener;
    }
    public void setMyTaskList(ArrayList<TaskModel> listMyTaskModels){
        this.listMyTaskModels = listMyTaskModels;
        notifyItemRangeChanged(0, listMyTaskModels.size());

    }
    public CustomMyTaskMainAdepter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolderMyTask onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_mytask_list_main_layout,parent,false);
        CustomMyTaskMainAdepter.ViewHolderMyTask viewHolderMyTask = new CustomMyTaskMainAdepter.ViewHolderMyTask(view);
        return viewHolderMyTask;
    }

    @Override
    public void onBindViewHolder(ViewHolderMyTask holder, int position) {

        TaskModel currentTaskModel = listMyTaskModels.get(position);
        holder.textViewTaskProjectName.setText(currentTaskModel.getProjectName());
        holder.textViewMyTaskName.setText(currentTaskModel.getTaskName());
        holder.textViewMyTaskDate.setText((currentTaskModel.getStartDateStr()+"/" + currentTaskModel.getEndDateStr()));
        if(currentTaskModel.getNow() == null)
            holder.textViewMyTaskDate.setTextColor(Color.RED);
        else if(currentTaskModel.getNow() == true)
            holder.textViewMyTaskDate.setTextColor(Color.BLUE);
    }

    @Override
    public int getItemCount() {
        return listMyTaskModels.size();
    }

    public class ViewHolderMyTask extends RecyclerView.ViewHolder {
        private TextView textViewTaskProjectName;
        private TextView textViewMyTaskName;
        private TextView textViewMyTaskDate;

        public ViewHolderMyTask (View itemView){
            super(itemView);
            textViewTaskProjectName = itemView.findViewById(R.id.textViewTaskProjectName);
            textViewMyTaskName = itemView.findViewById(R.id.textViewMyTaskName);
            textViewMyTaskDate = itemView.findViewById(R.id.textViewMyTaskDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
