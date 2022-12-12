package com.kornel.peopledb.repository;

import com.kornel.peopledb.annotation.SQL;
import com.kornel.peopledb.model.CrudOperation;
import com.kornel.peopledb.model.Person;
import java.math.BigDecimal;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class PeopleRepository extends CRUDRepository<Person> {
    public static final String SAVE_PERSON_SQL = "INSERT INTO PEOPLE (FIRST_NAME, LAST_NAME, DOB) VALUES(?, ?, ?)";
    public static final String FIND_PERSON_SQL = "SELECT ID, FIRST_NAME, LAST_NAME, DOB, SALARY FROM PEOPLE WHERE ID=?";
    public static final String FIND_ALL_SQL = "SELECT ID FIRST_NAME, LAST_NAME, DOB, SALARY FROM PEOPLE";
    public static final String COUNT_PERSON_SQL = "SELECT COUNT(*) FROM PEOPLE";
    public static final String DELETE_PERSON_SQL = "DELETE FROM PEOPLE WHERE ID=?";
    public static final String DELETE_MULTIPLE_SQL = "DELETE FROM PEOPLE WHERE ID IN (:ids)";
    public static final String UPDATE_PERSON_SQL = "UPDATE PEOPLE SET FIRST_NAME = ?, LAST_NAME = ?, DOB = ?, SALARY = ? WHERE ID = ?";

    public PeopleRepository(Connection connection) {
        super(connection);
    }

    private static Timestamp convertDobToTimestamp(ZonedDateTime dob) {
        return Timestamp.valueOf(dob.withZoneSameInstant(ZoneId.of("+0")).toLocalDateTime());
    }

    @Override
    @SQL(value = SAVE_PERSON_SQL,operationType = CrudOperation.SAVE)
    void mapForSave(Person entity, PreparedStatement ps) throws SQLException {
        ps.setString(1, entity.getFirstName());
        ps.setString(2, entity.getLastName());
        ps.setTimestamp(3, convertDobToTimestamp(entity.getDob()));
    }
    @Override
    @SQL(value = UPDATE_PERSON_SQL,operationType = CrudOperation.UPDATE)
    void mapForUpdate(Person entity, PreparedStatement ps) throws SQLException {
        ps.setString(1, entity.getFirstName());
        ps.setString(2, entity.getLastName());
        ps.setTimestamp(3, convertDobToTimestamp(entity.getDob()));
        ps.setBigDecimal(4, entity.getSalary());
    }

    @Override
    @SQL(value = FIND_PERSON_SQL,operationType = CrudOperation.FIND_BY_ID)
    @SQL(value = FIND_ALL_SQL,operationType = CrudOperation.FIND_ALL)
    @SQL(value = COUNT_PERSON_SQL, operationType = CrudOperation.COUNT)
    @SQL(value = DELETE_PERSON_SQL,operationType = CrudOperation.DELETE)
    @SQL(value = DELETE_MULTIPLE_SQL,operationType = CrudOperation.DELETE_ALL)
    Person extractEntityFromResultSet(ResultSet rs) throws SQLException {
        long personId = rs.getLong("ID");
        String firstName = rs.getString("FIRST_NAME");
        String lastName = rs.getString("LAST_NAME");
        ZonedDateTime dob = ZonedDateTime.of(rs.getTimestamp("DOB").toLocalDateTime(),ZoneId.of("+0"));
        BigDecimal salary = rs.getBigDecimal("SALARY");
        return new Person(personId,firstName,lastName,dob, salary);
    }
}
