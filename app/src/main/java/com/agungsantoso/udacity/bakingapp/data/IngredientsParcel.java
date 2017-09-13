package com.agungsantoso.udacity.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asanpra on 13/09/2017.
 */

public class IngredientsParcel
        implements Parcelable {

    //http://www.vogella.com/tutorials/AndroidParcelable/article.html
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public IngredientsParcel createFromParcel(Parcel in) {
            return new IngredientsParcel(in);
        }

        public IngredientsParcel[] newArray(int size) {
            return new IngredientsParcel[size];
        }
    };

    double quantity;
    String measure;
    String ingredients;

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public IngredientsParcel(
            double quantity,
            String measure,
            String ingredients) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredients = ingredients;
    }

    public IngredientsParcel(Parcel in) {
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.ingredients = in.readString();
    }

    @Override
    public void writeToParcel(
            Parcel dest,
            int flags
    ) {
        dest.writeDouble(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredients);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "parcel";
    }
}
