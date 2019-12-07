package com.serzh.service.impl;

import com.serzh.domain.User;
import com.serzh.domain.UserProfile;
import com.serzh.repository.UserProfileRepository;
import com.serzh.repository.UserRepository;
import com.serzh.service.MainService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private AtomicInteger index = new AtomicInteger(1);

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @PersistenceContext
    // injects a EntityManager proxy, a "shared EntityManager" that is related to current transaction
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save() {
        User user1 = User.builder()
                .name("Vas1")
                .build();

        User user2 = User.builder()
                .name("Vas2")
                .build();

        userRepository.saveAndFlush(user1);
        userRepository.saveAndFlush(user2);

        UserProfile userProfile1 = UserProfile.builder()
                .user(user1)
                .unread(true)
                .build();


/*        UserProfile userProfile = UserProfile.builder()
                .userId(1)
//                .id(user.getId())
                .info("inf")
                .build();*/

        userProfileRepository.save(userProfile1);
    }

    @Override
    @Transactional
    public void get(Integer id) {

        UserProfile userProfile = userProfileRepository.getOne(id);
//        UserProfile userProfile1 = userProfileRepository.getById(id);
        System.out.println();
    }

    @Override
    @Transactional
    public void update(Integer id) {
        try {
            Session session = entityManager.unwrap(Session.class);
            Query query = session.createSQLQuery("SELECT * FROM user_profile \n" +
                    "WHERE user_profile.user_id = :userId\n" + // !!!!!!
                    //                "WHERE user_profile.id = :userId\n" + // NOT!!!
                    " LIMIT 1\n")
                    .addEntity(UserProfile.class)
                    .setInteger("userId", id);
            UserProfile userProfile = (UserProfile) query.uniqueResult();

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

   /*     try {
            UserProfile one = userProfileRepository.getOne(id);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

  /*      UserProfile userProfile = UserProfile.builder()
                .userId(id)
                .info("inf new")
                .build();
//        userProfileRepository.save(userProfile); // work !!!

        System.out.println();*/
        try {
            Session session = entityManager.unwrap(Session.class);
            //    TODO
//            session.saveOrUpdate(userProfile);

            System.out.println();

            Query query = session.createSQLQuery("INSERT INTO user_profile (user_id, unread) VALUES (:user_id, :unread)\n" +
                    "ON CONFLICT (user_id) DO UPDATE SET unread = :unread")
                    //            query.setParameter("ds", null, Hibernate.BOOLEAN);
                    .setParameter("user_id", id)
//                    .setParameter("info", "inf new"); // work !!!
                    .setParameter("unread", false); // work !!!
            int rowsUpdated = query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* @Override
    @Transactional
    public void update(Integer id) {
        try {
            Session session = entityManager.unwrap(Session.class);
            Query query = session.createSQLQuery("SELECT * FROM user_profile \n" +
                    "WHERE user_profile.user_id = :userId\n" + // !!!!!!
                    //                "WHERE user_profile.id = :userId\n" + // NOT!!!
                    " LIMIT 1\n")
                    .addEntity(UserProfile.class)
                    .setInteger("userId", id);
            UserProfile userProfile = (UserProfile) query.uniqueResult();

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

   *//*     try {
            UserProfile one = userProfileRepository.getOne(id);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }*//*

     *//*      UserProfile userProfile = UserProfile.builder()
                .userId(id)
                .info("inf new")
                .build();
//        userProfileRepository.save(userProfile); // work !!!

        System.out.println();*//*
        try {
            Session session = entityManager.unwrap(Session.class);
            //    TODO
//            session.saveOrUpdate(userProfile);

            System.out.println();

            Query query = session.createSQLQuery("INSERT INTO user_profile (user_id, unread) VALUES (:user_id, :unread)\n" +
                    "ON CONFLICT (user_id) DO UPDATE SET unread = :unread")
                    //            query.setParameter("ds", null, Hibernate.BOOLEAN);
                    .setParameter("user_id", id)
//                    .setParameter("info", "inf new"); // work !!!
                    .setParameter("unread", false); // work !!!
            int rowsUpdated = query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

   /* @Transactional
    public void updateUgly(Integer id) {
        try {
            UserProfile profile = userProfileRepository.getOne(id);
            profile.setInfo("inf new");
            userProfileRepository.save(profile);
        } catch (EntityNotFoundException e) {
            User user = userRepository.getOne(id);
            UserProfile userProfile = UserProfile.builder()
                    .user(user)
                    .info("inf new")
                    .build();
            userProfileRepository.save(userProfile);
        }
    }*/


    //    @Override
    @Transactional
    public void update1(Integer id) {
//        you call insert to insert an object, or update to save new state of the object to the database.
        try {
            Session session = entityManager.unwrap(Session.class);
            Query query = session.createSQLQuery("UPDATE user_profile SET info=:info WHERE user_id=:id")
//            query.setParameter("ds", null, Hibernate.BOOLEAN);
                    .setParameter("id", id)
                    .setParameter("info", "inf new");
//                    .setInteger("id", id)
//                    .setString("info", "new 1");
            int rowsUpdated = query.executeUpdate();
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Session session = entityManager.unwrap(Session.class);
            Query query = session.createSQLQuery("UPDATE user_profile SET info='" + "new 2" + "' WHERE user_id=" + id);
            int rowsUpdated = query.executeUpdate();
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserProfile userProfile = userProfileRepository.getOne(id);
//        userProfile.setInfo("new 3");
        userProfileRepository.save(userProfile);
    }

    //    @Override
    @Transactional
    public void get2(Integer id) {
        try {
            Session session = entityManager.unwrap(Session.class);
            Query query = session.createSQLQuery("SELECT * FROM user_profile \n" +
                    "WHERE user_profile.user_id = :userId\n" + // !!!!!!
                    //                "WHERE user_profile.id = :userId\n" + // NOT!!!
                    " LIMIT 1\n")
                    .addEntity(UserProfile.class)
                    .setInteger("userId", id);
            UserProfile userProfile = (UserProfile) query.uniqueResult();

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

/* Query nativeQuery = entityManager.createNativeQuery("select m.ycu_user_id, count(p.*) " +
                " from ycnsg_post p" +
                " left join ycnsg_timeline t on t.id = p.timeline_id" +
                " left join ycnsg_support_group g on t.id = g.timeline_id" +
                " left join ycnsg_member m on m.support_group_id = g.id" +
                " where m.ycu_user_id in" + parametersIn +
                " and p.id not in (" +
                " select rp.post_id from ycnsg_read_posts rp" +
                " where rp.member_id = m.id)" +
                " GROUP BY m.ycu_user_id");

        @SuppressWarnings("unchecked") List<Object[]> resultList = nativeQuery.getResultList();

        Map<String, Integer> membersUnreadPostsCountMap = resultList.stream()
                .filter(result -> ((BigInteger) result[1]).signum() == 1)
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> ((BigInteger) result[1]).intValue(),
                        (existing, replacement) -> existing));*/
