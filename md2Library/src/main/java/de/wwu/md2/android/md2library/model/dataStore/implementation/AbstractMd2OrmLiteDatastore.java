package de.wwu.md2.android.md2library.model.dataStore.implementation;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.wwu.md2.android.md2library.model.dataStore.AtomicExpression;
import de.wwu.md2.android.md2library.model.dataStore.CombinedExpression;
import de.wwu.md2.android.md2library.model.dataStore.Expression;
import de.wwu.md2.android.md2library.model.dataStore.Filter;
import de.wwu.md2.android.md2library.model.dataStore.interfaces.Md2LocalStore;
import de.wwu.md2.android.md2library.model.type.interfaces.Md2Entity;

/**
 * Created by felix_000 on 19.05.2017.
 */

public abstract class AbstractMd2OrmLiteDatastore<T extends Md2Entity> extends AbstractMd2DataStore<T> implements Md2LocalStore<T> {
    @Override
    public void query(Filter filter) {
        try {
            QueryBuilder<T, Integer> queryBuilder = getMyDao().queryBuilder();
            if (filter != null && filter.getFilterTree() != null) {
                Where<T, Integer> where = queryBuilder.where();
                this.whereBuilder(filter.getFilterTree(), where);
            }
            PreparedQuery query = queryBuilder.prepare();
            System.out.println(query.getStatement());
            contentProvider.overwriteContent(getMyDao().query(query));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void query(Filter filter, Timestamp modifiedDate) {
        this.query(filter);
    }

    public Where<T, Integer> whereBuilder(Expression exp, Where<T, Integer> where) {
        if (exp != null) {
            if (exp instanceof AtomicExpression) {
                AtomicExpression aexp = (AtomicExpression) exp;
                switch (aexp.getOperator()) {
                    case EQUAL:
                        try {
                            where.eq((aexp).getLeftOperand(), aexp.getRightOperand());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case GREATEREQUAL:
                        try {
                            where.ge((aexp).getLeftOperand(), aexp.getRightOperand());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LESSEQUAL:
                        try {
                            where.le((aexp).getLeftOperand(), aexp.getRightOperand());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LESS:
                        try {
                            where.lt((aexp).getLeftOperand(), aexp.getRightOperand());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case GREATER:
                        try {
                            where.gt((aexp).getLeftOperand(), aexp.getRightOperand());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;

                }
            } else {
                CombinedExpression cexp = (CombinedExpression) exp;
                whereBuilder(cexp.getLeftExpression(), where);
                whereBuilder(cexp.getRightExpression(), where);
                switch (cexp.getJunction()) {
                    case AND:
                        where.and(2);
                        break;
                    case OR:
                        where.or(2);
                        break;
                }
            }
        }
        return where;
    }

    @Override
    public void load(long id, Class dataType) {
        try {
            T result = getMyDao().queryForId((int) (id));
            contentProvider.overwriteContent(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(Md2Entity md2Entity) {
        try {
            if (md2Entity.getId() > 0) {
                getMyDao().update((T) md2Entity);
            } else {
                getMyDao().create((T) md2Entity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(long id, Md2Entity md2Entity) {
        md2Entity.setId(id);
        put(md2Entity);
    }

    public abstract Dao<T, Integer> getMyDao();

    @Override
    public void remove(long id, Md2Entity md2Entity) {
        try {
            getMyDao().deleteById((int) id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> loadAll() {
        List<T> all = new ArrayList<T>();
        try {
            all = getMyDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }
}
