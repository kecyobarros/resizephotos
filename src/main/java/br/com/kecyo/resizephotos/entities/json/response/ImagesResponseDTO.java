package br.com.kecyo.resizephotos.entities.json.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter
public class ImagesResponseDTO implements Serializable{

    private List<ImageDTO> images;

    public ImagesResponseDTO(@JsonProperty("images") List<ImageDTO> images){
       this.images = images;
    }
}
