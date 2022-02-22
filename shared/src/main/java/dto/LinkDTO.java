package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.Links;
import models.UrlCollections;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkDTO {

    private Long linkId;
    private String urlLink;
    private String linkDescription;
    private Long collectionId;

    public static Links linkDtoToLink(LinkDTO linkDTO) {
        return new Links(
                linkDTO.getLinkId(),
                linkDTO.getUrlLink(),
                linkDTO.getLinkDescription(),
                UrlCollections.builder().collectionId(linkDTO.getCollectionId()).build()
        );
    }

    public static LinkDTO linksTolinkDTO(Links link) {
        return new LinkDTO(
                link.getLinkId(),
                link.getUrlLink(),
                link.getLinkDescription(),
                link.getCollection().getCollectionId()
        );
    }
}
