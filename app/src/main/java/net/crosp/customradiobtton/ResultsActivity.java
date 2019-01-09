package net.crosp.customradiobtton;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.MotionEvent;
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




public class ResultsActivity extends AppCompatActivity {

    Button mPrevButton;

    public static final int SELECTED_SEGMENT_OFFSET = 150;

    private TextView donutSizeTextView;
    private SeekBar donutSizeSeekBar;

    private ListView mDiseaseListView;

    public PieChart pie;

    private Segment s1;
    private Segment s2;
    private Segment s3;
    private Segment s4;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

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
        pie.getLegend().setSize(new Size(
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
                0, VerticalPositioning.RELATIVE_TO_TOP);

       // donutSizeTextView = (TextView) findViewById(R.id.donutSizeTextView);
        //updateDonutText();



        s1 = new Segment("Disease 1", 60);
        s2 = new Segment("Disease 2", 15);
        s3 = new Segment("Disease 3", 12);
        s4 = new Segment("Disease 4", 12);

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

        pie.addSegment(s1, sf1);
        pie.addSegment(s2, sf2);
        pie.addSegment(s3, sf3);
        pie.addSegment(s4, sf4);

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);

        mDiseaseListView = (ListView) findViewById(R.id.disease_list_view);

        //todo custom adapter for names of diseases and percentages

        String [] values = new String[7];
        for (int d=0; d<7; d++)
        {
            values[d]="Disease " + String.valueOf(d+5);
        }



        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Fourth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
               R.layout.disease_list_view_row, R.id.diseaseListTextView, values);


        // Assign adapter to ListView
        mDiseaseListView.setAdapter(adapter);
    }

    public void GoToMain()
    {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }


}
