package com.kornel.peopledb.repository;

import com.kornel.peopledb.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PeopleRepositoryTest {

    private Connection connection;
    private PeopleRepository repo;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:~/peopleTest".replace("~", System.getProperty("user.home")));
        connection.setAutoCommit(false);
        repo = new PeopleRepository(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void canSaveOnePerson() throws SQLException {
        Person jhon = new Person("Jhon","Smith", ZonedDateTime.of(1990,12,15,15,15,0,0, ZoneId.of("-6")));
        Person savedPerson = repo.save(jhon);
        assertThat(savedPerson.getId()).isGreaterThan(0);
    }
    @Test
    public void canSaveTwoPeople() {
        Person jhon = new Person("Jhon","Smith", ZonedDateTime.of(1990,12,15,15,15,0,0, ZoneId.of("-6")));
        Person bobby = new Person("Bobby","Wale", ZonedDateTime.of(1990,12,15,15,15,0,0, ZoneId.of("-6")));
        Person savedPerson1 = repo.save(jhon);
        Person savedPerson2 = repo.save(bobby);
        assertThat(savedPerson1.getId()).isNotEqualTo(savedPerson2.getId());
    }
    @Test
    public void canFindPersonById() {
        Person savedPerson = repo.save(new Person("test", "jackson", ZonedDateTime.of(1999,12,15,15,15,0,0,ZoneId.of("+0"))));
        Person foundPerson = repo.findById(savedPerson.getId()).get();
        assertThat(foundPerson).isEqualTo(savedPerson);

    }
    @Test
    public void testPersonIdNotFound() {
        Optional<Person> foundPerson = repo.findById(-1L);
        assertThat(foundPerson).isEmpty();
    }

    @Test
    public void canGetCount() {
        int startCount = repo.count();
        repo.save(repo.save(new Person("test1", "jackson", ZonedDateTime.of(1999,12,15,15,15,0,0,ZoneId.of("+0")))));
        repo.save(repo.save(new Person("test2", "jackson", ZonedDateTime.of(1999,12,15,15,15,0,0,ZoneId.of("+0")))));
        int endCount = repo.count();
        assertThat(endCount).isGreaterThan(startCount);
        System.out.println(endCount + " " + startCount);

    }

    @Test
    public void canDelete() {
        Person savedPerson = repo.save(repo.save(new Person("test1", "jackson", ZonedDateTime.of(1999,12,15,15,15,0,0,ZoneId.of("+0")))));
        int startCount = repo.count();
        repo.delete(savedPerson);
        int endCount = repo.count();
        assertThat(endCount).isEqualTo(startCount-1);

    }

    @Test
    public void canDeleteMultiplePeople() {
        Person p1 = repo.save(repo.save(new Person("test1", "jackson", ZonedDateTime.of(1999,12,15,15,15,0,0,ZoneId.of("+0")))));
        Person p2 = repo.save(repo.save(new Person("test1", "jackson", ZonedDateTime.of(1999,12,15,15,15,0,0,ZoneId.of("+0")))));
        int start = repo.count();
        repo.delete(p1,p2);
        int end = repo.count();
        assertThat(end).isEqualTo(start-2);
    }

    @Test
    public void experiment() {
        Person p1 = new Person(10L,null,null,null);
        Person p2 = new Person(20L,null,null,null);
        Person p3 = new Person(30L,null,null,null);
        Person p4 = new Person(40L,null,null,null);
        Person p5 = new Person(50L,null,null,null);
    }
}
