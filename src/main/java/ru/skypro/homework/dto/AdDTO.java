package ru.skypro.homework.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class AdDTO {

        /**
         * id автора объявления
         */
        private int author;
        /**
         * Ссылка на картинку объявления
         */
        private String image;
        /**
         * id объявления
         */
        private int pk;
        /**
         * Цена объявления
         */
        private int price;
        /**
         * Заголовок объявления
         */
        private String title;

        public int getAuthor() {
                return author;
        }

        public void setAuthor(int author) {
                this.author = author;
        }

        public String getImage() {
                return image;
        }

        public void setImage(String image) {
                this.image = image;
        }

        public int getPk() {
                return pk;
        }

        public void setPk(int pk) {
                this.pk = pk;
        }

        public int getPrice() {
                return price;
        }

        public void setPrice(int price) {
                this.price = price;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        @Override
        public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                AdDTO adDTO = (AdDTO) o;
                return getAuthor() == adDTO.getAuthor() && getPk() == adDTO.getPk() && getPrice() == adDTO.getPrice() && Objects.equals(getImage(), adDTO.getImage()) && Objects.equals(getTitle(), adDTO.getTitle());
        }

        @Override
        public int hashCode() {
                return Objects.hash(getAuthor(), getImage(), getPk(), getPrice(), getTitle());
        }
}
