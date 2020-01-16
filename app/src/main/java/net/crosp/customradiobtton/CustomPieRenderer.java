package net.crosp.customradiobtton;

import android.graphics.*;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import net.crosp.customradiobtton.CustomPieSegmentFormatter;

import com.androidplot.exception.PlotRenderException;
import com.androidplot.ui.SeriesBundle;
import com.androidplot.ui.SeriesRenderer;
import com.androidplot.ui.RenderStack;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CustomPieRenderer extends PieRenderer  {

    private static final float FULL_PIE_DEGS = 360f;
    private static final float HALF_PIE_DEGS = 180f;

    // starting angle to use when drawing the first radial line of the first segment.
    private float startDegs = 0;

    // number of degrees to extend from startDegs; can be used to "shape" the pie chart.
    private float extentDegs = FULL_PIE_DEGS;

    // TODO: express donut in units other than px.
    private float donutSize = 0.5f;
    private DonutMode donutMode = DonutMode.PERCENT;

    public enum DonutMode {
        PERCENT,
        PIXELS
    }

    public CustomPieRenderer(PieChart plot)
    {
        super(plot);
    }

    @Override
    public void onRender(Canvas canvas, RectF plotArea, Segment series, SegmentFormatter formatter,
                         RenderStack stack) throws PlotRenderException {

        // This renderer renders all series in one shot, so exclude any remaining series
        // from causing subsequent invocations of onRender:
        stack.disable(getClass());

        float radius = getRadius(plotArea);
        PointF origin = new PointF(plotArea.centerX(), plotArea.centerY());

        double[] values = getValues();
        double [] roundedValues = new double[values.length];
        int index=0;
        for(double v :values)
        {
            BigDecimal bd = BigDecimal.valueOf(v);
            bd = bd.setScale(0, RoundingMode.HALF_UP);
            roundedValues[index] = bd.doubleValue();
            index++;
        }
        values= roundedValues;

        double scale = calculateScale(values);
        float offset = degsToScreenDegs(startDegs);

        RectF rec = new RectF(origin.x - radius, origin.y - radius, origin.x + radius,
                origin.y + radius);

        int i = 0;
        for (SeriesBundle<Segment, ? extends SegmentFormatter> sfPair : getSeriesAndFormatterList()) {
            float lastOffset = offset;
            float sweep = (float) (scale * (values[i]) * extentDegs);
            offset += sweep;
            String title = String.format("%d", (int)values[i]) + "%";
            Segment segment = sfPair.getSeries();
            segment.setTitle( segment.getTitle() + " (" + title + ")");
            drawSegment(canvas, rec, segment, sfPair.getFormatter(), radius, lastOffset,
                    sweep);
            i++;
        }
    }
    @Override
    protected void drawSegment(Canvas canvas, RectF bounds, Segment seg, SegmentFormatter f,
                               float rad, float startAngle, float sweep) {
        canvas.save();
        startAngle = startAngle + f.getRadialInset();
        sweep = sweep - (f.getRadialInset() * 2);

        // midpoint angle between startAngle and endAngle
        final float halfSweepEndAngle = startAngle + (sweep / 2);

        PointF translated = calculateLineEnd(
                bounds.centerX(), bounds.centerY(), f.getOffset(), halfSweepEndAngle);

        final float cx = translated.x;
        final float cy = translated.y;

        float donutSizePx;
        switch (donutMode) {
            case PERCENT:
                donutSizePx = donutSize * rad;
                break;
            case PIXELS:
                donutSizePx = (donutSize > 0) ? donutSize : (rad + donutSize);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported DonutMde: " + donutMode);
        }

        final float outerRad = rad - f.getOuterInset();
        final float innerRad = donutSizePx == 0 ? 0 : donutSizePx + f.getInnerInset();

        // do we have a segment of less than 100%
        if (Math.abs(sweep - extentDegs) > Float.MIN_VALUE) {
            // vertices of the first radial:
            PointF r1Outer = calculateLineEnd(cx, cy, outerRad, startAngle);
            PointF r1Inner = calculateLineEnd(cx, cy, innerRad, startAngle);

            // vertices of the second radial:
            PointF r2Outer = calculateLineEnd(cx, cy, outerRad, startAngle + sweep);
            PointF r2Inner = calculateLineEnd(cx, cy, innerRad, startAngle + sweep);

            Path clip = new Path();

            // outer arc:
            // leave plenty of room on the outside for stroked borders;
            // necessary because the clipping border is ugly
            // and cannot be easily anti aliased.  Really we only care about masking off the
            // radial edges.
            clip.arcTo(new RectF(bounds.left - outerRad,
                            bounds.top - outerRad,
                            bounds.right + outerRad,
                            bounds.bottom + outerRad),
                    startAngle, sweep);
            clip.lineTo(cx, cy);
            clip.close();
            canvas.clipPath(clip);

            Path p = new Path();

            // outer arc:
            p.arcTo(new RectF(
                            cx - outerRad,
                            cy - outerRad,
                            cx + outerRad,
                            cy + outerRad)
                    , startAngle, sweep);
            p.lineTo(r2Inner.x, r2Inner.y);

            // inner arc:
            // sweep back to original angle:
            p.arcTo(new RectF(
                            cx - innerRad,
                            cy - innerRad,
                            cx + innerRad,
                            cy + innerRad),
                    startAngle + sweep, -sweep);

            p.close();

            // fill segment:
            canvas.drawPath(p, f.getFillPaint());

            // draw radial lines
            canvas.drawLine(r1Inner.x, r1Inner.y, r1Outer.x, r1Outer.y, f.getRadialEdgePaint());
            canvas.drawLine(r2Inner.x, r2Inner.y, r2Outer.x, r2Outer.y, f.getRadialEdgePaint());
        } else {
            canvas.save();
            Path chart = new Path();
            chart.addCircle(cx, cy, outerRad, Path.Direction.CW);
            Path inside = new Path();
            inside.addCircle(cx, cy, innerRad, Path.Direction.CW);
            canvas.clipPath(inside, Region.Op.DIFFERENCE);
            canvas.drawPath(chart, f.getFillPaint());
            canvas.restore();
        }

        // draw inner line:
        canvas.drawCircle(cx, cy, innerRad, f.getInnerEdgePaint());


        // draw outer line:
        canvas.drawCircle(cx, cy, outerRad, f.getOuterEdgePaint());
        canvas.restore();

        PointF labelOrigin = calculateLineEnd(cx, cy,
                (outerRad - ((outerRad - innerRad) / 2)), halfSweepEndAngle);

        // TODO: move segment labelling outside the segment drawing loop
        // TODO: so that the labels will not be clipped by the edge of the next
        // TODO: segment being drawn.
       /* if (f.getLabelPaint() != null) {
            PointF r1Outer = calculateLineEnd(cx, cy, outerRad, startAngle);
            PointF r1Inner = calculateLineEnd(cx, cy, innerRad, startAngle);

            PointF r2Inner = calculateLineEnd(cx, cy, innerRad, startAngle + sweep);
            PointF r2Outer = calculateLineEnd(cx, cy, outerRad, startAngle + sweep);
            Path textPath = new Path();
            textPath.arcTo(new RectF(
                            (cx - outerRad),
                            cy - outerRad,
                            cx + outerRad,
                            cy + outerRad)
                    , startAngle, sweep);
            textPath.close();

            float xLineEnd = r2Outer.x;
            float yLineEnd = r2Outer.y-40;
            int lineSize =60;

            if(startAngle>=0 && startAngle<=180)
            {
                xLineEnd = r2Outer.x-lineSize;
                yLineEnd = r2Outer.y;

            }
            if(startAngle>180 && startAngle<=270)
            {
                xLineEnd = r2Outer.x;
                yLineEnd = r2Outer.y-lineSize;
            }
            if(startAngle>270 && startAngle<=360)
            {
                xLineEnd = r2Outer.x+lineSize;
                yLineEnd = r2Outer.y;

            }
            Paint p = f.getFillPaint();
            p.setTextSize(32.0f);
           String lab= seg.getTitle().split("\\(")[1].replace(")","");
             canvas.drawText(lab, xLineEnd, yLineEnd, p);


            canvas.drawLine(
                    r2Outer.x, r2Outer.y,
                      xLineEnd
                    , yLineEnd
                    , f.getFillPaint());
            //textPath.lineTo();



           // textPath.lineTo(r2Inner.x, r2Inner.y);
          //  canvas.drawTextOnPath(seg.getTitle(), textPath, 5.0f,  1.0f, f.getLabelPaint());

            drawSegmentLabel(canvas, labelOrigin, seg, f);
        }*/
    }

    @Override
    protected void drawSegmentLabel(Canvas canvas, PointF origin,
                                    Segment seg, SegmentFormatter f) {
        int lineSize = 70;


       // canvas.drawLine(origin.x, origin.y, origin.x+lineSize, origin.y-lineSize, f.getFillPaint());

       // canvas.drawLine(20, 0, 0, 20, paint);
    }
}
