package com.ss.poster.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post")
public class Post extends BaseModel {
    @Column
    private String content;

    @JsonBackReference(value = "user-post")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonBackReference(value = "post-reply")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replied_at")
    private Post repliedAt;

    @JsonManagedReference(value = "post-reply")
    @OneToMany(mappedBy = "repliedAt", fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE)
    List<Post> replies = new ArrayList<>();
}
