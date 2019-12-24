package com.serzh.domain;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET state = 'DELETED' WHERE id = ?", check = ResultCheckStyle.COUNT)
@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = ?1 AND u.state = 'ACTIVE'")
//em.createNamedQuery(“User.findById”, User.class);
@Where(clause = "state <> 'DELETED'") // you can't override it, but you can ignore @Where clause by defining nativeQuery
public class User implements Serializable {

    private static final long serialVersionUID = -8137927479202284270L;

    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String email;
    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserState state = UserState.ACTIVE;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @PreRemove
    public void deleteUser() {
        this.state = UserState.DELETED;
    }
}