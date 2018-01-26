# nats-requestreply-example
[![Build Status](https://travis-ci.org/gregwhitaker/nats-requestreply-example.svg?branch=master)](https://travis-ci.org/gregwhitaker/nats-requestreply-example)

An example of using [NATS](https://nats.io) for request-reply messaging.

This example starts a service that responds with `World! from {server id}` when it receives a request with the message `hello`. If you are running multiple service
instances, requests will be automatically load-balanced between them.

## Background
NATS supports two flavors of request reply messaging: point-to-point or one-to-many. Point-to-point involves the fastest or first to respond. In a one-to-many exchange, you set a limit on the number of responses the requestor may receive.

In a request-response exchange, publish request operation publishes a message with a reply subject expecting a response on that reply subject. You can request to automatically wait for a response inline.

The request creates an inbox and performs a request call with the inbox reply and returns the first reply received. This is optimized in the case of multiple responses.

More information can be found in the [NATS Documentation](https://nats.io/documentation/concepts/nats-req-rep/).

## Prerequisites
The examples require a local NATS server to be running. To start a NATS server as a Docker container run the following commands:

    $ docker pull nats
    $ docker run -p 4222:4222 -p 6222:6222 -p 8222:8222 -d --name nats-main nats

## Running the Example
### Start the Request-Reply Client
You can start the [Request-Reply Client](requestreply-client/README.md) using the following command:

    $ ./gradlew :requestreply-client:run
    
Once the client and service is running you will see messages similar to the following in the terminal:

    [main] INFO nats.example.requestreply.client.Main - Starting NATS Example Request-Reply Client
    [main] INFO nats.example.requestreply.client.Main - SENT: Hello
    [main] INFO nats.example.requestreply.client.Main - RECEIVED: World! from 494ad392-e136-4c40-9d1f-69a2d75a5ce6

### Start the Request-Reply Service
You can start the [Request-Reply Service](requestreply-service/README.md) using the following command:

    $ ./gradlew :requestreply-service:run

Once the cliend and service is running you will see messages similar to the following in the terminal:

    [main] INFO nats.example.requestreply.service.Main - Starting NATS Example Request-Reply Service 2c84095d-6a17-4db1-86c3-6d41eb247d9e
    [jnats-subscriptions] INFO nats.example.requestreply.service.Main - Received Message From _INBOX.9VgixJvxwVp7Ehi8gej7GJ: Hello
    [jnats-subscriptions] INFO nats.example.requestreply.service.Main - Received Message From _INBOX.9VgixJvxwVp7Ehi8gej7IK: Hello
    [jnats-subscriptions] INFO nats.example.requestreply.service.Main - Received Message From _INBOX.9VgixJvxwVp7Ehi8gej7KL: Hello

You can start multiple services and see that requests are automatically load-balanced between the instances.

## Bugs and Feedback
For bugs, questions and discussions please use the [Github Issues](https://github.com/gregwhitaker/nats-requestreply-example/issues).

## License
MIT License

Copyright (c) 2018 Greg Whitaker

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.