package sebastiao.tomas.faturantia.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sebastiao.tomas.faturantia.models.Fatura;
import java.util.Optional;



@Repository
public interface FaturaRepository extends CrudRepository<Fatura, Integer> {

    Iterable<Fatura> findByTitleContaining(String text);
    Iterable<Fatura> findByUserId(Integer id);

    Optional<Fatura> findById(Integer id);

    Fatura findByTitle(String title);
    Fatura findByStore(String store);
    Fatura findByStoreLocation(String storeLocation);
    Fatura findByDatePurchase(String datePurchase);
    boolean existsByTitle(String title);
    
    
}

