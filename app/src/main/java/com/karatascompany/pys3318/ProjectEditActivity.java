package com.karatascompany.pys3318;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karatascompany.pys3318.poco.Project;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;
import com.karatascompany.pys3318.session.Session;

import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectEditActivity extends AppCompatActivity {

    private EditText editTextProjectEditTitle;
    private EditText editTextProjectEditDetail;
    private Button btnProjectStartDate;
    private Button btnProjectEndDate;
    private TextView textViewEditStartDate;
    private TextView textViewEditEndDate;

    Session session;
    UserService userService;
    private int year_start,month_start,day_start,year_end,month_end,day_end;
    static final int DIALOG_ID = 0,DIALOG_ID2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);

        setTitle("Proje Düzenle");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextProjectEditDetail = findViewById(R.id.editTextProjectEditDetail);
        editTextProjectEditTitle = findViewById(R.id.editTextProjectEditTitle);

        textViewEditStartDate = findViewById(R.id.textViewEditStartDate);
        textViewEditEndDate = findViewById(R.id.textViewEditEndDate);

        session = new Session(this);
        final String userId = session.getUserId();
        userService = ApiUtils.getUserService();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!"".contentEquals(editTextProjectEditTitle.getText())){
                    Project project = new Project(0,String.valueOf(editTextProjectEditTitle.getText()),
                            String.valueOf(editTextProjectEditDetail.getText()),
                            String.valueOf(textViewEditStartDate.getText()),
                            String.valueOf(textViewEditEndDate.getText()),
                            Integer.parseInt(userId));
                    // String projectSerilized = new Gson().toJson(project);
                    Call<String> call = userService.InsertProjectClass(project);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try {
                                String result = response.body();
                                Toast.makeText(ProjectEditActivity.this, result, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ProjectEditActivity.this,MainFragmentActivity.class));

                            }catch (Exception e){
                                Toast.makeText(ProjectEditActivity.this, e.getMessage()+" Server Returned", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(ProjectEditActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{

                    Snackbar.make(view, "Lütfen Proje Başlık Giriniz!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
// "yyyy-MM-dd"





            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        final Calendar cal = Calendar.getInstance();
        year_start= year_end = cal.get(Calendar.YEAR);
        month_start = month_end = cal.get(Calendar.MONTH);
        day_start = day_end = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();
    }

    public void showDialogOnButtonClick(){
        btnProjectStartDate = findViewById(R.id.btnProjectStartDate);
        btnProjectEndDate = findViewById(R.id.btnProjectEndDate);

        btnProjectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
            }
        });
        btnProjectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID2);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener,year_start,month_start,day_start);
        if(id == DIALOG_ID2)
            return new DatePickerDialog(this, dpickerListenerEnd,year_end,month_end,day_end);
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            year_start = year;
            month_start = month+1;
            day_start = day;
            String month_startStr =String.valueOf(month_start),day_startStr = String.valueOf(day_start);
            if(month_start <10) month_startStr = "0"+String.valueOf(month_start); if(day_start <10) day_startStr = "0"+String.valueOf(day_start);
            textViewEditStartDate.setText(day_startStr+'.'+month_startStr+'.'+year_start);
            //textViewEditStartDate.setText(year_start +"-"+month_startStr+"-"+day_startStr);// "yyyy-MM-dd"
        }
    };
    private DatePickerDialog.OnDateSetListener dpickerListenerEnd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            year_end = year;
            month_end = month+1;
            day_end = day;
            String month_endStr =String.valueOf(month_end),day_endStr = String.valueOf(day_end);
            if(month_end <10) month_endStr = "0"+String.valueOf(month_end); if(day_end <10) day_endStr = "0"+String.valueOf(day_end);

            textViewEditEndDate.setText(day_endStr+'.'+month_endStr+'.'+year_end );
         //   Toast.makeText(ProjectEditActivity.this,year_end+"/"+month_end+"/"+day_end,Toast.LENGTH_SHORT).show();
        }
    };

}

