package br.com.kecyo.resizephotos.entities.json.request;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ImageB2WDTOTest {

    @Test
    public void successNameAndUrl(){
        ImageB2WDTO imageB2WDTO = new ImageB2WDTO();
        imageB2WDTO.setUrl("http://54.152.221.29/images/b737_5.jpg");

        assertThat(imageB2WDTO.getUrl(), is(equalTo("http://54.152.221.29/images/b737_5.jpg")));
        assertThat(imageB2WDTO.getName(), is(equalTo("b737_5")));
    }

    @Test
    public void successNameIsEmtpy(){
        ImageB2WDTO imageB2WDTO = new ImageB2WDTO();
        assertNull(imageB2WDTO.getUrl());
        assertThat(imageB2WDTO.getName(), isEmptyString());
    }

    @Test
    public void extensionNotExists(){
        ImageB2WDTO imageB2WDTO = new ImageB2WDTO();
        imageB2WDTO.setUrl("http://54.152.221.29/images/b737_5");

        assertThat(imageB2WDTO.getUrl(), is(equalTo("http://54.152.221.29/images/b737_5")));
        assertThat(imageB2WDTO.getName(), is(equalTo("b737_5")));
    }

    @Test
    public void separatorNotExists(){
        ImageB2WDTO imageB2WDTO = new ImageB2WDTO();
        imageB2WDTO.setUrl("b737_5.jpg");

        assertThat(imageB2WDTO.getUrl(), is(equalTo("b737_5.jpg")));
        assertThat(imageB2WDTO.getName(), is(equalTo("b737_5")));
    }
}
