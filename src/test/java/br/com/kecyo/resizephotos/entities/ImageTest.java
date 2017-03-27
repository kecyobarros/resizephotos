package br.com.kecyo.resizephotos.entities;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ImageTest {


    @Test
    public void testSuccess(){
        Image image = Image.builder().build();
        image.setId("testeId");
        image.setName("testeName");
        image.setResolution(ResolutionType.LARGE);
        image.setBytes("testeBytes".getBytes());

        assertThat(image.getId(), is(equalTo("testeId")));
        assertThat(image.getName(), is(equalTo("testeName")));
        assertThat(new String(image.getBytes()), is(equalTo("testeBytes")));
        assertThat(image.getResolution(), is(equalTo(ResolutionType.LARGE)));
        assertThat(image.builder().toString(), is(equalTo("Image.ImageBuilder(id=null, name=null, resolution=null, bytes=null, version=null)")));
    }
}
