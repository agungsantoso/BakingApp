package com.agungsantoso.udacity.bakingapp.data;

import java.util.List;

/**
 * Created by agung.santoso on 9/11/2017.
 */

public class Recipe {

    private int id;
    private String name;
    private List<Ingredients> ingredients;
    private List<Steps> steps;
    private int servings;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public String getImage() { return image; }

    public static class Ingredients {
        private double quantity;
        private String measure;
        private String ingredient;

        public double getQuantity() {
            return quantity;
        }


        public String getMeasure() {
            return measure;
        }


        public  String getIngredient() {
            return ingredient;
        }

    }

    public static class Steps {
        private String id;
        private String shortDescription;
        private String description;
        private String videoURL;
        private String thumbnailURL;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShortDescription() {
            return shortDescription;
        }


        public String getDescription() {
            return description;
        }


        public String getVideoURL() {
            return videoURL;
        }


        public String getThumbnailURL() {
            return thumbnailURL;
        }

    }
}
