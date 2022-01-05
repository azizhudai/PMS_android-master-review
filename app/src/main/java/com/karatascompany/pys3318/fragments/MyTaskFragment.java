package com.karatascompany.pys3318.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.karatascompany.pys3318.MyTaskDetailActivity;
import com.karatascompany.pys3318.R;
import com.karatascompany.pys3318.adepters.CustomMyTaskMainAdepter;
import com.karatascompany.pys3318.models.TaskModel;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;
import com.karatascompany.pys3318.session.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by azizmahmud on 3.3.2018.
 */

public class MyTaskFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,CustomMyTaskMainAdepter.OnItemClickListener{

    UserService userService;
    public ArrayList<TaskModel> myTaskModels = new ArrayList<>();

    SwipeRefreshLayout yenileme_nesnesi_mytask;
    private RecyclerView recyclerViewMyTask;
    private CustomMyTaskMainAdepter customMyTaskMainAdepter;

    public String userId;
    private Session session;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.mytask,container,false);

       getActivity().setTitle("Görevlerim");
       yenileme_nesnesi_mytask = view.findViewById(R.id.yenileme_nesnesi_my_task);
       yenileme_nesnesi_mytask.setOnRefreshListener(this);

       recyclerViewMyTask = view.findViewById(R.id.recycleViewMyTask);
       recyclerViewMyTask.setLayoutManager(new LinearLayoutManager(getActivity()));

       customMyTaskMainAdepter = new CustomMyTaskMainAdepter(getActivity());
       userService = ApiUtils.getUserService();

       session = new Session(getActivity());
     //  Bundle extras = getActivity().getIntent().getExtras();
    //   int uid = extras.getInt("userId");
     //  userId = String.valueOf(uid);
        userId = session.getUserId();
        //Bundle bundle = this.getArguments();
       // if (bundle != null) {
       //     userId = bundle.getString("userId");
       // }
       //if(extras != null){
           if(userId != null){

               LoadRecyclerViewMyTaskData(userId);
               recyclerViewMyTask.setAdapter(customMyTaskMainAdepter);
               customMyTaskMainAdepter.setOnItemClickListener(MyTaskFragment.this);
               customMyTaskMainAdepter.notifyDataSetChanged();
           }
     //  }

        return view;
    }

    private void LoadRecyclerViewMyTaskData(String userId) {
        Call<List<TaskModel>> call = userService.GetMyTask(userId);
        call.enqueue(new Callback<List<TaskModel>>() {
            @Override
            public void onResponse(Call<List<TaskModel>> call, Response<List<TaskModel>> response) {
                try {
                    myTaskModels = (ArrayList<TaskModel>) response.body();
                    customMyTaskMainAdepter.notifyDataSetChanged();
                    customMyTaskMainAdepter.setMyTaskList(myTaskModels);
                   // customMyTaskMainAdepter.notifyDataSetChanged();
                    ////////
                    //TaskDemo taskDemo = new Gson().fromJson(String.valueOf(myTaskModels),TaskDemo.class);
                   // String json = new Gson().toJson(myTaskModels);

                }catch (Exception e){
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<List<TaskModel>> call, Throwable t) {
                try {
                    Toast.makeText(getActivity(),"Hatalı",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onRefresh() {

        LoadRecyclerViewMyTaskData(userId);
        yenileme_nesnesi_mytask.setRefreshing(false);
    }

    @Override
    public void onItemClick(int position) {

        TaskModel clickedItem = myTaskModels.get(position);
        View parentLayout = getActivity().findViewById(android.R.id.content);

        Intent intent = new Intent(getActivity(), MyTaskDetailActivity.class);
        //intent.putExtra("userId",userId);
        intent.putExtra("taskId",clickedItem.getTaskId());
        intent.putExtra("taskName",clickedItem.getTaskName());
        intent.putExtra("taskStartDate",clickedItem.getStartDateStr());
        intent.putExtra("taskEndDate",clickedItem.getEndDateStr());
        startActivity(intent);
        Snackbar.make(parentLayout,clickedItem.getTaskName(),Snackbar.LENGTH_SHORT).show();

    }
   public class TaskDemo {

       @SerializedName("taskModels")
       @Expose
       private List<TaskModel> taskModels = new ArrayList<>();

       public List<TaskModel> getTaskModels() {
           return taskModels;
       }

       public void setTaskModels(List<TaskModel> taskModels) {
           this.taskModels = taskModels;
       }
   }
}


