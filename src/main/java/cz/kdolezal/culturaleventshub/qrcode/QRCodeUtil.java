package cz.kdolezal.eventmanagementsystem.qrcode;

import cz.kdolezal.eventmanagementsystem.api.exception.QRCodeGenerationException;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

@Component
public class QRCodeUtil {
    // method generates a PNG QR code file from a byte code
    public static void saveQrCodeToPng(byte[] qrCodeData, String outputPath) throws QRCodeGenerationException {
        try {
            // Creating an InputStream from a byte array
            ByteArrayInputStream bis = new ByteArrayInputStream(qrCodeData);
            // Loading an image from an InputStream
            BufferedImage qrImage = ImageIO.read(bis);
            // Save the image to a file as PNG
            ImageIO.write(qrImage, "png", new File(outputPath));
            bis.close();
        } catch (IOException e) {
            throw new QRCodeGenerationException("Failed to save QR code from byte code", e);
        }
    }
}