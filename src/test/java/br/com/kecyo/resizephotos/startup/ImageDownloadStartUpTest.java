package br.com.kecyo.resizephotos.startup;


import br.com.kecyo.resizephotos.usescases.Process;
import br.com.kecyo.resizephotos.usescases.impl.ImagesProcessImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;

public class ImageDownloadStartUpTest {

    private Process imagesProcess;
    private ImageDownloadStartUp startUp;

    @Before
    public void before(){
        imagesProcess = Mockito.mock(ImagesProcessImpl.class);
        startUp = new ImageDownloadStartUp(imagesProcess);
    }

    @Test
    public void success(){
        startUp.execute();
        Mockito.verify(imagesProcess, times(1)).process();
    }

}
