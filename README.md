﻿# Munchies

An app to simplify the food ordering process in small and medium size
companies

Food is an important and integral part of everyday life at any company. A lot of time and effort is
spent when:
1. employees decide on the restaurant
2. make the order to the chosen restaurant

This application tries to alleviate these problems by offering employees a centralized repository
of restaurants along with contact information and menus. Furthermore it allows employees to
create group orders that can easily be relaid to restaurants. It does this by having two distinct
parts:

**1. Administration portal**

Used for: adding restaurants, global configuration

**2. Employee portal**

Used for: viewing restaurant information, creating a group order, submitting individual
orders to the group order

## TECHNOLOGY STACK

* Spring via Spring Boot
* Sping Security
* MySQL as the RDMBS
* Flyway migrations
* Hibernate as an ORM
* Thymeleaf for server-side templating
* Twitter Bootstrap for the UI
* Slack Bolt API

## Roles

**1. Administrator**

Authenticated by an email and password. Only this role can access the administration
portal.

**2. Employee (anonymous)**

This role does not have any authentication requirements. All unauthenticated requests
are considered to belong to this role. This role can access the employee portal.

## Functional Requirements
Administration portal **/admin**.

The following pages require an authenticated user, which is accomplished by setting antmatchers:
* /admin
* /login
* /restaurants/delete/{id}
* restaurants/edit/{id}

| ***Page Name***                          | ***Description***                                                                                                                                                                                                                                                                                                      |
|------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **/admin**                               | View is currently empty.                                                                                                                                                                                                                                                                                                                   |
| **/login**                               | Simple login form with email and password fields.                                                                                                                                                                                                                                                                      |
| **/restaurants/add**                     | A form which allows administrators to add a new restaurant to the system. Input fields: restaurant name, address, phone number, menu URL, delivery information (delivery time, additional charges, etc.).                                                                                                              |
| **/restaurants**                         | A table with all restaurants in the system. Following columns are present: restaurant name, restaurant short name (generated by replacing spaces with underscores), address, phone number, menu link.                                                                                                                  |
| **/restaurants/restaurant-details/{id}** | Page which allows the administrator to view all restaurant details. This page has all fields associated with the restaurant. Furthermore it allows the administrator to delete and edit the restaurant. In case the delete action has been selected user is asked to confirm his choice through a confirmation dialog. |
| **/restaurants/edit/{id}**               | A form which allows the administrator to edit restaurant information. Structure of the edit form is the same as in the Add restaurant page.                                                                                                                                                                            |

Employee portal **/employee**

| ***Page Name***                               | ***Description***                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
|-----------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **/employee/**                                | A table with all active group orders                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
| **/restaurants/**                             | A table with all restaurants in the system. Following columns are present: restaurant name, restaurant short name (generated by replacing spaces with underscores), address, phone number, menu URL. Furthermore, the last column contains a link to the New group order page for this restaurant and restaurant details.                                                                                                                                                                                                                                                                                                                                                                  |
| **/restaurants/restaurant-details/{id}**      | Page which allows the employee to view all restaurant details. This page has all fields associated with the restaurant. This page have create new group order button.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| **/restaurants/order/add**                    | Page which allows the employee to add new group order from forms. Employee needs to enter name, select restaurant from drop down list and optionaly enter timeout for order.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| **/restaurants/order/{id}/group-order_items** | This page is displayed when a new group order has been created for a specific restaurant. It consists of three parts: <ol><li>Order information: order URL, order timeout (always 10 mins since the order start), order creator, restaurant name, restaurant phone no., menu URL</li> <li>Link to selection form which is used by other employees to add their selection to the order. This form has fields for: employee name, item name, and price.</li><li>Table of all selections for the current order. This table contains employee name, item name, and price and is automatically refreshed every 2 seconds. A total of all orders is displayed at the end of the table.</li></ol> |

**Employee and administrator share the same view. All separation is done with validation in view if the current user is authenticated or not.**

## DATABASE

The only data kept in the employee table is for admin users. Ideally, we will be able to relate to order goods, group orders, and have data for each user in the future.

![img.png](img.png)

## SLACK
I'm using the Slack Bolt API to implement Slack Bot, which is set up with the endpoint /slack/events where all command (POST methods) are submitted. The environment configuration includes SLACK BOT TOKEN and SLACK SIGNING SECRET. LayoutBrick lists are used to create the layout for sending all responses.


| ***Commands***                                         | ***Description***                                                                                                      |
|--------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| **/restaurants**                                       | Returns all restaurants in the system. Each restaurant is defined by restaurant name, address, phone number, menu URL. |
| **/order_new {restaurant_short_name} {order_timeout}** |This command allows the employee to start a new group order. Employees must enter the restaurant id and the order timeout. Bot return information about group order: id, initialized by, timeout, restaurant name, restaurant phone number, restaurant url |
|**/order {id} add {item_description} {item_price}** | This command is used by other employees to add their selection to the order. Employee name is derived from the message sender. Bot return information about ordered item: order id, employee name, price, description. |
|**/order_info {id}** | Return all information associated with an order. |
