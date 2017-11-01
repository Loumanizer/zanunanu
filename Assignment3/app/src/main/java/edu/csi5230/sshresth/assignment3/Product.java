package edu.csi5230.sshresth.assignment3;

import android.graphics.Bitmap;
import android.net.Uri;

public class Product {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String name = null;
    private String price = null;
    private String  image = null;

    public Product(String name, String price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
