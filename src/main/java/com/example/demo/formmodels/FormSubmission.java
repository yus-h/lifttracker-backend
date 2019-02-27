package com.example.demo.formmodels;

import java.util.List;

public class FormSubmission {

    private Long routineId;
    private String notes;
    private String date;
    private List<FormExercise> exercises;

    public Long getRoutineId() {
        return routineId;
    }

    public void setRoutineId(Long routineId) {
        this.routineId = routineId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<FormExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<FormExercise> exercises) {
        this.exercises = exercises;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}