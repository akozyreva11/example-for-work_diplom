package ru.skypro.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


/**
 * Модель для хранения сущностей "объявление"
 */
@Getter
@Setter
@Entity
@Table(name = "ads")
public class AdModel {
    /**
     * Идентификатор объявления.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Ссылка на эндпоинт, по которому доступна загрузка изображения из объявления. Генерируется в процессе обработки
     */
    private String image;
    /**
     * Стоимость товара в объявлении, указанная автором
     */
    @Length(max = 10000000)
    private Integer price;
    /**
     * Заголовок объявления, указанный автором
     */
    @Length(min = 8, max = 64)
    private String title;
    /**
     * Текстовое описание объявления, указанное автором
     */
    @Length(min = 4, max = 32)
    private String description;
    /**
     * Ссылка на сущность пользователя {@link UserModel}, который является автором объявления
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
    /**
     * Список сущностей - комментариев {@link CommentModel}, которые связаны с объявлением. Для извлечения из базы данных
     * комментариев при обращении к сущности объявления используется тип извлечения LAZY для нивелирования эффекта N+1
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ad")
    @JsonIgnore
    private List<CommentModel> comments;

    public AdModel() {
    }

    public AdModel(Integer price, String title, String description) {
        this.price = price;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AdModel adModel = (AdModel) o;
        return Objects.equals(id, adModel.id) && Objects.equals(image, adModel.image) && Objects.equals(price, adModel.price) && Objects.equals(title, adModel.title) && Objects.equals(description, adModel.description) && Objects.equals(user, adModel.user) && Objects.equals(comments, adModel.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image, price, title, description, user, comments);
    }

}