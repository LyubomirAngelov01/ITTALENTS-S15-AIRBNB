package com.finalproject.airbnb.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserEntity extends BaseEntity{

    @NotBlank(message = "enter a valid first name")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "enter a valid last name")
    @Column(name = "last_name")
    private String lastName;

    @Column
    private LocalDate birthdate;

    @Column
    private String email;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "country_code_id", referencedColumnName = "id")
    private CountryCode countryCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_host")
    private boolean isHost;

}
