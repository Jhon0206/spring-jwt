package dev.jhon0206.springjwt.user.dto;

import dev.jhon0206.springjwt.user.RolesEnum;
import dev.jhon0206.springjwt.user.StatusEnum;

public record UserResponse(
    Integer id, String name, String lastName, String email, RolesEnum role, StatusEnum status) {

}
