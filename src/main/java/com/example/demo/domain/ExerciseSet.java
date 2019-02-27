package com.example.demo.domain;

import javax.persistence.*;


@Entity
public class ExerciseSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int reps;
    private int weight;
    private String notes;
    // for a particular exercise for a particular routine, a number of sets are performed
    // this describes the order of sets which are performed.
    private int setOrder;

    //todo set order
    // TODO exercise order

    //mapper http://www.kumaranuj.com/2013/05/jpa-2-understanding-relationships.html
    @OneToOne
    private Exercise exercise;

//    @ManyToOne(cascade = {CascadeType.ALL}, fetch= FetchType.EAGER)
    @ManyToOne
    private Routine routine;

    public ExerciseSet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public int getSetOrder() {
        return setOrder;
    }

    public void setSetOrder(int setOrder) {
        this.setOrder = setOrder;
    }

    @Override
    public String toString() {
        return "ExerciseSet{" +
                "id=" + id +
                ", reps=" + reps +
                ", weight=" + weight +
                ", notes='" + notes + '\'' +
                ", exercise=" + exercise +
                ", routine=" + routine.getId() +
                '}';
    }
}
