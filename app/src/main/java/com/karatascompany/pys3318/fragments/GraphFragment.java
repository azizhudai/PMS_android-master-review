package com.karatascompany.pys3318.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.karatascompany.pys3318.R;
import com.karatascompany.pys3318.models.Graphs.ManagerProjectDaysModel;
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
 * Created by azizmahmud on 3.3.2018.
 */

public class GraphFragment extends Fragment {

    RelativeLayout relativeLayoutGraphs;
    LinearLayout linearLayoutUserRating;
    private PieChart pieChart;
    private PieChart pieChartProject;
    private float[] yData={5,10,15,30,40};
    private String[] xData = {"sony","huavi", "LG","Appple","Samsung"};

    UserService userService;
    Session session; String userId;

    androidx.appcompat.widget.Toolbar myToolbar;
    Spinner mySpinner;
    ArrayAdapter<String> myAdepter;
    private String[] iller={"Görev Durumu","Proje Gün Sayıları","Puan Analizi"};

    /////////////////////
    private BarChart barChartProject;

    List<ManagerProjectDaysModel> managerProjectDays;

    RatingBar averageUserTaskRating;

    TextView textViewFive,textViewFour,textViewThree,textViewTwo,textViewOne,textViewAverage;

    TextRoundCornerProgressBar textProgressBarFiveStars,textProgressBarFourStars,textProgressBarThreeStars,textProgressBarTwoStars,textProgressBarOneStars;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.graph,container,false);

       userService = ApiUtils.getUserService();
       session = new Session(getActivity());userId = session.getUserId();
        getActivity().setTitle("Grafiklerim");
       myToolbar = view.findViewById(R.id.toolbarGraphs);
       mySpinner = view.findViewById(R.id.spinnerGraphs);
       // android.support.v7.app.ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

