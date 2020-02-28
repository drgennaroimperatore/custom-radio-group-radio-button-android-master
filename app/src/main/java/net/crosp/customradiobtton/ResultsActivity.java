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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;


import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.ui.Anchor;
import com.androidplot.ui.SeriesRenderer;
import com.androidplot.ui.SizeMetric;
import com.androidplot.ui.TableModel;
import com.androidplot.util.*;
import com.androidplot.ui.DynamicTableModel;
import com.androidplot.ui.FixedTableModel;
import com.androidplot.ui.TableOrder;
import com.androidplot.ui.Size;
import com.androidplot.ui.SizeMode;
import com.androidplot.ui.HorizontalPositioning;
import com.androidplot.ui.VerticalPositioning;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.Step;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYLegendItem;
import com.androidplot.xy.XYLegendWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import android.content.Context;

import net.crosp.customradiobtton.CustomPieSegmentFormatter;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ResultsActivity extends AppCompatActivity {

    Button mPrevButton, mGoToLogCaseButton;

    public static final int SELECTED_SEGMENT_OFFSET = 150;

    private ExpandableListView mDiseaseListView;

   // public PieChart pie;
   // private PieRenderer mPieRenderer;

    private XYPlot mPlot;
    private XYSeries mSeries;
    private MyBarFormatter mBarFormatter;

    private String mSpecies;
    private HashMap<String, Float> mDiagnoses = new HashMap<>();
    private HashMap<String, String> mSigns = new HashMap<>();
    private AbstractMap.SimpleEntry<String, Float> mMostLikelyDiagnosis;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //Get the diagnoses from the previous activity

        mDiagnoses = sortDiagnoses ((HashMap<String, Float>) getIntent().getSerializableExtra("diagnoses"));
        mSigns = (HashMap<String, String>) getIntent().getSerializableExtra("signs");
        mSpecies = getIntent().getStringExtra("species");


        mPrevButton =(Button) findViewById(R.id.prev_button);

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GoToMain();
            }
        });

        mGoToLogCaseButton = (Button) findViewById(R.id.go_to_LogCase_button);
        mGoToLogCaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToLogCase();
            }
        });

        mPlot =(XYPlot) findViewById(R.id.mySimplePieChart);


        /*// initialize our XYPlot reference:
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
                0, VerticalPositioning.RELATIVE_TO_TOP);*/

    //  reducePieSize();

       // donutSizeTextView = (TextView) findViewById(R.id.donutSizeTextView);
        //updateDonutText();


       Set<Map.Entry<String,Float>> entries = mDiagnoses.entrySet();

       int i =0;
       Float sum =0.0f;
      // Segment [] segments = new Segment[4];

        Paint fillPaint = new Paint();
        fillPaint.setColor(Color.BLACK);
        mPlot.getGraph().setGridBackgroundPaint(fillPaint);
        mPlot.getGraph().getDomainGridLinePaint().setColor(Color.TRANSPARENT);
        mPlot.getGraph().getRangeGridLinePaint().setColor(Color.TRANSPARENT);

        mPlot.getGraph().getSize().setHeight(new SizeMetric(450,SizeMode.FILL));
        mPlot.getGraph().setMarginTop(PixelUtils.dpToPix(0));
        mPlot.getGraph().setMarginBottom(PixelUtils.dpToPix(0));



     Number[] numbers = new Number[4];
     String[] titles = new String[4];
       for(Map.Entry<String, Float> e : entries) {
           String s = String.format("%d", Math.round(e.getValue()));



           if(i==0)
           {
               mMostLikelyDiagnosis = new AbstractMap.SimpleEntry(e.getKey(), e.getValue());

           }


           if(i<=2)
           {
               //segments[i] = new Segment((String) e.getKey(),(int)Float.parseFloat(s) )
               // ;
               numbers [i]= Integer.parseInt(s) ;
               sum+=e.getValue();
               titles[i]=e.getKey() + "("+e.getValue()+"%"+")";
           }
           i++;
       }

        numbers[3] = 100-sum;
       titles[3] = "Less Likely";



       SimpleXYSeries[] seriesArray = new SimpleXYSeries[4];

       int index =0;
       for(Number n: numbers)
       {
           List temp = new ArrayList();
           temp.add(n);
           seriesArray[index] = new SimpleXYSeries(temp, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, titles[index]);
           index++;
       }




       mSeries= new SimpleXYSeries(Arrays.asList(numbers),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Disease Likelihood");

       mPlot.setDomainBoundaries(-3, mSeries.size(), BoundaryMode.FIXED);
       mPlot.setRangeUpperBoundary(SeriesUtils.minMax(mSeries).getMaxY().doubleValue(),BoundaryMode.FIXED);
       mPlot.setRangeLowerBoundary(0.2,BoundaryMode.FIXED);


       MyBarFormatter[] barFormatterList= new MyBarFormatter[4];
       barFormatterList[0] = new MyBarFormatter(Color.YELLOW,Color.BLACK);
        barFormatterList[1] = new MyBarFormatter(Color.RED,Color.BLACK);
        barFormatterList[2] = new MyBarFormatter(Color.GREEN,Color.BLACK);
        barFormatterList[3] = new MyBarFormatter(Color.argb(255, 10,10,10),Color.BLACK);

        barFormatterList[0].setMarginLeft(PixelUtils.dpToPix(10));
        barFormatterList[1].setMarginLeft(PixelUtils.dpToPix(10));
        barFormatterList[2].setMarginLeft(PixelUtils.dpToPix(10));
        barFormatterList[3].setMarginLeft(PixelUtils.dpToPix(10));

        barFormatterList[0].setMarginRight(PixelUtils.dpToPix(10));
        barFormatterList[1].setMarginRight(PixelUtils.dpToPix(10));
        barFormatterList[2].setMarginRight(PixelUtils.dpToPix(10));
        barFormatterList[3].setMarginRight(PixelUtils.dpToPix(10));


       mPlot.addSeries(seriesArray[0],barFormatterList[0]);
       mPlot.addSeries(seriesArray[1],barFormatterList[1]);
       mPlot.addSeries(seriesArray[2],barFormatterList[2]);
       mPlot.addSeries(seriesArray[3],barFormatterList[3]);

       mPlot.setDomainStep(StepMode.INCREMENT_BY_VAL,6);

       Paint whitePaint = new Paint();
       whitePaint.setColor(Color.BLACK);
       mPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setPaint(whitePaint);
        mPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.RIGHT).setPaint(whitePaint);
        mPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setPaint(whitePaint);
        mPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.TOP).setPaint(whitePaint);
        mPlot.getLegend().setAnchor(Anchor.RIGHT_BOTTOM);
        mPlot.getLegend().setTableModel(new DynamicTableModel(2, 2, TableOrder.COLUMN_MAJOR));
        //mPlot.getLegend().setSize(new Size(15,SizeMode.ABSOLUTE,2000,SizeMode.ABSOLUTE ));
        mPlot.getBorderPaint().setColor(Color.TRANSPARENT);
        mPlot.getBackgroundPaint().setColor(Color.TRANSPARENT);

       /*mPlot.getLegend().position(mPlot.getX(),HorizontalPositioning.ABSOLUTE_FROM_LEFT,
               mPlot.getY()-30,VerticalPositioning.ABSOLUTE_FROM_BOTTOM );*/





       /* mPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).
                setFormat(new GraphXLabelFormat(new String[]{"Test", "Test2"}));*/

       MyBarRenderer barRenderer = mPlot.getRenderer(MyBarRenderer.class);
       barRenderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_WIDTH, PixelUtils.dpToPix(240));
        //barRenderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_GAP, PixelUtils.dpToPix(5));
       barRenderer.setBarOrientation(BarRenderer.BarOrientation.SIDE_BY_SIDE);


     //  mPlot.setDomainLabel("test");



       ;


       /*
               EmbossMaskFilter emf = new EmbossMaskFilter(
                new float[]{1, 1, 1}, 0.4f, 10, 9.2f);

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
        sf4.getLabelPaint().setShadowLayer(3, 0, 0, Color.WHITE);
        sf4.getFillPaint().setMaskFilter(emf);
        sf4.getFillPaint().setColor(Color.DKGRAY);


        pie.addSegment(segments[0], sf1);
        pie.addSegment(segments[1], sf2);
        pie.addSegment(segments[2], sf3);
        pie.addSegment(segments[3], sf4);

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
        mPieRenderer = pie.getRenderer(CustomPieRenderer.class);

      mPieRenderer.setStartDegs(0);*/

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

             //mPieRenderer.setDonutSize(1.0f, PieRenderer.DonutMode.PIXELS);
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

   /* public void reducePieSize()
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

    }*/




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

    public void GoToLogCase()
    {
        Intent intent = new Intent(this, LogCaseActivity.class);
        intent.putExtra("diagnoses",mDiagnoses);
        intent.putExtra("signs", mSigns);
        intent.putExtra("species",mSpecies);
        intent.putExtra("mostLikelyDiagnosis", mMostLikelyDiagnosis);
        startActivity(intent);
    }


}


class MyBarFormatter extends BarFormatter {

    public MyBarFormatter(int fillColor, int borderColor) {
        super(fillColor, borderColor);
    }

    @Override
    public Class<? extends SeriesRenderer> getRendererClass() {
        return MyBarRenderer.class;
    }

    @Override
    public SeriesRenderer doGetRendererInstance(XYPlot plot) {
        return new MyBarRenderer(plot);
    }
}

class MyBarRenderer extends BarRenderer<MyBarFormatter> {

    public MyBarRenderer(XYPlot plot) {
        super(plot);
    }

    /**
     * Implementing this method to allow us to inject our
     * special selection getFormatter.
     * @param index index of the point being rendered.
     * @param series XYSeries to which the point being rendered belongs.
     * @return
     */
    @Override
    public MyBarFormatter getFormatter(int index, XYSeries series) {
       /* if (selection != null &&
                selection.second == series &&
                selection.first == index) {
            return selectionFormatter;
        } else {*/
            return getFormatter(series);
        }
    }


