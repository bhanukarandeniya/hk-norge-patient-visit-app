package com.norge.patientvisit.dto;

import org.modelmapper.ModelMapper;

public class DtoConverter<V, K> {

    private ModelMapper modelMapper;

    public DtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private K convertToDto(V entity, ClassName className) throws ClassNotFoundException {
        K value = (K) modelMapper.map(entity, Class.forName(className.toString()));
        return value;
    }

    private K convertToEntity(V dto, ClassName className) throws ClassNotFoundException {
        K value = (K) modelMapper.map(dto, Class.forName(className.toString()));
        return value;
    }
    
}
