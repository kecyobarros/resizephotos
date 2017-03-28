package br.com.kecyo.resizephotos.entities.json.request;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ImagesResponseB2WDTO implements Serializable{

    private List<ImageB2WDTO> images;

    public ImagesResponseB2WDTO(){
       images = new ArrayList<>();
    }
}
