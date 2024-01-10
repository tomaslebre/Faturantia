package sebastiao.tomas.faturantia.controlers;


import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import sebastiao.tomas.faturantia.models.Fatura;
import sebastiao.tomas.faturantia.models.User;
import sebastiao.tomas.faturantia.repositories.FaturaRepository;
import sebastiao.tomas.faturantia.repositories.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;



@RestController
@RequestMapping(path = "/api/fatura")
public class FaturaController {

    private Logger logger = LoggerFactory.getLogger(FaturaController.class);

    @Autowired
    private FaturaRepository faturaRepository;

    @Autowired
    private UserRepository userRepository;
    

    @GetMapping("/{userId}/list")
    public ResponseEntity<?> getFaturasByUserId(@PathVariable Integer userId) {
        try {
            Iterable<Fatura> faturas = faturaRepository.findByUserId(userId);
            return ResponseEntity.ok(faturas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error fetching faturas: " + e.getMessage());
        }
    }
    
    

    @PostMapping("/user/{userId}/add")
    public ResponseEntity<?> addFaturaToUser(@PathVariable int userId, @RequestBody Fatura fatura) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            logger.info("User not found for ID: " + userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOpt.get();
        fatura.setUser(user); // Associar fatura ao usuário
        Fatura savedFatura = faturaRepository.save(fatura);
        logger.info("Fatura added to user with ID: " + userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFatura);
    }



    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFatura(@PathVariable int id, @RequestBody Fatura updatedFatura) {
        Optional<Fatura> existingFaturaOpt = faturaRepository.findById(id);
        if (existingFaturaOpt.isPresent()) {
            Fatura existingFatura = existingFaturaOpt.get();
            
            // Atualize os campos da fatura existente com os dados da fatura atualizada
            existingFatura.setTitle(updatedFatura.getTitle());
            existingFatura.setStore(updatedFatura.getStore());
            existingFatura.setStoreLocation(updatedFatura.getStoreLocation());
            existingFatura.setDatePurchase(updatedFatura.getDatePurchase());
            // Adicione mais campos conforme necessário

            // Salve a fatura atualizada no banco de dados
            Fatura savedFatura = faturaRepository.save(existingFatura);
            return ResponseEntity.ok(savedFatura);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fatura with id " + id + " not found.");
        }
    }

    



    @Transactional
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteFatura(@PathVariable("id") int id) {
        logger.info("Attempting to delete fatura with id: " + id);
        if(faturaRepository.existsById(id)) {
            faturaRepository.deleteById(id);
            logger.info("Deleted fatura with id " + id);
            return ResponseEntity.ok("Bill with id" + id + "was successfuly deleted.");
        }else{
            logger.info("Bill with id " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill with id " + id + " not found.");
        }

    }
    
}


