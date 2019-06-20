package de.tenolo.weddingplanner;

import android.os.Parcel;
import android.os.Parcelable;

public class AllgemeinesObject implements Parcelable {
    String types;
    String[] hints;
    String listenname;
    int anzahlFelder;
    String name;
    String specials;
    float[] weights;
    boolean[] editable;
    boolean[] shown;
    String groupName;

    private AllgemeinesObject(Parcel in) {
        types = in.readString();
        hints = in.createStringArray();
        listenname = in.readString();
        anzahlFelder = in.readInt();
        name = in.readString();
        specials = in.readString();
        weights = in.createFloatArray();
        editable = in.createBooleanArray();
        shown = in.createBooleanArray();
        groupName = in.readString();
    }

    public static final Creator<AllgemeinesObject> CREATOR = new Creator<AllgemeinesObject>() {
        @Override
        public AllgemeinesObject createFromParcel(Parcel in) {
            return new AllgemeinesObject(in);
        }

        @Override
        public AllgemeinesObject[] newArray(int size) {
            return new AllgemeinesObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(types);
        dest.writeStringArray(hints);
        dest.writeString(listenname);
        dest.writeInt(anzahlFelder);
        dest.writeString(name);
        dest.writeString(specials);
        dest.writeFloatArray(weights);
        dest.writeBooleanArray(editable);
        dest.writeBooleanArray(shown);
        dest.writeString(groupName);
    }

    public AllgemeinesObject(String types, String[] hints, String listenname, int anzahlFelder, String name, String specials, String groupName){
        this.types = types;
        this.hints = hints;
        this.listenname = listenname;
        this.anzahlFelder = anzahlFelder;
        this.name = name;
        this.specials = specials;
        this.groupName = groupName;
    }

    public AllgemeinesObject(String types, String[] hints, String listenname, int anzahlFelder, String name, String specials, float[] weights, boolean[] editable, boolean[] shown, String groupName){
        this.types = types;
        this.hints = hints;
        this.listenname = listenname;
        this.anzahlFelder = anzahlFelder;
        this.name = name;
        this.specials = specials;
        this.weights = weights;
        this.editable = editable;
        this.shown = shown;
        this.groupName = groupName;
    }
}
