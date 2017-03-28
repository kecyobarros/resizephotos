package br.com.kecyo.resizephotos.entities.json.response;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ImageDTOTest {

    @Test
    public void success(){
        ImageDTO imageDTO = new ImageDTO("teste", Arrays.asList("url1", "url2"));

        assertThat(imageDTO.getName(), is(equalTo("teste")));
        assertThat(imageDTO.getUrls().size(), is(equalTo(2)));
    }

    @Test
    public void success2(){
        ImageDTO imageDTO = new ImageDTO("teste", Arrays.asList("url1", "url2"));
        imageDTO.setName("teste2");
        imageDTO.setUrls(Arrays.asList("url"));

        assertThat(imageDTO.getName(), is(equalTo("teste2")));
        assertThat(imageDTO.getUrls().size(), is(equalTo(1)));
    }
}
