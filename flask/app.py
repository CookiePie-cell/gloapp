from flask import Flask, request
import numpy as np 
from tensorflow.keras.preprocessing import image
from tensorflow.keras.models import load_model
import os
import tempfile
import json

app = Flask(__name__)

model = load_model('gloapp.h5')

@app.route('/', methods=['GET'])
def hello_world():
    return 'Hello World'

@app.route('/predict', methods=['POST'])
def predict():
    # Retrieve the image file from the POST request
    image_file = request.files['file']

    # Save the file to a temporary location
    temp_path = tempfile.mktemp(suffix='.jpg')
    image_file.save(temp_path)

    # Load and preprocess the image
    img = image.load_img(temp_path, target_size=(299, 299))
    img_array = image.img_to_array(img)
    img_array = np.expand_dims(img_array, axis=0)
    img_array = img_array / 255.0

    # Make the prediction using the loaded model
    predictions = model.predict(img_array)

    list_ = []

    result_arr = predictions[0] * 100
    list_.append(result_arr)

    classes = [
        'Acne', 'Dermatophyte Infections Ringworms', 'Eczema', 'Hair Diseases', 'Insecets Bite and Sting',
        'Nail Disease', 'Pigment Disorder', 'Skin Cancer', 'Urticaria Skin Alergey', 'Vascular Tumors', 'Vasculitis', 'Versicolor'
    ]
    list_.append(classes)

    # Match the highest accuracy prediction index with the skin disease index
    max_ = np.max(list_[0])
    max_index = np.where(list_[0] == max_)
    index = max_index[0][0]
    disease_name = list_[1][index]
    accuracy = str(max_)

    # Generate the JSON response
    response = {
        "diseaseName": disease_name,
        "accuracy": accuracy,
        "description": get_disease_description(disease_name),
        "cause": get_disease_cause(disease_name),
        "prevention": get_disease_prevention(disease_name),
        "disclaimer": "Untuk diagnosa lebih lanjut terkait jenis penyakit yang Anda alami, silakan menghubungi dokter terkait. Apabila terjadi gejala yang lebih parah, segerakan untuk berkonsultasi kepada dokter kulit."
    }

    # Convert the response to JSON string with ordered keys
    json_response = json.dumps(response, ensure_ascii=False, indent=4)

    # Set the response content type as JSON
    headers = {'Content-Type': 'application/json'}

    # Remove the temporary file
    os.remove(temp_path)

    return json_response, 200, headers


