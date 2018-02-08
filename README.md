# Service to Service Rest Comm. ( with different context roots )

## Introduction

Purpose of this project is to display simple service to service communication with different context-root per application.
Includes two alternative ways to do it with Ribbon which both does the same thing with different mechanisms ( one fully from client side,
and one under the hood)


## Installation

Just run ./demo.sh which would spin up 5 services:

One registry,
3 service A with different context roots : "/" , "/omg" , "/omg2"
1 service B to make a sys2sys rest call to service A

## Testing

Making a request to : ```localhost:8082/test``` and ```localhost:8082/test2``` would both display service to service call,
with load balanced requests. Each service would also display their different context roots as the calls are made.
