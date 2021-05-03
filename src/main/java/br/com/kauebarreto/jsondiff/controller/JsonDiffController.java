package br.com.kauebarreto.jsondiff.controller;

import br.com.kauebarreto.jsondiff.exception.DiffNotFound;
import br.com.kauebarreto.jsondiff.exception.DifferentSizeException;
import br.com.kauebarreto.jsondiff.exception.NotEqualsException;
import br.com.kauebarreto.jsondiff.model.DiffDTO;
import br.com.kauebarreto.jsondiff.model.DiffResponseDTO;
import br.com.kauebarreto.jsondiff.service.DiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/diff")
public class JsonDiffController {
    public static final String DIFF_SUCCESSFULLY_ADD = "Diff successfully add";
    public static final String THE_BOTH_CONTENT_ARE_IDENTICAL = "The both content are identical";

    @Autowired
    private DiffService diffService;

    @PutMapping("/{diffId}/left")
    public ResponseEntity addDiffLeft(@PathVariable Long diffId, @RequestBody DiffDTO diffDTO){
        diffService.saveLeftDiff(diffDTO, diffId);
        return new ResponseEntity<>(new DiffResponseDTO(DIFF_SUCCESSFULLY_ADD), HttpStatus.OK );
    }

    @PutMapping("/{diffId}/right")
    public ResponseEntity addDiffRight(@PathVariable Long diffId, @RequestBody DiffDTO diffDTO){
        diffService.saveRightDiff(diffDTO, diffId);
        return new ResponseEntity<>(new DiffResponseDTO(DIFF_SUCCESSFULLY_ADD), HttpStatus.OK );
    }

    @GetMapping("/{diffId}")
    public Object processDiff(@PathVariable Long diffId) {
        try {
            diffService.processDiff(diffId);
            return new ResponseEntity<>(new DiffResponseDTO(THE_BOTH_CONTENT_ARE_IDENTICAL), HttpStatus.OK);
        } catch (DiffNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NotEqualsException | DifferentSizeException e){
            return new ResponseEntity<>(new DiffResponseDTO(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
