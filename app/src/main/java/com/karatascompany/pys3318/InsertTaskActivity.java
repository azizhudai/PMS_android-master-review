package com.karatascompany.pys3318;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.protobuf.Empty;
import com.karatascompany.pys3318.adepters.CustomProjectListViewAdepter;
import com.karatascompany.pys3318.models.ProjectModel;
import com.karatascompany.pys3318.poco.Task;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;
import com.karatascompany.pys3318.session.Session;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.karatascompany.pys3318.LoginActivity.userId;

public class InsertTaskActivity extends AppCompatActivity {

    private Button btnEvet;
    private Button btnHayir;
    private Spinner spinnerUserProjectList;
    private EditText editTextTaskName, EditTextTaskDetail;
    private TextView textViewTaskStartDate, textViewTaskEndDate;

    private int publicStartDate, publicEndDate;
    private int year_start, month_start, day_start, year_end, month_end, day_end;
    static private final int DIALOG_ID = 0, DIALOG_ID2 = 1;

    private UserService userService;
    public String uid = "";
    int projectId;
    private Session session;
    private String userId;
    public ProjectModel projectModel1;
    public ArrayList<ProjectModel> projectModels = new ArrayList<>();
    private ArrayAdapter<String> dataAdapterProjectList;
    private CustomProjectListViewAdepter customProjectAdepter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_insert_task, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_task);
        setTitle("Görev Ekleme");

        userService = ApiUtils.getUserService();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.btnInsertTask);

        editTextTaskName = findViewById(R.id.editTextTaskName);
        EditTextTaskDetail = findViewById(R.id.EditTextTaskDetail);
        textViewTaskStartDate = findViewById(R.id.textViewTaskStartDate);
        textViewTaskEndDate = findViewById(R.id.textViewTaskEndDate);
        spinnerUserProjectList = findViewById(R.id.spinnerUserProjectList);
        //   textViewTaskStartDate.getText().setText("");
        //  if(userId != 0) {
        //uid = String.valueOf(userId);
        //TaskListDoldur(uid,projectModel1);
        session = new Session(this);
        userId = session.getUserId();
        Bundle extras = getIntent().getExtras();
        projectId = extras.getInt("projectId");
        //Toast.makeText(this, "Proje id: " + projectId, Toast.LENGTH_SHORT).show();
        LoadRecylerViewData(userId);

        // }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final View viewFinal = view;
                String taskName = editTextTaskName.getText().toString();
                String taskDetail = EditTextTaskDetail.getText().toString();
                String startDate = textViewTaskStartDate.getText().toString();
                String endDate = textViewTaskEndDate.getText().toString();

                String state = isCheckEmpty(taskName, startDate, endDate);
                if (state.equals("")) {
                    Task task = new Task(Integer.parseInt(session.getUserId()),projectId, taskName, taskDetail, startDate, endDate);

                    Call<String> call = userService.InsertTaskClass(task);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try{
                                String result = response.body();
                                if(result.equals("1"))
                                    Toast.makeText(InsertTaskActivity.this, "Ekleme Başarılı.", Toast.LENGTH_SHORT).show();

                                  //  Snackbar.make(viewFinal, "Ekleme Başarılı.", Snackbar.LENGTH_LONG)
                                   //         .setAction("Action", null).show();
                                else
                                    Toast.makeText(InsertTaskActivity.this, result, Toast.LENGTH_SHORT).show();
                                  //  Snackbar.make(viewFinal, "Hata Oluştu!. Tekrar deneyiniz.", Snackbar.LENGTH_LONG)
                                  //          .setAction("Action", null).show();
                            }catch (Exception e){
                                Toast.makeText(InsertTaskActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                               // Snackbar.make(viewFinal, "Ekleme Başarılı.", Snackbar.LENGTH_LONG)
                                    //   .setAction("Action", null).show();
                            }
                            finish();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(InsertTaskActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            //Snackbar.make(viewFinal, "Hata Oluştu!. Tekrar deneyiniz.", Snackbar.LENGTH_LONG)
                              //      .setAction("Action", null).show();
                        }
                    });


                }else{
                    Toast.makeText(InsertTaskActivity.this, state, Toast.LENGTH_SHORT).show();
                    //Snackbar.make(view, state, Snackbar.LENGTH_LONG)
                      //      .setAction("Action", null).show();
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnEvet = findViewById(R.id.btnEvet);
        btnHayir = findViewById(R.id.btnHayir);

// tarih bölümü
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        // System.out.println();
        textViewTaskStartDate.setText(dateFormat.format(date));
        textViewTaskEndDate.setText(dateFormat.format(date));
        DateIslemi();
        showDialogOnButtonClick();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i = item.getItemId();
        if (i == R.id.action_delete_task) {
            Toast.makeText(this, "Silinme işlemi", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(InsertTaskActivity.this);

            builder.setTitle("Silme İşlemi");
            builder.setMessage("Silmek mi istersin?");

            builder.setCancelable(false);

            builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    InsertTaskActivity.this.finish();
                }
            });

            builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog olustur = builder.create();

            olustur.show();
        }
        return false;
    }

    private void LoadRecylerViewData(String uid) {

        Call<List<ProjectModel>> call = userService.GetProject(uid);
        call.enqueue(new Callback<List<ProjectModel>>() {
            @Override
            public void onResponse(Call<List<ProjectModel>> call, Response<List<ProjectModel>> response) {
                try {
                    projectModels = (ArrayList<ProjectModel>) response.body();
                    //final List<String> listProject = new ArrayList<>();

                    // final ProjectModel[] projects2 = new ProjectModel[projectModels.size()+1];
                    final String[] projectNameList = new String[projectModels.size()];
                    final int[] projectIdList = new int[projectModels.size()];
                    int setValueid = 1;
                    for (int i = 0; i < projectModels.size(); i++) {
                        // listProjects.add( projectModels.get(i));
                        //listProject.add( projectModels.get(i).getProjectName());
                        projectNameList[i] = projectModels.get(i).getProjectName();
                        projectIdList[i] = projectModels.get(i).getProjectId();
                        if (projectId == projectModels.get(i).getProjectId()) {
                            setValueid = i;
                        }
                        //projects2[i] = new ProjectModel(i);
                        //  projects2[i].setProjectId(projectModels.get(i).getProjectId());
                        //  projects2[i].setProjectName(projectModels.get(i).getProjectName());

                    }

                    ///customProjectAdepter.setProjectList(projectModels);
                    //Spinner'lar için adapterleri hazırlıyoruz. //simple_spinner_item
                    dataAdapterProjectList = new ArrayAdapter<String>(InsertTaskActivity.this, android.R.layout.simple_spinner_item, projectNameList);
                    //Listelenecek verilerin görünümünü belirliyoruz.
                    dataAdapterProjectList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Hazırladğımız Adapter'leri Spinner'lara ekliyoruz.
                    spinnerUserProjectList.setAdapter(dataAdapterProjectList);
                    spinnerUserProjectList.setSelection(setValueid);

                } catch (Exception e) {
                    Toast.makeText(InsertTaskActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(InsertTaskActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });

    }


    private void DateIslemi() {
        try {
            Calendar cal = Calendar.getInstance();

            String[] valueStartDate = textViewTaskStartDate.getText().toString().split("\\.");
            String[] valueEndDate = textViewTaskEndDate.getText().toString().split("\\.");
            year_start = Integer.valueOf(valueStartDate[2]);
            publicStartDate = Integer.valueOf(valueStartDate[1]); // 1 ise 1dir...
            month_start = Integer.valueOf(valueStartDate[1]) - 1;
            day_start = Integer.valueOf(valueStartDate[0]);

            year_end = Integer.valueOf(valueEndDate[2]); //cal.get(Integer.valueOf(valueEndDate[2]));
            month_end = Integer.valueOf(valueEndDate[1]) - 1;//cal.get(Integer.valueOf(valueEndDate[0]));
            publicEndDate = Integer.valueOf(valueEndDate[1]);
            day_end = Integer.valueOf(valueEndDate[0]);//cal.get(Integer.valueOf(valueEndDate[1]));

        } catch (Exception e) {
            Toast.makeText(InsertTaskActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    public void showDialogOnButtonClick() {

        textViewTaskStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DIALOG_ID);
            }
        });
        textViewTaskEndDate.setOnClickListener(new View.OnClickListener() {
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
                Toast.makeText(InsertTaskActivity.this, "Son tarihten büyük olamaz!", Toast.LENGTH_SHORT).show();

            }
            if (isSaveStartDate == true) {
                year_start = year;
                month_start = month + 1;
                day_start = day;
                publicStartDate = month_start;
                String month_startStr = String.valueOf(month_start), day_startStr = String.valueOf(day_start);
                if (month_start < 10) month_startStr = "0" + String.valueOf(month_start);
                if (day_start < 10) day_startStr = "0" + String.valueOf(day_start);

                textViewTaskStartDate.setText(day_startStr + "." + month_startStr + "." + year_start);// "yyyy-MM-dd"

                // String newStartDate = String.valueOf(year_start + "." + month_startStr + "." + day_startStr);
                //Call<String> call = userService.EditProjectTaskStartDate(userId, taskIdStr, newStartDate);
                /*call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {

                            String result = response.body();
                            if (result.equals("OK")) {
                                Toast.makeText(InsertTaskActivity.this, "Başlama Tarih Güncellesi Başarılı.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(InsertTaskActivity.this, result, Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            Toast.makeText(InsertTaskActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(InsertTaskActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });*/
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
                Toast.makeText(InsertTaskActivity.this, "ilk tarihten küçük olamaz!", Toast.LENGTH_SHORT).show();

            }
            if (isSaveEndDate == true) {
                year_end = year;
                month_end = month + 1;
                publicEndDate = month_end;
                day_end = day;
                String month_endStr = String.valueOf(month_end), day_endStr = String.valueOf(day_end);
                if (month_end < 10) month_endStr = "0" + String.valueOf(month_end);
                if (day_end < 10) day_endStr = "0" + String.valueOf(day_end);

                textViewTaskEndDate.setText(day_endStr + "." + month_endStr + "." + year_end);
                //   Toast.makeText(ProjectEditActivity.this,year_end+"/"+month_end+"/"+day_end,Toast.LENGTH_SHORT).show();

                String newEndDate = String.valueOf(year_end + "." + month_endStr + "." + day_endStr);
                // Call<String> call = userService.EditProjectTaskEndDate(userId, taskIdStr, newEndDate);
                /*call.enqueue(new Callback<String>() {
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
                });*/
            }

        }
    };

    public String isCheckEmpty(String taskName, String startDate, String endDate){

        String statu = "";
        if(taskName.isEmpty()) statu = "Görev Adı Boş ";
        //if(taskDetail.isEmpty()) statu = "Görev Adı Boş ";
        if(startDate.isEmpty()) statu = "Görev Başlama Tarihi Boş ";
        if(endDate.isEmpty()) statu = "Görev Bitiş Tarihi Boş ";

        return statu;
    }

}
