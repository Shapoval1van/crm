package com.becomejavasenior.controller;

import com.becomejavasenior.model.Tag;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class InputDataManager {

    public static List<Tag> stringToTags(String tagsString) {
        List<Tag> tags = new ArrayList<>();
        Tag tag;
        for (String tagName : tagsString.split(",")) {
            tagName = tagName.trim();
            tag = new Tag();
            tag.setName(tagName);
            tags.add(tag);
        }
        return tags;
    }
}
