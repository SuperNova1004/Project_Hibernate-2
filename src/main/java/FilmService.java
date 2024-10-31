import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;

public class FilmService {

    private final EntityManager entityManager;

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
            entityManager.persist(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    public void returnRentedFilm(Long rentalId) {

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Rental rental = entityManager.find(Rental.class, rentalId);
            if (rental == null) {
                throw new IllegalArgumentException("Rental not found");
            }
            rental.setReturnDate(LocalDateTime.now());
            entityManager.merge(rental);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    public void rentFilm(Long customerId, Long filmId) {

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Customer customer = entityManager.find(Customer.class, customerId);
            Film film = entityManager.find(Film.class, filmId);

            // Проверка доступности фильма для аренды
            boolean isAvailable = entityManager.createQuery(
                            "SELECT COUNT(r) FROM Rental r WHERE r.film.id = :filmId AND r.returnDate IS NULL", Long.class)
                    .setParameter("filmId", filmId)
                    .getSingleResult() == 0;

            if (!isAvailable) {
                throw new IllegalStateException("Фильм уже арендован.");
            }

            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setFilm(film);
            rental.setRentalDate(LocalDateTime.now());
            entityManager.persist(rental);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
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
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }
}


