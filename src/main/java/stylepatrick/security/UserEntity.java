package stylepatrick.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter(AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private String login;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @Column(name = "role")
    private String role;
}
