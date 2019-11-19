package sample.dao;

import org.hibernate.Session;
import sample.entity.SuperEntity;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class CrudDAOImpl<T extends SuperEntity,ID extends Serializable> implements CrudDAO<T, ID>{

    protected Session session;
    private Class<T> entity;

    public CrudDAOImpl() {
        entity=(Class<T>)(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
    }

    @Override
    public T find(ID valueOF_ID) throws Exception {
        return session.get(entity, valueOF_ID);
    }

    @Override
    public List<T> findAll() throws Exception {
        return session.createQuery("FROM "+entity.getName(),entity).list();
    }

    @Override
    public void save(T entity) throws Exception {
        session.save(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        session.merge(entity);
    }

    @Override
    public void delete(ID valueOF_ID) throws Exception {
        session.delete(session.load(entity,valueOF_ID));
    }

    @Override
    public void setSession(Session session) {
        this.session=session;
    }
}
