package com.cuichuang.common.bean;

/**
 * File descripition:
 *
 * @author cc
 * @date 2019/6/6
 */

public class Bean3 {

    /**
     * id : 6
     * name : 氨氮
     * unit : mg/L
     * db_field : nh3n
     * qa_ratio : true
     */

    private int id;
    private String name;
    private String unit;
    private String db_field;
    private boolean qa_ratio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDb_field() {
        return db_field;
    }

    public void setDb_field(String db_field) {
        this.db_field = db_field;
    }

    public boolean isQa_ratio() {
        return qa_ratio;
    }

    public void setQa_ratio(boolean qa_ratio) {
        this.qa_ratio = qa_ratio;
    }
}
