package br.com.kecyo.resizephotos.entities;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ResolutionTypeTest {


    @Test
    public void validationEnum(){
        assertThat(ResolutionType.SMALL.getWidth(), is(equalTo(320)));
        assertThat(ResolutionType.SMALL.getHeight(), is(equalTo(240)));
        assertThat(ResolutionType.MEDIUM.getWidth(), is(equalTo(384)));
        assertThat(ResolutionType.MEDIUM.getHeight(), is(equalTo(288)));
        assertThat(ResolutionType.LARGE.getWidth(), is(equalTo(640)));
        assertThat(ResolutionType.LARGE.getHeight(), is(equalTo(480)));
    }

    @Test
    public void testValues(){
        assertThat(ResolutionType.values().length, is(equalTo(3)));
    }
}
