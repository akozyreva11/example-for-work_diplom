package ru.skypro.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Модель для хранения и обработки сущности "пользователь"
 */

@Entity
@Table(name = "users")
public class UserModel {
    /**
     * Идентификатор пользователя. Генерируется на уровне базы данных
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Адрес электронной почты, указанный пользователем при регистрации в приложении. Используется для идентификации
     * пользователя при авторизации, а также внутри логики приложения. Поэтому имеет ограничения уникальности и не может
     * быть Null.
     */
    @Column(name = "user_name", nullable = false, unique = true)
    private String email;
    /**
     * Пароль для входа в личный кабинет и доступа ко всем эндпоинтам, требующим аутентификации. Хранится в базе данных
     * в зашифрованном виде.
     */
    @Length(min = 6, max = 16)
    private String password;
    /**
     * Имя пользователя, указанное при регистрации
     */
    @Length(min = 2, max = 16)
    private String firstName;
    /**
     * Фамилия пользователя, указанная при регистрации
     */
    @Length(min = 2, max = 16)
    private String lastName;
    /**
     * Номер телефона пользователя, указанный при регистрации. Значение поля валидируется при помощи регулярного выражения.
     */
    private String phone;
    /**
     * Роль пользователя в системе, определяющая набор доступных ему функций. Задается при регистрации пользователя.
     * Поле содержит одно из константных значений, определенных в классе.
     */
    private Role role;
    /**
     * Ссылка на эндпоинт, по которому доступна загрузка изображения - аватара пользователя. Генерируется в процессе обработки
     * запроса на обновление аватара пользователя в методе updateUserAvatar() в сервисе {@link ru.skypro.homework.service.UserService}
     */
    private String image;
    /**
     * Список сущностей - объявлений {@link AdModel}, которые связаны с пользователем. Для извлечения из базы данных
     * объявлений при обращении к сущности пользователя используется тип извлечения LAZY для нивелирования эффекта N+1
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private List<AdModel> ads;
    /**
     * Список сущностей - комментариев {@link CommentModel}, авторами которых является пользователь. Для извлечения из базы данных
     * комментариев при обращении к сущности пользователя используется тип извлечения LAZY для нивелирования эффекта N+1
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private List<CommentModel> comments;

    public UserModel() {
    }

    public UserModel(String email, String password, String firstName, String lastName, String phone, Role role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<AdModel> getAds() {
        return ads;
    }

    public void setAds(List<AdModel> ads) {
        this.ads = ads;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(getEmail(), userModel.getEmail()) && Objects.equals(getPassword(), userModel.getPassword())
                && Objects.equals(getFirstName(), userModel.getFirstName()) && Objects.equals(getLastName(),
                userModel.getLastName()) && Objects.equals(getPhone(), userModel.getPhone()) && getRole() == userModel.getRole()
                && Objects.equals(getImage(), userModel.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword(), getFirstName(), getLastName(), getPhone(), getRole(), getImage());
    }
}
