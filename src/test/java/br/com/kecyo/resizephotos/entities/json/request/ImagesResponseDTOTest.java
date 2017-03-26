package br.com.kecyo.resizephotos.entities.json.request;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ImagesResponseDTOTest {

    @Test
    public void success(){
        ImagesResponseDTO imageDTO = new ImagesResponseDTO();
        assertNotNull(imageDTO.getImages());
    }

    @Test
    public void successSetImages(){
        ImagesResponseDTO imageDTO = new ImagesResponseDTO();
        imageDTO.setImages(Arrays.asList(new ImageDTO()));

        assertNotNull(imageDTO.getImages());
        assertThat(imageDTO.getImages().size(), is(equalTo(1)));
    }

}
