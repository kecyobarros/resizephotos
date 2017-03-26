package br.com.kecyo.resizephotos.http.helpers;


import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.ResolutionType;
import br.com.kecyo.resizephotos.usescases.Process;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/images")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImagesController {

    private final Process imagesProcess;

    @RequestMapping(method = RequestMethod.GET,
            produces = "image/jpeg",
            value = "/{resolution}/{name}")
    public ResponseEntity<byte[]> findByNameAndResolution(@PathVariable("resolution") ResolutionType resolution,
                                                          @PathVariable("name") String name) {

        Optional<Image> optionalImage = imagesProcess.findByNameAndResolution(name, resolution);

        if (optionalImage.isPresent()) {
            return new ResponseEntity(optionalImage.get().getBytes(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
