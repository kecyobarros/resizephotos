package br.com.kecyo.resizephotos.entities.json.request;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ImagesResponseDTO implements Serializable{

    private List<ImageDTO> images;

    public ImagesResponseDTO(){
       images = new ArrayList<>();
    }
}
