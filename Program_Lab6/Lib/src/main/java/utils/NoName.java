package utils;

import labWork.*;

import java.io.Serializable;

public class NoName implements Serializable {
    private static final long serialVersionUID = 1L;
    String string;
    LabWork labWork;

    public NoName(String string) {
        this.string = string;
    }

    public NoName(String string, LabWork labWork) {
        this.string = string;
        this.labWork = labWork;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setLabWork(LabWork labWork) {
        this.labWork = labWork;
    }

    public LabWork getLabWork() {
        return labWork;
    }
}
