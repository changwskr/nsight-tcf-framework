package nhnis.mk.co.a.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nhnis.mk.co.a.dto.mkpca8888DtoIn;
import nhnis.mk.co.a.dto.mkpca8888ListResponseDto;
import nhnis.mk.co.a.service.mkpca8888Service;

@ExtendWith(MockitoExtension.class)
class mkpca8888ControllerTest {

    @Mock
    private mkpca8888Service service;

    @InjectMocks
    private mkpca8888Controller controller;

    @Test
    void listDelegatesToService() {
        mkpca8888DtoIn in = new mkpca8888DtoIn();
        mkpca8888ListResponseDto serviceResult = new mkpca8888ListResponseDto();
        when(service.mkpca8888S0(in)).thenReturn(serviceResult);

        mkpca8888ListResponseDto response = controller.mkpca8888S0(in);

        assertThat(response).isSameAs(serviceResult);
    }

    @Test
    void createDelegatesToService() {
        mkpca8888DtoIn in = new mkpca8888DtoIn();

        controller.mkpca8888I0(in);

        verify(service).mkpca8888I0(in);
    }
}
