package com.karatascompany.pys3318;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;
import com.karatascompany.pys3318.session.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagementTotalCountActivity extends AppCompatActivity implements ValueAnimator.AnimatorUpdateListener{

    TextView managementProjectCountTV;
    TextView managementTaskCountTV;
    TextView doneTaskCountTV;
    TextView goingTaskCountTV;
    TextView overDueTaskCountTV;
    CardView allTaskCV;

    private Session session;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_total_count);



        overDueTaskCountTV = findViewById(R.id.overDueTaskCountTV);
        managementProjectCountTV = findViewById(R.id.managementProjectCountTV);
        managementTaskCountTV = findViewById(R.id.managementTaskCountTV);
        doneTaskCountTV = findViewById(R.id.doneTaskCountTV);
        goingTaskCountTV = findViewById(R.id.goingTaskCountTV);
        allTaskCV = findViewById(R.id.allTaskCV);

        allTaskCV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ManagementTotalCountActivity.this, UserAppointedTaskListActivity.class);
                //intent.putExtra("userMail", session.getUserMail());
                startActivity(intent);

            }
        });

        session = new Session(this);
        userService = ApiUtils.getUserService();
        Call<String> call = userService.GetTotalCountProjectDetail(session.getUserId());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    String count = response.body();

                    if(count.contains(",")){
                        String[] countList = count.split(",");
                        int projectManagementCount = Integer.parseInt(countList[0]);
                        ValueAnimator animator1 = new ValueAnimator();
                        animator1.setObjectValues(0, projectManagementCount);// here you set the range, from 0 to "count" value
                        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator animation) {
                                managementProjectCountTV.setText(String.valueOf(animation.getAnimatedValue()));
                            }
                        });
                        animator1.setDuration(3000); // here you set the duration of the anim
                        animator1.start();
                        int taskManagementCount = Integer.parseInt(countList[1]);

                        ValueAnimator animator2 = new ValueAnimator();
                        animator2.setObjectValues(0, taskManagementCount);// here you set the range, from 0 to "count" value
                        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator animation) {
                                managementTaskCountTV.setText(String.valueOf(animation.getAnimatedValue()));
                            }
                        });
                        animator2.setDuration(3000); // here you set the duration of the anim
                        animator2.start();

                        int doneTaskCount = Integer.parseInt(countList[2]);

                        ValueAnimator animator3 = new ValueAnimator();
                        animator3.setObjectValues(0, doneTaskCount);// here you set the range, from 0 to "count" value
                        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator animation) {
                                doneTaskCountTV.setText(String.valueOf(animation.getAnimatedValue()));
                            }
                        });
                        animator3.setDuration(3000); // here you set the duration of the anim
                        animator3.start();

                        int goingTaskCont = Integer.parseInt(countList[3]);

                        ValueAnimator animator4 = new ValueAnimator();
                        animator4.setObjectValues(0, goingTaskCont);// here you set the range, from 0 to "count" value
                        animator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator animation) {
                                goingTaskCountTV.setText(String.valueOf(animation.getAnimatedValue()));
                            }
                        });
                        animator4.setDuration(3000); // here you set the duration of the anim
                        animator4.start();

                        int overdueTaskCount = Integer.parseInt(countList[4]);





                        ValueAnimator animator = new ValueAnimator();
                        animator.setObjectValues(0, overdueTaskCount);// here you set the range, from 0 to "count" value
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator animation) {
                                overDueTaskCountTV.setText(String.valueOf(animation.getAnimatedValue()));
                            }
                        });
                        animator.setDuration(3000); // here you set the duration of the anim
                        animator.start();



                    }
                }
                catch (Exception e){
                    Toast.makeText(ManagementTotalCountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ManagementTotalCountActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
      /*  ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, 49);// here you set the range, from 0 to "count" value
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                overDueTaskCountTV.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.setDuration(1000); // here you set the duration of the anim
        animator.start();*/
    }
}
