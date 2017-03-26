package br.com.kecyo.resizephotos.gateways.repository.mongo;

import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.ResolutionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImagesRepository extends MongoRepository<Image, String> {

    boolean existsByNameAndResolution(final String name, final ResolutionType resolution);

    Optional<Image> findByNameAndResolution(final String name, final ResolutionType resolution);
}
