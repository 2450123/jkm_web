package com.example.jkm_web.model;

public class Class {
    private String classId;
    private String name;
    private String college;
    private String professional;
    private String teacherId;

    @Override
    public String toString() {
        return "Class{" +
                "classId='" + classId + '\'' +
                ", name='" + name + '\'' +
                ", college='" + college + '\'' +
                ", professional='" + professional + '\'' +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
