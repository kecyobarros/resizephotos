package br.com.kecyo.resizephotos.usescases.impl;

import br.com.kecyo.resizephotos.config.rest.RestProperties;
import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.ResolutionType;
import br.com.kecyo.resizephotos.entities.json.request.ImagesResponseDTO;
import br.com.kecyo.resizephotos.gateways.ImagesGateway;
import br.com.kecyo.resizephotos.usescases.Process;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class ImagesProcessImplTest {

    private RestTemplate restTemplate;

    private RestProperties restProperties;

    private ImagesGateway imagesGateway;

    private Process imagesProcess;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void before(){
        restTemplate = mock(RestTemplate.class);
        restProperties = mock(RestProperties.class);
        imagesGateway = mock(ImagesGateway.class);
        imagesProcess = new ImagesProcessImpl(restTemplate,restProperties,imagesGateway);
    }

    @Test
    public void success() throws URISyntaxException, IOException {

        String json = Files.toString(new File(getUrl("json/images.json")), Charset.defaultCharset());;

        byte[] image = Files.toByteArray(new File(getUrl("image/b737_5.jpg")));

        ImagesResponseDTO imagesResponseDTO = mapper.readValue(json, ImagesResponseDTO.class);

        ResponseEntity<ImagesResponseDTO> response = new ResponseEntity<>(imagesResponseDTO, HttpStatus.OK);

        doReturn(response).when(restTemplate).getForEntity(anyString(), eq(ImagesResponseDTO.class));
        doReturn(image).when(restTemplate).getForObject(anyString(), eq(byte[].class));
        doReturn("http://54.152.221.29").when(restProperties).getImagesRepository();
        doReturn(false).when(imagesGateway).existsByNameAndResolution(anyString(), any(ResolutionType.class));

        imagesProcess.process();

        verify(imagesGateway, times(30)).save(any(Image.class));
        verify(imagesGateway, times(30)).existsByNameAndResolution(anyString(), any(ResolutionType.class));
        verify(restTemplate, times(10)).getForObject(anyString(), eq(byte[].class));
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(ImagesResponseDTO.class));

    }

    @Test
    public void imagesResponseIsEmpty() throws URISyntaxException, IOException {
        String json = Files.toString(new File(getUrl("json/images.json")), Charset.defaultCharset());;

        ImagesResponseDTO imagesResponseDTO = mapper.readValue(json, ImagesResponseDTO.class);
        imagesResponseDTO.getImages().clear();

        ResponseEntity<ImagesResponseDTO> response = new ResponseEntity<>(imagesResponseDTO, HttpStatus.OK);

        doReturn(response).when(restTemplate).getForEntity(anyString(), eq(ImagesResponseDTO.class));
        doReturn("http://54.152.221.29").when(restProperties).getImagesRepository();

        imagesProcess.process();

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(ImagesResponseDTO.class));
        verify(imagesGateway, times(0)).save(any(Image.class));
        verify(imagesGateway, times(0)).existsByNameAndResolution(anyString(), any(ResolutionType.class));
        verify(restTemplate, times(0)).getForObject(anyString(), eq(byte[].class));
    }

    @Test
    public void successExists() throws URISyntaxException, IOException {

        String json = Files.toString(new File(getUrl("json/images.json")), Charset.defaultCharset());;

        byte[] image = Files.toByteArray(new File(getUrl("image/b737_5.jpg")));

        ImagesResponseDTO imagesResponseDTO = mapper.readValue(json, ImagesResponseDTO.class);

        ResponseEntity<ImagesResponseDTO> response = new ResponseEntity<>(imagesResponseDTO, HttpStatus.OK);

        doReturn(response).when(restTemplate).getForEntity(anyString(), eq(ImagesResponseDTO.class));
        doReturn(image).when(restTemplate).getForObject(anyString(), eq(byte[].class));
        doReturn("http://54.152.221.29").when(restProperties).getImagesRepository();
        doReturn(true).when(imagesGateway).existsByNameAndResolution(anyString(), any(ResolutionType.class));

        imagesProcess.process();

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(ImagesResponseDTO.class));
        verify(restTemplate, times(10)).getForObject(anyString(), eq(byte[].class));
        verify(imagesGateway, times(30)).existsByNameAndResolution(anyString(), any(ResolutionType.class));
        verify(imagesGateway, times(0)).save(any(Image.class));
    }


    @Test(expected = UnknownHostException.class)
    public void errorHost() throws URISyntaxException, IOException {
        doThrow(UnknownHostException.class).when(restTemplate).getForEntity(anyString(), eq(ImagesResponseDTO.class));
        doReturn("http://54.152.221.29").when(restProperties).getImagesRepository();
        imagesProcess.process();
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

    private URI getUrl(String path) throws URISyntaxException {
        return this.getClass().getClassLoader().getResource(path).toURI();
    }
}
