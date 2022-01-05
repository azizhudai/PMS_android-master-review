package com.karatascompany.pys3318.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.karatascompany.pys3318.ProjectTaskDetailActivity;
import com.karatascompany.pys3318.R;
import com.karatascompany.pys3318.adepters.CustomTaskAdapter;
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
 * Created by azizmahmud on 6.3.2018.
 */

public class Tab1Task extends Fragment implements SwipeRefreshLayout.OnRefreshListener,CustomTaskAdapter.OnItemClickListener{

    UserService userService;
    public ArrayList<TaskModel> taskModels = new ArrayList<>();

    SwipeRefreshLayout yenileme_nesnesi_tasks;
    private RecyclerView recycleViewTasks;
    private CustomTaskAdapter customTaskAdapter;

    public String userId;
    public int projectId;
    private Session session;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.tab1_task, container, false);

        yenileme_nesnesi_tasks = view.findViewById(R.id.yenileme_nesnesi_tasks);
        yenileme_nesnesi_tasks.setOnRefreshListener(this);

        recycleViewTasks = view.findViewById(R.id.recycleViewTasks);
        recycleViewTasks.setLayoutManager(new LinearLayoutManager(getActivity()));

        customTaskAdapter = new CustomTaskAdapter(getActivity());
        userService = ApiUtils.getUserService();

        Bundle extras = getActivity().getIntent().getExtras();

        session = new Session(getActivity());

        userId = session.getUserId();
                // extras.getString("userIdint");
        projectId = extras.getInt("projectId");

        if (extras != null) {
            if (userId != null && projectId != 0) {

                LoadRecyclerViewTaskData(userId, projectId);
                customTaskAdapter.notifyDataSetChanged();
                recycleViewTasks.setAdapter(customTaskAdapter);
                customTaskAdapter.setOnItemClickListener(Tab1Task.this);

            }
        }
            return view;

    }

    private void LoadRecyclerViewTaskData(String userId,int projectId) {

        Call<List<TaskModel>> call = userService.GetTask(userId,String.valueOf(projectId));
        call.enqueue(new Callback<List<TaskModel>>() {
            @Override
            public void onResponse(Call<List<TaskModel>> call, Response<List<TaskModel>> response) {
                try {
                    taskModels = (ArrayList<TaskModel>) response.body();
                    customTaskAdapter.setTaskList(taskModels);
                }catch (Exception e){
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<List<TaskModel>> call, Throwable t) {

                Toast.makeText(getActivity(),"Hatalı",Toast.LENGTH_SHORT).show();

            }
        });

        }

    @Override
    public void onRefresh() {

        LoadRecyclerViewTaskData(userId,projectId);
        yenileme_nesnesi_tasks.setRefreshing(false);
    }

    @Override
    public void onItemClick(int position) {

        TaskModel clickedItem = taskModels.get(position);
      //  View parentLayout = getActivity().findViewById(android.R.id.content);

     //   Snackbar.make(parentLayout,clickedItem.getTaskName(),Snackbar.LENGTH_SHORT).show();
      //  Toast.makeText(getActivity(),clickedItem.getTaskName(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(),ProjectTaskDetailActivity.class);
        intent.putExtra("projectId",projectId);
        intent.putExtra("taskIdStr",String.valueOf(clickedItem.getTaskId()));
        intent.putExtra("taskName",clickedItem.getTaskName());
        intent.putExtra("taskDateStartStr",clickedItem.getStartDateStr());
        intent.putExtra("taskDateEndStr",clickedItem.getEndDateStr());
        intent.putExtra("isDone",clickedItem.getNow());

        startActivity(intent);


    }
}
