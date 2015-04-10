package com.student;

import java.util.List;
import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;

public class ManageStudent 
{
	private static SessionFactory sf;
	public static void main(String[] args) 
	{
		try
		{
		  sf = new Configuration().configure().buildSessionFactory();
		}
		catch(Throwable e)
		{
			System.out.println(e);
		}
		ManageStudent ms = new ManageStudent();
		
		//Integer st1 = ms.addStudent(10,"kishan","gr","male");
		Integer st2 = ms.addStudent(20,"rashmi","kb","female");
		
		ms.show();
		ms.updateStudent(st2,"rashmi");
		ms.delete(st2);
		ms.show();
	}
	private void updateStudent(Integer st1, String string) {
		Session session = sf.openSession();
		 Transaction t = null;
		 try
		 {
			 t = session.beginTransaction();
			 Student s =(Student)session.get(Student.class,st1);
			 s.setFirstname(string);
			 session.update(s);
			 t.commit();
		 }catch(HibernateException he)
		 {
			 if(t!=null)
			 {
				 t.rollback();
				 he.printStackTrace();
			 }
		 }finally
		 {
			 session.close();
		 }
	 }
	 
	  void delete(Integer i)
	 {
		 Session session = sf.openSession();
		 Transaction t = null;
		 try
		 {
			 t = session.beginTransaction();
			 Student s1 =(Student)session.get(Student.class,i);
			 session.delete(s1);
			 t.commit();
		 }catch(HibernateException he)
		 {
			 if(t!=null)
				 t.rollback();
			 	he.printStackTrace();
		 }finally
		 {
			 session.close();
		 }
		
	}
	private void show() 
	{
		Session session = sf.openSession();
		 Transaction t = null;
		 try
		 {
			 t = session.beginTransaction();
			 List<?> Student = session.createQuery("from Student").list();
			 Iterator<?> it = Student.iterator();
			 while(it.hasNext())
			 {
				 Student st =(Student)it.next();
				 System.out.println("Student id: "+st.getId());
				 System.out.println("Firstname: "+st.getFirstname());
				 System.out.println("Lastname: "+st.getLastname());
				 System.out.println("Gender: "+st.getGender());
			 }
			 t.commit();
		 }catch(HibernateException he)
		 {
			 if(t!=null)
				 t.rollback();
			 	he.printStackTrace();
		 }
		 finally
		 {
			 session.close();
		 }
		
	}
	public Integer addStudent(int i, String fname, String lname,
			String gen)
	{
		Session session = sf.openSession();
		 Transaction t = null;
		 Integer eid = null;
		 try
		 {
			t = session.beginTransaction();
			Student s = new Student(i,fname,lname,gen);
			eid =(Integer)session.save(s);
			t.commit();
		 }catch(HibernateException he)
		 {
			 if(t!=null)
				 t.rollback();
			 he.printStackTrace();
		 }
		 finally
		 {
			 session.close();
		 }
		 return eid;
	}
		
}
