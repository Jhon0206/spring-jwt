package dev.jhon0206.springjwt.util;

public record RegisterRequest(
  String name,
  String lastname,
  String email,
  String password) { }
