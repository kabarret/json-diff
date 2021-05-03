package br.com.kauebarreto.jsondiff.service;

import br.com.kauebarreto.jsondiff.exception.DiffException;
import br.com.kauebarreto.jsondiff.model.Diff;
import br.com.kauebarreto.jsondiff.model.DiffDTO;
import br.com.kauebarreto.jsondiff.repository.DiffRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DiffServiceTest {

    public static final String HELLO_WORLD = Base64.getEncoder().encodeToString("Hello World".getBytes(StandardCharsets.UTF_8));
    public static final long DIFF_ID = 1L;
    @Mock
    private DiffRepository diffRepository;
    @Mock
    private ComparatorService comparatorService;

    private DiffService diffService;

    @BeforeEach
    void setup(){
        diffService = new DiffService(diffRepository, comparatorService);
    }

    @Test
    public void shouldSaveLeftDiff(){
        DiffDTO diffDTO = new DiffDTO();
        diffDTO.setData(HELLO_WORLD);

        diffService.saveLeftDiff(diffDTO, DIFF_ID);
        Mockito.verify(diffRepository).save(new Diff(DIFF_ID, HELLO_WORLD, null));
    }

    @Test
    public void shouldSaveRightDiff(){
        DiffDTO diffDTO = new DiffDTO();
        diffDTO.setData(HELLO_WORLD);

        diffService.saveRightDiff(diffDTO, DIFF_ID);
        Mockito.verify(diffRepository).save(new Diff(DIFF_ID, null, HELLO_WORLD));
    }

    @Test
    public void shouldGetDiffById() throws DiffException {
        Diff diff = new Diff(DIFF_ID, HELLO_WORLD, HELLO_WORLD);
        Mockito.when(diffRepository.findById(DIFF_ID)).thenReturn(Optional.of(diff));
        Assertions.assertEquals(diff, diffService.getDiff(DIFF_ID));
    }

    @Test
    public void shouldProcessDiff() throws DiffException {
        Diff diff = new Diff(DIFF_ID, HELLO_WORLD, HELLO_WORLD);
        Mockito.when(diffRepository.findById(DIFF_ID)).thenReturn(Optional.of(diff));
        diffService.processDiff(DIFF_ID);
        Mockito.verify(comparatorService).compareData(
                Base64.getDecoder().decode(HELLO_WORLD.getBytes()),
                Base64.getDecoder().decode(HELLO_WORLD.getBytes())
        );
    }
}
