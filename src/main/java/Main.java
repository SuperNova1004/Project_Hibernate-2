import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        // Создаем EntityManagerFactory
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("moviePU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            FilmService filmService = new FilmService(entityManager);
            entityManager.getTransaction().begin();

            // Создаем нового покупателя
            filmService.createCustomer("Имя", "Фамилия", "email@example.com");

            // Создаем новый язык и сохраняем его
            Language language = new Language(); // Предположим, что у вас уже есть созданный язык
            language.setName("English");
            entityManager.persist(language); // Сохраняем язык перед его использованием

            // Создаем новый фильм
            filmService.addNewFilm("Название фильма", "Описание фильма", language);

            // Арендуем фильм (здесь используйте реальные ID)
            filmService.rentInventory(1L, 1L, 1L); // Замените на реальные идентификаторы

            // Возвращаем фильм (здесь также используйте реальный ID)
            filmService.returnRentedFilm(1L); // Замените на реальный идентификатор аренды

            entityManager.getTransaction().commit();
            System.out.println("Все операции выполнены успешно.");
        } catch (Exception e) {
            // Откат транзакции в случае ошибки
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}




