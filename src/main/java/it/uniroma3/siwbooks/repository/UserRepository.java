/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.uniroma3.siwbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siwbooks.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
