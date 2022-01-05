package com.karatascompany.pys3318;

import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.karatascompany.pys3318.models.TaskDetailModel;
import com.karatascompany.pys3318.models.TaskStatusModel;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;
import com.karatascompany.pys3318.session.Session;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTaskDetailActivity extends AppCompatActivity {

    private CollapsingToolbarLayout toolbar_layout;

    private TextView yLabelStart;
    private TextView mLabelStart;
    private TextView dLabelStart;
    private TextView eLabelStart;

    private TextView yLabelEnd;
    private TextView mLabelEnd;
    private TextView dLabelEnd;
    private TextView eLabelEnd;
    private TextView textViewMyTaskStatement;
    private TextView textViewByUser;

    private UserService userService;
    private Session session;
    private TaskDetailModel taskDetailModel;

    private String taskIdStr, userId;
    private String statuTask = "1";
    int taskId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task_detail);

        setTitle("Görev Detay");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Boolean statu = SetTaskStatu("4",statuTask);
                Call<TaskStatusModel> call = userService.SetTaskStatus(String.valueOf(taskId),statuTask);
                call.enqueue(new Callback<TaskStatusModel>() {
                    @Override
                    public void onResponse(Call<TaskStatusModel> call, Response<TaskStatusModel> response) {
                        status = response.body();
                        if(status != null){
                            String str = "";
                            if(status.getTaskStatu() == true) str = "1";
                            else str="0";

                            if(status.getTaskStatu() == true){
                                statuTask = "0";
                                fab.setImageResource(R.drawable.icons8_tick_box_96);
                            }else {

                                fab.setImageResource(R.drawable.icons8_unchecked_checkbox_48);
                                statuTask = "1";
                            }

                            //   Toast.makeText(MyTaskDetailActivity.this,str,Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<TaskStatusModel> call, Throwable t) {
                        Toast.makeText(MyTaskDetailActivity.this,"Hata!",Toast.LENGTH_SHORT).show();

                    }

                });
                Snackbar.make(view, "Görev Durumu Güncellendi", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar_layout = findViewById(R.id.toolbar_layout);

        yLabelStart = findViewById(R.id.yLabelStart);
        mLabelStart = findViewById(R.id.mLabelStart);
        dLabelStart = findViewById(R.id.dLabelStart);
        eLabelStart = findViewById(R.id.eLabelStart);
        yLabelEnd = findViewById(R.id.yLabelEnd);
        mLabelEnd = findViewById(R.id.mLabelEnd);
        dLabelEnd = findViewById(R.id.dLabelEnd);
        eLabelEnd = findViewById(R.id.eLabelEnd);
        textViewMyTaskStatement = findViewById(R.id.textViewMyTaskStatement);
        textViewByUser = findViewById(R.id.textViewByUser);

        userService = ApiUtils.getUserService();
        session = new Session(this);

       /* Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMMM/d/E", Locale.getDefault());

        String strDate = sdf.format(cal.getTime());
        String[] values = strDate.split("/",0);

        for(int i=0;i<values.length;i++){
            Log.v("check date",values[i]);
        }
        yLabelStart.setText(values[0]);
        mLabelStart.setText(values[1]);
        dLabelStart.setText(values[2]);
        eLabelStart.setText(values[3]);*/

       userId = session.getUserId();

        Bundle extras = getIntent().getExtras();
        taskId = extras.getInt("taskId");
        if(userId != null && taskId != 0){
            String taskName = extras.getString("taskName");
            toolbar_layout.setTitle(taskName);

            String taskStartDate = extras.getString("taskStartDate");
            String taskEndDate = extras.getString("taskEndDate");
            //Calendar calendar
            String dateStringStart = taskStartDate;//"15 5 2013 17:38:34 +0300";
            String dateStringEnd = taskEndDate;
            // System.out.println(dateStringStart);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            DateFormat targetFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String formattedDate = null;
            // String formattedDateEnd = null;
            Date convertedDate = new Date();
            Date convertedDateEnd = new Date();
            try {
                convertedDate = dateFormat.parse(dateStringStart);
                convertedDateEnd = dateFormat.parse(dateStringEnd);
                System.out.println(dateStringStart);
                formattedDate = targetFormat.format(convertedDate);
                System.out.println(" +++"+formattedDate);
            } catch (ParseException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("asdada"+convertedDate);
            String[] valuesDate = String.valueOf(convertedDate).split(" ",0);
            String[] valuesDateEnd = String.valueOf(convertedDateEnd).split(" ",0);


            yLabelStart.setText(valuesDate[5]);
            mLabelStart.setText(valuesDate[1]);
            dLabelStart.setText(valuesDate[2]);
            eLabelStart.setText(valuesDate[0]);

            yLabelEnd.setText(valuesDateEnd[5]);
            mLabelEnd.setText(valuesDateEnd[1]);
            dLabelEnd.setText(valuesDateEnd[2]);
            eLabelEnd.setText(valuesDateEnd[0]);

            taskIdStr = String.valueOf(taskId);
            GetTaskDetailWebService(userId,taskIdStr);
        }


    }

    private void GetTaskDetailWebService(String userId, String taskIdStr) {

        Call<TaskDetailModel> call = userService.GetTaskDetail(userId,taskIdStr);
        call.enqueue(new Callback<TaskDetailModel>() {
            @Override
            public void onResponse(Call<TaskDetailModel> call, Response<TaskDetailModel> response) {
                taskDetailModel = response.body();
                textViewMyTaskStatement.setText(taskDetailModel.getTaskDetail());
                textViewByUser.setText(taskDetailModel.getByUserName()+" "+ taskDetailModel.getSystemStartDate());

            }

            @Override
            public void onFailure(Call<TaskDetailModel> call, Throwable t) {

            }
        });
    }
    TaskStatusModel status;
    private Boolean SetTaskStatu(String taskId,String isTaskStatu){
        Call<TaskStatusModel> call = userService.SetTaskStatus(taskId,isTaskStatu);
        call.enqueue(new Callback<TaskStatusModel>() {
            @Override
            public void onResponse(Call<TaskStatusModel> call, Response<TaskStatusModel> response) {
                status = response.body();
                if(status != null){
                    String str = "";
                    if(status.getTaskStatu() == true) str = "1";
                    else str="0";

                   // Toast.makeText(MyTaskDetailActivity.this,str,Toast.LENGTH_SHORT).show();

                }
           }

            @Override
            public void onFailure(Call<TaskStatusModel> call, Throwable t) {
                Toast.makeText(MyTaskDetailActivity.this,"Hata!",Toast.LENGTH_SHORT).show();

            }

        });
        return status.getTaskStatu();
    }
}
