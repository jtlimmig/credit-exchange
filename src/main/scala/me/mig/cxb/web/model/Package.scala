package me.mig.cxb.web.model

import java.util.Date

case class Package(id:Long,
                   product_code:String,
                   price:Int,
                   currency_code:String,
                   name:String,
                   description:String,
                   imageUrl:String,
                   discount_percentage:Int,
                   published:Boolean,
                   created_at: Date,
                   updated_at:Date,
                   allow_gifting:Boolean,
                   content_mime_type:String
                   ) {
}

