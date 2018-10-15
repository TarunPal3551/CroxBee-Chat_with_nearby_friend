package com.croxbee.croxbee;

public class User_Profile_Prefences {
    String prefence_gender;
    int prefence_age_min;
    int prefence_age_max;
    int min_distance;
    int max_distance;
    String interest;

    public User_Profile_Prefences() {

    }

    public User_Profile_Prefences(String prefence_gender, int prefence_age_min, int prefence_age_max, int min_distance, int max_distance, String interest) {

        this.prefence_gender = prefence_gender;
        this.prefence_age_min = prefence_age_min;
        this.prefence_age_max = prefence_age_max;
        this.min_distance = min_distance;
        this.max_distance = max_distance;
        this.interest = interest;
    }

    public String getPrefence_gender() {
        return prefence_gender;
    }

    public void setPrefence_gender(String prefence_gender) {
        this.prefence_gender = prefence_gender;
    }

    public int getPrefence_age_min() {
        return prefence_age_min;
    }

    public void setPrefence_age_min(int prefence_age_min) {
        this.prefence_age_min = prefence_age_min;
    }

    public int getPrefence_age_max() {
        return prefence_age_max;
    }

    public void setPrefence_age_max(int prefence_age_max) {
        this.prefence_age_max = prefence_age_max;
    }

    public int getMin_distance() {
        return min_distance;
    }

    public void setMin_distance(int min_distance) {
        this.min_distance = min_distance;
    }

    public int getMax_distance() {
        return max_distance;
    }

    public void setMax_distance(int max_distance) {
        this.max_distance = max_distance;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
