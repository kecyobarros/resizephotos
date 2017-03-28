package br.com.kecyo.resizephotos.http.helpers;


import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.ResolutionType;
import br.com.kecyo.resizephotos.entities.json.response.ImageDTO;
import br.com.kecyo.resizephotos.entities.json.response.ImagesResponseDTO;
import br.com.kecyo.resizephotos.usescases.Process;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/images")
public class ImagesController {

    private final Process imagesProcess;

    @ApiOperation(value = "Request List Photos")
    @ApiResponses(value = {
            @ApiResponse(code=200, message="Photos found")
    })
    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImagesResponseDTO> findAll() {

        List<ImageDTO> listImages = imagesProcess.findAll();

        if (!CollectionUtils.isEmpty(listImages)) {
            return new ResponseEntity(new ImagesResponseDTO(listImages), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Request Photo and Resolution")
    @ApiResponses(value = {
            @ApiResponse(code=200, message="Photos found")
    })
    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE,
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
