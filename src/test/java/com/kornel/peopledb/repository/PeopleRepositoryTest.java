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
        Person savedPerson = repo.save(new Person("test", "jackson", ZonedDateTime.now()));
        Person foundPerson = repo.findById(savedPerson.getId());
        assertThat(foundPerson).isEqualTo(savedPerson);

    }
}
