package com.example.middleexpansionrecycler.bean;

import androidx.annotation.NonNull;

public class ItemBean {
    public String title;
    public String link;

    @NonNull
    @Override
    public String toString() {
        return "{\"title\" :\"" + title + "\",\n" + "\"link\" : \"" + link + "\"},\n";
    }
}
