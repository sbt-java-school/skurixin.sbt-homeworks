package ru.sbt.javaschool.homeworks.hw6;

/**
 * Created by скурихин on 24.08.2016.
 */
public class ParentClass {
    private int parentField;
    public void ParentPublicMethod(){
        System.out.println("parentPublicMethod");
    }

    private int ParentPrivateMethod(){
        System.out.println("parentPrivateMethod");
        return this.hashCode();
    }

    public int getParentField() {
        return parentField;
    }
}
