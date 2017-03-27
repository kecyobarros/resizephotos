package br.com.kecyo.resizephotos.usescases;

import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.ResolutionType;
import br.com.kecyo.resizephotos.entities.json.response.ImageDTO;

import java.util.List;
import java.util.Optional;

public interface Process {

    void process();

    Optional<Image> findByNameAndResolution(final String name, final ResolutionType resolution);

    List<ImageDTO> findAll();

}
