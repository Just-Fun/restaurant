package com.serzh.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = -8137927479202281169L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String name;

    private String description;

    private String image;

    @Column(scale = 5, precision = 20, nullable = false)
    private BigDecimal price;

    @ToString.Exclude
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "item_types",
            joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_item_types_item")),
            inverseJoinColumns = @JoinColumn(name = "type_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_item_types_type"))
    )
    private Set<Type> types = new HashSet<>();

}
