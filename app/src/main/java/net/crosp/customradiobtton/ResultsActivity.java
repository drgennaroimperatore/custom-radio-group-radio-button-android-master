package net.crosp.customradiobtton;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.MotionEvent;
import android.widget.ExpandableListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;


import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.util.*;
import com.androidplot.ui.DynamicTableModel;
import com.androidplot.ui.FixedTableModel;
import com.androidplot.ui.TableOrder;
import com.androidplot.ui.Size;
import com.androidplot.ui.SizeMode;
import com.androidplot.ui.HorizontalPositioning;
import com.androidplot.ui.VerticalPositioning;
import android.content.Context;

import net.crosp.customradiobtton.CustomPieSegmentFormatter;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ResultsActivity extends AppCompatActivity {

    Button mPrevButton;

    public static final int SELECTED_SEGMENT_OFFSET = 150;

    private TextView donutSizeTextView;
    private SeekBar donutSizeSeekBar;

    private ExpandableListView mDiseaseListView;

    public PieChart pie;
    private PieRenderer mPieRenderer;



    private HashMap<String, Float> mDiagnoses = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //Get the diagnoses from the previous activity
        mDiagnoses = sortDiagnoses ((HashMap<String, Float>) getIntent().getSerializableExtra("diagnoses"));


        mPrevButton =(Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                GoToMain();

            }
        });

        // initialize our XYPlot reference:
        pie = (PieChart) findViewById(R.id.mySimplePieChart);

        // enable the legend:
        pie.getLegend().setVisible(true);
        pie.getLegend().setTableModel(new DynamicTableModel(1,4, TableOrder.ROW_MAJOR));
       /* pie.getLegend().setSize(new Size(
                PixelUtils.dpToPix(85), SizeMode.ABSOLUTE,
                PixelUtils.dpToPix(70), SizeMode.ABSOLUTE));
        pie.getLegend().position(
                0.5f, HorizontalPositioning.RELATIVE_TO_LEFT,
                0.1f, VerticalPositioning.RELATIVE_TO_TOP);

        final float padding = PixelUtils.dpToPix(15);
        pie.getPie().setPadding(padding, padding, padding, padding);

        pie.getPie().setSize(new Size(
                PixelUtils.dpToPix(170), SizeMode.ABSOLUTE,
                PixelUtils.dpToPix(140), SizeMode.ABSOLUTE));
        pie.getPie().position(
                0, HorizontalPositioning.RELATIVE_TO_LEFT,
                0, VerticalPositioning.RELATIVE_TO_TOP);*/

      reducePieSize();

       // donutSizeTextView = (TextView) findViewById(R.id.donutSizeTextView);
        //updateDonutText();




       Set<Map.Entry<String,Float>> entries = mDiagnoses.entrySet();

       int i =0;
       Float sum =0.0f;
       Segment [] segments = new Segment[4];

       for(Map.Entry<String, Float> e : entries)
       {
           String s =String.format("%.2f", (Float) e.getValue());

           if(i<=2)
           {
               segments[i] = new Segment((String) e.getKey(),Float.parseFloat(s) );
               sum+=e.getValue();
           }
           i++;
       }

        segments[3] =new Segment("Others", 100-sum);



               EmbossMaskFilter emf = new EmbossMaskFilter(
                new float[]{1, 1, 1}, 0.4f, 10, 8.2f);

        CustomPieSegmentFormatter sf1 = new CustomPieSegmentFormatter(this, R.xml.pie_segment_formatter1);
        sf1.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);
        sf1.getFillPaint().setMaskFilter(emf);

        CustomPieSegmentFormatter sf2 = new CustomPieSegmentFormatter(this, R.xml.pie_segment_formatter2);
        sf2.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);
        sf2.getFillPaint().setMaskFilter(emf);

        CustomPieSegmentFormatter sf3 = new CustomPieSegmentFormatter(this, R.xml.pie_segment_formatter3);
        sf3.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);
        sf3.getFillPaint().setMaskFilter(emf);

        CustomPieSegmentFormatter sf4 = new CustomPieSegmentFormatter(this, R.xml.pie_segment_formatter4);
        sf4.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);
        sf4.getFillPaint().setMaskFilter(emf);


        pie.addSegment(segments[0], sf1);
        pie.addSegment(segments[1], sf2);
        pie.addSegment(segments[2], sf3);
        pie.addSegment(segments[3], sf4);

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
        mPieRenderer = pie.getRenderer(CustomPieRenderer.class);

      mPieRenderer.setStartDegs(180);

        mDiseaseListView = (ExpandableListView) findViewById(R.id.disease_list_view);

        //todo custom adapter for names of diseases and percentages

        int len = mDiagnoses.size()-3;
        List <DiagnosisListElement> values = new ArrayList<>();

        int j=0;
       for(Map.Entry<String, Float> e:mDiagnoses.entrySet())
       {
           if(j>=3)
           {
               DiagnosisListElement dle= new DiagnosisListElement((String) e.getKey(), (Float) e.getValue());
               values.add(dle);
           }
           j++;
       }



        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Fourth - the Array of data



       HashMap<String, List<DiagnosisListElement>> listDetail = new HashMap<>();
       listDetail.put("Disease", values);


       ArrayList listTitle = new ArrayList<>(listDetail.keySet());


        DiagnosisListAdapter adapter = new DiagnosisListAdapter(this,listTitle ,listDetail);


        // Assign adapter to ListView
        mDiseaseListView.setAdapter(adapter);

       mDiseaseListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();



              // mPieRenderer.setDonutSize(200, PieRenderer.DonutMode.PIXELS);
               //pie.setTranslationX(3.0f);
              // pie.setScaleX(2.0f);
               // pie.redraw();

            }
        });

        mDiseaseListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

             mPieRenderer.setDonutSize(1.0f, PieRenderer.DonutMode.PIXELS);
             // pie.redraw();
            }
        });

        mDiseaseListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDetail.get(groupPosition)
                                + " -> "
                                + listDetail.get(
                                listTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;


        mDiseaseListView.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));

    }

    public void reducePieSize()
    {
        pie.getLegend().setSize(new Size(
                PixelUtils.dpToPix(80), SizeMode.ABSOLUTE,
                PixelUtils.dpToPix(70), SizeMode.ABSOLUTE));
        pie.getLegend().position(
                0.35f, HorizontalPositioning.RELATIVE_TO_LEFT,
                0.2f, VerticalPositioning.RELATIVE_TO_TOP);
        pie.getLegend().getTextPaint().setTextSize(38.0f);


        final float padding = PixelUtils.dpToPix(15);
        pie.getPie().setPadding(padding, padding, padding, padding);

        pie.getPie().setSize(new Size(
                PixelUtils.dpToPix(170), SizeMode.ABSOLUTE,
                PixelUtils.dpToPix(140), SizeMode.ABSOLUTE));
        pie.getPie().position(
                0, HorizontalPositioning.RELATIVE_TO_LEFT,
                0, VerticalPositioning.RELATIVE_TO_TOP);

        pie.refreshDrawableState();
        pie.redraw();

    }




    public int GetPixelFromDips(float pixels){
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    public HashMap<String, Float> sortDiagnoses(HashMap <String, Float> originalList )
    {
        List<Map.Entry<String, Float> > list =
                new LinkedList<Map.Entry<String, Float> >(originalList.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Float> >() {
            public int compare(Map.Entry<String, Float> o1,
                               Map.Entry<String, Float> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Float> temp = new LinkedHashMap<String, Float>();
        for (Map.Entry<String, Float> aa : list) {


            BigDecimal bd = new BigDecimal(Float.toString(aa.getValue()));
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);


            temp.put(aa.getKey(), bd.floatValue());
        }
        return temp;
    }

    public void GoToMain()
    {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }


}
