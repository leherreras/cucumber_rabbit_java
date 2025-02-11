#!/bin/bash

until rabbitmqctl status; do
  echo "Esperando a que RabbitMQ esté listo..."
  sleep 2
done

rabbitmqadmin -u admin -p admin declare exchange name=testing.customer type=direct
rabbitmqadmin -u admin -p admin declare queue name=testing.customer.query.is-customer-available
rabbitmqadmin -u admin -p admin declare queue name=testing.customer.response.is-customer-available
rabbitmqadmin -u admin -p admin declare binding source=testing.customer destination=testing.customer.query.is-customer-available routing_key=query.is-customer-available
rabbitmqadmin -u admin -p admin declare binding source=testing.customer destination=testing.customer.response.is-customer-available routing_key=response.is-customer-available

echo "Rabbit configurado exitosamente."