def get_disease_description(disease_name):
    descriptions = {
        'Acne': 'Jerawat adalah masalah kulit yang terjadi ketika pori-pori kulit tersumbat oleh kotoran kotoran, debu, minyak, ataupun sel kulit mati.  Hal ini menyebabkan peradangan yang ditandai dengan munculnya benjolan kecil yang terkadang berisi nanah di atas kulit. Jerawat tidak hanya terjadi pada area wajah saja, tetapi seluruh bagian tubuh dengan kelenjar minyak terbanyak, seperti leher, bagian atas dada, dan punggung.',
        'Dermatophyte Infections Ringworms': 'Kurap merupakan penyakit yang terjadi karena adanya infeksi jamur pada kulit. Gejala yang biasanya dialami oleh penderita penyakit ini adalah memiliki ruam yang berwarna merah, gatal, dan berbentuk bundar dengan warna kulit yang lebih jelas di bagian tengah. Penyakit ini masih berhubungan dengan kutu air (tinea pedis), gatal di selangkangan (tinea cruris), dan kuran kulit kepala (tinea capitis).',
        'Eczema': 'Eksim adalah kondisi pada saat terjadinya reaksi peradangan pada kulit yang menyebabkan rasa iritasi atau terbakar pada kulit. Reaksi peradangan ini dapat ditandai dengan timbulnya warna kemerahan, ruam, dan rasa gatal. Penyakit ini dapat diderita oleh berbagai kalangan usia, mulai dari bayi, remaja, hingga dewasa.',
        'Hair Diseases': 'Hair diseases, juga dikenal sebagai gangguan rambut atau penyakit rambut, mengacu pada berbagai kondisi yang mempengaruhi rambut dan kulit kepala. Kondisi-kondisi ini dapat mempengaruhi penampilan, tekstur, dan kesehatan rambut secara keseluruhan. Adapun beberapa penyakit rambut yang umum terjadi, seperti ketombe, kutu rambu, folikulitis, kista sebasea, dan lain sebagainya.',
        'Insecets Bite and Sting': 'Serangga menggigit, menyengat, atau mengeluarkan racun sebagai pertahanan. Digigit serangga adalah bentuk dermatitis kontak iritan, yaitu peradangan kulit karena kontak dengan alergen seperti liur, bulu, atau gigitan serangga. Gejala yang timbul adalah reaksi alergi atau hipersensitivitas. Reaksi tergantung pada jenis serangga dan racun yang dikeluarkannya.',
        'Nail Disease': 'Dengan bertambahnya usia, kuku bisa menjadi lebih tebal atau lebih rapuh dan mengalami perubahan warna. Perubahan ini umumnya tidak berbahaya dan dapat diatasi dengan perawatan sederhana di rumah. Namun, jika perubahan tersebut menunjukkan penyakit kuku seperti kuku yang tumbuh ke dalam dan menimbulkan nyeri, perubahan bentuk kuku, dll., hal itu perlu ditangani secara medis. Dalam kasus yang lebih parah, penyakit kuku dapat menunjukkan adanya gangguan kesehatan yang serius. Beberapa penyakit yang dapat terjadi pada kuku adalah cantengan, infeksi jamur kkuku, kuku tumbuh ke dalam, nail pitting, dll.',
        'Pigment Disorder': 'Pigment Disorder (gangguan pigmentasi kulit) adalah suatu kondisi yang memengaruhi warna kulit. Warna kulit sendiri dipengaruhi oleh jumlah pigmen atau melanin pada kulit yang berasal dari sel-sel melanosit. Namun, sel-sel tersebut dapat rusak, baik akibat paparan sinar matahari, efek samping pengobatan, maupun kondisi medis tertentu. Ketika melanosit ini rusak, produksi melanin akan terganggu dan dapat memengaruhi warna kulit. Adapun beberapa kelainan pigmen yang umum terjadi, yaitu melasma, vitiligo, dan hiperpigmentasi pascainflamasi.',
        'Skin Cancer': 'Kanker kulit melanoma adalah kanker kulit yang berkembang dari melanosit. Melanosit adalah sel pigmen kulit yang berfungsi menghasilkan melanin, yaitu pigmen yang menghasilkan warna kulit manusia. Melanoma ditandai dengan munculnya tahi lalat baru atau adanya perubahan warna maupun bentuk pada tahi lalat yang lama. Melanoma juga dapat terbentuk di mata dan di dalam tubuh, seperti hidung atau tenggorokan.',
        'Urticaria Skin Alergey': 'Biduran adalah kondisi ketika kulit mengalami ruam gatal berwarna merah yang menimbulkan benjolan sebagai akibat reaksi kulit. Secara umum, biduran muncul dengan gejala seperti lesi kulit yang menonjol di area tubuh manapun, lesi muncul secara berkelompok dan cenderung bersifat gatal, lesi dapat berwarna merah muda, merah, ataupun berwarna kulit, serta  ukurannya dapat berkisar dari seukuran tusukan peniti hingga beberapa inci.',
        'Vascular Tumors': 'Vascular Tumors adalah pertumbuhan atau lesi abnormal yang melibatkan pembuluh darah dalam berbagai jaringan atau organ tubuh. Ada jenis-jenis seperti hemangioma dan malformasi vaskular. Hemangioma muncul segera setelah lahir, dengan fase pertumbuhan cepat dan regresi lambat. Hemangioma terdiri dari pembuluh darah kecil dan bisa muncul di permukaan kulit atau dalam jaringan dan organ. Malformasi vaskular sudah ada sejak lahir, tidak mengalami pertumbuhan cepat, dan diklasifikasikan berdasarkan jenis pembuluh darah yang terlibat.',
        'Vasculitis': 'Vaskulitis adalah kondisi medis yang ditandai oleh peradangan pada dinding pembuluh darah. Peradangan ini dapat mengenai berbagai jenis pembuluh darah, termasuk arteri, vena, dan kapiler. Vasculitis dapat menyebabkan kerusakan pada pembuluh darah, mengganggu aliran darah normal, dan berpotensi mempengaruhi organ-organ tubuh yang terhubung dengan pembuluh darah tersebut.',
        'Versicolor': 'Penyakit versicolor, atau tinea versicolor, adalah infeksi jamur pada kulit yang disebabkan oleh spesies Malassezia. Infeksi ini umumnya terjadi di daerah yang berminyak seperti punggung, dada, leher, dan lengan. Penyakit versicolor seringkali ditandai dengan munculnya bercak berwarna putih, kemerahan, cokelat, atau kekuningan pada kulit yang terkena.'
    }

    return descriptions.get(disease_name, "Description not available.")


