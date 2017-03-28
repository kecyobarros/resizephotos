package br.com.kecyo.resizephotos.usescases.impl;

import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.ResolutionType;
import br.com.kecyo.resizephotos.entities.json.ImageConverterDTO;
import br.com.kecyo.resizephotos.entities.json.request.ImageB2WDTO;
import br.com.kecyo.resizephotos.entities.json.request.ImagesResponseB2WDTO;
import br.com.kecyo.resizephotos.entities.json.response.ImageDTO;
import br.com.kecyo.resizephotos.gateways.ImagesGateway;
import br.com.kecyo.resizephotos.gateways.ImagesPartnerGateway;
import br.com.kecyo.resizephotos.gateways.impl.ImagesB2WGatewayImpl;
import br.com.kecyo.resizephotos.usescases.Process;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ImagesProcessImplTest {

    private ImagesGateway imagesGateway;

    private Process imagesProcess;

    private ObjectMapper mapper = new ObjectMapper();

    private ImageConverterDTO converterDTO;

    private ImagesPartnerGateway imagesB2WGateway;

    @Before
    public void before(){
        imagesB2WGateway = mock(ImagesB2WGatewayImpl.class);
        imagesGateway = mock(ImagesGateway.class);
        converterDTO = mock(ImageConverterDTO.class);
        imagesProcess = new ImagesProcessImpl(imagesGateway, converterDTO, imagesB2WGateway);
    }

    @Test
    public void success() throws URISyntaxException, IOException {

        String json = Files.toString(new File(getUrl("json/images.json")), Charset.defaultCharset());;

        byte[] image = Files.toByteArray(new File(getUrl("image/b737_5.jpg")));

        ImagesResponseB2WDTO imagesResponseB2WDTO = mapper.readValue(json, ImagesResponseB2WDTO.class);

        doReturn(imagesResponseB2WDTO).when(imagesB2WGateway).getImagesResponse();
        doReturn(image).when(imagesB2WGateway).getImage(any(ImageB2WDTO.class));
        doReturn(false).when(imagesGateway).existsByNameAndResolution(anyString(), any(ResolutionType.class));

        imagesProcess.process();

        verify(imagesGateway, times(30)).save(any(Image.class));
        verify(imagesGateway, times(30)).existsByNameAndResolution(anyString(), any(ResolutionType.class));
        verify(imagesB2WGateway, times(10)).getImage(any(ImageB2WDTO.class));
        verify(imagesB2WGateway, times(1)).getImagesResponse();

    }

    @Test
    public void imagesResponseIsEmpty() throws URISyntaxException, IOException {
        String json = Files.toString(new File(getUrl("json/images.json")), Charset.defaultCharset());;

        ImagesResponseB2WDTO imagesResponseB2WDTO = mapper.readValue(json, ImagesResponseB2WDTO.class);
        imagesResponseB2WDTO.getImages().clear();

        doReturn(imagesResponseB2WDTO).when(imagesB2WGateway).getImagesResponse();

        imagesProcess.process();

        verify(imagesB2WGateway, times(1)).getImagesResponse();
        verify(imagesGateway, times(0)).save(any(Image.class));
        verify(imagesGateway, times(0)).existsByNameAndResolution(anyString(), any(ResolutionType.class));
        verify(imagesB2WGateway, times(0)).getImage(any(ImageB2WDTO.class));
    }

    @Test
    public void successExists() throws URISyntaxException, IOException {

        String json = Files.toString(new File(getUrl("json/images.json")), Charset.defaultCharset());;

        byte[] image = Files.toByteArray(new File(getUrl("image/b737_5.jpg")));

        ImagesResponseB2WDTO imagesResponseB2WDTO = mapper.readValue(json, ImagesResponseB2WDTO.class);

        doReturn(imagesResponseB2WDTO).when(imagesB2WGateway).getImagesResponse();
        doReturn(image).when(imagesB2WGateway).getImage(any(ImageB2WDTO.class));
        doReturn(true).when(imagesGateway).existsByNameAndResolution(anyString(), any(ResolutionType.class));

        imagesProcess.process();

        verify(imagesB2WGateway, times(1)).getImagesResponse();
        verify(imagesB2WGateway, times(10)).getImage(any(ImageB2WDTO.class));
        verify(imagesGateway, times(30)).existsByNameAndResolution(anyString(), any(ResolutionType.class));
        verify(imagesGateway, times(0)).save(any(Image.class));
    }

    @Test
    public void testFindByNameAndResolutionResultValue() throws URISyntaxException, IOException {
        doReturn(Optional.ofNullable(Image.builder().build()))
                .when(imagesGateway).findByNameAndResolution(anyString(), any(ResolutionType.class));

        Optional<Image> result = imagesProcess.findByNameAndResolution("b737_5", ResolutionType.LARGE);

        verify(imagesGateway, times(0)).save(any(Image.class));

        assertTrue(result.isPresent());

    }

    @Test
    public void testFindByNameAndResolutionEmpty() throws URISyntaxException, IOException {
        doReturn(Optional.empty())
                .when(imagesGateway).findByNameAndResolution(anyString(), any(ResolutionType.class));

        Optional<Image> result = imagesProcess.findByNameAndResolution("b737_5", ResolutionType.LARGE);

        verify(imagesGateway, times(0)).save(any(Image.class));

        assertFalse(result.isPresent());

    }

    @Test
    public void testFindAll() throws URISyntaxException, IOException {
        ArgumentCaptor<Map.Entry> argumentCaptor = ArgumentCaptor.forClass(Map.Entry.class);

        doReturn(getListImage()).when(imagesGateway).findAll();

        doReturn(new ImageDTO("teste1", Arrays.asList("url1","url2","url3"))).when(converterDTO).convert(any());

        imagesProcess.findAll();

        verify(converterDTO).convert(argumentCaptor.capture());

        Map.Entry resultArgument = argumentCaptor.getValue();

        assertNotNull(resultArgument);
        assertThat(resultArgument.getKey(), is(equalTo("teste1")));
        List<Image> listImage = (List<Image>) resultArgument.getValue();
        assertThat(listImage.size(), is(equalTo(3)));

    }

    private List<Image> getListImage(){
        return Arrays.asList(
                Image.builder().name("teste1").resolution(ResolutionType.SMALL).build(),
                Image.builder().name("teste1").resolution(ResolutionType.MEDIUM).build(),
                Image.builder().name("teste1").resolution(ResolutionType.LARGE).build());



    }

    private URI getUrl(String path) throws URISyntaxException {
        return this.getClass().getClassLoader().getResource(path).toURI();
    }
}
