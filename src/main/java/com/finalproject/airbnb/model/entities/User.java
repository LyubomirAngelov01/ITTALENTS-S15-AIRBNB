package com.finalproject.airbnb.model.entities;

import com.finalproject.airbnb.Utility;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
@Setter
@Getter
@Entity(name = "users")
public class User extends BaseEntity{

    @NotBlank(message = "enter a valid first name")
    @Column(name = "first_name")
    private String firstName;
    @NotBlank(message = "enter a valid last name")
    @Column(name = "last_name")
    private String lastName;
    @Column
    private LocalDate birthdate;

    @Column
    @Email(message = "invalid email")
    private String email;

    @Column
    //@Pattern(regexp = "^(?=.*d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[!@#$%^_*-]).{8,}$", message = "Weak password")
    private String password;
    @ManyToOne
    @JoinColumn(name = "country_code_id", referencedColumnName = "id")
    private CountryCode countryCode;


    @Column(name = "phone_number")
    @NotBlank
    @Pattern(regexp = "^[0-9]+$",message = "invalid phone number")
    private String phoneNumber;



    @Column(name = "is_host")
    private boolean isHost;


}
