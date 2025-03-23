package kepco.common.config;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

	@Autowired
	@Qualifier("jasyptStringEncryptor")
	private StringEncryptor stringEncryptor;
	    
    public String encryptData(String data) {
        // 데이터 암호화
        String encryptedData = stringEncryptor.encrypt(data);
        return encryptedData;
    }
    public String decryptData(String encryptedData) {
        // 데이터 복호화
        String decryptedData = stringEncryptor.decrypt(encryptedData);
        return decryptedData;
    }

	
}
