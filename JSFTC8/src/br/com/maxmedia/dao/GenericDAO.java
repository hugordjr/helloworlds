package br.com.maxmedia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;


public abstract class GenericDAO<T> {

	protected final Logger log = Logger.getLogger(this.getClass()); 
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("testejsfPU");
	
	private EntityManager em;
	
	private Class<T> entityClass;

	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public GenericDAO(Class<T> entityClass, EntityManager em) {
		this.entityClass = entityClass;
		this.setEm(em);
	}

	//////////////////////////////////
	/// Controle Manuel das transações
	//////////////////////////////////
	
	public void beginTransaction() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
 
    public void commit() {
        em.getTransaction().commit();
    }
 
    public void rollback() {
        em.getTransaction().rollback();
    }
 
    public void closeTransaction() {
        em.close();
    }
 
    public void commitAndCloseTransaction() {
        commit();
        closeTransaction();
    }
 
    public void flush() {
        em.flush();
    }	
    ////////////////////////////////////
	
	
	public void save(T entity) {
		getEm().persist(entity);
	}

	public void delete(T entity) {
		T entityToBeRemoved = getEm().merge(entity);
		getEm().remove(entityToBeRemoved);
	}

	public T update(T entity) {
		return getEm().merge(entity);
	}

	
	public T find(Long entityID) {
		try {
			return getEm().find(entityClass, entityID);
		} catch(PersistenceException pe) {
			pe.printStackTrace();
			try {
				return entityClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;	
	}


	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findAll() {
		CriteriaQuery cq = getEm().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEm().createQuery(cq).getResultList();
	}

	
	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;

		try {
			Query query = getEm().createNamedQuery(namedQuery);

			// Method that will populate parameters if they are passed not null
			// and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = (T) query.getSingleResult();

		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	private void populateQueryParameters(Query query, Map<String, Object> parameters) {

		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	
	@SuppressWarnings("unchecked")
	public T getSingleResult(Query q) {
		try {
			return (T) q.getSingleResult();
		} catch (Exception ex) {
			try {
				return entityClass.newInstance();
			} catch (Exception e) {
				return null;
			}
		}
	}

	public int executeNativeQuery(String sql) {
		int retorno = -1;
		try {
			final Query query = this.getEm().createNativeQuery(sql);
			retorno = query.executeUpdate();
		} catch (Exception ex) {
			System.out.println("[ERRO] NQ " + ex.getMessage());
		}
		return retorno;
	}
	
	public Long recuperarLastInsertId(PreparedStatement pstm) {
		long lastId = -1;
		try {
//			Statement st = this.getConnection().createStatement();
//			ResultSet rs = st.executeQuery("SELECT @@IDENTITY AS NewID");
//			if (rs.next()) {
//				lastId = rs.getLong(1);
//			} else {
//				System.out.println("[ERRO] Nada recuperado.");
//			}
			ResultSet rs = pstm.getGeneratedKeys();
			if (rs.next() ) {
			    // The generated id
				lastId = rs.getLong(1);
			    //System.out.println("executeUpdate: " + executeUpdate + ", id: " + id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastId;
	}
}