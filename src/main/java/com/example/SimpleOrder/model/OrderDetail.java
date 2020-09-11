package com.example.SimpleOrder.model;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "order_detail")
public class OrderDetail extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonProperty("order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_id", nullable = false)
    @JsonProperty("inventory_id")
    private Inventory inventory;

    /**
     *  Choice selectedChoice = poll.getChoices().stream()
     *                 .filter(choice -> choice.getId().equals(voteRequest.getChoiceId()))
     *                 .findFirst()
     *                 .orElseThrow(() -> new ResourceNotFoundException("Choice", "id", voteRequest.getChoiceId()));
     *
     *         Vote vote = new Vote();
     *         vote.setPoll(poll);
     *         vote.setUser(user);
     *         vote.setChoice(selectedChoice);
     *
     *         //choice la inventory
     *         //for each inventory item, insert new order detail
     *
     *
     * concurrency when updating:
     *  (1)- if there is new item in the update order: same as create new order for that item, or the case of: same item being ordered
     *  at the same time, then JPA optimistic lock handle that for us
     *  (2)- if there is update quantity of existing item:
     *      + if new quantity less than old quantity:
     *            - update order detail for that itemId and orderId
     *            - update inventory quantity for that itemId
     *      + if new quantity greater than old quantity
     *            - same as (1)
     *  Conclusion: the whole create order and update order has to be transactional
     *  Test: spawn a thread pool and order same item with quantity greater than inventory availability
     *
     */
    @NotNull
    private int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}