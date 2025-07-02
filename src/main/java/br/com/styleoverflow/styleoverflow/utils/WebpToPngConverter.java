package br.com.styleoverflow.styleoverflow.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebpToPngConverter {

    private static List<String> webpPhotos = new ArrayList<>();
    private static List<String> pngPhotos = new ArrayList<>();

    public static String convertWebPToPng(String webpUrl) throws Exception {

        if (webpPhotos.contains(webpUrl)) return pngPhotos.get(webpPhotos.indexOf(webpUrl));

        webpPhotos.add(webpUrl);

        InputStream inputStream = new URL(webpUrl).openStream();
        BufferedImage image = ImageIO.read(inputStream);

        if (image == null) {
            throw new RuntimeException("A imagem não pôde ser lida. Formato possivelmente não suportado.");
        }

        File pngFile = File.createTempFile("converted-", ".png");
        ImageIO.write(image, "png", pngFile);

        String pngUrl = pngFile.toURI().toURL().toString();
        pngPhotos.add(pngUrl);

        return pngUrl;
    }
}
