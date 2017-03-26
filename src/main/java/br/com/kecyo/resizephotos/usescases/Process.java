package br.com.kecyo.resizephotos.usescases;

import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.ResolutionType;

import java.util.Optional;

public interface Process {

    void process();

    Optional<Image> findByNameAndResolution(final String name, final ResolutionType resolution);
}
