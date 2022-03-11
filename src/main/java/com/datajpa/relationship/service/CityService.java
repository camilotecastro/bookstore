package com.datajpa.relationship.service;

import com.datajpa.relationship.dto.requestdto.CityRequestDto;
import com.datajpa.relationship.persistence.entity.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CityService {
    public City addCity(CityRequestDto cityRequestDto);
    public List<City> getCities();
    public City getCity(Long cityId);
    public City deleteCity(Long cityId);
    public City editCity(Long cityId, CityRequestDto cityRequestDto);
}
