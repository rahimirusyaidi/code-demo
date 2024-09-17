package com.demo.assignment.dao;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

/**
 * @author rahimi.riduan
 */

@AllArgsConstructor
@Builder
@Entity(name = "Drug")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CustomDrug {

    @Id
    @GeneratedValue(generator = "UUID")
    UUID applicationId;

    String manufacturerName;

    @ElementCollection
    List<String> substanceName;

}
