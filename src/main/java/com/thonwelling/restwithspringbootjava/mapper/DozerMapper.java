package com.thonwelling.restwithspringbootjava.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;
/**
 * O Dozer garante que os seus objetos de domínio interno não sejam vistos pelas camadas de apresentação ou por
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
public class DozerMapper {
  private static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

  public static <O, D> D parseObject(O origin, Class<D> destination) {
    return mapper.map(origin, destination);
  }
  public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
    List<D> destinationObjects = new ArrayList<D>();
    for (O o : origin) {
      destinationObjects.add(mapper.map(o, destination));
    }
    return destinationObjects;
  }
}
