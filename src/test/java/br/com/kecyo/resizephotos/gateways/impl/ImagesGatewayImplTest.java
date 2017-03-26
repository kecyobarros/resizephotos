package br.com.kecyo.resizephotos.gateways.impl;


import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.ResolutionType;
import br.com.kecyo.resizephotos.gateways.repository.mongo.ImagesRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ImagesGatewayImplTest {

    private ImagesRepository repository;

    private ImagesGatewayImpl imagesGatewayImpl;

    @Before
    public void before(){
        repository = Mockito.mock(ImagesRepository.class);
        imagesGatewayImpl = new ImagesGatewayImpl(repository);
    }

    @Test
    public void successSave(){

        ArgumentCaptor<Image> argumentImage = ArgumentCaptor.forClass(Image.class);

        Image image = Image.builder()
                            .name("teste")
                            .id("id1")
                            .bytes("teste".getBytes())
                            .resolution(ResolutionType.LARGE)
                            .build();

        imagesGatewayImpl.save(image);

        verify(repository, times(1)).save(any(Image.class));
        verify(repository).save(argumentImage.capture());

        Image imageParam = argumentImage.getValue();

        assertThat(image.getName(), is(equalTo(imageParam.getName())));
        assertThat(image.getId(), is(equalTo(imageParam.getId())));
        assertThat(image.getResolution(), is(equalTo(imageParam.getResolution())));
        assertThat(image.getBytes(), is(equalTo(imageParam.getBytes())));

    }

    @Test
    public void successExistsByNameAndResolutionResultTrue(){

        when(repository.existsByNameAndResolution(anyString(), any(ResolutionType.class)))
                .thenReturn(true);

        boolean result = imagesGatewayImpl.existsByNameAndResolution("teste", ResolutionType.LARGE);

        assertTrue(result);
    }

    @Test
    public void successExistsByNameAndResolutionResultFalse(){

        when(repository.existsByNameAndResolution(anyString(), any(ResolutionType.class)))
                .thenReturn(false);

        boolean result = imagesGatewayImpl.existsByNameAndResolution("teste", ResolutionType.LARGE);

        assertFalse(result);
    }

    @Test
    public void successfindByNameAndResolutionResult(){

        when(repository.findByNameAndResolution(anyString(), any(ResolutionType.class)))
                .thenReturn(Optional.ofNullable(Image.builder().build()));

        Optional<Image> result = imagesGatewayImpl.findByNameAndResolution("teste", ResolutionType.LARGE);

        assertTrue(result.isPresent());

    }

    @Test
    public void successfindByNameAndResolutionResultEmpty(){

        when(repository.findByNameAndResolution(anyString(), any(ResolutionType.class)))
                .thenReturn(Optional.empty());

        Optional<Image> result = imagesGatewayImpl.findByNameAndResolution("teste", ResolutionType.LARGE);

        assertFalse(result.isPresent());
    }

}
