package br.com.kecyo.resizephotos.entities.json.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class ImageDTO implements Serializable{

    private String url;

    @JsonIgnore
    public String getName(){
        String name = Optional.ofNullable(url).orElse("");
        return name.substring(name.lastIndexOf("/")+1, name.length()).replace(".jpg","");
    }
}
