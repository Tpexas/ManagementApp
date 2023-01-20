package com.mygroup;

import com.mygroup.role.Role;
import com.mygroup.role.RoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTest {
    @Autowired
    RoleRepository repo;

    @Test
    public void testCreateRoles(){
        Role patient = new Role("Patient");
        Role admin = new Role("Admin");

        repo.saveAll(List.of(patient, admin));

        List<Role> listRoles = repo.findAll();

        Assertions.assertThat(listRoles.size()).isEqualTo(2);
    }

}
