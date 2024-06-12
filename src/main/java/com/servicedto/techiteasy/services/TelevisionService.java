package com.servicedto.techiteasy.services;

import com.servicedto.techiteasy.dtos.inputs.TelevisionInputDto;
import com.servicedto.techiteasy.dtos.mappers.TelevisionMapper;
import com.servicedto.techiteasy.dtos.outputs.TelevisionOutputDto;
import com.servicedto.techiteasy.exceptions.RecordNotFoundException;
import com.servicedto.techiteasy.models.Television;
import com.servicedto.techiteasy.repositories.TelevisionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelevisionService {

    private final TelevisionRepository repo;

    public TelevisionService(TelevisionRepository repo) {
        this.repo = repo;
    }

    public List<Television> getAllTelevisions() {
        return this.repo.findAll();
    }

    public TelevisionOutputDto getTelevisionById(Long id) {
        Television t = this.repo.findById(id).orElseThrow(() -> new RecordNotFoundException("No television with id #" + id + " in list."));
        return TelevisionMapper.fromTelevisionToOutputDto(t);
    }

    public TelevisionOutputDto createTelevision(TelevisionInputDto televisionInputDto) {
        Television t = this.repo.save(TelevisionMapper.fromInputDtoToTelevision(televisionInputDto));
        return TelevisionMapper.fromTelevisionToOutputDto(t);
    }

    public void deleteTelevision(Long id) {
        if (repo.findById(id).isPresent()) {
            repo.deleteById(id);
        } else {
            throw new RecordNotFoundException("No television in list with id " + id + "!");
        }
    }

    public TelevisionOutputDto updateTelevision (Long id, TelevisionInputDto televisionInputDto) {
        Television t1 = this.repo.findById(id).orElseThrow(() -> new RecordNotFoundException("No television in list with id " + id + "!"));
        Television t2 = TelevisionMapper.fromInputDtoToTelevision(televisionInputDto);

        t1.setType(t2.getType());
        t1.setBrand(t2.getBrand());
        t1.setName(t2.getName());
        t1.setPrice(t2.getPrice());
        t1.setAvailableSize(t2.getAvailableSize());
        t1.setRefreshRate(t2.getRefreshRate());
        t1.setScreenType(t2.getScreenType());
        t1.setScreenQuality(t2.getScreenQuality());
        t1.setSmartTv(t2.isSmartTv());
        t1.setWifi(t2.isWifi());
        t1.setVoiceControl(t2.isVoiceControl());
        t1.setHdr(t2.isHdr());
        t1.setBluetooth(t2.isBluetooth());
        t1.setAmbiLight(t2.isAmbiLight());
        t1.setOriginalStock(t2.getOriginalStock());
        t1.setSold(t2.getSold());

        return TelevisionMapper.fromTelevisionToOutputDto(this.repo.save(t1));
    }
}
