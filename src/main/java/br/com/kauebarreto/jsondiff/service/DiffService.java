package br.com.kauebarreto.jsondiff.service;

import br.com.kauebarreto.jsondiff.exception.DiffException;
import br.com.kauebarreto.jsondiff.exception.DiffNotCompleted;
import br.com.kauebarreto.jsondiff.exception.DiffNotFound;
import br.com.kauebarreto.jsondiff.model.Diff;
import br.com.kauebarreto.jsondiff.model.DiffDTO;
import br.com.kauebarreto.jsondiff.repository.DiffRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiffService {

    private DiffRepository diffRepository;
    private ComparatorService comparatorService;

    @Autowired
    public DiffService(DiffRepository diffRepository, ComparatorService comparatorService) {
        this.diffRepository = diffRepository;
        this.comparatorService = comparatorService;
    }

    public void saveRightDiff(DiffDTO diffDTO, Long diffId){
        Diff diffById = diffRepository.findById(diffId).orElse(new Diff(diffId));
        diffById.setRightContent(diffDTO.getData());
        diffRepository.save(diffById);
    }

    public void saveLeftDiff(DiffDTO diffDTO, Long diffId){
        Diff diffById = diffRepository.findById(diffId).orElse(new Diff(diffId));
        diffById.setLeftContent(diffDTO.getData());
        diffRepository.save(diffById);
    }

    public Diff getDiff(Long diffId) throws DiffException {
       return diffRepository.findById(diffId).orElseThrow(DiffNotFound::new);
    }

    public void processDiff(Long diffId) throws DiffException {
        Diff diff = getDiff(diffId);
        if (diff.getLeftContent() == null || diff.getRightContent() == null) {
            throw new DiffNotCompleted();
        }
        comparatorService.compareData(
                Base64.decodeBase64(diff.getLeftContent()),
                Base64.decodeBase64(diff.getRightContent())
        );
    }

}
