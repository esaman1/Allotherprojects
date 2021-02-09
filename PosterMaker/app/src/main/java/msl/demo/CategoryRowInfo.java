package msl.demo;

public class CategoryRowInfo {
    private int CATEGORY_ID = 0;
    private String CATEGORY_NAME = "";
    private int SEQUENCE = 0;
    private int TOTAL_ITEMS = 0;

    public CategoryRowInfo(String str, int i, int i2) {
        this.CATEGORY_NAME = str;
        this.SEQUENCE = i;
        this.TOTAL_ITEMS = i2;
    }

    public int getCATEGORY_ID() {
        return this.CATEGORY_ID;
    }

    public void setCATEGORY_ID(int i) {
        this.CATEGORY_ID = i;
    }

    public String getCATEGORY_NAME() {
        return this.CATEGORY_NAME;
    }

    public void setCATEGORY_NAME(String str) {
        this.CATEGORY_NAME = str;
    }

    public int getSEQUENCE() {
        return this.SEQUENCE;
    }

    public void setSEQUENCE(int i) {
        this.SEQUENCE = i;
    }

    public int getTOTAL_ITEMS() {
        return this.TOTAL_ITEMS;
    }

    public void setTOTAL_ITEMS(int i) {
        this.TOTAL_ITEMS = i;
    }
}
