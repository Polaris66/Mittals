package com.example.mittals;

import com.example.mittals.Entities.User;

import static org.assertj.core.api.Assertions.assertThat;
import com.example.mittals.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    @Test
    public void testCreateCustomer(){
        User customer = new User();
        customer.setEmail("new@gmail.com");
        customer.setPassword("hi");
        customer.setFirstName("Dhruv");
        customer.setLastName("Arora");

        User savedCustomer = repo.save(customer);
        User existCustomer = entityManager.find(User.class, savedCustomer.getId());
        assertThat(customer.getEmail()).isEqualTo(existCustomer.getEmail());
    }

}
