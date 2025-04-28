package com.vanhdev.todo.controller;

import com.nimbusds.jose.JOSEException;
import com.vanhdev.todo.dto.request.*;
import com.vanhdev.todo.dto.response.AuthenticationResponse;
import com.vanhdev.todo.dto.response.IntrospectResponse;
import com.vanhdev.todo.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void authenticate_ValidRequest_ReturnsAuthenticationResponse() {
        AuthenticationRequest request = new AuthenticationRequest();
        AuthenticationResponse expectedResponse = new AuthenticationResponse();

        when(authenticationService.authenticate(request)).thenReturn(expectedResponse);

        ApiResponse<AuthenticationResponse> actualResponse = authenticationController.authenticate(request);

        verify(authenticationService, times(1)).authenticate(request);
    }

    @Test
    void introspect_ValidRequest_ReturnsIntrospectResponse() throws ParseException, JOSEException {
        IntrospectRequest request = new IntrospectRequest();
        IntrospectResponse expectedResponse = new IntrospectResponse();

        when(authenticationService.introspect(request)).thenReturn(expectedResponse);

        ApiResponse<IntrospectResponse> actualResponse = authenticationController.authenticate(request);

        verify(authenticationService, times(1)).introspect(request);
    }

    @Test
    void refresh_ValidRequest_ReturnsAuthenticationResponse() throws ParseException, JOSEException {
        RefreshRequest request = new RefreshRequest();
        AuthenticationResponse expectedResponse = new AuthenticationResponse();

        when(authenticationService.refreshToken(request)).thenReturn(expectedResponse);

        ApiResponse<AuthenticationResponse> actualResponse = authenticationController.authenticate(request);

        verify(authenticationService, times(1)).refreshToken(request);
    }

    @Test
    void logout_ValidRequest_ReturnsVoidApiResponse() throws ParseException, JOSEException {
        LogoutRequest request = new LogoutRequest();

        doNothing().when(authenticationService).logout(request);

        ApiResponse<Void> actualResponse = authenticationController.logout(request);

        verify(authenticationService, times(1)).logout(request);
    }
}
