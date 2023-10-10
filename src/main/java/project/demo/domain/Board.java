package project.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "board")
    private List<Post> postList = new ArrayList<>();

    public void changeName(String name){
        this.name = name;
    }
    @Builder
    public Board(Long id, String name, List<Post> postList) {
        this.id = id;
        this.name = name;
        this.postList = postList;
    }
}
