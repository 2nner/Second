package com.inner.second.core.util

import com.inner.second.core.model.ContractFormInput
import com.inner.second.core.model.ContractKey
import com.inner.second.core.model.ContractType
import com.inner.second.core.model.DateDuration
import com.inner.second.core.model.response.User
import java.text.NumberFormat
import java.time.LocalDateTime

fun ContractType.getHtmlForm(
    map: Map<String, Any>,
    user: User = User("김한슬", "서울 동작구 사당로 50 (상도동, 숭실대학교 정보과학관) 910호", "010-5752-3665"),
): String {
    val title = map[ContractFormInput.ContractTitle.key] as String
    val duration = map[ContractFormInput.DateDuration.key] as DateDuration

    val content = when (this) {
        is ContractType.IOU -> {
            val opponentName = map[ContractFormInput.OpponentName.key]
            val itemType = buildList {
                listOf(
                    ContractFormInput.ItemName,
                    ContractFormInput.Price
                )
                    .forEach {
                        if (map[it.key] != null) {
                            add(it)
                        }
                    }
            }
                .firstOrNull()
            val item = itemType?.let { input ->
                when (input) {
                    is ContractFormInput.Price -> {
                        NumberFormat.getInstance().format(map[input.key] as Long) + "원"
                    }
                    else -> map[input.key]
                }
            }

            """
                <div class="contract-description">
                    <p><b><u>${opponentName}</u></b>(이하 '채무자'라고 합니다)는 <b><u>${user.name}</u></b>(이하 '채권자'라고 합니다)로부터 아래와 같이 차용하였음을 확인하고, 변제할 것을 확약합니다.</p>
                </div>

                <div class="contract-under">
                    <p><b>- 아 래 -</b></p>
                </div>
                
                <div class="contract-item">
                    ${itemType?.title} : $item
                    </br>
                    기간 : ${duration.startDate.toFormattedString()} ~ ${duration.endDate.toFormattedString()}
                </div>
                
                <div class="contract-date">
                    ${LocalDateTime.now().toFormattedStringInKorean()}
                </div>
                
                <div>
                    <div class="signature-container">
                        <div>
                            <b>채권자 : <u>${user.name}</u></b>
                        </div>
                        <div style="position: relative; display: inline-block;">
                            <span>(서명 또는 인)</span>
                            ${
                                if (map.containsKey(ContractKey.Signature.name)) {
                                    "<img src=\"data:image/png;base64, ${map[ContractKey.Signature.name] as String}\">"
                                } else {
                                    ""
                                }
                            }
                           
                        </div>
                    </div>
                    <div>
                        <b>주소 : <u>${user.address}</u></b>
                    </div>
                    <div>
                        <b>전화번호 : <u>${user.phoneNumber}</u></b>
                    </div>
                </div>
                
                </br>
                
                <div>
                    <div class="signature-container">
                        <div>
                            <b>채권자 : <u>$opponentName</u></b>
                        </div>
                        <div style="position: relative; display: inline-block;">
                            <span>(서명 또는 인)</span>
                        </div>
                    </div>
                    <div>
                        <b>주소 : <u></u></b>
                    </div>
                    <div>
                        <b>전화번호 : <u></u></b>
                    </div>
                </div>
            """
        }

        is ContractType.New -> {
            """
                
            """
        }
    }
        .trimIndent()

    return """
        <!DOCTYPE html>
        <html>
        <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <style>
        body {
            font-family: '맑은 고딕', sans-serif;
            line-height: 1.6;
            margin: 20px;
            font-size: 14px; 
        }
        
        .contract-title {
            font-size: 24px;
            font-weight: bold;
            text-align: center;
            margin-bottom: 20px;
        }

        .contract-description {
            margin-bottom: 30px;
        }
        
        .contract-item {
            font-weight: bold;
            margin-bottom: 50px;
        }
        
        .contract-date {
            font-weight: bold;
            text-align: center;
            margin-bottom: 30px;
        }
        
        .contract-under {
           text-align: center; 
           margin-bottom: 20px;
           margin-top: 20px;
        }
        
        .signature-container {
            display: flex;
            justify-content: flex-start;
        }
        
        .signature-container > div {
            width: fit-content;
            margin-right: 16px;
        }
        
        .signature-container img {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%; 
            height: 100%; 
            object-fit: contain; 
            transform: scale(2);
        }
       
        </style>
        </head>
        <body>

        <div class="contract-title">
            $title
        </div>
        
        $content

        </body>
        </html>
    """.trimIndent()
}