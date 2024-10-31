

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class FilmService {
    @PersistenceContext
    private EntityManager entityManager;

    public FilmService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createCustomer(String firstName, String lastName, String email) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setCreateDate(LocalDateTime.now());
            customer.setRentals(new ArrayList<>()); // Инициализируем список аренд

            entityManager.persist(customer);
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

    public void rentFilm(Long customerId, Long inventoryId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Customer customer = entityManager.find(Customer.class, customerId);
            Inventory inventory = entityManager.find(Inventory.class, inventoryId);

            // Проверка доступности инвентаря для аренды
            if (inventory.getRentals().isEmpty() || inventory.getRentals().get(inventory.getRentals().size() - 1).getClass() != null) {
                Rental rental = new Rental();
                rental.setCustomer(customer);
                rental.setInventory(inventory);
                rental.setRentalDate(LocalDateTime.now());

                entityManager.persist(rental);
            } else {
                throw new IllegalStateException("Фильм недоступен для аренды");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void addNewFilm(String title, String description, Language language) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Film film = new Film();
            film.setTitle(title);
            film.setDescription(description);
            film.setLanguage(language);

            entityManager.persist(film);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Rental> getRentalsForFilm(Long filmId) {
        Film film = entityManager.find(Film.class, filmId);
        return film.getRentals();  // Получаем список аренд для данного фильма
    }

    public void returnRentedFilm(long l) {
    }

    public void rentInventory(long l, long l1, long l2) {
    }
}

