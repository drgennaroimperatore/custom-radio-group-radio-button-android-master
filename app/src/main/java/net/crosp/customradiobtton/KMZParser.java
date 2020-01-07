package net.crosp.customradiobtton;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

    public void readDocument(XmlPullParser parser) throws XmlPullParserException, IOException {
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
                readFolder(parser);

            }
            else
            {
                skip(parser);
            }

        }
    }

    public void readFolder(XmlPullParser parser) throws XmlPullParserException, IOException
    {
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
                readPlacemark(parser);

            }
            else
            {
                skip(parser);
            }

        }

    }

    public void readPlacemark(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        parser.require(XmlPullParser.START_TAG, ns, "Placemark");
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
            }

            String name = parser.getName();
            if(parser.getName().equals("ExtendedData"))
            {
                readExtendedData(parser);

            }
            else
            {
                skip(parser);
            }

        }

    }

    public void readExtendedData(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        parser.require(XmlPullParser.START_TAG, ns, "ExtendedData");
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
            }

            String name = parser.getName();
            if(parser.getName().equals("SchemaData"))
            {
                readSchemaData(parser);

            }
            else
            {
                skip(parser);
            }

        }

    }

    public void readSchemaData(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        parser.require(XmlPullParser.START_TAG, ns, "SchemaData");
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
            }

            String name = parser.getName();
            if(parser.getName().equals("SimpleData"))
            {
                readSimpleData(parser);

            }
            else
            {
                skip(parser);
            }

        }

    }

    public static class SimpleData
    {
        String nationName=null;
        String regionName=null;
        String districtName=null;
        String woredaName=null;

        private SimpleData(String nation, String region, String district, String woreda)
        {
            nationName = nation;
            regionName = region;
            districtName = district;
            woredaName= woreda;
        }

    }

    public  SimpleData readSimpleData(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "SimpleData");
        String nation = null;
        String region = null;
        String district = null;
        String woreda = null;

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }

                String name = parser.getName();
                if(name.equals("SimpleData"))
                {
                    nation = readText(parser);
                    region =parser.getAttributeValue(null, "NAME_1");
                    district= parser.getAttributeValue(null, "NAME_2");
                    woreda= parser.getAttributeValue(null, "NAME_3");
                }


            }

        }
        return new SimpleData(nation,region,district,woreda);
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


