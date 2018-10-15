package com.croxbee.croxbee;

public class User_Complete_Profile {
    String name;
    String firstName;
    String lastName;
    String gender;
    String birthday;
    String hometown;
    String fb_user_id;
    String email;
    String profile_picture;
    String firebase_user_id;
    int age;
    String job;
    String employer;
    String school_college;
    String aboutme;

    public User_Complete_Profile(String name, String firstName, String lastName, String gender, String birthday, String hometown, String fb_user_id, String email, String profile_picture, String firebase_user_id, int age, String job, String employer, String school_college, String aboutme) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.hometown = hometown;
        this.fb_user_id = fb_user_id;
        this.email = email;
        this.profile_picture = profile_picture;
        this.firebase_user_id = firebase_user_id;
        this.age = age;
        this.job = job;
        this.employer = employer;
        this.school_college = school_college;
        this.aboutme = aboutme;
    }

    public User_Complete_Profile() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getFb_user_id() {
        return fb_user_id;
    }

    public void setFb_user_id(String fb_user_id) {
        this.fb_user_id = fb_user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getFirebase_user_id() {
        return firebase_user_id;
    }

    public void setFirebase_user_id(String firebase_user_id) {
        this.firebase_user_id = firebase_user_id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getSchool_college() {
        return school_college;
    }

    public void setSchool_college(String school_college) {
        this.school_college = school_college;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }
}
