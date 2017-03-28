package br.com.kecyo.resizephotos.gateways;


import br.com.kecyo.resizephotos.entities.json.request.ImageB2WDTO;
import br.com.kecyo.resizephotos.entities.json.request.ImagesResponseB2WDTO;

public interface ImagesPartnerGateway {

    ImagesResponseB2WDTO getImagesResponse();

    byte[] getImage(final ImageB2WDTO image);

}
