package br.com.kecyo.resizephotos.entities.json;

import br.com.kecyo.resizephotos.config.app.ConfigProperties;
import br.com.kecyo.resizephotos.entities.Image;
import br.com.kecyo.resizephotos.entities.json.response.ImageDTO;
import br.com.kecyo.resizephotos.exception.MalformedUrlImageException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageConverterDTO implements Converter<Map.Entry<String, List<Image>>, ImageDTO> {

    private final ConfigProperties configProperties;

    @Override
    public ImageDTO convert(Map.Entry<String, List<Image>> entryImage) {

        final String name = entryImage.getKey();

        final List<String> listUrl = entryImage.getValue()
                                                .stream()
                                                .map(this::getUrl)
                                                .collect(Collectors.toList());

        return new ImageDTO(name, listUrl);
    }

    private String getUrl(final Image image){

        String url = "";
        try {
            url = "http://"
                .concat(InetAddress.getLocalHost().getHostAddress())
                .concat(":")
                .concat(configProperties.getServerPort())
                .concat(configProperties.getContextPath())
                .concat("/api/images/")
                .concat(image.getResolution().name())
                .concat("/")
                .concat(image.getName());
        } catch (Exception e) {
            throw new MalformedUrlImageException(e);
        }
        return url;
    }

}
