package org.amarcel.app.pets.resolvers;

import java.util.ArrayList;
import java.util.List;

import org.amarcel.app.pets.entities.Age;
import org.amarcel.app.pets.entities.Animal;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import org.amarcel.app.pets.entities.Pet;

@Component
public class Query implements GraphQLQueryResolver {

  public Animal animal() {
    Animal a = new Animal();
     a.prop1 = "pro1";
     a.prop2 = "pro2";
     a.prop3 = "pro3";
     a.prop4 = "pro4";
     a.prop5 = "pro5";
     a.prop6 = "pro6";
     a.prop7 = "pro7";
     a.prop8 = "pro8";
     a.prop9 = "pro9";
     a.prop10 = "pro10";
     a.prop11 = "pro11";
     a.prop12 = "pro12";
     a.prop13 = "pro13";
     a.prop14 = "pro14";
     a.prop15 = "pro15";
     a.prop16 = "pro16";
     a.prop17 = "pro17";
     a.prop18 = "pro18";
     a.prop19 = "pro19";
     a.prop20 = "pro20";
     a.prop21 = "pro21";
     a.prop22 = "pro22";
//     a.prop23 = "pro23";
//     a.prop24 = "prop24";
//     a.prop25 = "prop25";
     return a;
  } 
  
  public List<Pet> pets()  {
    Pet pet1 = new Pet();
    pet1.id = 1L;
    pet1.name = "Bill";
    pet1.age = 9;

    Pet pet2 = new Pet();
    pet2.id = 2L;
    pet2.name = "Bob";
    pet2.age = 99;

    List<Pet> res = new ArrayList<>();
    res.add(pet1);
    res.add(pet2);
    return res;
  }

}
