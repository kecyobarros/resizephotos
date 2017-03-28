package br.com.kecyo.resizephotos.http.helpers;


import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.ResolutionType;
import br.com.kecyo.resizephotos.entities.json.response.ImageDTO;
import br.com.kecyo.resizephotos.entities.json.response.ImagesResponseDTO;
import br.com.kecyo.resizephotos.usescases.Process;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ImagesControllerTest {

    private Process imagesProcess;

    private ImagesController imagesController;

    @Before
    public void before(){
        imagesProcess = Mockito.mock(Process.class);
        imagesController = new ImagesController(imagesProcess);
    }

    @Test
    public void successResultOK(){

        when(imagesProcess.findByNameAndResolution(anyString(), any(ResolutionType.class)))
                .thenReturn(Optional.ofNullable(Image.builder().build()));

        ResponseEntity<byte[]> result = imagesController
                .findByNameAndResolution(ResolutionType.SMALL, "b737_4");

        verify(imagesProcess, times(1))
                .findByNameAndResolution(anyString(), any(ResolutionType.class));

        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    public void successResultNOTFOUND(){

        when(imagesProcess.findByNameAndResolution(anyString(), any(ResolutionType.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<byte[]> result = imagesController
                .findByNameAndResolution(ResolutionType.SMALL, "b737_4");

        verify(imagesProcess, times(1))
                .findByNameAndResolution(anyString(), any(ResolutionType.class));

        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));
    }

    @Test
    public void successFindAll(){

        when(imagesProcess.findAll()).thenReturn(Arrays.asList(new ImageDTO("teste", Arrays.asList("url1", "url2"))));

        ResponseEntity<ImagesResponseDTO> result = imagesController.findAll();

        verify(imagesProcess, times(1)).findAll();

        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(result.getBody().getImages().size(), is(equalTo(1)));
        assertThat(result.getBody().getImages().get(0).getName(), is(equalTo("teste")));
        assertThat(result.getBody().getImages().get(0).getUrls().size(), is(equalTo(2)));
    }

    @Test
    public void successFindAllIsEmpty(){

        when(imagesProcess.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<ImagesResponseDTO> result = imagesController.findAll();

        verify(imagesProcess, times(1)).findAll();

        assertThat(result.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));
    }
}
