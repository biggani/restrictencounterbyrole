package org.openmrs.module.restrictbyenctype.db.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Patient;
import org.openmrs.Role;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.restrictbyenctype.EncTypeRestriction;
import org.openmrs.module.restrictbyenctype.db.RestrictByRoleDAO;

public class HibernateRestrictByRoleDAO implements RestrictByRoleDAO {

	private SessionFactory sessionFactory;
	
	public EncTypeRestriction getEncTypeRestriction(Integer id) throws DAOException {
		return (EncTypeRestriction) getSessionFactory().getCurrentSession().get(EncTypeRestriction.class, id);
	}

	public List<EncTypeRestriction> getEncTypeRestrictions() throws DAOException {
		return getSessionFactory().getCurrentSession().createQuery("from EncTypeRestriction").list();
	}
	
	public List<EncTypeRestriction> getEncTypeRestrictions(Role role) throws DAOException {
		Criteria crit = getSessionFactory().getCurrentSession().createCriteria(EncTypeRestriction.class);
		crit.add(Restrictions.eq("role", role));
		return crit.list();
	}
	
	public void createEncTypeRestriction(EncTypeRestriction EncTypeRestriction) throws DAOException {
		getSessionFactory().getCurrentSession().save(EncTypeRestriction);
	}

	public void deleteEncTypeRestriction(EncTypeRestriction EncTypeRestriction) throws DAOException {
		getSessionFactory().getCurrentSession().delete(EncTypeRestriction);
	}

	public void updateEncTypeRestriction(EncTypeRestriction EncTypeRestriction) throws DAOException {
		getSessionFactory().getCurrentSession().update(EncTypeRestriction);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Encounter> getEncountersByPatientWithEncTypeRestriction(
			Patient patient, Set<EncounterType> encTypes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EncTypeRestriction getEncTypeRestrictionForRole(Role role) {
		Criteria crit = getSessionFactory().getCurrentSession().createCriteria(EncTypeRestriction.class);
		crit.add(Restrictions.eq("role", role));
		return (EncTypeRestriction) crit.uniqueResult();
	}

	@Override
	public EncTypeRestriction getEncTypeRestrictionByDescription(
			String description) {
		Criteria crit = getSessionFactory().getCurrentSession().createCriteria(EncTypeRestriction.class);
		crit.add(Restrictions.eq("description", description));
		return (EncTypeRestriction) crit.uniqueResult();
	}

	@Override
	public int countEncTypeRestrictions() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EncTypeRestriction.class);
		criteria.add(Restrictions.eq("voided", false));
		Number rs =  (Number) criteria.setProjection( Projections.rowCount() ).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EncTypeRestriction> listlistEncTypeRestriction(int startPos,
			int pageSize) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EncTypeRestriction.class);
		criteria.add(Restrictions.eq("voided", false));
		return criteria.setFirstResult(startPos).setMaxResults(pageSize).list();
	}
	
}
