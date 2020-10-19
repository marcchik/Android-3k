package com.smuzdev.lab_04.auxiliary;

import android.os.Environment;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static android.content.ContentValues.TAG;

public class Json {

    static File file = new File(Environment.getExternalStorageDirectory() + "/LAB_04/StudentsList.txt");

    public static void Serialize(Users users) {

        Log.d("LAB4", Environment.getExternalStorageDirectory() + "/LAB_04/StudentsList.txt");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        try {
            objectMapper.writeValue(file, users);
            Log.d(TAG, "Serialized successful.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Users Deserialize() {

        Users users = new Users();
        ObjectMapper objectMapper = new ObjectMapper();

        //Set pretty printing of json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        try {
            FileReader fr = new FileReader(file);
            users = objectMapper.readValue(fr, Users.class);
            Log.d("LAB4", "DeSerialized successful. Result:");
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

}
