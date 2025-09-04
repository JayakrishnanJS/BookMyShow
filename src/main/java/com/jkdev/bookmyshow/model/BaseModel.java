package com.jkdev.bookmyshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass // so that JPA knows this is not a table but a base class for other entities
@EntityListeners(AuditingEntityListener.class) // to enable auditing for createdAt and lastModifiedAt
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate // to automatically set the creation date
    @Temporal(TemporalType.DATE) // to store only the date part
    private String createdAt;

    @LastModifiedDate
    private String lastModifiedAt;
}
