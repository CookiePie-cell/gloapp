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
  > http://localhost:8000/gloapp/register

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
```
**Example Response**
- Status Code 200 OK
```json
{
  "msg": "Selamat! Registrasi Berhasil"
}
```

**Path :**
> /gloapp/login

**Method :**

> `POST'

**Login User**
  > http://localhost:8000/gloapp/login

**Example request**
 - Raw Format
```json
{
  "username" : "example123",
  "password" : "admin123",
}
```
**Example Response**
- Status Code 200 OK
```json
{
  "msg": "Login Berhasil",
  "username": "example123",
  "token": "SAKSNKANAKSNASNKQWNQOWHROQ91928491824192412JB41JB21 241B"
}
```

**Path :**
> /gloapp/resetPassword

**Method :**

> `PUT'

**Reset Password**
  > http://localhost:8000/gloapp/resetPassword

**Example request**
 - Raw Format
 - query required : username, code
```json
{
  "username" : "example123",
  "password" : "admin111",
}
```
**Example Response**
- Status Code 200 OK
```json
{
  "msg": "Password berhasil diganti ...!",
}
```

**NOTE**
- Several routes have yet to be explained, such as registerMail, authenticate, get username, generateOTP, VerifyOTP, createResetSession, and update user. Please try and explore further. Thank you


# API Machine Learning
In this section for model data machine learning using Flask python. Response from each URL using **JSON** format.

**Base URL :**

> http://:192.168.100.68:5000

**Path :**
> /

**Method :**

> `GET'

**GET entrypoint link**
  >  http://:192.168.100.68:5000

**Example request**
 - None

**Example Response**
- Status Code 200 OK
```json
{
  "msg": "Method GET berhasil"
}
```

**Path :**
> /predict

**Method :**

> `POST'

**GET entrypoint link**
  >  http://:192.168.100.68:5000/predict

**Example request**
 - form-data:
| Key                   | Value      |            
| --------------------- | ---------- |
| file                  | acne.jpg   | 

**Example Response**
- Status Code 200 OK
```json
{
    "diseaseName": "Acne",
    "accuracy": "99.79778",
    "description": "Jerawat adalah masalah kulit yang terjadi ketika pori-pori kulit tersumbat oleh kotoran kotoran, debu, minyak, ataupun sel kulit mati.  Hal ini menyebabkan peradangan yang ditandai dengan munculnya benjolan kecil yang terkadang berisi nanah di atas kulit. Jerawat tidak hanya terjadi pada area wajah saja, tetapi seluruh bagian tubuh dengan kelenjar minyak terbanyak, seperti leher, bagian atas dada, dan punggung.",
    "cause": "Penyakit Acne disebabkan oleh beberapa faktor, seperti produksi minyak berlebih oleh kelenjar sebum di kulit yang dapat menyumbat folikel rambut. Bakteri Propionibacterium acnes juga berperan dalam menyebabkan peradangan dan memperburuk kondisi jerawat. Faktor genetik, perubahan hormon, penggunaan kosmetik yang tidak cocok, serta stres juga dapat mempengaruhi timbulnya jerawat, dan pola makan yang tidak sehat.",
    "prevention": "Ada beberapa langkah penting untuk mencegah Acne. Pertama, bersihkan riasan wajah sebelum tidur dan cuci muka dua kali sehari dengan pembersih yang sesuai. Mengelola stres dengan olahraga atau meditasi juga penting karena stres dapat memicu perubahan hormonal yang berkontribusi pada jerawat. Hindari pakaian ketat dan gunakan pakaian yang longgar berbahan bernapas. Pilih produk kosmetik non-komedogenik dan hindari yang mengandung minyak berlebih. Terakhir, jaga kebersihan tubuh dengan mandi setelah beraktivitas.",
    "disclaimer": "Untuk diagnosa lebih lanjut terkait jenis penyakit yang Anda alami, silakan menghubungi dokter terkait. Apabila terjadi gejala yang lebih parah, segerakan untuk berkonsultasi kepada dokter kulit."
}
```
