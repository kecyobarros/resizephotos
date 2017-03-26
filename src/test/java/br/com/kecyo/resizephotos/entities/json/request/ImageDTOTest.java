package br.com.kecyo.resizephotos.entities.json.request;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ImageDTOTest {

    @Test
    public void successNameAndUrl(){
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setUrl("http://54.152.221.29/images/b737_5.jpg");

        assertThat(imageDTO.getUrl(), is(equalTo("http://54.152.221.29/images/b737_5.jpg")));
        assertThat(imageDTO.getName(), is(equalTo("b737_5")));
    }

    @Test
    public void successNameIsEmtpy(){
        ImageDTO imageDTO = new ImageDTO();
        assertNull(imageDTO.getUrl());
        assertThat(imageDTO.getName(), isEmptyString());
    }

    @Test
    public void extensionNotExists(){
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setUrl("http://54.152.221.29/images/b737_5");

        assertThat(imageDTO.getUrl(), is(equalTo("http://54.152.221.29/images/b737_5")));
        assertThat(imageDTO.getName(), is(equalTo("b737_5")));
    }

    @Test
    public void separatorNotExists(){
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setUrl("b737_5.jpg");

        assertThat(imageDTO.getUrl(), is(equalTo("b737_5.jpg")));
        assertThat(imageDTO.getName(), is(equalTo("b737_5")));
    }
}
