package com.karatascompany.pys3318;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karatascompany.pys3318.adepters.CustomUserListAdapter;
import com.karatascompany.pys3318.common_function.CommonFunction;
import com.karatascompany.pys3318.firebase_poco.EvaluationStatus;
import com.karatascompany.pys3318.fragments.TabbedTaskListFragment;
import com.karatascompany.pys3318.models.TaskDetailModel;
import com.karatascompany.pys3318.models.UserFriendModel;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;
import com.karatascompany.pys3318.session.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectTaskDetailActivity extends AppCompatActivity implements CustomUserListAdapter.OnItemClickListener {


    Button btnAppointUser;
    TextView textViewProjectTaskDetailStartDate;
    TextView textViewProjectTaskDetailEndDate;
    TextView textViewEditProjectTaskOlusturma, textViewEditProjectTaskAciklama;
    CollapsingToolbarLayout toolbar_layoutProjectTaskName;

    private UserService userService;
    private Session session;
    private String taskIdStr, taskName, taskDateStartStr, taskDateEndStr, userId;
    int projectId;
    private Boolean isDone;
    private int publicStartDate, publicEndDate;
    private int year_start, month_start, day_start, year_end, month_end, day_end;
    static private final int DIALOG_ID = 0, DIALOG_ID2 = 1;
    public ArrayList<UserFriendModel> userFriendModelArrayList = new ArrayList<>();
    private CustomUserListAdapter customUserListAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_insert_task, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_task_detail);

        userService = ApiUtils.getUserService();
        session = new Session(this);
        userId = session.getUserId();

        Bundle extras = getIntent().getExtras();
        projectId = extras.getInt("projectId");
        taskIdStr = extras.getString("taskIdStr");
        taskName = extras.getString("taskName");
        taskDateStartStr = extras.getString("taskDateStartStr");
        taskDateEndStr = extras.getString("taskDateEndStr");
        isDone = extras.getBoolean("isDone");

        Toolbar toolbarProjectTaskName = findViewById(R.id.toolbarProjectTaskName);
        setSupportActionBar(toolbarProjectTaskName);

        toolbar_layoutProjectTaskName = findViewById(R.id.toolbar_layoutProjectTaskName);
        toolbar_layoutProjectTaskName.setTitle(taskName);
        toolbar_layoutProjectTaskName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBulider = new AlertDialog.Builder(ProjectTaskDetailActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.popwindow_edit_title, null);

                final EditText editText_pop_edit_title = mView.findViewById(R.id.editText_pop_edit_title);
                editText_pop_edit_title.setText(taskName);
                mBulider.setView(mView);

                mBulider.setTitle("Görev Adı");
                mBulider.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Call<String> call = userService.EditTaskName(userId,taskIdStr,editText_pop_edit_title.getText().toString());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String result = response.body();
                                try {
                                    toolbar_layoutProjectTaskName.setTitle(editText_pop_edit_title.getText().toString());
                                    taskName = editText_pop_edit_title.getText().toString();

                                  //  String tokenId = CommonFunction.GetUserTokenId("4");

                                ////    PushNotifHttp not =new PushNotifHttp(); //taskName.substring(0,40)
                                //    not.execute(tokenId, "Görev Güncelleme",session.getUserMail()+" kişisi tarafından ("+ taskName +") güncellendi.");

                                    List<UserFriendModel> userFriendModelListNot = new ArrayList<>();
                                    for(int i=0;i<userFriendModelArrayList.size();i++){
                                        if(userFriendModelArrayList.get(i).getAppoint() == true){//atanan kişilere bildirim için

                                            userFriendModelListNot.add(userFriendModelArrayList.get(i));

                                        }
                                    }

                                 /*   for(int i=0;i<userFriendModelListNot.size();i++){
                                      CommonFunction.GetUserTokenId(String.valueOf(userFriendModelListNot.get(i).getUserId()),session.getUserMail(),taskName+ " güncellendi" );
                                   baş   /* if(userTokenId != "0" && !userTokenId.isEmpty() && userTokenId != null){
                                           PushNotifHttp not2 =new PushNotifHttp(); //taskName.substring(0,40)
                                           not2.execute(userTokenId, "Görev Güncelleme",session.getUserMail()+" kişisi tarafından ("+ taskName +") güncellendi.");

                                       }-- bit
                                    }*/

                                    Toast.makeText(ProjectTaskDetailActivity.this, result, Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Toast.makeText(ProjectTaskDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(ProjectTaskDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        //Toast.makeText(ProjectTaskDetailActivity.this, "değişti demo ", Toast.LENGTH_SHORT).show();
                    }
                });

                mBulider.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // Toast.makeText(ProjectTaskDetailActivity.this, "İptal edildi.", Toast.LENGTH_SHORT).show();
                    }
                });

                final AlertDialog dialog = mBulider.create();
                dialog.show();
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textViewEditProjectTaskAciklama = findViewById(R.id.textViewEditProjectTaskAciklama);
        textViewEditProjectTaskOlusturma = findViewById(R.id.textViewEditProjectTaskOlusturma);

        Call<TaskDetailModel> call = userService.GetProjectTaskDetail(userId, taskIdStr);
        call.enqueue(new Callback<TaskDetailModel>() {
            @Override
            public void onResponse(Call<TaskDetailModel> call, Response<TaskDetailModel> response) {
                try {

                    TaskDetailModel taskDetail = response.body();
                    if (taskDetail != null) {
                        textViewEditProjectTaskOlusturma.setTextSize(12f);
                        textViewEditProjectTaskOlusturma.setTextColor(Color.GRAY);
                        textViewEditProjectTaskAciklama.setText(taskDetail.getTaskDetail());
                        textViewEditProjectTaskOlusturma.setText(taskDetail.getByUserName() + " kişisi tarafından" + taskDetail.getSystemStartDate() + " tarihinde oluşturuldu.");
                    } else {
                        Toast.makeText(ProjectTaskDetailActivity.this, "Hata", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    Toast.makeText(ProjectTaskDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<TaskDetailModel> call, Throwable t) {
                Toast.makeText(ProjectTaskDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        textViewProjectTaskDetailStartDate = findViewById(R.id.textViewProjectTaskDetailStartDate);
        textViewProjectTaskDetailEndDate = findViewById(R.id.textViewProjectTaskDetailEndDate);

        if (taskIdStr != null) {
            textViewProjectTaskDetailStartDate.setText(taskDateStartStr);
            textViewProjectTaskDetailEndDate.setText(taskDateEndStr);
            DateIslemi();
            showDialogOnButtonClick();
           /* textViewProjectTaskDetailStartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(ProjectTaskDetailActivity.this,"tarih tıklandı",Toast.LENGTH_SHORT).show();
                }
            });*/
        }

        btnAppointUser = findViewById(R.id.btnAppointUser);
        btnAppointUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mBulider = new AlertDialog.Builder(ProjectTaskDetailActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.popwindowuserlist, null);
                ArrayList mItems = new ArrayList<String>();

                RecyclerView recViewUserList = mView.findViewById(R.id.recViewUserList);
                recViewUserList.setHasFixedSize(true);
                recViewUserList.setLayoutManager(new LinearLayoutManager(ProjectTaskDetailActivity.this));
                customUserListAdapter = new CustomUserListAdapter(ProjectTaskDetailActivity.this);

                LoadRecyclerViewUserData(userId, taskIdStr);
                recViewUserList.setAdapter(customUserListAdapter);
                customUserListAdapter.setOnItemClickListener(ProjectTaskDetailActivity.this);
                //    recViewUserList.add
                //  recViewUserList.setAdapter();
                mBulider.setView(mView);

                //mBulider.setTitle("Görev Atama");
                mBulider.setPositiveButton("Atama Yap", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //userFriendList


                        List<UserFriendModel> friendModelList = userFriendList;

                        List<EvaluationStatus> evaluationStatusList = new ArrayList<>();
                      //  EvaluationStatus evaluationStatus = new EvaluationStatus(friendModelList.get(i));
                        for(i = 0;i<friendModelList.size();i++){

                            EvaluationStatus evaluationStatus = new EvaluationStatus(String.valueOf(friendModelList.get(i).getUserId()),
                                    session.getUserId(),String.valueOf(projectId),
                                    taskIdStr,userFriendList.get(i).getAppoint());
                            evaluationStatusList.add(evaluationStatus);

                        }

                        Call<String> callAppoint = userService.PostAppointUserList(evaluationStatusList);
                        callAppoint.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {

                                try {
                                    String result = response.body();
                                    if(result.equals("OK"))
                                    Toast.makeText(ProjectTaskDetailActivity.this, "Atama Başarılı.", Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Toast.makeText(ProjectTaskDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                                Toast.makeText(ProjectTaskDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                        //evaluationStatuses.add();
                      //  List<UserFriendModel> friendModelList = CustomUserListAdapter.getUserList();
                        //Toast.makeText(ProjectTaskDetailActivity.this, "Atama Başarılı.", Toast.LENGTH_SHORT).show();
                    }
                });

                mBulider.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ProjectTaskDetailActivity.this, "İptal edildi.", Toast.LENGTH_SHORT).show();
                    }
                });

                final AlertDialog dialog = mBulider.create();
                dialog.show();
            }
        });
    }

    public List<UserFriendModel> userFriendList = new ArrayList<>();
    private void LoadRecyclerViewUserData(String userId, String taskIdStr) {
        Call<List<UserFriendModel>> call = userService.GetUserFriend(userId, taskIdStr);
        call.enqueue(new Callback<List<UserFriendModel>>() {
            @Override
            public void onResponse(Call<List<UserFriendModel>> call, Response<List<UserFriendModel>> response) {


                try {

                    userFriendModelArrayList.clear();userFriendList.clear();
                    userFriendModelArrayList = (ArrayList<UserFriendModel>) response.body();
                    userFriendList = (ArrayList) userFriendModelArrayList.clone();
                  //  userFriendList = new ArrayList<>(userFriendModelArrayList);
                    customUserListAdapter.notifyDataSetChanged();
                    customUserListAdapter.setUserList(userFriendModelArrayList);
                } catch (Exception e) {
                    Toast.makeText(ProjectTaskDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<UserFriendModel>> call, Throwable t) {
                Toast.makeText(ProjectTaskDetailActivity.this, "Hatalı", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void DateIslemi() {
        try {
            Calendar cal = Calendar.getInstance();

            String[] valueStartDate = taskDateStartStr.split("\\.");
            String[] valueEndDate = taskDateEndStr.split("\\.");
            year_start = Integer.valueOf(valueStartDate[2]);
            publicStartDate = Integer.valueOf(valueStartDate[1]); // 1 ise 1dir...
            month_start = Integer.valueOf(valueStartDate[1]) - 1;
            day_start = Integer.valueOf(valueStartDate[0]);

            year_end = Integer.valueOf(valueEndDate[2]); //cal.get(Integer.valueOf(valueEndDate[2]));
            month_end = Integer.valueOf(valueEndDate[1]) - 1;//cal.get(Integer.valueOf(valueEndDate[0]));
            publicEndDate = Integer.valueOf(valueEndDate[1]);
            day_end = Integer.valueOf(valueEndDate[0]);//cal.get(Integer.valueOf(valueEndDate[1]));

        } catch (Exception e) {
            Toast.makeText(ProjectTaskDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    public void showDialogOnButtonClick() {

        textViewProjectTaskDetailStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DIALOG_ID);
            }
        });
        textViewProjectTaskDetailEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID2);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, year_start, month_start, day_start);
        if (id == DIALOG_ID2)
            return new DatePickerDialog(this, dpickerListenerEnd, year_end, month_end, day_end);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            //"yyyy.MM.dd"

            boolean statu = false;
            boolean isSaveStartDate = false;
            if (year_end > year) {
                isSaveStartDate = true;
                statu = true;
            }

            if (year_end == year && publicEndDate > month + 1) {
                isSaveStartDate = true;
                statu = true;
            }

            if (year_end == year && publicEndDate == month + 1) {
                if (day_end > day) {
                    isSaveStartDate = true;
                    statu = true;
                }
            }

            if (statu == false) {
                //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //         .setAction("Action", null).show();
                Toast.makeText(ProjectTaskDetailActivity.this, "Son tarihten büyük olamaz!", Toast.LENGTH_SHORT).show();

            }
            if (isSaveStartDate == true) {
                year_start = year;
                month_start = month + 1;
                day_start = day;
                publicStartDate = month_start;
                String month_startStr = String.valueOf(month_start), day_startStr = String.valueOf(day_start);
                if (month_start < 10) month_startStr = "0" + String.valueOf(month_start);
                if (day_start < 10) day_startStr = "0" + String.valueOf(day_start);

                textViewProjectTaskDetailStartDate.setText(day_startStr + "." + month_startStr + "." + year_start);// "yyyy-MM-dd"

                String newStartDate = String.valueOf(year_start + "." + month_startStr + "." + day_startStr);
                Call<String> call = userService.EditProjectTaskStartDate(userId, taskIdStr, newStartDate);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {

                            String result = response.body();
                            if (result.equals("OK")) {
                                Toast.makeText(ProjectTaskDetailActivity.this, "Başlama Tarih Güncellesi Başarılı.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProjectTaskDetailActivity.this, result, Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            Toast.makeText(ProjectTaskDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ProjectTaskDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }
    };
    private DatePickerDialog.OnDateSetListener dpickerListenerEnd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            boolean statuEnd = false;
            boolean isSaveEndDate = false;
            if (year_start < year) {
                isSaveEndDate = true;
                statuEnd = true;
            }
            if (year_start == year && publicStartDate < month + 1) {
                isSaveEndDate = true;
                statuEnd = true;
            }

            if (year_start == year && publicStartDate == month + 1) {
                if (day_start < day) {
                    isSaveEndDate = true;
                    statuEnd = true;
                }
            }

            if (statuEnd == false) {
                //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //         .setAction("Action", null).show();
                Toast.makeText(ProjectTaskDetailActivity.this, "ilk tarihten küçük olamaz!", Toast.LENGTH_SHORT).show();

            }
            if (isSaveEndDate == true) {
                year_end = year;
                month_end = month + 1;
                publicEndDate = month_end;
                day_end = day;
                String month_endStr = String.valueOf(month_end), day_endStr = String.valueOf(day_end);
                if (month_end < 10) month_endStr = "0" + String.valueOf(month_end);
                if (day_end < 10) day_endStr = "0" + String.valueOf(day_end);

                textViewProjectTaskDetailEndDate.setText(day_endStr + "." + month_endStr + "." + year_end);
                //   Toast.makeText(ProjectEditActivity.this,year_end+"/"+month_end+"/"+day_end,Toast.LENGTH_SHORT).show();

                String newEndDate = String.valueOf(year_end + "." + month_endStr + "." + day_endStr);
                Call<String> call = userService.EditProjectTaskEndDate(userId, taskIdStr, newEndDate);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            String result = response.body();
                            if (result.equals("OK")) {
                                Toast.makeText(ProjectTaskDetailActivity.this, "Bitirme Tarih Güncellesi Başarılı.", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(ProjectTaskDetailActivity.this, result, Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            Toast.makeText(ProjectTaskDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ProjectTaskDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

        }
    };

    @Override
    public void onItemClick(int position) {

        UserFriendModel clickedItem = userFriendModelArrayList.get(position);


        // Toast.makeText(ProjectTaskDetailActivity.this,clickedItem.getUserMail(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_delete_task) {
          //  Toast.makeText(this, "Görev Silme işlemi", Toast.LENGTH_SHORT).show();

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ProjectTaskDetailActivity.this);

            builder.setTitle("Görev Silme İşlemi");
            builder.setMessage("Görevi Silmek istediğinizden emin misin?");

            builder.setCancelable(false);

            builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    DeleteTask(userId, taskIdStr);
                    //ProjectTaskDetailActivity.this.finish();

                }
            });

            builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            androidx.appcompat.app.AlertDialog olustur = builder.create();

            olustur.show();
        }
        return false;
    }

    private void DeleteTask(String userId, String taskIdStr) {

        Call<String> call = userService.DeleteTask(userId, taskIdStr);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                try {
                    Toast.makeText(ProjectTaskDetailActivity.this, result, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProjectTaskDetailActivity.this, TabbedTaskListFragment.class);
                   // int projeIdint = Integer.parseInt(projectId);
                    intent.putExtra("projectId",projectId);
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(ProjectTaskDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ProjectTaskDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}