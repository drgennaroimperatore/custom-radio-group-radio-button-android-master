package net.crosp.customradiobtton;
import android.content.Context;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import net.crosp.customradiobtton.CustomPieRenderer;
import com.androidplot.pie.PieChart;

public class CustomPieSegmentFormatter extends SegmentFormatter {

   public CustomPieSegmentFormatter(Integer j, Integer k)
   {
       super (j,k);
   }

  public CustomPieSegmentFormatter (Context context, int xmlCfgId) {
      super(context, xmlCfgId);
  }

    @Override
    public Class<CustomPieRenderer> getRendererClass() {
        return CustomPieRenderer.class;
    }

    @Override
    public CustomPieRenderer doGetRendererInstance( PieChart plot) {
        return new CustomPieRenderer(plot);
    }

}
