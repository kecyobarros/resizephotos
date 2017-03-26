package br.com.kecyo.resizephotos.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "images")
@Builder
public class Image {

    @Id
    private String id;

    private String name;

    private ResolutionType resolution;

    private byte[] bytes;
}
