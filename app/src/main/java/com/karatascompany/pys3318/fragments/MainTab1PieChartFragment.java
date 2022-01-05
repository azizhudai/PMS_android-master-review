package com.karatascompany.pys3318.fragments;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.karatascompany.pys3318.R;
import com.karatascompany.pys3318.models.Graphs.UserAssignedTaskStatusModel;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;
import com.karatascompany.pys3318.session.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by azizmahmud on 25.3.2018.
 */

public class MainTab1PieChartFragment extends Fragment {

    private PieChart pieChartProject;
    UserService userService;
    Session session;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintab1_piechart, container, false);

        userService = ApiUtils.getUserService();
        session = new Session(getActivity());

        pieChartProject = view.findViewById(R.id.pieChartProject);

        pieChartProject.setUsePercentValues(true);
        pieChartProject.setDrawHoleEnabled(true);
        pieChartProject.getDescription().setEnabled(false);

        pieChartProject.setExtraOffsets(5,10,5,5);
        pieChartProject.setDragDecelerationFrictionCoef(0.95f);
        //   pieChartProject.setHoleColor(Color.WHITE);

        // pieChartProject.setHoleRadius(7);
        pieChartProject.setTransparentCircleRadius(61f);
        // pieChartProject.setRotationAngle(0);
        pieChartProject.setRotationEnabled(false);

        Description description = new Description();
        description.setText("Proje Durumu");
        description.setTextSize(12);
        pieChartProject.setDescription(description);
        pieChartProject.animateY(1000, Easing.EasingOption.EaseInCubic);
        pieChartProject.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(e==null)
                    return;
                // Toast.makeText(getActivity(), xData[e.getX()], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        addDataTask();

        return view;

    }

    private void addDataTask() {

        Call<List<UserAssignedTaskStatusModel>> call = userService.UserTaskStatus(session.getUserId());
        call.enqueue(new Callback<List<UserAssignedTaskStatusModel>>() {
            @Override
            public void onResponse(Call<List<UserAssignedTaskStatusModel>> call, Response<List<UserAssignedTaskStatusModel>> response) {
                List<UserAssignedTaskStatusModel> taskStatu = response.body();
                try {

                    ArrayList<PieEntry> yVal = new ArrayList<>();
                    for(int i=0;i<taskStatu.size();i++)
                        yVal.add(new PieEntry(taskStatu.get(i).getUserTaskStatusInPercent(),taskStatu.get(i).getUserTaskStatus()));

                    PieDataSet dataSet = new PieDataSet(yVal,"Proje Durumu");
                    dataSet.setSliceSpace(3f);
                    dataSet.setSelectionShift(5f);

                    ArrayList<Integer> colors = new ArrayList<>();

               /*     for(int c: ColorTemplate.VORDIPLOM_COLORS)
                        colors.add(c);
*/
                    //  for(int c: ColorTemplate.JOYFUL_COLORS)
                    //     colors.add(c);

                    for(int c: ColorTemplate.COLORFUL_COLORS)
                        colors.add(c);
                    for(int c: ColorTemplate.LIBERTY_COLORS)
                        colors.add(c);
                    //  for(int c: ColorTemplate.PASTEL_COLORS)
                    //        colors.add(c);

                    colors.add(ColorTemplate.getHoloBlue());
                    dataSet.setColors(colors);

                    PieData data = new PieData(dataSet);
                    data.setValueTextSize(14f);
                    data.setValueTextColor(Color.GRAY);
                    pieChartProject.setData(data);

                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<UserAssignedTaskStatusModel>> call, Throwable t) {
                try {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}