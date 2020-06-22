package com.jundev.weightloss.POJOS;

import com.jundev.weightloss.POJOS.interactive.AllDiets;

import java.io.Serializable;
import java.util.List;

public class Global implements Serializable {
    private List<Section> sectionsArray;
    private String name;
    private AllDiets allDiets;

    public Global() {
    }

    public Global(List<Section> sectionsArray, String name, AllDiets allDiets) {
        this.sectionsArray = sectionsArray;
        this.name = name;
        this.allDiets = allDiets;
    }

    public List<Section> getSectionsArray() {
        return sectionsArray;
    }

    public void setSectionsArray(List<Section> sectionsArray) {
        this.sectionsArray = sectionsArray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AllDiets getAllDiets() {
        return allDiets;
    }

    public void setAllDiets(AllDiets allDiets) {
        this.allDiets = allDiets;
    }
}
