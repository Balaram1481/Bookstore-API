package com.bookstore.api.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity 
@Table(name ="audit_logs")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class AuditLog {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userEmail;
    @Column(nullable = false)
    private String userRole;
    @Column(nullable=false)
    private String action;
    @Column(nullable=false)
    private String httpMethod;
    @Column(nullable=false)
    private String endpoint;
    @Column(columnDefinition = "TEXT")
    private String details;
    @Column(nullable=false)
    private String ipAddress;
    @Column(nullable=false)
    private LocalDateTime timestamp;
}
