package com.example.springbootmvcthymeleaf.sec.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRole {
    @Id
    private String role;

    @ManyToMany(mappedBy = "roles",fetch=FetchType.EAGER)
    private List<AppUser> users = new ArrayList<>();
}
