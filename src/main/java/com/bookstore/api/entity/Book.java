package com.bookstore.api.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import java.math.BigDecimal;
@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
@Entity 
@Table (name="books")
public class Book {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String author;

    @Column(nullable=false,unique = true)
    private String isbn;

    @Column(nullable=false)
    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @Column(nullable = false)
    @Min(0)
    private Integer stockQuantity;


}
