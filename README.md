This is a lost and found portal backend built in Spring boot with postgres database

Front-end here: https://github.com/agalzh/lost_and_found_frontend
**There are two types of users:**
people who find lost item: post the pic and details about it
people who lost item:post the pic and other details about the thing they lost

**priveleges:**
normal user:as mentioned above-only create , edit , delete their own posts
admin:above priveleges + delete other posts an

without login:can view all posts

it is highly secured with jwt auth

**To just use this code without any setup(container way): **

(Make sure you got docker,podman or any other container service configured in your device)

1)clone the repo
2)setting up env variables
  2.1)extract  export DBUSERNAME=["your db username here"]
  2.2)export DBPASSWORD=["your password"]
3)cd into the cloned directory
3)run "docker compose up"

all set your backend server will run 

you can accesss the docs in http://localhost:8080/swagger-ui/index.html

then use it as you like

**If you want the whole code setup**
using ide like intellij is recommended

1)clone the repo
2)setting up env variables
  2.1)extract  export DB_USERNAME=["your db username here"]
  2.2)export DB_PASSWORD=["your password"]
3)cd into the cloned directory
4)just click the run button
or 
4)./mvnw clean compile
./mvnw spring-boot:run
in cmd line
[using ide is highly recommended as dependencies usually mess a lot]



