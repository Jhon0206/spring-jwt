package dev.jhon0206.springjwt.util;

public record RegisterResponse(
  String email,
  String status,
  String message) { }
