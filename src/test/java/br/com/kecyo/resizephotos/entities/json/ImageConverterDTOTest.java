package br.com.kecyo.resizephotos.entities.json;

import br.com.kecyo.resizephotos.config.app.ConfigProperties;
import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.ResolutionType;
import br.com.kecyo.resizephotos.entities.json.response.ImageDTO;
import br.com.kecyo.resizephotos.exception.MalformedUrlImageException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ImageConverterDTOTest {

    private ConfigProperties configProperties;

    private ImageConverterDTO imageConverterDTO;

    @Before
    public void before(){
        configProperties = mock(ConfigProperties.class);
        imageConverterDTO = new ImageConverterDTO(configProperties);
    }

    @Test
    public void successConvert(){

        Map<String, List<Image>> images = new HashMap<>();
        images.put("teste", Arrays.asList(Image.builder()
                                                .name("teste")
                                                .resolution(ResolutionType.LARGE)
                                                .build(),
                                        Image.builder()
                                                .name("teste")
                                                .resolution(ResolutionType.SMALL)
                                                .build(),
                                        Image.builder()
                                                .name("teste")
                                                .resolution(ResolutionType.MEDIUM)
                                                .build()));

        doReturn("/resizephotos").when(configProperties).getContextPath();
        doReturn("8090").when(configProperties).getServerPort();

        ImageDTO result = imageConverterDTO.convert(images.entrySet().stream().findFirst().get());

        assertNotNull(result);
        assertThat(result.getName(), is(equalTo("teste")));
        assertThat(result.getUrls().size(), is(equalTo(3)));
    }

    @Test(expected = MalformedUrlImageException.class)
    public void successConvertNameIsEmptu(){

        Map<String, List<Image>> images = new HashMap<>();
        images.put("teste", Arrays.asList(Image.builder()
                        .resolution(ResolutionType.LARGE)
                        .build(),
                Image.builder()
                        .resolution(ResolutionType.SMALL)
                        .build(),
                Image.builder()
                        .resolution(ResolutionType.MEDIUM)
                        .build()));

        doReturn("/resizephotos").when(configProperties).getContextPath();
        doReturn("8090").when(configProperties).getServerPort();

        ImageDTO result = imageConverterDTO.convert(images.entrySet().stream().findFirst().get());

    }
}