def get_disease_cause(disease_name):
    causes = {
        'Acne': 'Penyakit Acne disebabkan oleh beberapa faktor, seperti produksi minyak berlebih oleh kelenjar sebum di kulit yang dapat menyumbat folikel rambut. Bakteri Propionibacterium acnes juga berperan dalam menyebabkan peradangan dan memperburuk kondisi jerawat. Faktor genetik, perubahan hormon, penggunaan kosmetik yang tidak cocok, serta stres juga dapat mempengaruhi timbulnya jerawat, dan pola makan yang tidak sehat.',
        'Dermatophyte Infections Ringworms': 'Penyakit kurap disebabkan oleh jamur yang dikenal sebagai dermatofita. Terdapat tiga jenis jamur yang dapat menyebabkan munculnya penyakit ini, yaitu trichophyta, microsporum, dan epidermophyton. Penyakit ini biasanya muncul pada iklim yang hangat dan lembap. Selain itu, penyakit ini juga merupakan penyakit menular dan dapat disebarkan melalui kontak langsung ataupun tidak langsung dari manusia ke manusia, hewan ke manusia, barang ke manusia, dan tanah ke manusia.',
        'Eczema': 'Sejauh ini belum dapat diketahui penyebab utama dari penyakit ini. Akan tetapi, secara umum terdapat dua faktor penyebab timbulnya penyakit ini, yaitu faktor eksternal yang berasal dari bahan kimia dan mikroorganisme, serta faktor internal seperti eksim atopik.',
        'Hair Diseases': 'Penyakit pada rambut dapat disebabkan oleh banyak hal, baik genetik maupun gaya hidup. Secara umum, penyakit pada rambut dapat disebabkan oleh adanya jamur yang berlebihan pada kulit kepala, iritasi kulit, produksi minyak berlebih, infeksi bakteri, paparan sinar matahari yang berlebihan, ataupun reaksi terhadap obat-obatan tertentu.',
        'Insecets Bite and Sting': 'Gigitan serangga dapat menyebabkan reaksi hipersensitivitas atau alergi. Jenis serangga yang dapat menyebabkan reaksi tersebut dibagi menjadi beracun dan tidak beracun. Serangga beracun seperti lebah, tawon, kalajengking, semut api, laba-laba, dan tomcat dapat menyebabkan reaksi alergi lokal, seluruh tubuh, atau reaksi alergi berat. Serangga tidak beracun seperti nyamuk, lalat, kutu, dan ulat bulu menyebabkan reaksi ringan dan lokal. Beberapa jenis serangga juga dapat menyebarkan penyakit seperti malaria, demam berdarah, virus Zika, demam chikungunya, penyakit tidur, penyakit kaki gajah, dan penyakit Lyme.',
        'Nail Disease': 'Secara umum, penyakit yang terjadi pada kuku disebabkan oleh pemotongan kuku yang tidak tepat, pemakaian sepatu yang terlalu sempit, ataupun cedera pada kuku. Selain itu, penyakit pada kuku juga dapat terjadi akibat dari rendahnya kadar oksigen dalam darah, alergi, ataupun kurangnya vitamin dalam tubuh.',
        'Pigment Disorder': 'Pigment disorder terjadi karena beberapa alasan, seperti kelainan genetik, paparan sinar matahari, perubahan hormon pada ibu hamil, stres, dan produk perawatan kulit ataupun bahan kimia lainnya yang dapat merusak kulit.',
        'Skin Cancer': 'Penyebab dari melanoma masih belum dapat dipastikan. Akan tetapi, kemungkinan ada kombinasi faktor yang mempengaruhi kanker kulit ini muncul, seperti faktor lingkungan dan genetik. Dokter meyakini bahwa paparan radiasi ultraviolet (UV) dari matahari dan lampu di kamar tidur adalah penyebab utama dari kondisi ini.',
        'Urticaria Skin Alergey': 'Kondisi ini biasanya muncul ketika ada reaksi alergi terhadap pemicu, baik yang kontak dengan kulit maupun yang dikonsumsi. Ketika seseorang memiliki reaksi alergi, tubuh mulai melepaskan histamin ke dalam darah. Namun, histamin ini dapat menyebabkan pembengkakan, gatal dan banyak gejala lainnya. Beberapa kondisi yang menjadi penyebab tubuh melepaskan histamin adalah alergi makanan, udara luar, penyakit tertentu, berkeringat, alergi tungau debu, dan stres.',
        'Vascular Tumors': 'Penyebab pasti tumor vaskular belum sepenuhnya diketahui, tetapi diyakini bahwa mereka disebabkan oleh kelainan perkembangan dalam pembentukan pembuluh darah selama perkembangan janin. Beberapa tumor vaskular juga dapat memiliki komponen genetik dan dapat berjalan dalam keluarga.',
        'Vasculitis': 'Vaskulitis terjadi ketika sistem kekebalan tubuh menyerang pembuluh darah sendiri. Penyebabnya belum diketahui secara pasti, tetapi dapat dipicu oleh kondisi berikut: a) Penyakit autoimun, seperti rheumatoid arthritis, lupus, atau skleroderma; b) Reaksi alergi terhadap penggunaan obat-obatan tertentu; c) Reaksi terhadap infeksi, seperti hepatitis B dan hepatitis C; d) Kanker darah, seperti limfoma.',
        'Versicolor': 'Penyakit versicolor disebabkan oleh infeksi jamur Malassezia pada kulit. Faktor-faktor yang mempengaruhi infeksi ini termasuk kelembaban berlebihan, produksi minyak berlebihan, kekebalan tubuh yang lemah, faktor hormonal, dan faktor genetik'
    }

    return causes.get(disease_name, "Cause not available.")


