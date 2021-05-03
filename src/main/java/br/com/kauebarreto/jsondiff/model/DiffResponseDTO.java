package br.com.kauebarreto.jsondiff.model;

public class DiffResponseDTO {
    public String result;

    public DiffResponseDTO(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
