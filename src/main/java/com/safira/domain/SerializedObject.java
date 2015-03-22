package com.safira.domain;

/**
 * Class intended to store recieved serialized objects..
 */
public class SerializedObject {
    private String serializedObject;

    public SerializedObject(){
    }

    public SerializedObject(String serializedObject) {
        this.serializedObject = serializedObject;
    }

    public String getSerializedObject() {
        return serializedObject;
    }
}