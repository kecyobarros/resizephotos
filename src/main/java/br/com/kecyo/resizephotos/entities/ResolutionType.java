package br.com.kecyo.resizephotos.entities;

import lombok.Getter;

@Getter
public enum ResolutionType {

    SMALL(320, 240),
    MEDIUM(384, 288),
    LARGE(640, 480);

    private Integer width;

    private Integer height;

    ResolutionType(final Integer width, final Integer height){
        this.width = width;
        this.height = height;
    }

}
