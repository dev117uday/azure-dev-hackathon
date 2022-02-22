package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_user")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("userId")
    private Long userId;

    @Size(max = 100, message = "user_name too long")
    @JsonProperty("userEmail")
    private String userEmail;

    @Size(max = 255, message = "user_email too long")
    @JsonProperty("userName")
    private String userName;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    private List<UrlCollections> collections;

    public User(String userEmail, String userName) {
        this.userEmail = userEmail;
        this.userName = userName;
    }
}