//        actionBar.hide();

          mySpinner.setAdapter(myAdepter);
        pieChartProject = view.findViewById(R.id.pieChartProject);
        barChartProject = view.findViewById(R.id.barChartProject);

        linearLayoutUserRating = view.findViewById(R.id.linearLayoutUserRating);

        pieChartProject.setVisibility(View.VISIBLE);
            mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getActivity(), mySpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                    if(i==0) {
//mySpinner.getSelectedItem().toString().equals("Görev Durumu")
                        barChartProject.setVisibility(View.GONE);
                        linearLayoutUserRating.setVisibility(View.GONE);
                        pieChartProject.setVisibility(View.VISIBLE);
                        addDataTask();
                    }
                    else if(i == 1) {
                        pieChartProject.setVisibility(View.GONE);
                        linearLayoutUserRating.setVisibility(View.GONE);
                        barChartProject.setVisibility(View.VISIBLE);
                        addDataBarChartProject(userId);

                    }
                    else if(i==2){
                        barChartProject.setVisibility(View.GONE);
                        pieChartProject.setVisibility(View.GONE);
                        linearLayoutUserRating.setVisibility(View.VISIBLE);

                        getDataTaskAnalysis(userId);
                        // kod kısmını eklicezzadadad
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        averageUserTaskRating = view.findViewById(R.id.averageUserTaskRating);

        textViewFive = view.findViewById(R.id.textViewFive); textViewFour = view.findViewById(R.id.textViewFour); textViewThree = view.findViewById(R.id.textViewThree);
        textViewTwo = view.findViewById(R.id.textViewTwo); textViewOne = view.findViewById(R.id.textViewOne);

        textViewAverage = view.findViewById(R.id.textViewAverage);

        textProgressBarFiveStars = view.findViewById(R.id.textProgressBarFiveStars); textProgressBarFourStars = view.findViewById(R.id.textProgressBarFourStars);
        textProgressBarThreeStars = view.findViewById(R.id.textProgressBarThreeStars); textProgressBarTwoStars = view.findViewById(R.id.textProgressBarTwoStars);
        textProgressBarOneStars = view.findViewById(R.id.textProgressBarOneStars);

      //      relativeLayoutGraphs = view.findViewById(R.id.relativeLayoutGraphs);
       // pieChartProject = new PieChart(getActivity());
           // relativeLayoutGraphs.addView(pieChart);
       //     relativeLayoutGraphs.setBackgroundColor(Color.LTGRAY);

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
           // addData();



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

       /* Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
*/
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
                    pieChartProject.animateY(1000, Easing.EasingOption.EaseInCubic);

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

    private void addData() {


        //region Description
        // endregion
        ArrayList<PieEntry> yVal = new ArrayList<>();
        for(int i=0;i<yData.length;i++)
            yVal.add(new PieEntry(yData[i],xData[i]));

      //  ArrayList<String> xVal = new ArrayList<>();

      /*  for(int i=0;i<xData.length;i++)
            xVal.add(xData[i]);
*/
        PieDataSet dataSet = new PieDataSet(yVal,"Proje Durumu");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        for(int c: ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for(int c: ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for(int c: ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for(int c: ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for(int c: ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.GRAY);
        pieChartProject.setData(data);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myAdepter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1,iller);

        myAdepter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
                    BarDataSet set = new BarDataSet(entries, "Proje Günler");
                  //  barChartProject.animateY(1000, Easing.EasingOption.EaseInElastic);

                    barChartProject.animateY(1000);
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

    private void getDataTaskAnalysis(String userId){

        Call<String> call = userService.GetTaskPointAnalize(userId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    String result = response.body();
                    String arrayResult[] = result.split(",");
                    if(arrayResult.length <= 1){
                        Toast.makeText(getActivity(), "Değerlendirme Bulunamadı!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        textViewAverage.setText("Ortalama Puan("+arrayResult[5]+" / "+arrayResult[6]+" = "+arrayResult[7]+")");
                        averageUserTaskRating.setRating(Float.valueOf(arrayResult[7]));

                        textViewFive.setText(arrayResult[0]+" Tane");

                        float five = Float.valueOf(arrayResult[0]);
                        float total = Float.valueOf(arrayResult[6]);
                        float fivePer = Float.valueOf((five/total)*100);
                        textProgressBarFiveStars.setMax(100);
                        textProgressBarFiveStars.setProgress(fivePer);
                        textProgressBarFiveStars.setSecondaryProgress(fivePer+5);
                        textProgressBarFiveStars.setProgressText("5 Yıldız"+'('+fivePer+"%)");

                        textViewFour.setText(arrayResult[1]+" Tane");

                        float four = Float.valueOf(arrayResult[1]);
                        float fourPer = Float.valueOf((four/total)*100);
                        textProgressBarFourStars.setProgress(fourPer);
                        textProgressBarFourStars.setSecondaryProgress(fourPer+5);
                        textProgressBarFourStars.setProgressText("4 Yıldız"+'('+(int)fourPer+"%)");

                        textViewThree.setText(arrayResult[2]+" Tane");

                        float three = Float.valueOf(arrayResult[2]);
                        float threePer = Float.valueOf((three/total)*100);
                        textProgressBarThreeStars.setProgress(threePer);
                        textProgressBarThreeStars.setSecondaryProgress(threePer+5);
                        textProgressBarThreeStars.setProgressText("3 Yıldız"+'('+(int)threePer+"%)");

                        textViewTwo.setText(arrayResult[3]+" Tane");

                        float two = Float.valueOf(arrayResult[3]);
                        float twoPer = Float.valueOf((two/total)*100);
                        textProgressBarTwoStars.setProgress(twoPer);
                        textProgressBarTwoStars.setSecondaryProgress(twoPer+5);
                        textProgressBarTwoStars.setProgressText("2 Yıldız"+'('+(int)twoPer+"%)");

                        textViewOne.setText(arrayResult[4]+" Tane");

                        float one = Float.valueOf(arrayResult[4]);
                        float onePer = Float.valueOf((one/total)*100);
                        textProgressBarOneStars.setProgress(onePer);
                        textProgressBarOneStars.setSecondaryProgress(onePer+5);
                        textProgressBarOneStars.setProgressText("1 Yıldız"+'('+(int)onePer+"%)");
                    }

                }
                catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}

