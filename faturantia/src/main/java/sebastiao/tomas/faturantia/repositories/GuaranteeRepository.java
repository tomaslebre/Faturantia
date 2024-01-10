package sebastiao.tomas.faturantia.repositories;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Calendar;
import java.util.Optional;

import sebastiao.tomas.faturantia.models.Guarantee;

@Repository
public interface GuaranteeRepository extends CrudRepository<Guarantee, Integer> {

    Iterable<Guarantee> findByTitleContaining(String text);

    Guarantee findByTitle(String title);
    Guarantee findByExpDateEdit(String expDateEdit);
    Guarantee findByExpDateCalendar(Calendar expDateCalendar);
    Guarantee findByImportantCheck(boolean importantCheck);
    Guarantee findByRemDateCalendar(Calendar remDateCalendar);
    Optional<Guarantee> findById(Integer id);
    Guarantee findByNotes(String notes);
    boolean existsByTitle(String title);

    @Query("SELECT g FROM Guarantee g WHERE g.fatura.user.id = :userId ORDER BY g.expDateCalendar ASC")
    Iterable<Guarantee> findGuaranteesByUserIdOrderByExpDateAsc(@Param("userId") Integer userId);



    @Query("SELECT g FROM Guarantee g WHERE g.fatura.user.id = :userId")
    Iterable<Guarantee> findByFaturaUserIdOrderByExpDateCalendarAsc(Integer userId);

    @Query("SELECT g FROM Guarantee g WHERE g.fatura.user.id = :userId AND g.importantCheck = true")
    Iterable<Guarantee> findImportantGuaranteesByUserId(@Param("userId") Integer userId);

    
}