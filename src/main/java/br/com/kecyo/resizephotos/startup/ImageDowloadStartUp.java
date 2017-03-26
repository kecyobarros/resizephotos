package br.com.kecyo.resizephotos.startup;


import br.com.kecyo.resizephotos.usescases.Process;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageDowloadStartUp {

    private final Process imagesProcess;

    //10 minutos
    @Scheduled(fixedRate = 600000)
    public void execute(){
        imagesProcess.process();
    }
}
