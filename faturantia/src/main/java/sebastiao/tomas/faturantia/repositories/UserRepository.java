package sebastiao.tomas.faturantia.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;



import sebastiao.tomas.faturantia.models.User;





@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Iterable<User> findByNameContaining(String text);
    

    boolean existsByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    User findByName( String name);
    Optional<User> findById(int id);
    User findByEmail(String email);


}