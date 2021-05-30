package com.norge.patientvisit.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DtoConverter<E, D> {

    private ModelMapper modelMapper;

    public DtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public D convertToDto(E entity, Class c) throws ClassNotFoundException {
        D value = (D) modelMapper.map(entity, c);
        return value;
    }

    public E convertToEntity(D dto, Class c) throws ClassNotFoundException {
        E value = (E) modelMapper.map(dto, c);
        System.out.println(value.toString());
        return value;
    }

    public List<D> convertToDtoList(List<E> entityList, Class c) throws ClassNotFoundException {
        List<D> list = new ArrayList<>();
        for (E e : entityList) {
            list.add(convertToDto(e, c));
        }
        return list;
    }

}
