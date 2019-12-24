package com.serzh.cache;

import com.serzh.AbstractSpringBootTest;
import com.serzh.domain.User;
import com.serzh.domain.UserState;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

@Slf4j
public class UserServiceCacheImplTest extends AbstractSpringBootTest {

    @Autowired
    private UserServiceCache service;

    @Test
    public void get() {
        User user1 = service.create(User.builder().name("Vasya").email("vasya@fake.com").build());
        User user2 = service.create(User.builder().name("Kolya").email("kolya@fake.com").build());

        getAndPrint(user1.getId());
        getAndPrint(user2.getId());
        getAndPrint(user1.getId());
        getAndPrint(user2.getId());
    }

    @Test
    public void getMoreThanHeapSize() {
        User user1 = service.create(User.builder().name("Vasya1").email("vasya@fake.com").build());
        User user2 = service.create(User.builder().name("Vasya2").email("vasya@fake.com").build());
        User user3 = service.create(User.builder().name("Vasya3").email("vasya@fake.com").build());
        User user4 = service.create(User.builder().name("Vasya4").email("vasya@fake.com").build());

        getAndPrint(user1.getId());
        getAndPrint(user2.getId());
        getAndPrint(user3.getId());
        getAndPrint(user4.getId());
        getAndPrint(user4.getId());
        getAndPrint(user1.getId());
    }

    private void getAndPrint(Long id) {
        log.info("user found: {}", service.get(id));
    }

    @Test
    public void create() {
        createAndPrint("Ivan", "ivan@fake.com");
        createAndPrint("Ivan", "ivan1122@fake.com");
        createAndPrint("Sergey", "ivan@fake.com");

        log.info("all entries are below:");
        service.findAll().forEach(u -> log.info("{}", u.toString()));
    }

    private void createAndPrint(String name, String email) {
        log.info("created user: {}", service.create(name, email));
    }

    @Test
    public void createAndRefresh() {
        User user1 = service.createOrReturnCached(new User("Vasya", "vasya@fake.com"));
        log.info("created user1: {}", user1);

        User user2 = service.createOrReturnCached(new User("Vasya", "misha@fake.com"));
        log.info("created user2: {}", user2);

        User user3 = service.createAndRefreshCache(new User("Vasya", "kolya@fake.com"));
        log.info("created user3: {}", user3);

        User user4 = service.createOrReturnCached(new User("Vasya", "petya@fake.com"));
        log.info("created user4: {}", user4);
    }

    @Test
    public void delete() {
        User user1 = service.create(User.builder().name("Vasya").email("vasya@fake.com").state(UserState.ACTIVE).build());
        log.info("{}", service.get(user1.getId()));

        User user2 = service.create(User.builder().name("Misha").email("misha@fake.com").state(UserState.ACTIVE).build());
        log.info("{}", service.get(user2.getId()));

        service.delete(user1.getId());
        service.deleteAndEvict(user2.getId());

        log.info("{}", service.get(user1.getId()));
        try {
            log.info("{}", service.get(user2.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user3 = service.create(User.builder().name("Pall").email("pall@fake.com").state(UserState.ACTIVE).build());

        List<User> all = service.findAll();

        assertEquals(1, all.size());
        assertEquals(User.builder().name("Pall").email("pall@fake.com").state(UserState.ACTIVE).build(), all.get(0));

        List<User> deleted = service.findAllStatusDeletedQuery();

        assertEquals(0, deleted.size());

        List<User> deletedNative = service.findAllStatusDeletedNativeQuery();

        assertEquals(2, deletedNative.size());
        assertEquals(User.builder().name("Misha").email("misha@fake.com").state(UserState.DELETED).build(), deletedNative.get(1));

        System.out.println();


    }

    @Test
    public void checkSettings() throws InterruptedException {
        User user1 = service.createOrReturnCached(new User("Vasya", "vasya@fake.com"));
        log.info("{}", service.get(user1.getId()));

        User user2 = service.createOrReturnCached(new User("Vasya", "vasya@fake.com"));
        log.info("{}", service.get(user2.getId()));

        Thread.sleep(1000L);
        User user3 = service.createOrReturnCached(new User("Vasya", "vasya@fake.com"));
        log.info("{}", service.get(user3.getId()));
    }

}