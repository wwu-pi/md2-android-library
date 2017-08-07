package de.uni_muenster.wi.md2library.model.dataStore.implementation;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import de.uni_muenster.wi.md2library.model.contentProvider.interfaces.Md2ContentProvider;
import de.uni_muenster.wi.md2library.model.dataStore.Filter;
import de.uni_muenster.wi.md2library.model.dataStore.interfaces.Md2LocalStore;
import de.uni_muenster.wi.md2library.model.type.interfaces.Md2Entity;

/**
 * Created by felix_000 on 19.05.2017.
 */


public abstract class AbstractMd2OrmLiteDatastore<T extends  Md2Entity> extends  AbstractMd2DataStore<T> implements Md2LocalStore<T> {


    Dao<T , Integer> myDao;
    protected Md2ContentProvider contentProvider;

    @Override
    public void setContentProvider(Md2ContentProvider contentProvider) {
        this.contentProvider = contentProvider;
    }

    @Override
    public Md2ContentProvider getContentProvider(){
        return this.contentProvider;
    }

    @Override
    public void query(Filter filter) {

    }




@Override
    public  void getInternalId(Md2Entity entity) {
        try {
            getMyDao().extractId((T) entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
       // return 0;
    }

    @Override
    public void load(long id, Class dataType) {

    }

@Override
    public  void put(Md2Entity md2Entity) {
        try {
            getMyDao().createOrUpdate((T) md2Entity);

        } catch (SQLException e) {
            e.printStackTrace();
        }
//return md2Entity.getId();
    }

@Override
    public void put(long id, Md2Entity md2Entity) {
        try {
            getMyDao().createOrUpdate((T) md2Entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    //return md2Entity.getId();
    }


public abstract Dao<T,Integer> getMyDao();



    @Override
    public void remove(long id, Class md2Entity) {
        try {
            getMyDao().deleteById((int)id);
          //  myDao.delete(md2Entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






public List<T> loadAll(){
    List<T> all= new ArrayList<T>();
        try {
           all=  getMyDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }


}
