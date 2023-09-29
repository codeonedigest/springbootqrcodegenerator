package cod.qrgenerator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class QRCodeGeneratorService {

    @Value("${qrcode.message}")
    private String qrCodeMessage;

    @Value("${qrcode.output.directory}")
    private String outputLocation;

    private static final String charset = "UTF-8";

    private static final String strDateFormat = "yyyyMMddhhmmss";



    public void generateQRCode(String message) {
        log.info("### Generating QRCode ###");


        log.info("Output directory - {}", outputLocation);
        try {
            String finalMessage = (StringUtils.isBlank(message))?qrCodeMessage:message;
            log.info("Final Input Message - {}", finalMessage);
            processQRcode(finalMessage, prepareOutputFileName(), charset, 400, 400);

        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String prepareOutputFileName() {
        Date date = new Date();

        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);

        StringBuilder sb = new StringBuilder();
        sb.append(outputLocation).append("\\").append("QRCode-").append(formattedDate).append(".png");
        log.info("Final output file - "+sb.toString());
        return sb.toString();
    }

    private void processQRcode(String data, String path, String charset, int height, int width) throws WriterException, IOException {
        /*the BitMatrix class represents the 2D matrix of bits*/
       /* MultiFormatWriter is a factory class that finds the appropriate Writer subclass for
        the BarcodeFormat requested and encodes the barcode with the supplied contents.*/
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }



}
