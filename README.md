# Cloud Computing Path

Creating **RESTful APIs** and deploying to [Google Cloud Platform](https://cloud.google.com)
by using [Google App Engine](https://cloud.google.com/appengine) and [Google Compute Engine](https://cloud.google.com/compute) for communication between **Machine Learning System Model** and **Mobile Development**. And Creating database in [Cloud NoSQL (Mongodb)](https://console.cloud.google.com/marketplace/product/mongodb/mdb-atlas-self-service?authuser=3&project=gloapp-389203).

# RESTful APIs
In making the **RESTful APIs** we use [Node.js](https://github.com/python) and [Python](https://www.python.org/) using the Framework with [Flask](https://flask.palletsprojects.com/en/2.0.x/) and [Express.js](https://expressjs.com/). For responses using **JSON** format. For each URL that can be used will be explained below.

# Authentication
In this section for login, register and reset password using express.js. Response from each URL using **JSON** format.

**Base URL :**

> http://localhost:8000
**Path :**

> /gloapp/register

**Method :**

> `POST'

**Register User**
  > http://localhost:8000//gloapp/register

**Example request**
 - Raw Format
```json
{
  "username" : "example123",
  "password" : "admin123",
  "email": "example@gmail.com",
  "mobile": 8009860560,
  "profile": ""
}
<br>
**Example Response**
- status code 200 OK
```json
{
  msg: "Selamat! Registrasi Berhasil"
}
