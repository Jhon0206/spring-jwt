package dev.jhon0206.spring_jwt.util;

public record RegisterRequest(
  String name,
  String lastname,
  String email,
  String password) { }
