package com.socio.socio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "follows",
        uniqueConstraints ={
                @UniqueConstraint(columnNames = {"follower_id", "following_id"})
        }
)
@Getter
@Setter
public class Follow {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private  Long id;

        @ManyToOne
        @JoinColumn(name = "follower_id")
        private User follower;

        @ManyToOne
        @JoinColumn(name = "following_id")
        private User following;

}
