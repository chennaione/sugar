package com.orm.query;

import com.orm.SugarRecord;
import com.orm.util.NamingHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Select<T> implements Iterable {

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

    public static <T> Select<T> from(Class<T> record) {
        return new Select<T>(record);
    }

    public Select<T> orderBy(String prop) {
        this.orderBy = prop;
        return this;
    }

    public Select<T> groupBy(String prop) {
        this.groupBy = prop;
        return this;
    }

    public Select<T> limit(String limit) {
        this.limit = limit;
        return this;
    }

    public Select<T> where(String whereClause) {
        this.whereClause = whereClause;
        return this;
    }

    public Select<T> where(Condition... condition) {

        mergeConditions(condition, Condition.Type.AND);

        return this;
    }

    private void mergeConditions(Condition[] conditions, Condition.Type type) {
        StringBuilder toAppend = new StringBuilder("");
        for (Condition condition : conditions) {
            if (toAppend.length() != 0) {
                toAppend.append(" ").append(type.name()).append(" ");
            }

            if (Condition.Check.LIKE.equals(condition.getCheck()) ||
                    Condition.Check.NOT_LIKE.equals(condition.getCheck())) {
                toAppend
                    .append(condition.getProperty())
                    .append(condition.getCheckSymbol())
                    .append("'")
                    .append(condition.getValue().toString())
                    .append("'");

            } else {
                toAppend
                    .append(condition.getProperty())
                    .append(condition.getCheckSymbol())
                    .append("? ");
                args.add(condition.getValue());
            }
        }
        
        if (!"".equals(whereClause)) {
            whereClause += " " + type.name() + " ";
        }

        whereClause += "(" + toAppend + ")";
    }

    public Select<T> whereOr(Condition... args) {
        mergeConditions(args, Condition.Type.OR);
        return this;
    }

    public Select<T> and(Condition... args) {
        mergeConditions(args, Condition.Type.AND);
        return this;
    }

    public Select<T> or(Condition... args) {
        mergeConditions(args, Condition.Type.OR);
        return this;
    }

    public Select<T> where(String whereClause, String[] args) {
        this.whereClause = whereClause;
        this.arguments = args;
        return this;
    }

    public List<T> list() {
        if (arguments == null) {
            arguments = convertArgs(args);
        }

        return SugarRecord.find(record, whereClause, arguments, groupBy, orderBy, limit);
    }
    
    public long count() {
        if (arguments == null) {
            arguments = convertArgs(args);
        }
    	
        return SugarRecord.count(record, whereClause, arguments, groupBy, orderBy, limit);
    }

    public T first() {
        if (arguments == null) {
            arguments = convertArgs(args);
        }

        List<T> list = SugarRecord.find(record, whereClause, arguments, groupBy, orderBy, "1");
        return list.size() > 0 ? list.get(0) : null;
    }
    
    String toSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ").append(NamingHelper.toSQLName(this.record)).append(" ");

        if (whereClause != null) {
            sql.append("WHERE ").append(whereClause).append(" ");
        }

        if (orderBy != null) {
            sql.append("ORDER BY ").append(orderBy).append(" ");
        }

        if (limit != null) {
            sql.append("LIMIT ").append(limit).append(" ");
        }

        if (offset != null) {
            sql.append("OFFSET ").append(offset).append(" ");
        }

        return sql.toString();
    }

    String getWhereCond() {
        return whereClause;
    }

    String[] getArgs() {
        return convertArgs(args);
    }

    private String[] convertArgs(List<Object> argsList) {
        String[] argsArray = new String[argsList.size()];

        for (int i = 0; i < argsList.size(); i++) {
             argsArray[i] = argsList.get(i).toString();
        }

        return argsArray;
    }

    @Override
    public Iterator<T> iterator() {
        if (arguments == null) {
            arguments = convertArgs(args);
        }

        return SugarRecord.findAsIterator(record, whereClause, arguments, groupBy, orderBy, limit);
    }

}
