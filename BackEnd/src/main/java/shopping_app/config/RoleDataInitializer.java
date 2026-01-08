package shopping_app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shopping_app.entity.Role;
import shopping_app.repository.RoleRepository;

@Configuration
@RequiredArgsConstructor
public class RoleDataInitializer {

    private final RoleRepository roleRepository;

    @Bean
    public ApplicationRunner initRoles() {
        return args -> {

            if (roleRepository.count() == 0) {

                Role admin = new Role();
                admin.setName("ADMIN");

                Role user = new Role();
                user.setName("USER");

                roleRepository.save(admin);
                roleRepository.save(user);
            }
        };
    }
}
