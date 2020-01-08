package net.crosp.customradiobtton;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Xml;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class KMZParser extends AsyncTask<InputStream, Integer, Integer> {

private static final String ns=null;
private MainActivity mMainActivity =null;

    public KMZParser(MainActivity mainActivity) {

        super();
        mMainActivity = mainActivity;
    }

    @Override
    protected Integer doInBackground(InputStream... streams) {


        try {
            parseKMZ(streams[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPreExecute()
    {
        ((ProgressBar)mMainActivity.findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer o)
    {
        ((ProgressBar)mMainActivity.findViewById(R.id.progressBar)).setVisibility(View.INVISIBLE);
        super.onPostExecute(o);
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
    }

    private List parseKMZ(InputStream in) throws IOException, XmlPullParserException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readKML(parser);
        }


        finally {
            in.close();
        }
    }

    private List readKML(XmlPullParser parser) throws XmlPullParserException, IOException
    {

        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "kml");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            // Starts by looking for the entry tag
            if (name.equals("Document")) {
              readDocument(parser);
            } else {
                skip(parser);
            }
        }
        return entries;

    }

    public List<Folder> readDocument(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Folder> folders = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "Document");
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
            }

            String name = parser.getName();
            if(name.equals("Folder"))
            {
              folders.add(readFolder(parser));

            }
            else
            {
                skip(parser);
            }

        }
        return folders;


    }

   public class Folder
    {
        List<Placemark> mPlaceMarks;

        public Folder(List<Placemark> placemarks) {mPlaceMarks = placemarks;}
        public List<Placemark> getmPlaceMarks(){return mPlaceMarks;}

    }

    public Folder readFolder(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        Folder folder = null;

        List<Placemark> placemarks = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, "Folder");
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
            }

            String name = parser.getName();



            if(parser.getName().equals("Placemark"))
            {
              placemarks.add(readPlacemark(parser));

            }



        }

        return new Folder(placemarks);



    }

    class Placemark
    {
        ExtendedData mExtendedData;

        public ExtendedData getExtendedData() {
                return mExtendedData;
        }

        public Placemark( ExtendedData extendedData) {mExtendedData = extendedData;}
    }

    public Placemark readPlacemark(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        Placemark placemark = null;
        parser.require(XmlPullParser.START_TAG, ns, "Placemark");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if(parser.getName().equals("ExtendedData"))
            {
               placemark = (new Placemark( readExtendedData(parser)));

            }
            else
                skip(parser);


        }

        return placemark;

    }

    public class ExtendedData
    {
        List<SimpleData> mSchemaData;

        public ExtendedData(List<SimpleData> schema)
        {
            mSchemaData = schema;
        }

        public List<SimpleData> getSchemaData() {return mSchemaData;}

    }

    public ExtendedData readExtendedData(XmlPullParser parser) throws XmlPullParserException, IOException
    {

        parser.require(XmlPullParser.START_TAG, ns, "ExtendedData");
       ExtendedData extendedData = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if(parser.getName().equals("SchemaData"))
            {
              extendedData = new ExtendedData( readSchemaData(parser));

            }
            else
            {
                skip(parser);
            }

        }

        return  extendedData;

    }

    HashMap<String, String> mDistrictsinRegion= new HashMap<String, String>();
    HashMap<String, String> mWoredasinDistricts= new HashMap<String, String>();






    public List<SimpleData> readSchemaData(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        parser.require(XmlPullParser.START_TAG, ns, "SchemaData");
       List<SimpleData> schemaData = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
           int index=0;



            String name = parser.getName();
            if(parser.getName().equals("SimpleData"))
            {
             schemaData.add(  readSimpleData(parser));




            }
            else
            {
                skip(parser);
            }

        }
        return schemaData;

    }

    public static class SimpleData
    {
        String attributeName=null;
        String textContent=null;


        private SimpleData(String attributeName, String text )
        {
            this.attributeName = attributeName;
            textContent =text;

        }
         public String getAttributeName() {return attributeName;}
         public String getText() {return textContent;}

    }

    //Dictionary<String,Dictionary<String,Dictionary<String,String>>> regionalData = null;




    public  SimpleData readSimpleData(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "SimpleData");


        String attributeName=null;
        String text = null;





                    attributeName= parser.getAttributeValue(null, "name");
                    text = readText(parser);

                 SimpleData data = new SimpleData(attributeName, text);


        return data;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


}


