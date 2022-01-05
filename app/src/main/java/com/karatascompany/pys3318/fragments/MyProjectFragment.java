package com.karatascompany.pys3318.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.karatascompany.pys3318.ProjectEditActivity;
import com.karatascompany.pys3318.R;
import com.karatascompany.pys3318.adepters.CustomProjectListViewAdepter;
import com.karatascompany.pys3318.models.ProjectModel;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.karatascompany.pys3318.LoginActivity.userId;

/**
 * Created by azizmahmud on 3.3.2018.
 */

public class MyProjectFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,CustomProjectListViewAdepter.OnItemClickListener {

    //ListView listViewMyProject;
    UserService userService;
    private SwipeRefreshLayout yenileme_nesnesi;
    public String uid = "";
    public ProjectModel projectModel1;
    public ArrayList<ProjectModel> projectModels = new ArrayList<>();

    private RecyclerView recyclerViewListProjectHits;
    private CustomProjectListViewAdepter  customProjectAdepter;
    private FloatingActionButton fabProjectAdd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.myproject,container,false);

       getActivity().setTitle("Projelerim");
        //listViewMyProject = view.findViewById(R.id.listViewMyProject);

        recyclerViewListProjectHits = view.findViewById(R.id.recycleViewProject);

        recyclerViewListProjectHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        customProjectAdepter = new CustomProjectListViewAdepter(getActivity());

        fabProjectAdd = view.findViewById(R.id.fabProjectAdd);
        fabProjectAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), ProjectEditActivity.class));
            }
        });

        yenileme_nesnesi = view.findViewById(R.id.yenileme_nesnesi);
        yenileme_nesnesi.setOnRefreshListener(this);

        userService = ApiUtils.getUserService();

        if(userId != 0){
            uid = String.valueOf(userId);
            //TaskListDoldur(uid,projectModel1);

            LoadRecylerViewData(uid);
            recyclerViewListProjectHits.setAdapter(customProjectAdepter);

            customProjectAdepter.setOnItemClickListener(MyProjectFragment.this);


            /*Call<List<ProjectModel>> call = userService.GetProject(uid);
            call.enqueue(new Callback<List<ProjectModel>>() {
                @Override
                public void onResponse(Call<List<ProjectModel>> call, Response<List<ProjectModel>> response) {

                    final List<ProjectModel> project = response.body();
                    if (project != null) {
                        List<String> listProject = new ArrayList<>();
                        ProjectModel[] projectModels = new ProjectModel[project.size() + 1];


                    }
                }

                @Override
                public void onFailure(Call<List<ProjectModel>> call, Throwable t) {

                }
            });*/

        }

        return view;
    }

    private void LoadRecylerViewData(String uid) {

        Call<List<ProjectModel>> call = userService.GetProject(uid);
        call.enqueue(new Callback<List<ProjectModel>>() {
            @Override
            public void onResponse(Call<List<ProjectModel>> call, Response<List<ProjectModel>> response) {
                try {
                    projectModels = (ArrayList<ProjectModel>) response.body();
                    final List<String> listProject = new ArrayList<>();

                    final ProjectModel[] projects2 = new ProjectModel[projectModels.size()+1];

                    for(int i = 0; i< projectModels.size(); i++){
                        // listProjects.add( projectModels.get(i));
                        listProject.add( projectModels.get(i).getProjectName());
                        //projects2[i] = new ProjectModel(i);
                        //  projects2[i].setProjectId(projectModels.get(i).getProjectId());
                        //  projects2[i].setProjectName(projectModels.get(i).getProjectName());

                    }

                    customProjectAdepter.setProjectList(projectModels);

                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                //adepter = new CustomProjectListViewAdepter(getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,projects2);

            //listViewTask = findViewById(R.id.listViewTask);
            //   ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>
            //           (getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, listProject);


            //(C) adımı
                //listViewMyProject.setAdapter(veriAdaptoru);

               /* listViewMyProject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    AlertDialog.Builder diyalogOlusturucu =
                            new AlertDialog.Builder(getActivity());

                    Toast.makeText(getActivity(),projects2[position].getProjectName()+"id "+projects2[position].getProjectId(),Toast.LENGTH_SHORT).show();
                    //diyalogOlusturucu.setMessage(listProject.get(position))
                    diyalogOlusturucu.setMessage(projects2[position].getProjectName())

                            .setCancelable(false)
                            .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    diyalogOlusturucu.create().show();
                }
            });*/

        }

            @Override
            public void onFailure(Call<List<ProjectModel>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });

    }

    private void TaskListDoldur(String uid, ProjectModel projectModel1) {
        Call<List<ProjectModel>> call = userService.GetProject(uid);
        call.enqueue(new Callback<List<ProjectModel>>() {
            @Override
            public void onResponse(Call<List<ProjectModel>> call, Response<List<ProjectModel>> response) {
                final List<ProjectModel> projectModels = response.body();
                //final List<String> listProject = new ArrayList<>();

                final ProjectModel[] projects2 = new ProjectModel[projectModels.size()+1];

                for(int i = 0; i< projectModels.size(); i++){
                    //listProject.add( projectModels.get(i).getProjectName());
                   // projects2[i] = new ProjectModel();
                   // projects2[i].setProjectId(projectModels.get(i).getProjectId());
                  //  projects2[i].setProjectName(projectModels.get(i).getProjectName());

                }




                //(C) adımı
              /*  listViewMyProject.setAdapter(veriAdaptoru);

                listViewMyProject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        AlertDialog.Builder diyalogOlusturucu =
                                new AlertDialog.Builder(getActivity());

                        Toast.makeText(getActivity(),projects2[position].getProjectName()+"id "+projects2[position].getProjectId(),Toast.LENGTH_SHORT).show();
                        //diyalogOlusturucu.setMessage(listProject.get(position))
                        diyalogOlusturucu.setMessage(projects2[position].getProjectName())

                                .setCancelable(false)
                                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        diyalogOlusturucu.create().show();
                    }
                });*/

            }

            @Override
            public void onFailure(Call<List<ProjectModel>> call, Throwable t) {

            }


        });
    }

    @Override
    public void onRefresh() {
        LoadRecylerViewData(uid);
        Toast.makeText(getActivity(), "Yenileme başarılı", Toast.LENGTH_LONG).show();
        yenileme_nesnesi.setRefreshing(false); /* nesnenin yenileme özelliği kapatıldı
         aksi halde sürekli çalışır bu kısmı işleminiz yapılsada yapılmasada kullanın çünkü işlem başarısız olsada
         hata mesajı verirsiniz ama işlem yapılana kadar olan kısımda bu kodu kullanmayın sonrası için kullanın */

    }


    @Override
    public void onItemClick(ProjectModel projectModel,int position) {

       ProjectModel clickedItem = projectModel;
               //projectModels.get(position);
       Toast.makeText(getActivity(),String.valueOf(clickedItem.getProjectId())+" "+ clickedItem.getProjectName(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), TabbedTaskListFragment.class);//TabbedTaskActivity
        intent.putExtra("userIdint",uid);
        intent.putExtra("projectId",clickedItem.getProjectId());
       startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       //inflater = getLayoutInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = null;
        if(searchItem != null){
            searchView = (SearchView) searchItem.getActionView();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                customProjectAdepter.getFilter().filter(s);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}
