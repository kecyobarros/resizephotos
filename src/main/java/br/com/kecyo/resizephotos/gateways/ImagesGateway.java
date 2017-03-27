package br.com.kecyo.resizephotos.gateways;


import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.ResolutionType;

import java.util.List;
import java.util.Optional;

public interface ImagesGateway {

    void save(final Image image);

    boolean existsByNameAndResolution(final String name, final ResolutionType resolution);

    Optional<Image> findByNameAndResolution(final String name, final ResolutionType resolution);

    List<Image> findAll();

}
