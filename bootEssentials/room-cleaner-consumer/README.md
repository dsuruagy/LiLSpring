Commented by Guido HelmersGuido Helmers

The AMQP message consumer created in section 4.6 expects that there is a RabbitMQ message broker running on the default port on localhost. For those who are having trouble getting that up and running, this is what I did:

There is a Docker image for RabbitMQ on https://hub.docker.com/_/rabbitmq

1. Make sure you have Docker installed on your machine (for instance Docker Desktop for Mac).
2. Download the image: docker pull rabbitmq
3. Start a container of this image. Make sure to publish the ports to localhost:
docker run -d --hostname rabbit-boot2-essentials --name rabbit-boot2-essentials -p 5672:5672 -p 15672:15672 rabbitmq:3-management

RabbitMQ is now available at:
* Broker: localhost:5672
* Management console: http://localhost:15672

You should now be able to start the consumer at the end of section 4.6, and you will see a new connection appear in the RabbitMQ management console...


But... at the end of section 4.7, messages don't seem to come through from the CLR to the consumer on my machine.

I started the consumer, and it seems to connect properly to RabbitMQ. 
Log message: Created new connection: rabbitConnectionFactory#264c5d07:0/SimpleConnection@1f387978 [delegate=amqp://guest@127.0.0.1:5672/, localPort= 65437] 
And in the RabbitMQ console I see the connection appear.

I started the CLR application:
Created new connection: rabbitConnectionFactory#5eccd3b9:0/SimpleConnection@1ac45389 [delegate=amqp://guest@127.0.0.1:5672/, localPort= 65464]
Auto-declaring a non-durable, auto-delete, or exclusive Queue (room-cleaner) durable:false, auto-delete:false, exclusive:false. It will be redeclared if the broker stops and is restarted while the connection factory is alive, but all messages will be lost.

And then I see all the "Sending message" log messages. So it sending something somewhere. 

However, no logging in my consumer application, and also in the RabbitMQ management console I see no messages passing through any of the queues.

I checked the application.properties files, they're identical on both sides (same queue name, same exchange) so that shouldn't be the problem.

Any ideas?