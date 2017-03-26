package br.com.kecyo.resizephotos.gateways.impl;

import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.ResolutionType;
import br.com.kecyo.resizephotos.gateways.ImagesGateway;
import br.com.kecyo.resizephotos.gateways.repository.mongo.ImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImagesGatewayImpl implements ImagesGateway {

    private final ImagesRepository repository;

    @Override
    public void save(final Image image){
        repository.save(image);
    }

    @Override
    public boolean existsByNameAndResolution(final String name, final ResolutionType resolution){
        return repository.existsByNameAndResolution(name, resolution);
    }

    @Override
    public Optional<Image> findByNameAndResolution(final String name, final ResolutionType resolution){
        return repository.findByNameAndResolution(name, resolution);
    }
}
