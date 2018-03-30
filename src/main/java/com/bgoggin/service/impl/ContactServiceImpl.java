package com.bgoggin.service.impl;

import com.bgoggin.model.Contact;
import com.bgoggin.service.ContactService;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

public class ContactServiceImpl implements ContactService {
    private static final String CREATE_SQL =
            "INSERT INTO contact (last_name, first_name, mi, email) " +
                    "VALUES (:lastName, :firstName, :mi, :email)";
    private static final String FIND_ALL_SQL =
            "SELECT id, last_name, first_name, mi, email FROM contact";
    private static final String FILE_ALL_BY_EMAIL_LIKE_SQL =
            "SELECT id, last_name, first_name, mi, email FROM contact WHERE email LIKE :email";
    private static final String FIND_ONE_SQL =
            "SELECT  id, last_name, first_name, mi, email FROM contact WHERE id = :id";
    private static final String UPDATE_SQL =
            "UPDATE contact SET last_name = :lastName, first_name = :firstName, mi = :mi, " +
                    "email = :email WHERE id = :id";
    private static final String DELETE_SQL =
            "DELETE FROM contact WHERE id = :id";

    @Inject private NamedParameterJdbcOperations jdbcTemplate;
    @Inject private ContactRowMapper contactRowMapper;

    @Override
    public void createContact(Contact contact) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("lastName", contact.getLastName())
                .addValue("firstName", contact.getFirstName())
                .addValue("mi", contact.getMiddleInitial())
                .addValue("email", contact.getEmail());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(CREATE_SQL, params, keyHolder);
        contact.setId(keyHolder.getKey().longValue());
    }

    @Override
    public List<Contact> getContacts() {
        return jdbcTemplate.query(FIND_ALL_SQL, new HashMap<String, Object>(), contactRowMapper);
    }

    @Override
    public List<Contact> getContactsByEmail(String email) {
        return null;
    }

    @Override
    public Contact getContact(Long id) {
        return null;
    }

    @Override
    public void updateContact(Contact contact) {

    }

    @Override
    public void deleteContact(Long id) {

    }
}
