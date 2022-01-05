package com.karatascompany.pys3318;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.karatascompany.pys3318.adepters.CustomUserAppointedTaskAdapter;
import com.karatascompany.pys3318.common_function.CommonFunction;
import com.karatascompany.pys3318.models.UserAppointedTaskModel;
import com.karatascompany.pys3318.models.UserScoringModel;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;
import com.karatascompany.pys3318.session.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAppointedTaskListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,CustomUserAppointedTaskAdapter.OnItemClickListener{

    UserService userService;
    RatingBar ratingUserScoring; TextView textViewUserScoring;
    public ArrayList<UserAppointedTaskModel> userAppointedTaskList = new ArrayList<>();

    SwipeRefreshLayout yenileme_nesnesi_appointed_task;
    private RecyclerView recycleViewAppointedTask;
    private CustomUserAppointedTaskAdapter customUserAppointedTaskAdapter;

    private String userId;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_appointed_task); //activity_user_appointed_task_list
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Yeni Görev", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/

        yenileme_nesnesi_appointed_task = findViewById(R.id.yenileme_nesnesi_appointed_task);
        yenileme_nesnesi_appointed_task.setOnRefreshListener(this);

        recycleViewAppointedTask = findViewById(R.id.recycleViewAppointedTask);
        recycleViewAppointedTask.setLayoutManager(new LinearLayoutManager(this));

        customUserAppointedTaskAdapter = new CustomUserAppointedTaskAdapter(this);
        userService = ApiUtils.getUserService();

        session = new Session(this);
        userId = session.getUserId();

        if(userId != null){
            LoadRecyclerViewAppointedTaskData(userId);
            customUserAppointedTaskAdapter.notifyDataSetChanged();
            recycleViewAppointedTask.setAdapter(customUserAppointedTaskAdapter);
            customUserAppointedTaskAdapter.setOnItemClickListener(this);


        }

    }

    private void LoadRecyclerViewAppointedTaskData(String userId) {
        Call<List<UserAppointedTaskModel>> call = userService.GetUserAppointedTaskList(userId);
        call.enqueue(new Callback<List<UserAppointedTaskModel>>() {
            @Override
            public void onResponse(Call<List<UserAppointedTaskModel>> call, Response<List<UserAppointedTaskModel>> response) {
                try {

                    userAppointedTaskList = (ArrayList) response.body();
                    customUserAppointedTaskAdapter.setAppointedTaskList(userAppointedTaskList);

                }catch (Exception e){
                    Toast.makeText(UserAppointedTaskListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserAppointedTaskModel>> call, Throwable t) {
                Log.e("Hata Mesajj",t.getMessage());
                Toast.makeText(UserAppointedTaskListActivity.this,"Hatalı"+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onRefresh() {

        LoadRecyclerViewAppointedTaskData(userId);
        yenileme_nesnesi_appointed_task.setRefreshing(false);
    }

    @Override
    public void onItemClick(UserAppointedTaskModel taskModel,int position) {

        final UserAppointedTaskModel clickedItem = taskModel;
                //userAppointedTaskList.get(position);
        //Toast.makeText(this, clickedItem.getUserMail()+" "+clickedItem.getAppointedId(), Toast.LENGTH_SHORT).show();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.popwindowuserscoring,null);

        ratingUserScoring = mView.findViewById(R.id.ratingUserScoring);
        textViewUserScoring = mView.findViewById(R.id.textViewUserScoring);
        TextView textViewTaskNamePop = mView.findViewById(R.id.textViewTaskNamePop);
        TextView textViewUserNamePop = mView.findViewById(R.id.textViewUserNamePop);

        TextView textViewProjectNamePop = mView.findViewById(R.id.textViewProjectNamePop);
        textViewProjectNamePop.setText("Proje Adı: "+clickedItem.getProjectName());

        textViewTaskNamePop.setText("Görev Adı: " +clickedItem.getTaskName());
        textViewUserNamePop.setText("Kullanıcı Mail: "+ clickedItem.getUserMail());
        if(clickedItem.getScore() != null){
            ratingUserScoring.setRating(clickedItem.getScore().intValue());
            textViewUserScoring.setText(clickedItem.getScore().toString()+" Yıldız");
        }

        LayerDrawable stars = (LayerDrawable) ratingUserScoring.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#FFC300"), PorterDuff.Mode.SRC_ATOP);

        mBuilder.setView(mView);

        mBuilder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                UserScoringModel model = new UserScoringModel(clickedItem.AppointedId,Integer.valueOf(userId),clickedItem.getUserId(),ratingUserScoring.getRating());
                Call<String> call = userService.SetUserScoring(model);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {
                          String message =  response.body();
                          Toast.makeText(UserAppointedTaskListActivity.this, message, Toast.LENGTH_SHORT).show();
                          String send = "(değerlendirme) Proje Adı:"+clickedItem.getProjectName()+" Görev Adı: "+ clickedItem.getTaskName()+" " + textViewUserScoring.getText() + " güncellendi";
                           // CommonFunction.GetUserTokenId(String.valueOf(clickedItem.getUserId()) ,session.getUserMail(),send);

                        }
                        catch (Exception e){
                            Toast.makeText(UserAppointedTaskListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

            }
        });


        mBuilder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        ratingUserScoring.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){


            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                textViewUserScoring.setText(v+" Yıldız");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
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
                customUserAppointedTaskAdapter.getFilter().filter(s);
                return true;
            }
        });
        return true;
       // super.onCreateOptionsMenu(menu);
    }
}
