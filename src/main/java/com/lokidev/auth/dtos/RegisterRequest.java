package com.lokidev.auth.dtos;

import com.lokidev.auth.models.RoleName;

public record RegisterRequest(String username, String password, RoleName roleName) {}
