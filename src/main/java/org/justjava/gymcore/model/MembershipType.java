package org.justjava.gymcore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class MembershipType {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String description;
        private BigDecimal price;

        // Default constructor is required by JPA
        public MembershipType() {}

        public MembershipType(String name, String description, BigDecimal price) {
                this.name = name;
                this.description = description;
                this.price = price;
        }

        // Getters and Setters
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public BigDecimal getPrice() {
                return price;
        }

        public void setPrice(BigDecimal price) {
                this.price = price;
        }
}
