package com.sinopec.opc.bean;

/**
 * @Author : Pnoker
 * @E-mail : pnokers@gmail.com
 * @Date : 2017年07月16日 16:13
 * @Description : opcXml ...
 */
public class PointValue {
    private String pointName;
    private String tableName;
    private String idColumn;
    private String id;
    private String valueColumn;

    public PointValue(String pointName, String tableName, String idColumn, String id, String valueColumn) {
        this.pointName = pointName;
        this.tableName = tableName;
        this.idColumn = idColumn;
        this.id = id;
        this.valueColumn = valueColumn;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(String idColumn) {
        this.idColumn = idColumn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }
}
