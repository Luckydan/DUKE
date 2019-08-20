package com.letcode.poi;

import java.util.List;

/**
 * 数据表实体类
 *
 */
public class TableInfo {
    private String tableName;
    private String tableName_CN;
    private List<String> columnName;
    private List<String> columnName_CN;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName_CN() {
        return tableName_CN;
    }

    public void setTableName_CN(String tableName_CN) {
        this.tableName_CN = tableName_CN;
    }

    public List<String> getColumnName() {
        return columnName;
    }

    public void setColumnName(List<String> columnName) {
        this.columnName = columnName;
    }

    public List<String> getColumnName_CN() {
        return columnName_CN;
    }

    public void setColumnName_CN(List<String> columnName_CN) {
        this.columnName_CN = columnName_CN;
    }
}
