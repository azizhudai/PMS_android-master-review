package com.karatascompany.pys3318.fragments;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.karatascompany.pys3318.R;
import com.karatascompany.pys3318.models.Graphs.ManagerProjectDaysModel;
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

public class MainTab2BarChartFragment extends Fragment {

    private BarChart barChartProject;
    UserService userService;
    Session session;
    String userId;
    List<ManagerProjectDaysModel> managerProjectDays;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintab2_barchart, container, false);

        userService = ApiUtils.getUserService();
        session = new Session(getActivity());
        userId = session.getUserId();

        barChartProject = view.findViewById(R.id.barChartProject);
       /* BarData data = new BarData(getXAxisValues(), getDataSet());
        barChartProject.setData(data);
        barChartProject.setDescription("My Chart");
        barChartProject.animateXY(2000, 2000);
        barChartProject.invalidate();*/
        addDataBarChartProject(userId);
        barChartProject.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(e==null)
                    return;
                //String a =
                 Toast.makeText(getActivity(), managerProjectDays.get((int)e.getX()).getProjectName()+"--"+managerProjectDays.get((int)e.getX()).getProjectDaysCount(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        return view;
    }

    private void addDataBarChartProject(String userId) {

        Call<List<ManagerProjectDaysModel>> call = userService.GetManagerProjectDays(userId);
        call.enqueue(new Callback<List<ManagerProjectDaysModel>>() {
            @Override
            public void onResponse(Call<List<ManagerProjectDaysModel>> call, Response<List<ManagerProjectDaysModel>> response) {

                try {
                    managerProjectDays = response.body();
                    List<BarEntry> entries = new ArrayList<>();
                    ArrayList<String> projectNameList = new ArrayList<>();

                    for (int i = 0; i < managerProjectDays.size(); i++) {
                        entries.add(new BarEntry( i,managerProjectDays.get(i).getProjectDaysCount()));

                        projectNameList.add(managerProjectDays.get(i).getProjectName());

                    }
                    BarDataSet set = new BarDataSet(entries, "Proje Kalan Günler");


                    set.setColors(ColorTemplate.MATERIAL_COLORS);
                    BarData data = new BarData(set);
                    data.setBarWidth(0.9f); // set custom bar width
                    data.setValueTextSize(10f);
                    barChartProject.setData(data);
                    barChartProject.setFitBars(true); // make the x-axis fit exactly all bars
                    barChartProject.invalidate(); // refresh


                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ManagerProjectDaysModel>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

      /*  List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        // gap of 2f
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));

        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        barChartProject.setData(data);
        barChartProject.setFitBars(true); // make the x-axis fit exactly all bars
        barChartProject.invalidate(); // refresh*/

    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }
    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }
}


