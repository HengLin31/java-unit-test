package pers.henglin.test.bean;

/**
 * Created by linheng on 25/04/2020.
 */
public class Data {
    private int id;
    private String col1;
    private String col2;

    public Data(int id, String col1, String col2) {
        this.id = id;
        this.col1 = col1;
        this.col2 = col2;
    }

    public int getId() {
        return id;
    }

    public String getCol1() {
        return col1;
    }

    public String getCol2() {
        return col2;
    }
}
