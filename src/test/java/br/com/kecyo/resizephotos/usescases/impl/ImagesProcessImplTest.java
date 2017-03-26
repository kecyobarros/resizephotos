package br.com.kecyo.resizephotos.usescases.impl;

import br.com.kecyo.resizephotos.config.rest.RestProperties;
import br.com.kecyo.resizephotos.entities.json.request.ImagesResponseDTO;
import br.com.kecyo.resizephotos.gateways.ImagesGateway;
import br.com.kecyo.resizephotos.usescases.Process;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ImagesProcessImplTest {

    private RestTemplate restTemplate;

    private RestProperties restProperties;

    private ImagesGateway imagesGateway;

    private Process imagesProcess;

    @Before
    public void before(){
        restTemplate = mock(RestTemplate.class);
        restProperties = mock(RestProperties.class);
        imagesGateway = mock(ImagesGateway.class);
        imagesProcess = new ImagesProcessImpl(restTemplate,restProperties,imagesGateway);
    }

    @Test
    public void success(){

        when(restTemplate.getForEntity(anyString(), Matchers.eq(ImagesResponseDTO.class)))
                .thenReturn(new ResponseEntity<ImagesResponseDTO>(HttpStatus.OK));

        when(restTemplate.getForObject(anyString(), Matchers.eq(byte[].class)))
                .thenReturn(new byte[10]);

        imagesProcess.process();
    }
}
