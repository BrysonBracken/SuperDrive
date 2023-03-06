package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private CredentialService credentialService;

    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getAllCredentials(int userId){
        return credentialMapper.getCredentials(userId);
    }

    public void saveCredential(Credential credential){
        credentialMapper.insert(encryptPassword(credential));
    }

    public void updateCredential(Credential credential){
        credentialMapper.credentialUpdate(encryptPassword(credential));
    }

    public void deleteCredential(int credentialId){
        credentialMapper.credentialDelete(credentialId);
    }

    private Credential encryptPassword(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        credential.setEncryptionKey(encodedKey);
        credential.setCredentialPassword(encryptionService.encryptValue(credential.getCredentialPassword(), encodedKey));
        System.out.println(credential.getEncryptionKey());
        return credential;
    }

//    public String decryptPassword(String password, String key){
//            credential.setCredentialPassword(encryptionService.decryptValue(credential.getCredentialPassword(), credential.getEncryptionKey()));
//            System.out.println(credential.getCredentialPassword());
//        return encryptionService.decryptValue(password, key);
//    }

    public boolean urlInUse (String url){
        return credentialMapper.getByUrl(url) == null;
    }

}
