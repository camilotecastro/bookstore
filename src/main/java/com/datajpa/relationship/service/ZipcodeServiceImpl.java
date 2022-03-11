package com.datajpa.relationship.service;

import com.datajpa.relationship.dto.requestdto.ZipCodeRequestDto;
import com.datajpa.relationship.persistence.entity.City;
import com.datajpa.relationship.persistence.entity.Zipcode;
import com.datajpa.relationship.persistence.repository.ZipCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ZipcodeServiceImpl implements ZipcodeService {
    private final ZipCodeRepository zipCodeRepository;
    private final CityService cityService;

    @Autowired
    public ZipcodeServiceImpl(ZipCodeRepository zipCodeRepository, CityService cityService) {
        this.zipCodeRepository = zipCodeRepository;
        this.cityService = cityService;
    }

    @Transactional
    @Override
    public Zipcode addZipcode(ZipCodeRequestDto zipCodeRequestDto) {
        Zipcode zipcode = new Zipcode();
        zipcode.setName(zipCodeRequestDto.getName());
        if (zipCodeRequestDto.getCityId() == null)
            return zipCodeRepository.save(zipcode);
        City city = cityService.getCity(zipCodeRequestDto.getCityId());
        zipcode.setCity(city);
        return zipCodeRepository.save(zipcode);
    }

    @Override
    public List<Zipcode> getZipcodes() {
        return StreamSupport
                .stream(zipCodeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Zipcode getZipcode(Long zipcodeId) {
        return zipCodeRepository.findById(zipcodeId)
                .orElseThrow(()->
                        new IllegalArgumentException(
                                "Zipcode with id: " + zipcodeId + "could not be found"));
    }

    @Override
    public Zipcode deleteZipcode(Long zipcodeId) {
        Zipcode zipcode = getZipcode(zipcodeId);
        zipCodeRepository.delete(zipcode);
        return zipcode;
    }

    @Transactional
    @Override
    public Zipcode editZipcode(Long zipcodeId, ZipCodeRequestDto zipCodeRequestDto) {
        Zipcode zipcodeToEdit = getZipcode(zipcodeId);
        zipcodeToEdit.setName(zipCodeRequestDto.getName());
        if (zipCodeRequestDto.getCityId() != null)
            return zipcodeToEdit;
        City city = cityService.getCity(zipCodeRequestDto.getCityId());
        zipcodeToEdit.setCity(city);
        return zipcodeToEdit;
    }

    @Transactional
    @Override
    public Zipcode addCityToZipcode(Long zipcodeId, Long cityId) {
        Zipcode zipcode = getZipcode(zipcodeId);
        City city = cityService.getCity(cityId);
        if (Objects.nonNull(zipcode.getCity()))
            throw new IllegalArgumentException("Zipcode already has a city");
        return zipcode;
    }

    @Transactional
    @Override
    public Zipcode removeCityFromZipcode(Long zipcodeId) {
        Zipcode zipcode = getZipcode(zipcodeId);
        if (!Objects.nonNull(zipcode.getCity()))
            throw new IllegalArgumentException("Zipcode does not have a city");
        zipcode.setCity(null);
        return zipcode;
    }
}
