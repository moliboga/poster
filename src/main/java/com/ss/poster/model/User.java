package com.ss.poster.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseModel {
    @Column
    private String nickname;
    @Column()
    private String name;
    @Column()
    private String info;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    List<Post> posts = new ArrayList<>();
}
