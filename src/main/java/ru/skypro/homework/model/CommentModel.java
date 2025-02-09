package ru.skypro.homework.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Модель для хранения и обработки сущностей "комментарий"
 */

@Entity
@Table(name = "comments")
public class CommentModel {
    /**
     * Идентификатор комментария. Генерируется на уровне базы данных
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Время создания комментария. Генерируется автоматически
     */
    private Timestamp createdAt;
    /**
     * Текстовое содержание комментария, заданное автором
     */
    @Length(min = 8, max = 64)
    private String text;
    /**
     * Ссылка на сущность объявления {@link AdModel},к которому привязан комментарий. При удалении из базы данных объявления
     * автоматически удаляются также все связанные с ним комментарии
     */
    @ManyToOne
    @JoinColumn(name = "ad_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AdModel ad;
    /**
     * Ссылка на сущность пользователя {@link UserModel}, который является автором комментария
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    public CommentModel() {
    }

    public CommentModel(String text) {
        this.text = text;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AdModel getAd() {
        return ad;
    }

    public void setAd(AdModel ad) {
        this.ad = ad;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CommentModel that = (CommentModel) o;
        return Objects.equals(id, that.id) && Objects.equals(createdAt, that.createdAt) && Objects.equals(text, that.text) && Objects.equals(ad, that.ad) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, text, ad, user);
    }
}