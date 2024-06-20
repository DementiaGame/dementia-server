//package synapse.dementia.global.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import synapse.dementia.domain.initialgame.repository.UserRepository;
//import synapse.dementia.domain.users.domain.Users;
//import synapse.dementia.domain.users.domain.Gender;
//import synapse.dementia.domain.users.domain.Role;
//
//@Component
//public class TestDataLoader implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    public TestDataLoader(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Check if the user already exists to avoid duplications
//        if (!userRepository.existsById(1L)) {
//            Users user = new Users();
//            user.setBirthYear(1990);
//            user.setGender(Gender.MALE);
//            user.setNickName("testuser");
//            user.setPassword("password");
//            user.setDeleted(false);
//            user.setRole(Role.ROLE_USER);
//            userRepository.save(user);
//        }
//    }
//}
