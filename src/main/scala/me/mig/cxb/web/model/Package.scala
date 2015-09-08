package me.mig.cxb.web.model

case class Package(id:Long,
                   product_code:String,
                   price:Int,
                   currency_code:String,
                   name:String,
                   description:String,
                   imageUrl:String,
                   discount_percentage:Int,
                   published:Boolean,
                   created_at: String,
                   updated_at: String,
                   allow_gifting:Boolean,
                   content_mime_type:String
                   ) {
}

