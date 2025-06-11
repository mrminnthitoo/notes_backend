package com.mrminnthitoo.notes_backend.helpers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass){
        return source.stream()
                .map(element->this.modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public <D> D map(Object source, Class<D> destinationType){
        return this.modelMapper.map(source, destinationType);
    }

    public ModelMapper getModelMapper(){
        return this.modelMapper;
    }

}
