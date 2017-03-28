package br.com.kecyo.resizephotos.entities.json.request;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ImagesResponseB2WDTOTest {

    @Test
    public void success(){
        ImagesResponseB2WDTO imageDTO = new ImagesResponseB2WDTO();
        assertNotNull(imageDTO.getImages());
    }

    @Test
    public void successSetImages(){
        ImagesResponseB2WDTO imageDTO = new ImagesResponseB2WDTO();
        imageDTO.setImages(Arrays.asList(new ImageB2WDTO()));

        assertNotNull(imageDTO.getImages());
        assertThat(imageDTO.getImages().size(), is(equalTo(1)));
    }

}
