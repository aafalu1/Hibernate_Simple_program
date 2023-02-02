package com.aafalu;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aafalu.persistence.SessionFactoryProvider;

public class HibernateTest {
	private static SessionFactory sessionFactory;

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		String m = "";
		System.out.println("Enter a message : ");
		m = sc.nextLine();
		sessionFactory = SessionFactoryProvider.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Short id = null;
		try {
			tx = session.beginTransaction();
			Message msg = new Message(m);
			id=(Short)session.save(msg);
			List messages = session.createQuery("FROM Message").list();
			for (Iterator iterator = messages.iterator(); iterator.hasNext();) {
				Message message = (Message) iterator.next();
				System.out.println("message : " + message.getMessage());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
				e.printStackTrace();
			}
		} finally {
			session.close();
		}
	}
}