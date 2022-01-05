package com.karatascompany.pys3318.fragments;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.karatascompany.pys3318.models.Graphs.ProjectTaskStatusModel;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;
import com.karatascompany.pys3318.session.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by azizmahmud on 26.3.2018.
 */

public class Tab3Project extends Fragment {

    PieChart pieChartTab3ProjectStatu;
    UserService userService;

    public String userId;
    public int projectId;
    private Session session;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab3_graphs, container, false);

        userService = ApiUtils.getUserService();
        Bundle extras = getActivity().getIntent().getExtras();
        projectId = extras.getInt("projectId");

        session = new Session(getActivity());
        userId = session.getUserId();


        pieChartTab3ProjectStatu = view.findViewById(R.id.pieChartTab3ProjectStatu);

        pieChartTab3ProjectStatu.setUsePercentValues(true);
        pieChartTab3ProjectStatu.setDrawHoleEnabled(true);
        pieChartTab3ProjectStatu.getDescription().setEnabled(false);

        pieChartTab3ProjectStatu.setExtraOffsets(5,10,5,5);
        pieChartTab3ProjectStatu.setDragDecelerationFrictionCoef(0.95f);
        //   pieChartProject.setHoleColor(Color.WHITE);

        // pieChartProject.setHoleRadius(7);
        pieChartTab3ProjectStatu.setTransparentCircleRadius(61f);
        // pieChartProject.setRotationAngle(0);
        pieChartTab3ProjectStatu.setRotationEnabled(false);

        Description description = new Description();
        description.setText("Proje Durumu");
        description.setTextSize(12);
        pieChartTab3ProjectStatu.setDescription(description);
        pieChartTab3ProjectStatu.animateY(1000, Easing.EasingOption.EaseInCubic);

        pieChartTab3ProjectStatu.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(e==null)
                    return;
          //       Toast.makeText(getActivity(), xData[e.getX()], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        addDataProjectStatus(userId,String.valueOf(projectId));

        return view;

    }

    private void addDataProjectStatus(String userId, String projectId) {

        Call<List<ProjectTaskStatusModel>> call = userService.GetManagerProjectTaskStatus(userId,projectId);
        call.enqueue(new Callback<List<ProjectTaskStatusModel>>() {
            @Override
            public void onResponse(Call<List<ProjectTaskStatusModel>> call, Response<List<ProjectTaskStatusModel>> response) {
                try {

                    List<ProjectTaskStatusModel> taskStatu = response.body();
                    ArrayList<PieEntry> yVal = new ArrayList<>();
                    for(int i=0;i<taskStatu.size();i++)
                        yVal.add(new PieEntry(taskStatu.get(i).getTaskStatusInPercent(),taskStatu.get(i).getTaskStatus()));

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
                    pieChartTab3ProjectStatu.setData(data);

                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProjectTaskStatusModel>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
