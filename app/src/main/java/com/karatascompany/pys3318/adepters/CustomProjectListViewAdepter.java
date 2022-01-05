package com.karatascompany.pys3318.adepters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.karatascompany.pys3318.R;
import com.karatascompany.pys3318.models.ProjectModel;

import java.util.ArrayList;

/**
 * Created by azizmahmud on 5.3.2018.
 */

public class CustomProjectListViewAdepter extends RecyclerView.Adapter<CustomProjectListViewAdepter.ViewHolderProject> implements Filterable{


    private LayoutInflater layoutInflater;
    private ArrayList<ProjectModel> listProjectModels = new ArrayList<>();
    private ArrayList<ProjectModel> projectListFiltered = new ArrayList<>();
    private OnItemClickListener mListener;
   // private CustomItemClickListener customItemClickListener;


    public interface OnItemClickListener{
        void onItemClick(ProjectModel projectModel,int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public CustomProjectListViewAdepter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    public void setProjectList(ArrayList<ProjectModel> listProjectModels){
        this.listProjectModels = listProjectModels;
        this.projectListFiltered = listProjectModels;
        notifyItemRangeChanged(0, listProjectModels.size());
    }

    @Override
    public ViewHolderProject onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custem_project_list_layout,parent,false);
        ViewHolderProject viewHolderProject = new ViewHolderProject(view);
        return viewHolderProject;
    }

    @Override
    public void onBindViewHolder(ViewHolderProject holder, int position) {

        ProjectModel currentProjectModel = projectListFiltered.get(position);
       // for(int i=0; i<listProjectModels.size();i++){
            holder.textViewProjectName.setText(currentProjectModel.getProjectName());
      //      holder.textViewProjectId.setText(String.valueOf(currentProjectModel.getProjectId()));
     //   }
       // holder.textViewProjectName.setText(currentProjectModel.getProjectName());
      //  holder.textViewProjectId.setText(currentProjectModel.getProjectId());
    }

    @Override
    public int getItemCount() {
        return projectListFiltered.size();
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchString = charSequence.toString();
                if(searchString.isEmpty()){
                    projectListFiltered = listProjectModels;
                }else{

                    ArrayList<ProjectModel> tempFilteredList = new ArrayList<>();

                    for (ProjectModel project : listProjectModels){
                        if(project.getProjectName().toLowerCase().contains(searchString))
                            tempFilteredList.add(project);
                    }

                    projectListFiltered = tempFilteredList;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = projectListFiltered;
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                projectListFiltered = (ArrayList<ProjectModel>) filterResults.values;
                notifyDataSetChanged();

            }
        };

    }



    public class ViewHolderProject extends RecyclerView.ViewHolder{

        private ImageView imageViewProject;
        private TextView textViewProjectId;
        private TextView textViewProjectName;

       public  ViewHolderProject (View itemView){
           super(itemView);
           imageViewProject = itemView.findViewById(R.id.imageViewProject);
           textViewProjectId = itemView.findViewById(R.id.textViewProjectId);
           textViewProjectName = itemView.findViewById(R.id.textViewProjectName);
         //  final ViewHolderProject myViewHolder = new ViewHolderProject(itemView);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(mListener != null){
                       int position = getAdapterPosition();
                       if(position != RecyclerView.NO_POSITION){
                           mListener.onItemClick(projectListFiltered.get(position),position);
                       }
                   }
               }
           });

       }


   }


}
