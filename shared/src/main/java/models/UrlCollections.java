package models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_url_collections", indexes = @Index(columnList = "user_id", name = "fk_idx_user_id"))
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlCollections {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long collectionId;

    @Size(max = 100)
    private String collectionName;

    @Size(max = 255)
    private String collectionDescription;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    @ToString.Exclude
    private User user;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "collection",
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    private List<Links> linksList;
}

