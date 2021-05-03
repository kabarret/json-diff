package br.com.kauebarreto.jsondiff.service;

import br.com.kauebarreto.jsondiff.exception.DifferentSizeException;
import br.com.kauebarreto.jsondiff.exception.NotEqualsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparatorServiceTest {

    @Test
    public void compareEqualsBinary(){
        byte[] leftDiff  = "hello World".getBytes();
        byte[] rightDiff = "hello World".getBytes();

        assertTrue(new ComparatorService().isEqualsData(leftDiff, rightDiff));
    }

    @Test
    public void compareDifferentBinary(){
        byte[] leftDiff  = "hello World!".getBytes();
        byte[] rightDiff = "hello new World".getBytes();

        assertFalse(new ComparatorService().hasTheSameLength(leftDiff, rightDiff));
    }

    @Test
    public void shouldFailForDifferentSize() {
        byte[] leftDiff  = "hello World".getBytes();
        byte[] rightDiff = "hello World!".getBytes();

        Assertions.assertThrows(DifferentSizeException.class, () -> new ComparatorService().compareData(leftDiff, rightDiff));
    }
    @Test
    public void shouldFailForNotEqualsCompare() {
        byte[] leftDiff  = "hello world".getBytes();
        byte[] rightDiff = "hello World".getBytes();

        Assertions.assertThrows(NotEqualsException.class, () -> new ComparatorService().compareData(leftDiff, rightDiff));
    }
}
