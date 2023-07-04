package com.thonwelling.restwithspringbootjava.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * O ModelMapper garante que os seus objetos de domínio interno não sejam vistos pelas camadas de apresentação ou por
 * consumidores externos. Ele também pode ajudar a mapear os seus objetos de domínio para chamadas externas de API’s
 * e vice-versa.
 * Em outras palavras, ao invés de você expor as entidades JPA, por exemplo, o que tornaria indiretamente públicas
 * informações da sua base de dados, cria um POJO similar às suas entidades JPA, contudo sem as anotações,
 * e expõe esse POJO para os clientes da sua aplicação. Entre o POJO exposto e a entidade JPA poderia utilizar
 * um framework como o Dozer e parsear dados das suas entidades JPA para o POJO exposto e vice-versa, encapsulando
 * informações importantes da sua aplicação.
 *
 * @author Thonwelling
 * */

@Component
public class ModelMapperMapping {
  private final ModelMapper modelMapper;

  @Autowired
  public ModelMapperMapping(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }
  public <Origin, Destination> Destination parseObject(Origin origin, Class<Destination> destination) {
    return modelMapper.map(origin, destination);
  }
  public <Origin, Destination> List<Destination> parseListObjects(List<Origin> origin, Class<Destination> destination) {
    return origin.stream()
        .map(origem -> modelMapper.map(origem, destination))
        .collect(Collectors.toList());
  }
}