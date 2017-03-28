package br.com.kecyo.resizephotos.gateways.impl;

import br.com.kecyo.resizephotos.config.rest.RestProperties;
import br.com.kecyo.resizephotos.entities.json.request.ImageB2WDTO;
import br.com.kecyo.resizephotos.entities.json.request.ImagesResponseB2WDTO;
import br.com.kecyo.resizephotos.gateways.ImagesPartnerGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class ImagesB2WGatewayImplTest {

    private RestTemplate restTemplate;

    private RestProperties restProperties;

    private ImagesPartnerGateway imagesPartnerGateway;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void before(){
        restTemplate = Mockito.mock(RestTemplate.class);
        restProperties = Mockito.mock(RestProperties.class);
        imagesPartnerGateway = new ImagesB2WGatewayImpl(restTemplate, restProperties);
    }

    @Test
    public void success() throws IOException, URISyntaxException {

        String json = Files.toString(new File(getUrl("json/images.json")), Charset.defaultCharset());;

        ImagesResponseB2WDTO imagesResponseB2WDTO = mapper.readValue(json, ImagesResponseB2WDTO.class);

        ResponseEntity<ImagesResponseB2WDTO> response = new ResponseEntity<>(imagesResponseB2WDTO, HttpStatus.OK);

        doReturn(response).when(restTemplate).getForEntity(anyString(), eq(ImagesResponseB2WDTO.class));

        ImagesResponseB2WDTO imagesResponse = imagesPartnerGateway.getImagesResponse();

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(ImagesResponseB2WDTO.class));

        Assert.assertNotNull(imagesResponse);
        Assert.assertThat(imagesResponse.getImages().size(), is(equalTo(10)));

    }

    @Test(expected = UnknownHostException.class)
    public void errorHost() throws URISyntaxException, IOException {
        doThrow(UnknownHostException.class).when(restTemplate).getForEntity(anyString(), eq(ImagesResponseB2WDTO.class));
        doReturn("http://54.152.221.29").when(restProperties).getEndpointB2WConsult();
        imagesPartnerGateway.getImagesResponse();
    }

    @Test
    public void successGetImages() throws IOException, URISyntaxException {

        byte[] image = Files.toByteArray(new File(getUrl("image/b737_5.jpg")));
        doReturn(image).when(restTemplate).getForObject(anyString(), eq(byte[].class));

        byte[] result = imagesPartnerGateway.getImage(new ImageB2WDTO());

        verify(restTemplate, times(1)).getForObject(anyString(), eq(byte[].class));

        Assert.assertNotNull(result);
        Assert.assertThat(image, is(equalTo(result)));

    }

    private URI getUrl(String path) throws URISyntaxException {
        return this.getClass().getClassLoader().getResource(path).toURI();
    }

}
