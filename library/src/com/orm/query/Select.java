package com.orm.query;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class Select<T extends SugarRecord> {

    private Class<T> record;
    private String[] arguments;
    private String whereClause = "";
    private String orderBy;
    private String groupBy;
    private String limit;
    private String offset;

    private List<Object> args = new ArrayList<Object>();

    public Select(Class<T> record) {
        this.record = record;
    }

    public static <T extends SugarRecord> Select from(Class<T> record) {
        return new Select<T>(record);
    }

    public Select orderBy(String prop) {
        this.orderBy = prop;
        return this;
    }

    public Select groupBy(String prop) {
        this.groupBy = prop;
        return this;
    }

    public Select limit(String limit) {
        this.limit = limit;
        return this;
    }



    public Select where(String whereClause) {
        this.whereClause = whereClause;
        return this;
    }

    public Select where(Condition... condition) {

        mergeConditions(condition, Condition.Type.AND);

        return this;
    }

    private void mergeConditions(Condition[] conditions, Condition.Type type) {
        for (Condition condition : conditions) {

            if (!"".equals(whereClause)) {
                whereClause += " " + type.name() + " ";
            }

            if(Condition.Check.LIKE.equals(condition.getCheck()) || Condition.Check.NOT_LIKE.equals(condition.getCheck())){

                whereClause += condition.getProperty() + condition.getCheckSymbol() + "'" + condition.getValue().toString() +"'";

            }else{

                whereClause += condition.getProperty() + condition.getCheckSymbol() + "? ";
                args.add(condition.getValue());
            }

        }
    }

    public Select whereOr(Condition... args) {
        mergeConditions(args, Condition.Type.OR);
        return this;
    }

    public Select and(Condition... args) {
        mergeConditions(args, Condition.Type.AND);
        return this;
    }

    public Select or(Condition... args) {
        mergeConditions(args, Condition.Type.OR);
        return this;
    }

    public Select where(String whereClause, String[] args) {
        this.whereClause = whereClause;
        this.arguments = args;
        return this;
    }

    public List<T> list() {

        if(arguments == null) arguments = convertArgs(args);

        return T.find(record, whereClause, arguments, groupBy, orderBy, limit);

    }

    public T first() {

        if(arguments == null) arguments = convertArgs(args);

        List<T> list = T.find(record, whereClause, arguments, groupBy, orderBy, "1");
        return list.size() > 0 ? list.get(0) : null;
    }
    
    String toSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM");

        sql.append("FROM ");

        sql.append(SugarRecord.getTableName(this.record) + " ");

        if (whereClause != null) {
            sql.append("WHERE " + whereClause + " ");
        }

        if (orderBy != null) {
            sql.append("ORDER BY " + orderBy + " ");
        }

        if (limit != null) {
            sql.append("LIMIT " + limit + " ");
        }

        if (offset != null) {
            sql.append("OFFSET " + offset + " ");
        }

        return sql.toString();
    }

    String getWhereCond() {
        return whereClause;
    }

    String[] getArgs(){
        return convertArgs(args);
    }

    private String[] convertArgs(List<Object> argsList) {
        String[] argsArray = new String[argsList.size()];

        for(int i=0; i< argsList.size();i++){
             argsArray[i] = argsList.get(i).toString();
        }

        return argsArray;
    }
}
