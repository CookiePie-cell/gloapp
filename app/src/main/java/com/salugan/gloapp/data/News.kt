package com.salugan.gloapp.data

data class News(
    val title: String,
    val publishedAt: String,
    val urlToImage: String,
    val url: String,
)

val newsData = arrayListOf(
    News(
        "7 Jenis Jerawat dan Ragam Cara Mengatasinya",
        "05 Juli 2022",
        "https://cdn.hellosehat.com/wp-content/uploads/2019/10/jerawat-di-dahi-700x467.jpg",
        "https://hellosehat.com/penyakit-kulit/jerawat/jenis-jerawat/"
    ),
    News(
        "9 Cara Merawat Kuku agar Cantik, Bisa dengan Bahan Alami!",
        "03 April 2023",
        "https://o-cdf.sirclocdn.com/unsafe/o-cdn-cas.sirclocdn.com/parenting/images/cara-merawat-kuku.width-800.format-webp.webp",
        "https://www.orami.co.id/magazine/cara-merawat-kuku"
    ),
    News(
        "Wajib Dipakai, 5 Manfaat Sunscreen bagi Kesehatan Kulit",
        "27 April 2023",
        "https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2022/07/29061907/Wajib-Dipakai-X-Manfaat-Sunscreen-bagi-Kesehatan-Kulit-03.jpg",
        "https://www.halodoc.com/artikel/wajib-dipakai-5-manfaat-sunscreen-bagi-kesehatan-kulit"
    ),
    News(
        "Mengenal Eksim Kering dan Perawatan Mudah di Rumah",
        "25 Januari 2022",
        "https://res.cloudinary.com/dk0z4ums3/image/upload/v1594038832/attached_image/mengenal-eksim-kering-dan-perawatan-mudah-di-rumah-0-alodokter.jpg",
        "https://www.alodokter.com/mengenal-eksim-kering-dan-perawatan-mudah-di-rumah"
    ),
    News(
        "Ketahui Perbedaan Eksim Basah Vs Eksim Kering",
        "26 Agustus 2022",
        "https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2022/08/26030512/ketahui-perbedaan-eksim-basah-vs-eksim-kering-halodoc.jpg.webp",
        "https://www.halodoc.com/artikel/ketahui-perbedaan-eksim-basah-vs-eksim-kering"
    ),
    News(
        "Panduan E-Prescription Gigitan Serangga",
        "26 Agustus 2022",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkC9M4NJm7I8W5w6QVsoehppCjuJyPQEkaog&usqp=CAU",
        "https://www.alomedika.com/penyakit/kegawatdaruratan-medis/gigitan-serangga/panduan-e-prescription"
    ),
    News(
        "Macam-Macam Alergi Kulit yang Harus Anda Tahu",
        "22 April 2022",
        "https://doktersehat.com/wp-content/uploads/2021/11/macam-macam-alergi-kulit-doktersehat-800x534.jpg",
        "https://doktersehat.com/informasi/penyakit-kulit/macam-macam-alergi-kulit-yang-harus-anda-tahu/"
    ),
    News(
        "14 Jenis Makanan Penyebab Jerawat Muncul Terus Menerus",
        "20 September 2021",
        "https://cdn1.katadata.co.id/media/images/thumb/2021/07/27/Ilustrasi_permasalahan_bekas_jerawat-2021_07_27-08_26_45_c7d25ba8d4ed94f384bf0ccde430019d_960x640_thumb.jpg",
        "https://katadata.co.id/intan/berita/614883ffe26a3/14-jenis-makanan-penyebab-jerawat-muncul-terus-menerus"
    )
)