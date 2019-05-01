package org.amarcel.app.pets.resolvers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import org.amarcel.app.pets.entities.Pet;

@Component
public class Query implements GraphQLQueryResolver {

  public List<Pet> pets()  {
    Pet pet1 = new Pet();
    pet1.id = 1L;
    pet1.name = "Bill";
    pet1.age = 9;

    Pet pet2 = new Pet();
    pet2.id = 2L;
    pet2.name = "Boul";
    pet2.age = 99;

    List<Pet> res = new ArrayList<>();
    res.add(pet1);
    res.add(pet2);
    return res;
  }

}
