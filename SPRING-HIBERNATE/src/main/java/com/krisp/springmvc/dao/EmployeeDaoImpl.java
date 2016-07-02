package com.krisp.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.krisp.springmvc.model.Employee;

@Repository("employeeDao")
public class EmployeeDaoImpl extends AbstractDao<Integer,Employee> implements EmployeeDao{

	public Employee findById(int id) {
		// TODO Auto-generated method stub
		return getByKey(id);
	}

	public void saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		persist(employee);
	}

	public void deleteEmployeeBySsn(String ssn) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery("delete from Employee where ssn = :ssn" );
		query.setString("ssn", ssn);
		query.executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	public List<Employee> findAllEmployees() {
		// TODO Auto-generated method stub
		Criteria criteria = createEntityCriteria();
		return (List<Employee>) criteria.list();
	}

	public Employee findEmployeeBySsn(String ssn) {
		// TODO Auto-generated method stub
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("ssn", ssn));
		return (Employee) criteria.uniqueResult();
	}

}
