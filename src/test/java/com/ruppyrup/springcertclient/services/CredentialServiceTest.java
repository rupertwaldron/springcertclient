package com.ruppyrup.springcertclient.services;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CredentialServiceTest {

//    @Mock
//    RestTemplate restTemplate;
//
//    @Mock
//    PathUtil pathUtil;
//
//    @InjectMocks
//    CredentialService credentialService;
//
//    private Credential credential = new Credential(1L, "a", "Boots", "www.boots.com", "RW", "password", "ruppyrup");
//
//    @Test
//    public void whenGetCredentialIsCalled_shouldReturnMockObject() {
//        ResponseEntity<Credential> entity = new ResponseEntity(credential, HttpStatus.OK);
//
//        Mockito
//                .when(pathUtil.getUriComponents(anyString())).thenReturn(any(UriComponents.class));
//
//        Mockito
//                .when(restTemplate.exchange(
//                        anyString(),
//                        eq(HttpMethod.GET),
//                        any(),
//                        eq(Credential.class))).thenReturn(entity);
//
//        ResponseEntity<Credential> response = credentialService.getCredential(null, "Boots");
//        Assertions.assertThat(response.getBody()).isEqualTo(credential);
//    }
//
//    @Test
//    public void whenGetCredentialsIsCalled_shouldReturnMockObject() {
//        ResponseEntity<Credential> entity = new ResponseEntity(credential, HttpStatus.OK);
//        Mockito
//                .when(restTemplate.exchange(
//                        anyString(),
//                        eq(HttpMethod.GET),
//                        any(),
//                        eq(Credential.class))).thenReturn(entity);
//
//        ResponseEntity<Credential> response = credentialService.getCredential(null, "Boots");
//        Assertions.assertThat(response.getBody()).isEqualTo(credential);
//    }

}