def get_disease_prevention(disease_name):
    preventions = {
        'Acne': 'Ada beberapa langkah penting untuk mencegah Acne. Pertama, bersihkan riasan wajah sebelum tidur dan cuci muka dua kali sehari dengan pembersih yang sesuai. Mengelola stres dengan olahraga atau meditasi juga penting karena stres dapat memicu perubahan hormonal yang berkontribusi pada jerawat. Hindari pakaian ketat dan gunakan pakaian yang longgar berbahan bernapas. Pilih produk kosmetik non-komedogenik dan hindari yang mengandung minyak berlebih. Terakhir, jaga kebersihan tubuh dengan mandi setelah beraktivitas.',
        'Dermatophyte Infections Ringworms': 'Cara mencegah timbulnya penyakit kurap adalah dengan menghindari kontak langsung dengan orang yang terinfeksi, tidak menggunakan pakaian yang terlalu ketat, menghindari berbagi barang-barang yang berkontak fisik secara langsung,, mengeringkan bagian tubuh  yang basah dengan benar, dan menghindari bersentuhan dengan hewan yang memiliki tanda terkena kurap.',
        'Eczema': 'Untuk mencegah penyakit eksim, langkah-langkah berikut dapat diambil. Hindari penggunaan bahan yang menyebabkan gatal seperti wol, gunakan sabun dan deterjen yang lembut, dan pastikan penggunaan pelembab udara di kamar tidur. Kurangi stress, hindari makanan pemicu alergi dan iritasi, serta jaga suhu dan kelembaban kulit yang seimbang. Hindari mandi dengan air panas, jangan memanaskan atau mendinginkan kulit secara berlebihan, dan gunakan pelindung saat bersentuhan dengan deterjen atau bahan kimia. Dengan mengikuti langkah-langkah ini, penyakit eksim dapat dicegah.',
        'Hair Diseases': 'Beberapa cara yang dapat dilakukan untuk mencegah terjadinya penyakit rambut adalah dengan menjaga kebersihan rambut, makan makanan yang sehat, menghindari penggunaan alat pemanas berlebih, menghindari gaya rambut yang menarik rambut secara berlebihan, melindungi rambut dari sinar matahari langsung, menjaga kelembaban rambut, menghindari penggunaan produk rambut yang mengandung bahan kimia berbahaya, dan menjaga kesehatan dengan mengelola stres serta tidur yang cukup.',
        'Insecets Bite and Sting': 'Upaya pencegahan gigitan serangga meliputi: hindari tempat bersemak, minta bantuan profesional untuk menghilangkan sarang tawon atau lebah, jaga kebersihan lingkungan, kenakan pakaian pelindung saat ke tempat bersemak, gunakan mosquito repellent, lakukan 3M (menutup, menguras, mengubur) secara berkala, lakukan fogging jika terdapat banyak sarang nyamuk, semprotkan racun serangga atau pasang kasa anti nyamuk di rumah.',
        'Nail Disease': 'Beberapa cara untuk mencegah terjadinya penyakit pada kuku adalah dengan menghindari menggigit atau menarik ujung kuku tanpa gunting kuku, memotong kuku secara teratur, rutin, dan benar, menggunakan gunting kuku yang tajam, mengoleskan lotion pada kuku, mencuci kaki secara teratur dengan sabun, serta menggunakan alas kaki yang tidak sempit dan bersih.',
        'Pigment Disorder': 'Secara umum, langkah pencegahan yang dapat dilakukan untuk mencegah munculnya pigment disorder adalah dengan menggunakan tabir surya (sunscreen), menerapkan pola hidup sehat, mengonsumsi banyak makanan yang kaya akan antioksidan, dan meminum air putih dalam jumlah yang cukup.',
        'Skin Cancer': 'Cara mencegah timbulnya penyakit kanker melanoma adalah dengan menghindari sinar matahari yang menyengat, menggunakan pakaian yang dapat melindungi tubuh dari paparan sinar matahari, menggunakan tabir surya (sunscreen) dengan SPF minimal 30 sekitar setengah jam sebelum pergi ke luar di bawah sinar matahari, menggunakan ulang (re-apply) tabir surya setiap dua jam, dan menggunakan lip balm dengan tabir surya.',
        'Urticaria Skin Alergey': 'Gatal-gatal yang dialami oleh penderita tidak bisa selalu dapat dicegah. Akan tetapi, kita dapat mencoba untuk mencegah munculnya pemicu biduran dengan menghindari makan makanan tertentu, kontak dengan tanaman, hewan, bahan kimia, ataupun lateks tertentu, suhu dingin, kulit panas dan berkeringat, reaksi terhadap obat, gigitan atau serangan serangga, menggaruk atau menekan kulit dengan pakaian yang ketat, infeksi akibat masalah sistem kekebalan tubuh, serta air atau sinar matahari.',
        'Vascular Tumors': 'Tidak ada cara spesifik untuk mencegah tumor vaskular. Tetapi langkah-langkah umum yang dapat membantu meliputi: menjalani gaya hidup sehat, melindungi kulit dari sinar matahari berlebihan, melakukan pemeriksaan kesehatan rutin, merawat luka dan cedera dengan baik, dan berkonsultasi dengan ahli medis jika memiliki riwayat kelainan pembuluh darah atau faktor risiko.',
        'Vasculitis': 'Penyebab vaskulitis belum diketahui, pencegahannya sulit. Jika sudah di diagnosa vaskulitis, lakukan kontrol rutin ke dokter dan ikuti pengobatan. Dengan begitu, kondisi terpantau dan komplikasi dapat dicegah. Untuk gaya hidup sehat, jaga berat badan ideal, konsumsi makanan bergizi, berolahraga teratur, kelola stress, dan berhenti merokok.',
        'Versicolor': 'Langkah-langkah pencegahan penyakit versicolor meliputi menjaga kebersihan kulit, menjaga kulit tetap kering, menghindari kelembaban berlebihan, dan mengenakan pakaian yang longgar.'
    }

    return preventions.get(disease_name, "Prevention information not available.")


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
