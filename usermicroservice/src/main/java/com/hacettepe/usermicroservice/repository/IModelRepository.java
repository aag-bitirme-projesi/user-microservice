package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface IModelRepository extends JpaRepository<Model, Long> {

    Optional<Model> findByName(String name);

}
