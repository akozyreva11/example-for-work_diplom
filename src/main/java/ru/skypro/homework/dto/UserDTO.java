package ru.skypro.homework.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Data

public class UserDTO {
    /**
     * id пользователя
     */
    private int id;
    /**
     * Логин пользователя
     */
    private String email;
    /**
     * Имя пользователя
     */
    private String firstName;
    /**
     * Фамилия пользователя
     */
    private String lastName;
    /**
     * Телефон пользователя
     */
    private String phone;
    /**
     * Телефон пользователя
     */
    private Role role;
    /**
     * Ссылка на аватар пользователя
     */
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return getId() == userDTO.getId() && Objects.equals(getEmail(), userDTO.getEmail()) && Objects.equals(getFirstName(), userDTO.getFirstName()) && Objects.equals(getLastName(), userDTO.getLastName()) && Objects.equals(getPhone(), userDTO.getPhone()) && getRole() == userDTO.getRole() && Objects.equals(getImage(), userDTO.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getFirstName(), getLastName(), getPhone(), getRole(), getImage());
    }
}