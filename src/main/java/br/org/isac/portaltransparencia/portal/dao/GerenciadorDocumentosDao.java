package br.org.isac.portaltransparencia.portal.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import br.org.isac.portaltransparencia.portal.entity.Arquivos;
import br.org.isac.portaltransparencia.portal.entity.Objeto;

@Repository
public class GerenciadorDocumentosDao {
	
	@PersistenceContext
    private EntityManager em;
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Objeto> getQuantidadeArquivosPorTipo () {  
        Session session = (Session) em.getDelegate();  
        
        @SuppressWarnings({ })
		List<Objeto> results = null;
		try {
			results = session.createCriteria(Arquivos.class)
					.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("tipo"), "tipo")
					.add(Projections.rowCount(), "quantidade"))  
			        .setResultTransformer(Transformers.aliasToBean(Objeto.class))  
			        .list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
		}  
          
        return results;  
    }  
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Objeto> getQuantidadeArquivosPorTipoAndUnidade (Integer idUnidade) {  
        Session session = (Session) em.getDelegate();  
        
        @SuppressWarnings({ })
		List<Objeto> results = null;
		try {
			results = session.createCriteria(Arquivos.class).add(Restrictions.eq("idUnidade", idUnidade))
					.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("tipo"), "tipo")
					.add(Projections.rowCount(), "quantidade"))  
			        .setResultTransformer(Transformers.aliasToBean(Objeto.class))
			        .list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
		}  
          
        return results;  
    } 
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Objeto> getQuantidadeArquivosPorEstado () {  
        Session session = (Session) em.getDelegate();  
        
        @SuppressWarnings({ })
		List<Objeto> results = null;
		try {
			results = session.createCriteria(Arquivos.class)
					.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("idEstadoAtual"), "tipo")
					.add(Projections.rowCount(), "quantidade"))  
			        .setResultTransformer(Transformers.aliasToBean(Objeto.class))  
			        .list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
		}  
          
        return results;  
    } 
	
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Objeto> getQuantidadeArquivosPorEstadoVisibilidade (Integer idEstado, String visibilidade) {  
        Session session = (Session) em.getDelegate();  
        
        @SuppressWarnings({ })
		List<Objeto> results = null;
		try {
			results = session.createCriteria(Arquivos.class).add(Restrictions.eq("idEstadoAtual", idEstado)).add(Restrictions.eq("visibilidade", visibilidade))
					.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("idEstadoAtual"), "tipo")
					.add(Projections.rowCount(), "quantidade"))  
			        .setResultTransformer(Transformers.aliasToBean(Objeto.class))
			        .list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
		}  
          
        return results;  
    } 
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Objeto> getQuantidadeArquivosPorEstadoVisibilidade (String visibilidade) {  
        Session session = (Session) em.getDelegate();  
        
        @SuppressWarnings({ })
		List<Objeto> results = null;
		try {
			results = session.createCriteria(Arquivos.class).add(Restrictions.eq("visibilidade", visibilidade))
					.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("idEstadoAtual"), "tipo")
					.add(Projections.rowCount(), "quantidade"))  
			        .setResultTransformer(Transformers.aliasToBean(Objeto.class))
			        .list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
		}  
          
        return results;  
    } 
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Objeto> getQuantidadeArquivosPorUnidadeEstadoVisibilidade (Integer idUnidade, Integer idEstado, String visibilidade) {  
        Session session = (Session) em.getDelegate();  
        
        @SuppressWarnings({ })
		List<Objeto> results = null;
		try {
			results = session.createCriteria(Arquivos.class).add(Restrictions.eq("idUnidade", idUnidade)).add(Restrictions.eq("idEstadoAtual", idEstado)).add(Restrictions.eq("visibilidade", visibilidade))
					.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("idEstadoAtual"), "tipo")
					.add(Projections.rowCount(), "quantidade"))  
			        .setResultTransformer(Transformers.aliasToBean(Objeto.class))
			        .list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
		}  
          
        return results;  
    } 
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Objeto> getQuantidadeArquivosPorUnidadeEstadoVisibilidade (Integer idUnidade, String visibilidade) {  
        Session session = (Session) em.getDelegate();  
        
        @SuppressWarnings({ })
		List<Objeto> results = null;
		try {
			results = session.createCriteria(Arquivos.class).add(Restrictions.eq("idUnidade", idUnidade)).add(Restrictions.eq("visibilidade", visibilidade))
					.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("idEstadoAtual"), "tipo")
					.add(Projections.rowCount(), "quantidade"))  
			        .setResultTransformer(Transformers.aliasToBean(Objeto.class))
			        .list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
		}  
          
        return results;  
    } 
	
	

}
