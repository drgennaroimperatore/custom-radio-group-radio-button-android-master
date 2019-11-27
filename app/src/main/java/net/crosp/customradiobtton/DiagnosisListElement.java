package net.crosp.customradiobtton;

public class DiagnosisListElement {
    String mName;
    float mPercentage;

    public String getName() {return mName;}
    public float getPercentage() {return mPercentage;}

    public void setName(String name) {mName = name;}
    public void setPercentage(float percentage) {mPercentage = percentage; }

    public DiagnosisListElement (String n, float p)
    {
        mName= n;
        mPercentage =p;
    }
}
