package me.mig.cxb.web.model

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
                        created_at:String,
                        updated_at:String,
                        transaction_type:String,
                        partner_reference:String
                        ) {


}
