package com.hanselnpetal.data.repos;

import com.hanselnpetal.domain.CustomerContact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerContactRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerContactRepository customerContactRepository;

    @Test
    public void testFindByEmail() {
        // setup data scenario
        CustomerContact aNewContact = new CustomerContact();
        aNewContact.setEmail("fredj@myemail.com");
        entityManager.persist(aNewContact);

        // Find an inserted recording using repository class
        CustomerContact foundContact = customerContactRepository.findByEmail("fredj@myemail.com");

        assertThat(foundContact.getEmail(), is(equalTo("fredj@myemail.com")));
    }
}
