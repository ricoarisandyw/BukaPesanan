package com.example.adiputra.bukapesanan;

/**
 * Created by adiputra on 5/11/2017.
 */

public class Person {
    private String name;
    private String age;
    private int photoId;

    Person(String name, String age, int photoId) {
        this.setName(name);
        this.setAge(age);
        this.setPhotoId(photoId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }


    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.

}
