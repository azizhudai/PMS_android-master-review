package com.karatascompany.pys3318.adepters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.karatascompany.pys3318.R;
import com.karatascompany.pys3318.models.UserAppointedTaskModel;

import java.util.ArrayList;

/**
 * Created by azizmahmud on 5.6.2018.
 */

public class CustomUserAppointedTaskAdapter extends RecyclerView.Adapter<CustomUserAppointedTaskAdapter.ViewHolderAppointedTask> implements Filterable{

    private LayoutInflater layoutInflater;

    private ArrayList<UserAppointedTaskModel> listAppointedModels = new ArrayList<>();
    private ArrayList<UserAppointedTaskModel> filterListAppointedModels = new ArrayList<>();

    private CustomUserAppointedTaskAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(UserAppointedTaskModel userAppointedTaskModel,int position);
    }
    public void setOnItemClickListener(CustomUserAppointedTaskAdapter.OnItemClickListener listener){
        mListener = listener;
    }
    public void setAppointedTaskList(ArrayList<UserAppointedTaskModel> listAppointedModels){
        this.listAppointedModels = listAppointedModels;
        this.filterListAppointedModels = listAppointedModels;
        notifyItemRangeChanged(0, listAppointedModels.size());

    }
    public CustomUserAppointedTaskAdapter(Context context){
       // context.getResources();
        layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolderAppointedTask extends RecyclerView.ViewHolder {

        private CardView cardViewUserAppointedTask;

        private TextView textViewTaskName;
        private TextView textViewProjectName;
        private TextView textViewUserMail;
        private RatingBar ratingBarUserPoint;
        private ProgressBar progressBarResudualDays;
        private TextView textViewResidualTotalDays;
        private TextView textViewProgressValue;

        public ViewHolderAppointedTask(View itemView) {
            super(itemView);
            textViewProjectName = itemView.findViewById(R.id.textViewProjectName);
            textViewTaskName = itemView.findViewById(R.id.textViewTaskName);
            textViewUserMail = itemView.findViewById(R.id.textViewUserMail);
            cardViewUserAppointedTask = itemView.findViewById(R.id.cardViewUserAppointedTask);
            ratingBarUserPoint = itemView.findViewById(R.id.ratingBarUserPoint);
            textViewResidualTotalDays = itemView.findViewById(R.id.textViewResidualTotalDays);
            textViewProgressValue = itemView.findViewById(R.id.textViewProgressValue);

            progressBarResudualDays = itemView.findViewById(R.id.progressBarResudualDays);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(filterListAppointedModels.get(position), position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public ViewHolderAppointedTask onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.content_user_appointed_task_list,parent,false);
        CustomUserAppointedTaskAdapter.ViewHolderAppointedTask viewHolderAppointedTask = new ViewHolderAppointedTask(view);
        return viewHolderAppointedTask;
    }

    @Override
    public void onBindViewHolder(ViewHolderAppointedTask holder, int position) {
        UserAppointedTaskModel userAppointedTask = filterListAppointedModels.get(position);
        holder.textViewProjectName.setText(userAppointedTask.getProjectName());
        holder.textViewTaskName.setText(userAppointedTask.getTaskName());
        holder.textViewUserMail.setText(userAppointedTask.getUserMail());
        holder.ratingBarUserPoint.setNumStars(5);
        holder.ratingBarUserPoint.setStepSize(1);

        LayerDrawable stars = (LayerDrawable) holder.ratingBarUserPoint.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#FFC300"), PorterDuff.Mode.SRC_ATOP);

        if(filterListAppointedModels.get(position).getScore() != null)
        holder.ratingBarUserPoint
                .setRating(filterListAppointedModels.get(position).getScore().intValue());

        if(filterListAppointedModels.get(position).getResidulaPercentageValue() > 0 && filterListAppointedModels.get(position).getDone() == false){
            holder.progressBarResudualDays.setProgress(filterListAppointedModels.get(position).getResidulaPercentageValue().intValue());
            holder.textViewResidualTotalDays.setText("Kalan Gün / Toplam Gün: "+filterListAppointedModels.get(position).getResidualTotalDays());
            holder.textViewProgressValue.setText(" % "+filterListAppointedModels.get(position).getResidulaPercentageValue().intValue());
        }
        else{
            holder.progressBarResudualDays.setProgress(0);
            holder.textViewProgressValue.setText("");
            holder.textViewResidualTotalDays.setText("");
        }

        if(userAppointedTask.getDone() == true)
        holder.cardViewUserAppointedTask.setCardBackgroundColor(Color.parseColor("#CCFFA3"));
        else  holder.cardViewUserAppointedTask.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

    }

    @Override
    public int getItemCount() {

        return filterListAppointedModels.size();
    }



    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchString = charSequence.toString();
                if(searchString.isEmpty()){
                    filterListAppointedModels = listAppointedModels;
                }else{

                    ArrayList<UserAppointedTaskModel> tempFilteredList = new ArrayList<>();

                    for (UserAppointedTaskModel taskModel : listAppointedModels){
                        if(taskModel.getTaskName().toLowerCase().contains(searchString)
                                || taskModel.getProjectName().toLowerCase().contains(searchString)
                                || taskModel.getUserMail().toLowerCase().contains(searchString))
                            tempFilteredList.add(taskModel);
                    }

                    filterListAppointedModels = tempFilteredList;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterListAppointedModels;
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                filterListAppointedModels = (ArrayList<UserAppointedTaskModel>) filterResults.values;
                notifyDataSetChanged();

            }
        };

    }

}
