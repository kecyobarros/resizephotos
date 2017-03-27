package br.com.kecyo.resizephotos.entities.json;

import br.com.kecyo.resizephotos.config.app.ConfigProperties;
import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.json.response.ImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageConverterDTO implements Converter<Image, ImageDTO> {

    private final ConfigProperties configProperties;

    @Override
    public ImageDTO convert(final Image image) {
        String url = "";
        try {
            url = "http://"
                .concat(InetAddress.getLocalHost().getHostAddress())
                .concat(":")
                .concat(configProperties.getServerPort())
                .concat(configProperties.getContextPath())
                .concat("/")
                .concat(image.getResolution().name())
                .concat("/")
                .concat(image.getName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return new ImageDTO(url);
    }
}
