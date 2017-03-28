package br.com.kecyo.resizephotos.entities.json.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ImageDTO {

    private String name;

    private List<String> urls;

}
