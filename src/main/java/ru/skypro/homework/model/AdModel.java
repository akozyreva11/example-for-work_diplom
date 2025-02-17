package ru.skypro.homework.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


/**
 * Модель для хранения и обработки сущностей "объявление"
 */

@Entity
@Table(name = "ads")

public class AdModel {
    /**
     * Идентификатор объявления. Генерируется на уровне базы данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Ссылка на эндпоинт, по которому доступна загрузка изображения из объявления. Генерируется в процессе обработки
     * запроса на добавление объявления в методе setImageToAd() в сервисе {@link ru.skypro.homework.service.AdService}
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
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