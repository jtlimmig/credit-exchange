package me.mig.cxb.web.model

import java.util.Date

import me.mig.fission.model.UserObject.UID

case class Transaction( id:Long,
                        transaction_code:Long,
                        service_name:String,
                        migme_user_id:UID,
                        migme_recipient_user_id:UID,
                        service_user_id:String,
                        service_recipient_user_id:String,
                        service_character_server:String,
                        service_character_id:String,
                        package_id:Int,
                        amount_deducted:Int,
                        currency_code:String,
                        transaction_status:String,
                        created_at:Date,
                        updated_at:Date,
                        transaction_type:String,
                        partner_reference:String
                        ) {


}
