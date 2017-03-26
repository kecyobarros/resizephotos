package br.com.kecyo.resizephotos.usescases.impl;


import br.com.kecyo.resizephotos.config.rest.RestProperties;
import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.ResolutionType;
import br.com.kecyo.resizephotos.entities.json.request.ImageDTO;
import br.com.kecyo.resizephotos.entities.json.request.ImagesResponseDTO;
import br.com.kecyo.resizephotos.gateways.ImagesGateway;
import br.com.kecyo.resizephotos.usescases.Process;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static javax.imageio.ImageIO.read;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImagesProcessImpl implements Process {

    private final RestTemplate restTemplate;

    private final RestProperties restProperties;

    private final ImagesGateway imagesGateway;

    private final String FORMAT_DEFAULT = "JPEG";

    @Override
    public void process() {

        log.info("Iniciando carga de imagens");

        final ImagesResponseDTO response = getImagesResponse();

        final Supplier<Stream<ResolutionType>> streamResolution = () -> Arrays.stream(ResolutionType.values());

        response.getImages().parallelStream().forEach(image -> {
            streamResolution.get()
                    .parallel()
                    .filter(resolutionType -> !imagesGateway.existsByNameAndResolution(image.getName(), resolutionType))
                    .forEach( resolutionType -> {
                                byte[] imageBytes = getImage(image);
                                ByteArrayOutputStream imOutputStream = new ByteArrayOutputStream();
                                try {
                                    resizeImage(resolutionType, imOutputStream, read(new ByteArrayInputStream(imageBytes)));
                                    save(image.getName(), resolutionType, imOutputStream);
                                } catch (IOException e) {
                                    log.error(e.getMessage());
                                }
                            }
                    );
        });

        log.info("Carga finalizada!!!");
    }

    private byte[] getImage(ImageDTO image) {
        return restTemplate.getForObject(image.getUrl(), byte[].class);
    }

    private ImagesResponseDTO getImagesResponse() {
        ResponseEntity<ImagesResponseDTO> forEntity =
                restTemplate.getForEntity(restProperties.getImagesRepository()
                        .concat("/images.json"),
                ImagesResponseDTO.class);

        return forEntity.getBody();
    }

    private void resizeImage(final ResolutionType resolutionType, final ByteArrayOutputStream imageOutputStream,
                             final BufferedImage arrayImage) throws IOException {
        Thumbnails
                .of(arrayImage)
                .size(resolutionType.getWidth(), resolutionType.getHeight())
                .outputFormat(FORMAT_DEFAULT)
                .toOutputStream(imageOutputStream);
    }

    private void save(final String name, final ResolutionType resolutionType, final ByteArrayOutputStream imageOutputStream) {
        imagesGateway.save(Image.builder()
                .name(name)
                .resolution(resolutionType)
                .bytes(imageOutputStream.toByteArray())
                .build());
    }

    @Override
    public Optional<Image> findByNameAndResolution(final String name, final ResolutionType resolution) {
        return imagesGateway.findByNameAndResolution(name, resolution);
    }
}
