package br.com.kecyo.resizephotos.entities.json.response;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ImagesResponseDTOTest {

    @Test
    public void success(){
        ImagesResponseDTO imagesResponseDTO = new ImagesResponseDTO(Arrays.asList(new ImageDTO("teste", Arrays.asList("url1", "url2"))));

        assertThat(imagesResponseDTO.getImages().size(), is(equalTo(1)));
        assertThat(imagesResponseDTO.getImages().get(0).getName(), is(equalTo("teste")));
        assertThat(imagesResponseDTO.getImages().get(0).getUrls().size(), is(equalTo(2)));
    }

    @Test
    public void success2(){
        ImagesResponseDTO imagesResponseDTO = new ImagesResponseDTO(Arrays.asList(new ImageDTO("teste", Arrays.asList("url1", "url2"))));

        imagesResponseDTO.setImages(Arrays.asList(new ImageDTO("teste2", Arrays.asList("url1"))));

        assertThat(imagesResponseDTO.getImages().size(), is(equalTo(1)));
        assertThat(imagesResponseDTO.getImages().get(0).getName(), is(equalTo("teste2")));
        assertThat(imagesResponseDTO.getImages().get(0).getUrls().size(), is(equalTo(1)));
    }
}
