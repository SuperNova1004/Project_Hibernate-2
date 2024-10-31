import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("moviePU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            FilmService filmService = new FilmService(entityManager);

            // Создаем новый язык
            Language language = new Language();
            language.setName("English");
            entityManager.getTransaction().begin();
            entityManager.persist(language); // Сохраняем язык перед его использованием
            entityManager.getTransaction().commit();

            // Создаем нового покупателя
            filmService.createCustomer("Имя", "Фамилия", "email@example.com");

            // Создаем новый фильм
            filmService.addNewFilm("Название фильма", "Описание фильма", language);

            // Арендуем фильм (здесь используйте реальные ID)
            filmService.rentFilm(1L, 1L); // Замените на реальные идентификаторы

            // Возвращаем фильм (здесь также используйте реальный ID)
            filmService.returnRentedFilm(1L); // Замените на реальный идентификатор аренды

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





