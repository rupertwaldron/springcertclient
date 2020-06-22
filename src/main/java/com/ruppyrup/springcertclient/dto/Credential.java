package com.ruppyrup.springcertclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credential {
    private Long id;
    private String uuid;
    private String credentialName;
    private String url;
    private String login;
    private String password;
    private UserDTO user;

    public Credential(CredentialDTO credentialDTO) {
        this.credentialName = credentialDTO.getCredentialName();
        this.url = credentialDTO.getUrl();
        this.login = credentialDTO.getLogin();
        this.password = credentialDTO.getPassword();
    }
}
