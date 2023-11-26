package by.clevertec.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
}
