package com.example.demo.formmodels;

import java.util.List;


public class FormExercise {

    private long exerciseId;
    private String exerciseName;
    private List<FormWeightAndReps> weightAndReps;

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public List<FormWeightAndReps> getWeightAndReps() {
        return weightAndReps;
    }

    public void setWeightAndReps(List<FormWeightAndReps> weightAndReps) {
        this.weightAndReps = weightAndReps;
    }
}