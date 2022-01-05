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
 * Created by azizmahmud on 7.3.2018.
 */

public class CustomTaskAdapter extends RecyclerView.Adapter<CustomTaskAdapter.ViewHolderTask>{

    private LayoutInflater layoutInflater;
    private ArrayList<TaskModel> listTaskModels = new ArrayList<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(CustomTaskAdapter.OnItemClickListener listener){
        mListener = listener;
    }
    public void setTaskList(ArrayList<TaskModel> listTaskModels){
        this.listTaskModels = listTaskModels;
        notifyItemRangeChanged(0, listTaskModels.size());
    }
    public CustomTaskAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolderTask onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_task_list_layout,parent,false);
        ViewHolderTask viewHolderTask = new ViewHolderTask(view);
        return viewHolderTask;
    }

    @Override
    public void onBindViewHolder(ViewHolderTask holder, int position) {

        TaskModel currentTaskModel = listTaskModels.get(position);
        holder.textViewTaskName.setText(currentTaskModel.getTaskName());
        holder.textViewTaskDate.setText((currentTaskModel.getStartDateStr()+"/" + currentTaskModel.getEndDateStr()));
        if(currentTaskModel.getNow() == null)
        holder.textViewTaskDate.setTextColor(Color.RED);
       else if(currentTaskModel.getNow() == true)
            holder.textViewTaskDate.setTextColor(Color.BLUE);
    }

    @Override
    public int getItemCount() {
        return listTaskModels.size();
    }

    public class ViewHolderTask extends RecyclerView.ViewHolder{
        private TextView textViewTaskName;
        private TextView textViewTaskDate;

        public ViewHolderTask(View itemView) {
            super(itemView);
            textViewTaskName = itemView.findViewById(R.id.textViewTaskName);
            textViewTaskDate = itemView.findViewById(R.id.textViewTaskDate);

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
