# Learning-Event-Sourcing

## Components

### Events

An **event** is a record of something that happened in the system. Events are the primary source of truth in event sourcing. Instead of storing the current state of an entity, a sequence of events describing the entity's changes over time is stored. The main types of events include:

- **Order Created**
- **Order Updated**
- **Order Deleted**
- **Invoice Paid**

These events are represented by the `OrderEvent` class, which captures details such as order ID, customer ID, items, event type, and timestamp.

### Stream

A **stream** is a sequence of events related to a specific entity or process. In event sourcing, events are stored in streams, with each stream representing a timeline of changes for a particular entity.. Kafka acts as a distributed log, storing events as they are produced and allowing subscribers to consume these events.

- **Kafka Producer:** Sends events to a Kafka topic named `order-events`.
- **Kafka Consumer:** Listens to the `order-events` topic and processes the events to update projections.

### Projection

Projections are read models created from events to provide a current state of the application in an easily consumable form. In this application, projections are used to generate summaries of orders.

- **OrderProjectionService:** Listens to events from the Kafka topic and updates the `OrderSummary` repository accordingly. It reconstructs the current state of an order by applying all relevant events.

### Subscription

Subscriptions enable services to react to events asynchronously. In this application, Kafka consumers act as subscribers to the `order-events` topic.

- **Kafka Listener:** The `OrderProjectionService` contains a Kafka listener method annotated with `@KafkaListener` that processes incoming events and updates the projection (i.e., order summary).
