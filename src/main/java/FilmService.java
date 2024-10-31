import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;

public class FilmService {
    @PersistenceContext
    private EntityManager entityManager;

    public void createCustomer(String firstName, String lastName, String email) {

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setCreateDate(LocalDateTime.now());

            entityManager.persist(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void rentFilm(Long customerId, Long inventoryId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Customer customer = entityManager.find(Customer.class, customerId);
            Inventory inventory = entityManager.find(Inventory.class, inventoryId);

            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setRentalDate(LocalDateTime.now());

            entityManager.persist(rental);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void returnFilm(Long rentalId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Rental rental = entityManager.find(Rental.class, rentalId);
            rental.setReturnDate(LocalDateTime.now());

            entityManager.merge(rental);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}

