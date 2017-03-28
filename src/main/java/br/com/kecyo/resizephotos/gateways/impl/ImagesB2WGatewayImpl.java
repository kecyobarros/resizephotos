package br.com.kecyo.resizephotos.gateways.impl;

import br.com.kecyo.resizephotos.config.rest.RestProperties;
import br.com.kecyo.resizephotos.entities.json.request.ImageB2WDTO;
import br.com.kecyo.resizephotos.entities.json.request.ImagesResponseB2WDTO;
import br.com.kecyo.resizephotos.gateways.ImagesPartnerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImagesB2WGatewayImpl implements ImagesPartnerGateway {

    private final RestTemplate restTemplate;

    private final RestProperties restProperties;

    @Override
    public ImagesResponseB2WDTO getImagesResponse() {
        ResponseEntity<ImagesResponseB2WDTO> forEntity =
                restTemplate.getForEntity(restProperties.getEndpointB2WConsult(),
                        ImagesResponseB2WDTO.class);

        return forEntity.getBody();
    }

    @Override
    public byte[] getImage(final ImageB2WDTO image) {
        return restTemplate.getForObject(image.getUrl(), byte[].class);
    }
}
