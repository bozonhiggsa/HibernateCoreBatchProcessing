package com.hibernateApp.hibernate.batch;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Entry point
 * @author Ihor Savchenko
 * @version 1.0
 */
public class DeveloperRunner {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {

        sessionFactory = new Configuration().configure().buildSessionFactory();
        DeveloperRunner developerRunner = new DeveloperRunner();

        System.out.println("Adding 1000 developer's records to the database...");
        developerRunner.addDevelopers();
        System.out.println("1000 developer's records successfully added to the database...");
        sessionFactory.close();
    }

    public void addDevelopers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer developerId = null;

        transaction = session.beginTransaction();

        for (int i = 0; i < 1000; i++) {
            String firstName = "First Name " + i;
            String lastName = "Last Name " + i;
            String specialty = "Specialty " + i;
            int experience = i;
            int salary = i * 10;
            Developer developer = new Developer(firstName, lastName, specialty, experience, salary);
            session.save(developer);
            if (i % 50 == 0) {
                session.flush();
                session.clear();
            }
        }
        transaction.commit();
        session.close();
    }

    public void listDevelopers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        SQLQuery sqlQuery = session.createSQLQuery("SELECT * FROM HIBERNATE_DEVELOPERS_8");
        sqlQuery.addEntity(Developer.class);
        List<Developer> developers = sqlQuery.list();

        for (Developer developer : developers) {
            System.out.println("=======================");
            System.out.println(developer);
            System.out.println("=======================");
        }
        transaction.commit();
        session.close();
    }
}
