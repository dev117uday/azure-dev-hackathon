package models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_links")
@Builder
public class Links {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long linkId;
    private String urlLink;
    private String linkDescription;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "collection_id",
            referencedColumnName = "collectionId"
    )
    private UrlCollections collection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Links links = (Links) o;
        return linkId != null && Objects.equals(linkId, links.linkId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
