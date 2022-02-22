package models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_url_collections", indexes = @Index(columnList = "user_id", name = "fk_idx_user_id"))
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
    private List<Links> linksList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UrlCollections that = (UrlCollections) o;
        return collectionId != null && Objects.equals(collectionId, that.collectionId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

