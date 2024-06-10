package org.acme.repository;

import java.util.List;

import org.acme.model.User;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public List<User> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return List.of();
        }
        return find("UPPER(name) LIKE UPPER(?1)", "%" + name + "%").list();
    }

    public PanacheQuery<User> findByEmailAndPassword(String email, String password) {
        if (email == null || password == null) {
            return null;
        }
        return find("email = ?1 AND password = ?2", email, password);
    }

    public User findByLoginAndSenha(String login, String senha) {
        if (login == null || senha == null) {
            return null;
        }
        return find("login = ?1 AND senha = ?2", login, senha).firstResult();
    }

    public PanacheQuery<User> findByEmail(String email) {
        if (email == null) {
            return null;
        }
        return find("email = ?1", email);
    }

    
}
