package com.bhagwat.scm.orderService.event;

import com.bhagwat.scm.orderService.dto.AddOrderLineCommand;
import com.bhagwat.scm.orderService.dto.CreateOrderCommand;
import com.bhagwat.scm.orderService.dto.OrderCreatedEvent;
import com.bhagwat.scm.orderService.dto.OrderLineAddedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.apache.tomcat.jni.SSLConf.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private int numberOfLines;

    public OrderAggregate() {
        // Default constructor for Axon Framework
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        apply(new OrderCreatedEvent(command.getOrderId(), command.getCustomerId(),
                command.getOrderCreatedDate(), command.getConsignmentId()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.numberOfLines = 0; // Initial state
    }

    // Add other handlers
    @CommandHandler
    public void handle(AddOrderLineCommand command) {
        apply(new OrderLineAddedEvent(command.getOrderId(), command.getLineId(),
                command.getSkuId(), command.getOrderedQuantity()));
    }

    @EventSourcingHandler
    public void on(OrderLineAddedEvent event) {
        this.numberOfLines += 1;
        // Optionally, store order line details in a separate projection
    }
}

