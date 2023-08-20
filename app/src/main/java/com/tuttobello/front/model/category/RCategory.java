package com.tuttobello.front.model.category;

import androidx.annotation.NonNull;

public class RCategory {

    public String categoryId;
    public String description;

    @NonNull
    @Override
    public String toString() {
        return description;
    }

}
