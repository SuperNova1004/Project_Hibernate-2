import com.p6spy.engine.logging.Category;
import jakarta.persistence.*;
import jdk.javadoc.internal.doclets.formats.html.taglets.SnippetTaglet;

import java.util.List;

@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filmId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private SnippetTaglet.Language language;

    @OneToOne(mappedBy = "film", cascade = CascadeType.ALL)
    private FilmText filmText;

    @ManyToMany
    @JoinTable(name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SnippetTaglet.Language getLanguage() {
        return language;
    }

    public void setLanguage(SnippetTaglet.Language language) {
        this.language = language;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public FilmText getFilmText() {
        return filmText;
    }

    public void setFilmText(FilmText filmText) {
        this.filmText = filmText;
    }
}

