package com.example.musicapp.service;

import com.example.musicapp.model.Song;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class SongService implements ISongService {
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.conf.xml").buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    @Override
    public List<Song> findAll() {
        String queryStr = "Select s from Song s";
        TypedQuery<Song> query = entityManager.createQuery(queryStr, Song.class);
        return query.getResultList();
    }

    @Override
    public Song findById(int id) {
        String queryStr = "Select s from Song s where s.id = :id";
        TypedQuery<Song> query = entityManager.createQuery(queryStr, Song.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void save(Song song) {
        Transaction transaction = null;
        Song origin;
        if (song.getId() == 0) {
            origin = new Song();
        } else {
            origin = findById(song.getId());
        }
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            origin.setTitle(song.getTitle());
            origin.setSinger(song.getSinger());
            origin.setCategory(song.getCategory());
            origin.setFile(song.getFile());
            session.saveOrUpdate(origin);
            transaction.commit();
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void delete(int id) {
        Song song = findById(id);
        if (song != null) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.delete(song);
                transaction.commit();
            } catch (Exception e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
}
