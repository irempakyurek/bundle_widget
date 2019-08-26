package com.example.team.bundle_widget;

/**
 * Created by ASUS on 5.08.2019.
 */

public class Symbol {

    public String code;
    public String description;
    public String type;
    public String name;

    public String key;
    public String imageName;
    public String emoji;



    public Symbol(String code, String description, String type, String name,String key, String imageName, String emoji){
        this.code = code;
        this.description = description;
        this.type = type;
        this.name = name;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
