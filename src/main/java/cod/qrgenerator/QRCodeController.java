package cod.qrgenerator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class QRCodeController {

    @Autowired
    QRCodeGeneratorService qrCodeGeneratorService;

    @PostMapping("/qrcode")
    public String addCustomer(@RequestBody String message) {
        log.info("Input Message is {} ", message);
        qrCodeGeneratorService.generateQRCode(message);
        return "Created QR Code";
    }

}
