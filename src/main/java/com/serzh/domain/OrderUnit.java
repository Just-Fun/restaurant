package com.serzh.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.serzh.service.util.Constants.DATE_TIME_FORMAT_PATTERN;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_unit")
public class OrderUnit implements Serializable {

    private static final long serialVersionUID = -8137927472202281169L;
    private static final String ORDER_UNIT_SEQ = "ORDER_UNIT_SEQ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ORDER_UNIT_SEQ)
    @SequenceGenerator(name = ORDER_UNIT_SEQ, sequenceName = "SEQUENCE_ORDER_UNIT", allocationSize = 10)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "fk_item_order_unit"))
    private Item item;

    private Integer quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_PATTERN)
    private LocalDateTime orderedTime;

    @ManyToOne
    @JoinColumn(name = "bill_order_id", foreignKey = @ForeignKey(name = "fk_bill_order_order_unit"))
    private BillOrder billOrder;

}