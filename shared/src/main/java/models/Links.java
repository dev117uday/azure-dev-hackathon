package models;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tbl_links")
@NoArgsConstructor
@AllArgsConstructor
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
    @ToString.Exclude
    private UrlCollections collection;

}
