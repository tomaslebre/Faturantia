package sebastiao.tomas.faturantia.controlers;

import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import sebastiao.tomas.faturantia.models.Guarantee;
import sebastiao.tomas.faturantia.repositories.GuaranteeRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Optional;
import sebastiao.tomas.faturantia.models.Fatura;
import sebastiao.tomas.faturantia.repositories.FaturaRepository;


@RestController
@RequestMapping(path = "/api/guarantee")
public class GuaranteeController {

    private Logger logger = LoggerFactory.getLogger(GuaranteeController.class);

    @Autowired 
    private GuaranteeRepository guaranteeRepository;

    @Autowired
    private FaturaRepository faturaRepository;
    //apenas da list do user selecionado

    @GetMapping(path = "/{userId}/list")
    public ResponseEntity<?> getGuaranteesByUserId(@PathVariable("userId") Integer userId) {
        try {
            Iterable<Guarantee> guarantees = guaranteeRepository.findByFaturaUserIdOrderByExpDateCalendarAsc(userId);
            // Adicione um log aqui para imprimir as garantias
            guarantees.forEach(guarantee -> logger.info("Guarantee: " + guarantee));
            return ResponseEntity.ok(guarantees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching guarantees: " + e.getMessage());
        }
    }
    


    @GetMapping("/{userId}/list/imp")
    public ResponseEntity<?> getImportantGuaranteesByUserId(@PathVariable Integer userId) {
        try {
            Iterable<Guarantee> importantGuarantees = guaranteeRepository.findImportantGuaranteesByUserId(userId);
            return ResponseEntity.ok(importantGuarantees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Erro ao buscar garantias importantes: " + e.getMessage());
        }
    }


    @PutMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateGuarantee(@PathVariable("id") Integer id, @RequestBody Guarantee guaranteeDetails) {
        return guaranteeRepository.findById(id)
            .map(existingGuarantee -> {
                // Update the properties of the existing guarantee
                existingGuarantee.setTitle(guaranteeDetails.getTitle());
                existingGuarantee.setExpDateEdit(guaranteeDetails.getExpDateEdit());
                existingGuarantee.setExpDateCalendar(guaranteeDetails.getExpDateCalendar());
                existingGuarantee.setImportantCheck(guaranteeDetails.isImportantCheck());
                existingGuarantee.setRemDateCalendar(guaranteeDetails.getRemDateCalendar());
                existingGuarantee.setNotes(guaranteeDetails.getNotes());
                // ... other properties as needed

                // Save the updated guarantee back to the database
                Guarantee updatedGuarantee = guaranteeRepository.save(existingGuarantee);
                return ResponseEntity.ok(updatedGuarantee);
            })
            .orElseGet(() -> ResponseEntity.notFound().build()); // Handle the case where the guarantee ID is not found
    }
    
        
    //adicionar garantia e associar a uma fatura
    
    @PostMapping(path = "/add/{faturaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addGuaranteeToFatura(@PathVariable Integer faturaId, @RequestBody Guarantee guarantee) {
        Optional<Fatura> optionalFatura = faturaRepository.findById(faturaId);

        if (optionalFatura.isPresent()) {
            Fatura fatura = optionalFatura.get();
            guarantee.setFatura(fatura);
            Guarantee savedGuarantee = guaranteeRepository.save(guarantee);

            fatura.setGuarantee(savedGuarantee);
            faturaRepository.save(fatura); // Save the Fatura to update the foreign key reference

            return ResponseEntity.status(HttpStatus.CREATED).body(savedGuarantee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fatura with ID " + faturaId + " not found.");
        }
    }

    //getbyID method
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGuaranteeById(@PathVariable("id") Integer id) {
        logger.info("Fetching guarantee with id: " + id);
        Optional<Guarantee> optionalGuarantee = guaranteeRepository.findById(id);

        if (optionalGuarantee.isPresent()) {
            Guarantee guarantee = optionalGuarantee.get();
            logger.info("Fetched guarantee with id: " + id);
            return ResponseEntity.ok(guarantee);
        } else {
            logger.info("Guarantee with id " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Guarantee with id " + id + " not found.");
        }
    }



    @Transactional
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        logger.info("Attempting to delete guarantee with id: " + id);

        if (guaranteeRepository.existsById(id.intValue())) {
            guaranteeRepository.deleteById(id.intValue());
            logger.info("Deleted guarantee with id:" + id);
            return ResponseEntity.ok("Guarantee with id " + id + " was successfully deleted.");
        } else {
            logger.info("Guarantee with id " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Guarantee with id " + id + " not found.");
        }
    }
    
}
