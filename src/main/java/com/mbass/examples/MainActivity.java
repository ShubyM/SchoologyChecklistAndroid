package com.mbass.examples;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.LocalDateTime;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    private TextView status;
    private Button updateButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY);
        status = findViewById(R.id.status);
        List<Course> courses = getUserData("16053918");

        for (Course course : courses) {
            Backendless.Data.of(Course.class).save(course, new AsyncCallback<Course>() {
                @Override
                public void handleResponse(Course response) {
                    Log.i("REQUEST", "Stuff was added");
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.i("ERROR", fault.toString());
                }
            });
        }
    }

    private List<Course> getUserData(String uid) {
        List<Course> courses = new ArrayList<>();
        String rawCourseData = getRawData("users/" + uid + "/sections");
        try {
            JSONObject sections = new JSONObject(rawCourseData);
            JSONArray course_data = sections.getJSONArray("section");
            for (int i = 0; i < course_data.length(); i++) {
                Course object = new Course();
                JSONObject course = course_data.getJSONObject(i);

                object.setName(course.get("course_title").toString());
                object.setID(course.get("id").toString());

                String rawAssignments = getRawData("sections/" + object.getID() + "/assignments");
                //object.setAssignments(parseAssignments(rawAssignments));
                parseAssignments(rawAssignments);
                courses.add(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return courses;
    }

    private String getRawData(String URL) {
        String rawData = "";
        try {
            Data data = new Data(URL);
            Thread request = new Thread(data);
            request.start();
            request.join();
            rawData = data.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rawData;
    }

//    public String parseAssignments(String data) {
//        String assignments = "";
//        try {
//            JSONArray assignment_list = new JSONArray(data.substring(14, data.length()));
//            for (int i = 0; i < assignment_list.length(); i++) {
//                JSONObject assignment_data = assignment_list.getJSONObject(i);
//                assignments += (assignment_data.get("title").toString() +",");
//            }
//            Log.i("107", assignments);
//        }
//
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return assignments;
//    }

    public List<Assignment> parseAssignments(String data) {
        List<Assignment> assignments = new ArrayList<>();
        try {
            JSONArray assignment_list = new JSONArray(data.substring(14, data.length()));
            for (int i = 0; i < assignment_list.length(); i++) {
                JSONObject assignment_data = assignment_list.getJSONObject(i);
                Assignment assignment = new Assignment();
                String rawDate = assignment_data.get("due").toString();
                Date date;

                if (!rawDate.equals("")) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
                    date = format.parse(rawDate);
                }


                else {
                    String curr  = LocalDateTime.now().toString();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd");
                    date = format.parse(curr);
                }

                Log.i("128", rawDate + " " + date.toString());
                assignment.setTitle(assignment_data.get("title").toString());
                assignment.setDueDate(date);
                assignment.setID(assignment_data.get("id").toString());
                assignment.setCompleted(false);
                assignments.add(assignment);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

//        for (int i = 0; i < assignments.size(); i++) {
//            Log.i("141", assignments.get(i).toString());
//        }
        return assignments;
    }


//    private static void setRelation(Course course, List assignments) {
//        Backendless.Data.of(Course.class).addRelation(course,
//                "assignment:Assignments:n", assignments,
//                new AsyncCallback() {
//                    @Override
//                    public void handleResponse() {
//
//                    }
//
//
//
//                    @Override
//                    public void handleFault(BackendlessFault fault) {
//                        Log.e("TESTING", fault.getMessage());
//                    }
//                });
//    }
}
                                    