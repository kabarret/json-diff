package br.com.kauebarreto.jsondiff.service;

import br.com.kauebarreto.jsondiff.exception.DiffException;
import br.com.kauebarreto.jsondiff.exception.DifferentSizeException;
import br.com.kauebarreto.jsondiff.exception.NotEqualsException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ComparatorService {

    public void compareData(final byte[] left, final byte[] right) throws DiffException {
        if (!isEqualsData(left, right)){
            if (hasTheSameLength(left, right)){
                diffContent(left, right);
            } else {
                throw new DifferentSizeException(String.format("Different size contents expected [%s] but found [%s]", left.length, right.length));
            }
        }
    }

    protected boolean isEqualsData(final byte[] left, final byte[] right){
        return Arrays.equals(left, right);
    }

    protected boolean hasTheSameLength(final byte[] left, final byte[] right){
        return left.length == right.length;
    }

    protected void diffContent(final byte[] left, final byte[] right) throws NotEqualsException {
        List<String> diffs = new ArrayList<>();
        for(int i = 0; i < left.length; ++i) {
            if (left[i] != right[i]) {
                diffs.add(String.valueOf(i));
            }
        }
        throw new NotEqualsException(String.format("Offset: [%s] Length: [%s]", String.join(",",diffs), left.length));
    }
}
